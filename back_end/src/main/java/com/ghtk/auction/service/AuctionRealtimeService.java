package com.ghtk.auction.service;

import java.util.List;

import org.springframework.security.oauth2.jwt.Jwt;

import com.ghtk.auction.dto.stomp.BidMessage;
import com.ghtk.auction.entity.Auction;

public interface AuctionRealtimeService {
    List<Auction> getJoinableNotis(Long userId);

    void checkControlJoin(Long userId, Long auctionId);
    void checkNotifJoin(Long userId, Long auctionId);
    void checkBidJoin(Long userId, Long auctionId);
    void checkCommentJoin(Long userId, Long auctionId);

    void joinAuction(Long userId, Long auctionId);
    void leaveAuction(Long userId, Long auctionId);

    void checkBidding(Long userId, Long auctionId);
    void checkCommenting(Long userId, Long auctionId);
    void checkNotifying(Long userId, Long auctionId);

    Long getCurrentPrice(Long userId, Long auctionId);
    BidMessage bid(Long userId, Long auctionId, Long bid);

    void openAuctionRoom(Long auctionId); // TODO: quartz 10p truoc khi bat dau
    void startAuction(Long auctionId);
    void endAuction(Long auctionId);
}
