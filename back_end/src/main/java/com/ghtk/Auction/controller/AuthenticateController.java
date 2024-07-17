package com.ghtk.Auction.controller;

import com.ghtk.Auction.dto.request.AuthenticationRequest;
import com.ghtk.Auction.dto.request.IntrospectRequest;
import com.ghtk.Auction.dto.request.LogoutRequest;
import com.ghtk.Auction.dto.request.RefreshRequest;
import com.ghtk.Auction.dto.response.AuthenticationResponse;
import com.ghtk.Auction.dto.response.IntrospectReponse;
import com.ghtk.Auction.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class AuthenticateController {
    AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/introspect")
    ResponseEntity<IntrospectReponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {

        return ResponseEntity.ok( authenticationService.introspect(request));
    }

    @PostMapping("/refresh")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    ResponseEntity<String> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ResponseEntity.ok("logout successfully");
    }

}
