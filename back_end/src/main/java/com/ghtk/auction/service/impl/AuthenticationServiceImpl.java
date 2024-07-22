package com.ghtk.auction.service.impl;

import com.ghtk.auction.component.AuthenticationComponent;
import com.ghtk.auction.dto.request.user.AuthenticationRequest;
import com.ghtk.auction.dto.request.user.IntrospectRequest;
import com.ghtk.auction.dto.request.user.LogoutRequest;
import com.ghtk.auction.dto.request.user.RefreshRequest;
import com.ghtk.auction.dto.response.user.AuthenticationResponse;
import com.ghtk.auction.dto.response.user.IntrospectResponse;
import com.ghtk.auction.entity.BlackListToken;
import com.ghtk.auction.entity.User;
import com.ghtk.auction.exception.AlreadyExistsException;
import com.ghtk.auction.exception.AuthenticatedException;
import com.ghtk.auction.repository.BlackListTokenRepository;
import com.ghtk.auction.repository.UserRepository;
import com.ghtk.auction.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {
    
    UserRepository userRepository;
    BlackListTokenRepository blackListTokenRepository;
    AuthenticationComponent authenticationComponent;
    
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    
    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;
    
    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        return authenticationComponent.introspect(request);
    }
    
    
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            User user = userRepository.findByEmail(request.getEmail());
            if(!user.getIsVerified())
            {
                throw new AuthenticatedException("Account is not verified");
            }
            boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
            if (authenticated) {
                String token = generateToken(user);
                return AuthenticationResponse.builder().token(token).authenticated(true).build();
            }
            else {
                throw new AuthenticatedException("Password is incorrect");
            }
        }
       
        throw new AlreadyExistsException("user with " + request.getEmail() + " does not exists!");
    }
    
    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        //try {
        var signToken = authenticationComponent.verifyToken(request.getToken(), true);
        
        String jit = signToken.getJWTClaimsSet().getJWTID();
        LocalDateTime expiryTime = (signToken.getJWTClaimsSet().getExpirationTime())
              .toInstant()
              .atZone(ZoneId.systemDefault())
              .toLocalDateTime();
        
        
        BlackListToken blackListToken =
              BlackListToken.builder()
                    .token(request.getToken())
                    .createdAt(LocalDateTime.now())
                    .expiryTime(expiryTime)
                    .build();
        
        blackListTokenRepository.save(blackListToken);

//        } catch (AppException exception){
//            log.info("Token already expired");
//        }
    
    }
    
    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = authenticationComponent.verifyToken(request.getToken(), true);
        
        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        LocalDateTime expiryTime = (signedJWT.getJWTClaimsSet().getExpirationTime())
              .toInstant()
              .atZone(ZoneId.systemDefault())
              .toLocalDateTime();
        
        BlackListToken blackListToken =
              BlackListToken.builder().createdAt(LocalDateTime.now()).token(request.getToken()).expiryTime(expiryTime).build();
        
        blackListTokenRepository.save(blackListToken);
        
        var email = signedJWT.getJWTClaimsSet().getSubject();
        
        User user =
              userRepository.findByEmail(email);
//                        .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED)
//                        );
        
        var token = generateToken(user);
        
        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }
    
    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
              .subject(user.getEmail())
              .issuer("Auction")
              .issueTime(new Date())
              .claim("id", user.getId())
              .claim("role", user.getRole())
              .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
              )).build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

//    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
//        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
//
//        SignedJWT signedJWT = SignedJWT.parse(token);
//
//        Date expiryTime = (isRefresh)
//              ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
//              .toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
//              : signedJWT.getJWTClaimsSet().getExpirationTime();
//
//        var verified = signedJWT.verify(verifier);
//
//        if (!(verified && expiryTime.after(new Date()))) {
//            throw new AuthenticatedException("Unauthenticated");
//        }
//
//        if (blackListTokenRepository.existsByToken(token))
//            throw new AuthenticatedException("Unauthenticated");
//
//        return signedJWT;
//    }
    
}
