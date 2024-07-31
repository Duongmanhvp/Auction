package com.ghtk.auction.service;

import java.util.List;

import org.springframework.security.oauth2.jwt.Jwt;

import com.ghtk.auction.dto.response.auction.AuctionResponse;
import com.ghtk.auction.dto.response.auction.BidResponse;

public interface AuctionRealtimeService {
    List<AuctionResponse> getJoinableNotis(Jwt principal);

    void joinAuction(Jwt principal, Long auctionId);
    void leaveAuction(Jwt principal, Long auctionId);

    Long getCurrentPrice(Jwt principal, Long auctionId);
    BidResponse bid(Jwt principal, Long auctionId, Long bid);

    void openAuctionRoom(Long auctionId); // TODO: quartz 10p truoc khi bat dau
    void startAuction(Long auctionId);
    void endAuction(Long auctionId);
}
