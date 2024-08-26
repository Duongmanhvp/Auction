package com.ghtk.auction.service.impl;

import com.ghtk.auction.entity.Auction;
import com.ghtk.auction.repository.AuctionRepository;
import com.ghtk.auction.scheduler.AuctionSchedule;
import com.ghtk.auction.scheduler.jobs.StartAuction;
import com.ghtk.auction.scheduler.jobs.CloseAuction;
import com.ghtk.auction.scheduler.jobs.EndAuction;
import com.ghtk.auction.scheduler.jobs.OpenAuction;
import com.ghtk.auction.service.JobSchedulerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobSchedulerServiceImpl implements JobSchedulerService {
	
	final Scheduler scheduler;
	final AuctionRepository auctionRepository;
	
	@Override
	public void scheduleAuction(Auction auction, AuctionSchedule schedule) throws SchedulerException {
    LocalDateTime closeTime = schedule.getEndRegistration();
    LocalDateTime openTime = schedule.getPreopenTime();
    LocalDateTime startTime = schedule.getStartTime();
    LocalDateTime endTime = schedule.getEndTime();

		// Lên lịch chuyển sang trạng thái CLOSED
		scheduleUpdate(auction, CloseAuction.class, closeTime);

    // Lên lịch mở phòng đấu giá
    scheduleUpdate(auction, OpenAuction.class, openTime);
		
		// Lên lịch chuyển sang trạng thái IN_PROGRESS
		scheduleUpdate(auction, StartAuction.class, startTime);
		
		// Lên lịch chuyển sang trạng thái FINISHED
		scheduleUpdate(auction, EndAuction.class, endTime);
	}

  private void scheduleUpdate(Auction auction, Class<? extends Job> jobClass, LocalDateTime scheduledTime) throws SchedulerException {
    JobDetail job = buildAuctionJobDetail(auction.getId(), jobClass, scheduledTime);
    Trigger trigger = buildAuctionJobTrigger(auction.getId(), jobClass, scheduledTime);
    scheduler.scheduleJob(job, trigger);
  }

	// @Override
	// public void scheduleRedisAuction(Auction auction, AuctionSchedule schedule) throws SchedulerException {
	// 	LocalDateTime openTime = schedule.getPreopenTime();
  //   LocalDateTime startTime = schedule.getStartTime();
  //   LocalDateTime endTime = schedule.getEndTime();
		
	// 	// Lên lịch thêm vào redis OpenAuction : preparing
	// 	scheduleRedisOpenAuction(auction, openTime);
		
	// 	// Lên lịch thêm vào redis thông tin Auction khi bắt đầu start
	// 	scheduleRedisStartAuction(auction, startTime);

  //   // Lên lịch thêm vào redis thông tin Auction khi kết thúc
  //   scheduleRedisEndAuction(auction, endTime);
	// }
	// private void scheduleStatusUpdate(Auction auction, AuctionStatus newStatus, LocalDateTime scheduledTime) throws SchedulerException {
	// 	AuctionUpdateStatusRequest request = new AuctionUpdateStatusRequest();
	// 	request.setAuctionId(auction.getId());
	// 	request.setAuctionStatus(newStatus);
		
	// 	JobDetail job = buildAuctionJobDetail(UpdateAuctionStatus.class, request);
	// 	Trigger trigger = buildAuctionJobTrigger(UpdateAuctionStatus.class, request, scheduledTime);
		
	// 	scheduler.scheduleJob(job, trigger);
	// }
	
	// private  void scheduleRedisOpenAuction(Auction auction, LocalDateTime scheduledTime) throws SchedulerException {
	// 	Long auctionId = auction.getId();
	// 	JobDetail job = buildRedisAuctionJobDetail(OpenAuction.class,auctionId);
	// 	Trigger trigger = buildRedisAuctionJobTrigger(OpenAuction.class,auctionId,scheduledTime);
		
	// 	scheduler.scheduleJob(job, trigger);
	// }
	
	// private void scheduleRedisStartAuction(Auction auction, LocalDateTime scheduledTime) throws SchedulerException {
	// 	Long auctionId = auction.getId();
	// 	JobDetail job = buildRedisAuctionJobDetail(StartAuction.class,auctionId);
	// 	Trigger trigger = buildRedisAuctionJobTrigger(StartAuction.class,auctionId,scheduledTime);
		
	// 	scheduler.scheduleJob(job, trigger);
	// }

	// private void scheduleRedisEndAuction(Auction auction, LocalDateTime scheduledTime) throws SchedulerException {
	// 	Long auctionId = auction.getId();
	// 	JobDetail job = buildRedisAuctionJobDetail(EndAuction.class,auctionId);
	// 	Trigger trigger = buildRedisAuctionJobTrigger(EndAuction.class,auctionId,scheduledTime);
		
	// 	scheduler.scheduleJob(job, trigger);
	// }
	
	private JobDetail buildAuctionJobDetail(Long auctionId, Class<? extends Job> jobClass, LocalDateTime scheduledTime) {
		return JobBuilder.newJob(jobClass)
				.withIdentity(jobClass.getName() + "-" + auctionId, "auction-update-jobs")
				.usingJobData("auctionId", auctionId)
				.storeDurably(true)
				.build();
	}
	
	private Trigger buildAuctionJobTrigger(Long auctionId, Class<? extends Job> jobClass, LocalDateTime scheduledTime) {
		ZoneId zoneId = ZoneId.systemDefault();
		Date initTime = Date.from(scheduledTime.atZone(zoneId).toInstant());
		return TriggerBuilder.newTrigger()
				.withIdentity(jobClass + "-" + auctionId, "auction-update-triggers")
				.startAt(initTime)
				.build();
	}

//	private JobDetail buildAuctionJobDetail(Class className, AuctionUpdateStatusRequest request) {
//		return newJob(className)
//				.withIdentity(className.getName(),"auction-jobs")
//				.usingJobData("auctionId", request.getAuctionId())
//				.usingJobData("auctionStatus", String.valueOf(request.getAuctionStatus()))
//				.storeDurably(true)
//				.build();
//	}
//
//	private Trigger buildAuctionJobTrigger(Class className, AuctionUpdateStatusRequest request) {
//		Auction auction = auctionRepository.findById(request.getAuctionId()).orElseThrow(
//				() -> new NotFoundException("Khong tim thay phien dau gia nao trung voi Id")
//		);
//
//		if (auction.getStatus().equals(AuctionStatus.PENDING)
//				|| auction.getStatus().equals(AuctionStatus.FINISHED)
//				|| auction.getStatus().equals(AuctionStatus.CANCELED)) {
//			throw new RuntimeException("Trang thai dau vao khong hop le");
//		}
//		LocalDateTime date = null;
//		if (auction.getStatus().equals(AuctionStatus.OPENING)) {
//			 date = auction.getEndRegistration();
//		}
//		if (auction.getStatus().equals(AuctionStatus.CLOSED)) {
//			 date = auction.getStartTime();
//		}
//		if (auction.getStatus().equals(AuctionStatus.IN_PROGRESS)) {
//			 date = auction.getEndTime();
//		}
//		if (date.isBefore(LocalDateTime.now())) {
//			throw new RuntimeException("Yeu cau khong hop le");
//		}
//
//		ZoneId zoneId = ZoneId.systemDefault();
//		Date initTime = Date.from(date.atZone(zoneId).toInstant());
//		return newTrigger()
//				.withIdentity(className.getName(),"auction-trigger-jobs")
//				.startAt(initTime)
//				.build();
//	}
}
