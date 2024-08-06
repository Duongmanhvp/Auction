package com.ghtk.auction.controller.product;

import com.ghtk.auction.dto.response.ApiResponse;
import com.ghtk.auction.service.ImageService;
import com.ghtk.auction.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/images")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ImageController {
    final ImageService imageService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<List<String>>> uploadImages(@RequestParam("files") List<MultipartFile> files) {
        return ResponseEntity.ok(ApiResponse.success(imageService.uploadImages(files)));
    }
    @PostMapping("/test")
    public ResponseEntity<ApiResponse<String>> uploadImages(@RequestParam(value = "files") MultipartFile files) throws IOException {
        return ResponseEntity.ok(ApiResponse.ok(imageService.uploadImage(files)));
    }
}
