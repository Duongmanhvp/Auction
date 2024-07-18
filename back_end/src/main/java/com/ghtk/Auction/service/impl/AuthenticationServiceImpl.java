package com.ghtk.Auction.service.impl;

import com.ghtk.Auction.dto.request.AuthenticationRequest;
import com.ghtk.Auction.dto.request.IntrospectRequest;
import com.ghtk.Auction.dto.request.LogoutRequest;
import com.ghtk.Auction.dto.request.RefreshRequest;
import com.ghtk.Auction.dto.response.AuthenticationResponse;
import com.ghtk.Auction.dto.response.IntrospectReponse;
import com.ghtk.Auction.entity.BlackListToken;
import com.ghtk.Auction.entity.User;
import com.ghtk.Auction.exception.AlreadyExistsException;
import com.ghtk.Auction.exception.AuthenticatedException;
import com.ghtk.Auction.repository.BlackListTokenRepository;
import com.ghtk.Auction.repository.UserRepository;
import com.ghtk.Auction.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;
    
//    @Autowired
//    PasswordEncoder passwordEncoder;
    
    @Override
    public IntrospectReponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        var token = request.getToken();
        boolean isValid = true;

        //try {
            verifyToken(token, false);
        //}
//        catch (AppException e) {
//            isValid = false;
//        }

        return IntrospectReponse.builder().valid(isValid).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if(userRepository.existsByEmail(request.getEmail())) {
            User user =userRepository.findByEmail(request.getEmail());
            boolean authenticated =passwordEncoder.matches(request.getPassword(),user.getPassword());
            if(authenticated) {
                String token = generateToken(user);
                return AuthenticationResponse.builder().token(token).authenticated(true).build();
                
            }
            throw new AuthenticatedException("Password or email is wrong");
        }
        throw new AuthenticatedException("Password or email is wrong");
        
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        //try {
            var signToken = verifyToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            LocalDateTime expiryTime = (signToken.getJWTClaimsSet().getExpirationTime())
                                        .toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDateTime();


            BlackListToken blackListToken =
                    BlackListToken.builder().token(request.getToken()).createAt(LocalDateTime.now()).expiryTime(expiryTime).build();

            blackListTokenRepository.save(blackListToken);

//        } catch (AppException exception){
//            log.info("Token already expired");
//        }

    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        LocalDateTime expiryTime = (signedJWT.getJWTClaimsSet().getExpirationTime())
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime();

        BlackListToken blackListToken =
                BlackListToken.builder().createAt(LocalDateTime.now()).token(request.getToken()).expiryTime(expiryTime).build();

        blackListTokenRepository.save(blackListToken);

        var email = signedJWT.getJWTClaimsSet().getSubject();

        User user =
                userRepository.findByEmail(email);
//                        .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED)
//                        );

        var token = generateToken(user);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    private String generateToken(User user){
        JWSHeader header =new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet =new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("Aution")
                .issueTime(new Date())
                .claim("Role",user.getRole())
                .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                )).build();

        Payload payload=new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject=new JWSObject(header,payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                .toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) {
            throw new AuthenticatedException("Unauthenticated");
        }

//        if (InvalidTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
//            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }
}
