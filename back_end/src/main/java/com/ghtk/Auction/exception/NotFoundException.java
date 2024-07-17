package com.ghtk.Auction.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String s, Exception e) {
        super(s,e);
    }
}
