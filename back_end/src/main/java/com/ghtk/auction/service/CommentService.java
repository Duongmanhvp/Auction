package com.ghtk.auction.service;


import java.util.List;

import org.springframework.security.oauth2.jwt.Jwt;

import com.ghtk.auction.dto.request.comment.CommentFilter;
import com.ghtk.auction.dto.response.comment.CommentResponse;

public interface CommentService {
    CommentResponse addComment(Jwt principal, Long auctionId, String message);
    // TODO: co nen xoa ko
    void deleteComment();

    List<CommentResponse> getComments(Jwt principal, Long auctionId, CommentFilter filter);
}
