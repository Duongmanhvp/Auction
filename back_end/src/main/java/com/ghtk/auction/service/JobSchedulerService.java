package com.ghtk.auction.service;

import com.ghtk.auction.dto.request.auction.AuctionUpdateStatusRequest;
import org.quartz.SchedulerException;

public interface JobSchedulerService {
	
//	public void updateClosedAuctionStatus(AuctionUpdateStatusRequest request) throws SchedulerException;
//
//	public void updateInProgressAuctionStatus(AuctionUpdateStatusRequest request);
//
//	public void updateFinishedAuctionStatus(AuctionUpdateStatusRequest request);
	
	public void updateAuctionStatus(AuctionUpdateStatusRequest request) throws SchedulerException;
	
}
