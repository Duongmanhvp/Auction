package com.ghtk.auction.service.impl;

import com.ghtk.auction.dto.request.auction.AuctionCreationRequest;
import com.ghtk.auction.dto.response.auction.AuctionCreationResponse;
import com.ghtk.auction.entity.Auction;
import com.ghtk.auction.service.AuctionService;

import java.util.List;

public class AuctionServiceImpl implements AuctionService {
	@Override
	public AuctionCreationResponse addAuction(AuctionCreationRequest request) {
	
		return null;
	}
	
	@Override
	public List<Auction> getMyCreatedAuction() {
		return List.of();
	}
	
	@Override
	public List<Auction> getMyJoinedAuction() {
		return List.of();
	}
	
	@Override
	public Auction getAuctionById(Long auctionId) {
		return null;
	}
	
	@Override
	public void updateStatusAuction(Auction auction) {
	
	}
	
	@Override
	public void deleteAuction(Long auctionId) {
	
	}
	
	@Override
	public void registerJoinAuction() {
	
	}
	
	@Override
	public void joinAuction(Long auctionId) {
	
	}
	
	@Override
	public void bid(Long auctionId, Long bid) {
	
	}
	
	@Override
	public List<Auction> getAuctionList() {
		return List.of();
	}
	
	@Override
	public void confirmAuction(Long auctionId) {
	
	}
	
	@Override
	public void setStatus(Long auctionId) {
	
	}
}
