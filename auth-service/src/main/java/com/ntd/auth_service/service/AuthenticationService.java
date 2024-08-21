package com.ntd.auth_service.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.ntd.auth_service.dto.request.*;
import com.ntd.auth_service.dto.response.AuthenticationResponse;
import com.ntd.auth_service.dto.response.IntrospectResponse;
import com.ntd.auth_service.dto.response.UserResponse;
import com.ntd.auth_service.entity.InvalidatedToken;
import com.ntd.auth_service.entity.Role;
import com.ntd.auth_service.entity.User;
import com.ntd.auth_service.exception.AppException;
import com.ntd.auth_service.exception.ErrorCode;
import com.ntd.auth_service.repository.IInvalidatedTokenRepository;
import com.ntd.auth_service.repository.IUserRepository;
import com.ntd.auth_service.repository.httpclient.OutboundIdentityClient;
import com.ntd.auth_service.repository.httpclient.OutboundUserClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserRepository userRepository;
    private final IInvalidatedTokenRepository invalidatedTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final OutboundIdentityClient outboundIdentityClient;
    private final OutboundUserClient outboundUserClient;

    @Value("${jwt.signerKey}")
    protected String signerKey;

    @Value("${jwt.valid-duration}")
    protected long validDuration;

    @Value("${jwt.refreshable-duration}")
    protected long refreshableDuration;

    @Value("${outbound.client-id}")
    protected String clientId;

    @Value("${outbound.client-secret}")
    protected String clientSecret;

    @Value("${outbound.redirect-uri}")
    protected String redirectUri;

    protected String grantType = "authorization_code";

    @Transactional
    public UserResponse register(UserCreateRequest request) {
        //        log.info("Create user service");
        //
        //        if (isUserExistsByUsername(request.getUsername())) {
        //            throw new AppException(ErrorCode.USER_EXISTED);
        //        }

        User user = modelMapper.map(request, User.class);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(
                Role.builder().name(com.ntd.auth_service.enums.Role.USER.name()).build());
        user.setRoles(roles);

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return modelMapper.map(user, UserResponse.class);
    }

    //    Login and create token
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(user);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    private String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("ntd.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(validDuration, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot generate token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());

                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
                }
            });
        }
        return stringJoiner.toString();
    }

    //    Refresh Token
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true);

        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

        invalidatedTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();

        var user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var token = generateToken(user);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    //    Logout
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException e) {
            log.info("Token already expired");
        }
    }

    //    Introspect token
    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    //    Verify Token
    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(refreshableDuration, ChronoUnit.SECONDS)
                        .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    public AuthenticationResponse outboundAuthenticate(String code) {
        //        Get access token from google
        var response = outboundIdentityClient.exchangeToken(ExchangeTokenRequest.builder()
                .code(code)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .grantType(grantType)
                .build());

        log.info("TOKEN_RESPONSE {}", response);

        //        Get user info
        var userInfo = outboundUserClient.exchangeToken("json", response.getAccessToken());

        log.info("User info {}", userInfo);

        Set<Role> roles = new HashSet<>();
        roles.add(
                Role.builder().name(com.ntd.auth_service.enums.Role.USER.name()).build());

        //        Onboard user
        var user = userRepository
                .findByUsername(userInfo.getEmail())
                .orElseGet(() -> userRepository.save(User.builder()
                        .username(userInfo.getEmail())
                        .firstName(userInfo.getGivenName())
                        .lastName(userInfo.getFamilyName())
                        .roles(roles)
                        .build()));

        //        Generate token
        var token = generateToken(user);

        return AuthenticationResponse.builder().authenticated(true).token(token).build();
    }

    public void createPassword(PasswordCreateRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (StringUtils.hasText(user.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_EXISTED);
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }
}
