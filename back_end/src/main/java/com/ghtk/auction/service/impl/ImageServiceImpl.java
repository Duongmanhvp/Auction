package com.ghtk.auction.service.impl;

import com.ghtk.auction.component.CloudinaryComponent;
import com.ghtk.auction.exception.UploadException;
import com.ghtk.auction.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final CloudinaryComponent cloudinaryComponent;
    @Override
    public List<String> uploadImages(List<MultipartFile> files) {
        return files.stream().map(file -> {
            try {
                return cloudinaryComponent.uploadFile(file);
            } catch (IOException e) {
                throw new UploadException(e.getMessage());
            }
        }).collect(Collectors.toList());
    }

    @Override
    public String uploadImage(MultipartFile file){
        try {
            return cloudinaryComponent.uploadFile(file);
        } catch (IOException e) {
            throw new UploadException(e.getMessage());
        }
    }
}
