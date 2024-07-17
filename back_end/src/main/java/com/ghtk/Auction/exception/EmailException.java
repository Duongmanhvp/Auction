package com.ghtk.Auction.exception;

public class EmailException extends RuntimeException {
    public EmailException(String otpServiceUnavailable, Exception e) {
        super(otpServiceUnavailable, e);
    }
}
