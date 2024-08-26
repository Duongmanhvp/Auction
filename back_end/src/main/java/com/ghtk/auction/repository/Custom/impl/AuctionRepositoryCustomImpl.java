package com.ghtk.auction.repository.Custom.impl;

import com.ghtk.auction.dto.response.auction.AuctionListResponse;
import com.ghtk.auction.enums.AuctionStatus;
import com.ghtk.auction.repository.Custom.AuctionRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class AuctionRepositoryCustomImpl implements AuctionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AuctionListResponse> getAllAuctionListResponse(Pageable pageable, AuctionStatus status) {
        StringBuilder sql = new StringBuilder("SELECT a.id, " +
              " a.product_id as productId, " +
              " a.title as title, " +
              " a.description as description, " +
              " p.image as image, " +
              " a.created_at as createdAt," +
                " a.confirm_date as confirmDate, " +
              " a.end_registration as endRegistration, " +
              " a.start_time as startTime ," +
              "  a.end_time as endTime, a.start_bid as startBid, a.price_per_step as pricePerStep, " +
                "a.end_bid as endBid, a.status as status FROM auction a ");
        StringBuilder where = new StringBuilder("WHERE 1=1 ");
        sql.append("join product p on p.id = a.product_id ");
        if(status != null) {
            where.append("AND a.status = " + "'" + status.toString() + "' ");
        }
        where.append("ORDER BY a.id DESC ");
        sql.append(where);
        sql.append(" LIMIT ").append(pageable.getPageSize()).append("\n");
        sql.append(" OFFSET ").append(pageable.getOffset());
        Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);
        List<Tuple> results = query.getResultList();
        List<AuctionListResponse> responses = new ArrayList<>();
        
        return results.stream().map(result -> {
            AuctionListResponse response = new AuctionListResponse();
            response.setId(result.get("id", Long.class));
            response.setProduct_id(result.get("productId", Long.class));
            response.setTitle(result.get("title", String.class));
            response.setDescription(result.get("description", String.class));
            response.setImage(result.get("image", String.class));
            response.setCreatedAt(result.get("createdAt", Timestamp.class).toLocalDateTime());
//            response.setConfirmDate(result.get("confirmDate", java.sql.Timestamp.class).toLocalDateTime());
//            response.setEndRegistration(result.get("endRegistration", java.sql.Timestamp.class).toLocalDateTime());
//            response.setStartTime(result.get("startTime", java.sql.Timestamp.class).toLocalDateTime());
//            response.setEndTime(result.get("endTime", java.sql.Timestamp.class).toLocalDateTime());
            response.setConfirmDate(Optional.ofNullable(result.get("confirmDate", Timestamp.class))
                  .map(Timestamp::toLocalDateTime)
                  .orElse(null));
            response.setEndRegistration(Optional.ofNullable(result.get("endRegistration", Timestamp.class))
                  .map(Timestamp::toLocalDateTime)
                  .orElse(null));
            response.setStartTime(Optional.ofNullable(result.get("startTime", Timestamp.class))
                  .map(Timestamp::toLocalDateTime)
                  .orElse(null));
            response.setEndTime(Optional.ofNullable(result.get("endTime", Timestamp.class))
                  .map(Timestamp::toLocalDateTime)
                  .orElse(null));
            response.setStartBid(result.get("startBid", Long.class));
            response.setPricePerStep(result.get("pricePerStep", Long.class));
            response.setEndBid(result.get("endBid", Long.class));
            response.setStatus(AuctionStatus.valueOf(result.get("status", String.class)));
            return response;
        }).toList();
    }
}
