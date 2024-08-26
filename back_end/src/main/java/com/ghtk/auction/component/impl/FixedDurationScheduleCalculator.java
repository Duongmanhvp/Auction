package com.ghtk.auction.component.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ghtk.auction.component.ScheduleCalculator;
import com.ghtk.auction.entity.Auction;
import com.ghtk.auction.scheduler.AuctionSchedule;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class FixedDurationScheduleCalculator implements ScheduleCalculator {
    
    private final Duration registrationDuration;
    private final Duration timeBeforeStart;
    private final Duration preopenDuration;
    private final Duration sessionDuration;

    public FixedDurationScheduleCalculator(
            @Value("${auction.schedule.registration_duration}") String registrationDuration,
            @Value("${auction.schedule.time_before_start}") String timeBeforeStart,
            @Value("${auction.schedule.preopen_duration}") String preopenDuration,
            @Value("${auction.schedule.session_duration}") String sessionDuration) {
        this.registrationDuration = parseDuration(registrationDuration);
        this.timeBeforeStart = parseDuration(timeBeforeStart);
        this.preopenDuration = parseDuration(preopenDuration);
        this.sessionDuration = parseDuration(sessionDuration);
    }

    @Override
    public AuctionSchedule getSchedule(Auction auction) {
        LocalDateTime confirmDate = auction.getConfirmDate();
        LocalDateTime endRegistration = confirmDate.plus(registrationDuration);
        LocalDateTime startTime = confirmDate.plus(timeBeforeStart);
        LocalDateTime preopenTime = startTime.minus(preopenDuration);
        LocalDateTime endTime = startTime.plus(sessionDuration);

        return AuctionSchedule.builder()
                .endRegistration(endRegistration)
                .preopenTime(preopenTime)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    private Duration parseDuration(String duration) {
        // parse from 'hh:mm:ss' format
        String[] parts = duration.split(":");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid duration format: " + duration);
        }
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        if (hours < 0 || minutes < 0 || seconds < 0 || minutes >= 60 || seconds >= 60) {
            throw new IllegalArgumentException("Invalid duration format: " + duration);
        }
        return Duration
                .ofHours(hours)
                .plusMinutes(minutes)
                .plusSeconds(seconds);
    }
  
}
