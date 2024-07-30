package com.ghtk.auction.service.impl;

import com.ghtk.auction.dto.request.auction.AuctionUpdateStatusRequest;
import com.ghtk.auction.entity.Auction;
import com.ghtk.auction.enums.AuctionStatus;
import com.ghtk.auction.exception.NotFoundException;
import com.ghtk.auction.repository.AuctionRepository;
import com.ghtk.auction.scheduler.jobs.UpdateAuctionStatus;
import com.ghtk.auction.service.JobSchedulerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobSchedulerServiceImpl implements JobSchedulerService {
	
	final Scheduler scheduler;
	final AuctionRepository auctionRepository;

//	@Override
//	public void updateClosedAuctionStatus(AuctionUpdateStatusRequest request) throws SchedulerException {
//
//		Auction auction = auctionRepository.findById(request.getAuctionId()).orElseThrow(
//				() -> new NotFoundException("Khong tim thay phien dau gia nao trung voi Id")
//		);
//
//		JobDetail job = newJob(UpdateAuctionStatus.class)
//				.withIdentity(UUID.randomUUID().toString(), "auction-jobs")
//				.usingJobData("auctionId", request.getAuctionId())
//				.usingJobData("auctionStatus", String.valueOf(request.getAuctionStatus()))
//				.storeDurably(true)
//				.build();
//
//		LocalDateTime date = auction.getEndTime();
//		ZoneId zoneId = ZoneId.systemDefault();
//		Date initTime = Date.from(date.atZone(zoneId).toInstant());
//		Trigger triggerInfo = newTrigger()
//				.withIdentity("trigger-update-auction" + auction.getId())
//				.startAt(initTime)
//				.build();
//
//		scheduler.scheduleJob(job, triggerInfo);
//	}
//
//	@Override
//	public void updateInProgressAuctionStatus(AuctionUpdateStatusRequest request) {
//
//
//	}
//
//	@Override
//	public void updateFinishedAuctionStatus(AuctionUpdateStatusRequest request) {
//
//	}
	
	@Override
	public void updateAuctionStatus(AuctionUpdateStatusRequest request) throws SchedulerException {
		Auction auction = auctionRepository.findById(request.getAuctionId()).orElseThrow(
				() -> new NotFoundException("Khong tim thay phien dau gia nao trung voi Id")
		);
		JobDetail job =  buildAuctionJobDetail(UpdateAuctionStatus.class,request);
		Trigger triggerInfo = buildAuctionJobTrigger(UpdateAuctionStatus.class,request);
		
		scheduler.scheduleJob(job,triggerInfo);
	}
	
	private JobDetail buildAuctionJobDetail(Class className, AuctionUpdateStatusRequest request) {
		return newJob(className)
				.withIdentity(className.getName(),"auction-jobs")
				.usingJobData("auctionId", request.getAuctionId())
				.usingJobData("auctionStatus", String.valueOf(request.getAuctionStatus()))
				.storeDurably(true)
				.build();
	}
	
	private Trigger buildAuctionJobTrigger(Class className, AuctionUpdateStatusRequest request) {
		Auction auction = auctionRepository.findById(request.getAuctionId()).orElseThrow(
				() -> new NotFoundException("Khong tim thay phien dau gia nao trung voi Id")
		);
		
		if (auction.getStatus().equals(AuctionStatus.PENDING)
				|| auction.getStatus().equals(AuctionStatus.FINISHED)
				|| auction.getStatus().equals(AuctionStatus.CANCELED)) {
			throw new RuntimeException("Trang thai dau vao khong hop le");
		}
		LocalDateTime date = null;
		if (auction.getStatus().equals(AuctionStatus.OPENING)) {
			 date = auction.getEndRegistration();
		}
		if (auction.getStatus().equals(AuctionStatus.CLOSED)) {
			 date = auction.getStartTime();
		}
		if (auction.getStatus().equals(AuctionStatus.IN_PROGRESS)) {
			 date = auction.getEndTime();
		}
		if (date.isBefore(LocalDateTime.now())) {
			throw new RuntimeException("Yeu cau khong hop le");
		}
		
		ZoneId zoneId = ZoneId.systemDefault();
		Date initTime = Date.from(date.atZone(zoneId).toInstant());
		return newTrigger()
				.withIdentity(className.getName(),"auction-trigger-jobs")
				.startAt(initTime)
				.build();
	}
	
}
