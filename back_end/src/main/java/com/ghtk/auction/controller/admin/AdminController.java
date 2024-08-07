package com.ghtk.auction.controller.admin;


import com.ghtk.auction.dto.response.ApiResponse;
import com.ghtk.auction.service.StompService;
import com.ghtk.auction.service.AuctionRealtimeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AdminController {
  final AuctionRealtimeService auctionRealtimeService;
  final StompService stompService;
	
  @PostMapping("/error")
  @PreAuthorize("hasRole('ADMIN')")
  public ApiResponse<Void> error(@RequestBody String content) {
    stompService.broadcastError(content);
    return ApiResponse.success(null);
  }

  @PostMapping("/{auctionId}/open")
  @PreAuthorize("hasRole('ADMIN')")
  public ApiResponse<Void> openAuction(
      @PathVariable Long auctionId
  ) {
    auctionRealtimeService.openAuctionRoom(auctionId);
    return ApiResponse.success(null);
  }

  @PostMapping("/{auctionId}/start")
  @PreAuthorize("hasRole('ADMIN')")
  public ApiResponse<Void> startAuction(
      @PathVariable Long auctionId
  ) {
    auctionRealtimeService.startAuction(auctionId);
    return ApiResponse.success(null);
  }

  @PostMapping("/{auctionId}/end")
  @PreAuthorize("hasRole('ADMIN')")
  public ApiResponse<Void> endAuction(
      @PathVariable Long auctionId
  ) {
    auctionRealtimeService.endAuction(auctionId);
    return ApiResponse.success(null);
  }
}
