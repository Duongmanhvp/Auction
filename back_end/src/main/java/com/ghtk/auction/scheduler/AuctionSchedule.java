package com.ghtk.auction.scheduler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuctionSchedule {
    private LocalDateTime endRegistration;
    private LocalDateTime preopenTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
