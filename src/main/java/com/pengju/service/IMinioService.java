package com.pengju.service;

import org.springframework.web.multipart.MultipartFile;

public interface IMinioService {
    String upload(MultipartFile file, String bucketName);
}
