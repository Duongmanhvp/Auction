package com.ghtk.auction.service.impl;

import com.ghtk.auction.dto.request.comment.CommentFilter;
import com.ghtk.auction.dto.response.comment.CommentResponse;
import com.ghtk.auction.service.CommentService;

import java.util.List;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
  @Override
  public CommentResponse addComment(Jwt principal, Long auctionId, String message) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'addComment'");
  }

  @Override
  public List<CommentResponse> getComments(Jwt principal, Long auctionId, CommentFilter filter) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getComments'");
  }

  @Override
  public void deleteComment() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteComment'");
  }
}
