package com.ghtk.auction.service;


import com.ghtk.auction.dto.request.auction.AuctionCreationRequest;
import com.ghtk.auction.dto.response.auction.AuctionCreationResponse;
import com.ghtk.auction.entity.Auction;

import java.util.List;

public interface AuctionService {

    AuctionCreationResponse addAuction(AuctionCreationRequest request);
    List<Auction> getMyCreatedAuction();
    List<Auction> getMyJoinedAuction();
    Auction getAuctionById(Long auctionId);
    void updateStatusAuction(Auction auction);
    void deleteAuction(Long auctionId);
    void registerJoinAuction();
    void joinAuction(Long auctionId);
    void bid(Long auctionId, Long bid);
    
    // ADMIN
    List<Auction> getAuctionList();
    void confirmAuction(Long auctionId);
    void setStatus(Long auctionId);
    
    
}
