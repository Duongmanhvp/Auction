package com.ghtk.auction.dto.response.auction;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ghtk.auction.enums.AuctionStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AuctionListResponse {
    Long id;
    Long productId;
    String title;
    String description;
    String image;
    LocalDateTime createdAt;
    LocalDateTime confirmDate;
    LocalDateTime endRegistration;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Long startBid;
    Long pricePerStep;
    Long endBid;

    @Enumerated(EnumType.STRING)
    AuctionStatus status;

    public AuctionListResponse(Long id, Long productId, String title, String description, String image,
                               LocalDateTime createdAt, LocalDateTime confirmDate, LocalDateTime endRegistration,
                               LocalDateTime startTime, LocalDateTime endTime, Long startBid, Long pricePerStep,
                               Long endBid, String status) {
        this.id = id;
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.image = image;
        this.createdAt = createdAt;
        this.confirmDate = confirmDate;
        this.endRegistration = endRegistration;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startBid = startBid;
        this.pricePerStep = pricePerStep;
        this.endBid = endBid;
        this.status = AuctionStatus.valueOf(status); // Convert string to Enum
    }
}
