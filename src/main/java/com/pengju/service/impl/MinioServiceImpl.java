package com.pengju.service.impl;

import com.pengju.config.MinioUtil;
import com.pengju.service.IMinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class MinioServiceImpl implements IMinioService {
    @Autowired
    private MinioUtil minioUtil;

    @Override
    public String upload(MultipartFile file, String bucketName) {
        if (file.isEmpty() || file.getSize() == 0) {
            return "uploaded file is null";
        }
        try {
            //get the input stream
            InputStream is = file.getInputStream();
            //get the file name
            String fileName = file.getOriginalFilename();
            int lastIndexOf = fileName.lastIndexOf(".");
            //get the file extension like .jpg
            String suffix = fileName.substring(lastIndexOf);

            String uuid = UUID.randomUUID().toString().replace("-", "");
            int yyyy = LocalDate.now().getYear();
            int MM = LocalDate.now().getMonthValue();
            int dd = LocalDate.now().getDayOfMonth();

            String newName = yyyy + "/" + MM + "/" + dd + "/" + uuid + suffix;

            //upload file to Minio bucket
            minioUtil.uploadFile(newName, is, bucketName);
            is.close();

            //return the URL
            String presignedUrl = minioUtil.getFileUrl(bucketName, newName);
            String url = presignedUrl.substring(0, presignedUrl.lastIndexOf('?'));
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
    }
}
