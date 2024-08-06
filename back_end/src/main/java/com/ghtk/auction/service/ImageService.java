package com.ghtk.auction.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<String> uploadImages(List<MultipartFile> files);
    String uploadImage(MultipartFile file) throws IOException;
}
