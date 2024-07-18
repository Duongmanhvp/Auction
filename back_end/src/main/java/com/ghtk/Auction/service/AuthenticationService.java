package com.ghtk.Auction.service;

import com.ghtk.Auction.dto.request.AuthenticationRequest;
import com.ghtk.Auction.dto.request.IntrospectRequest;
import com.ghtk.Auction.dto.request.LogoutRequest;
import com.ghtk.Auction.dto.request.RefreshRequest;
import com.ghtk.Auction.dto.response.AuthenticationResponse;
import com.ghtk.Auction.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {

    IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException;

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
