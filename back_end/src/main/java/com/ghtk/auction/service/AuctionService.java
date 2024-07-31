package com.ghtk.auction.service;


import com.ghtk.auction.dto.request.auction.AuctionCreationRequest;
import com.ghtk.auction.dto.request.auction.AuctionUpdateStatusRequest;
import com.ghtk.auction.dto.response.auction.AuctionCreationResponse;
import com.ghtk.auction.dto.response.auction.AuctionResponse;
import com.ghtk.auction.dto.response.user.PageResponse;
import com.ghtk.auction.entity.Auction;
import com.ghtk.auction.entity.UserAuction;
import org.quartz.SchedulerException;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface AuctionService {

    AuctionCreationResponse addAuction(Jwt principal, AuctionCreationRequest request);
    List<AuctionResponse> getMyCreatedAuction(Jwt principal);
    List<Auction> getMyJoinedAuction(Jwt principal);
    Auction getAuctionById(Long auctionId);
    Auction deleteAuction(Jwt principal, Long auctionId);
    UserAuction registerJoinAuction(Jwt principal, Long auctionId);
    void joinAuction(Jwt principal, Long auctionId);
    void bid(Long auctionId, Long bid);
    
    // ADMIN
    PageResponse<Auction> getAllList(int pageNo, int pageSize, String sortBy, String sortDir);
    
    // thay doi trang thai PENDING -> OPENING,
    // them fiels confirm_date, end_regis, start_time, end_time
    // neu tu choi thi xoa auction.
    Auction confirmAuction(Long auctionId) throws SchedulerException;
    void updateStatus(AuctionUpdateStatusRequest request);
}
