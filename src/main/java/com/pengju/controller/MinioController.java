package com.pengju.controller;

import com.pengju.controller.utils.R;
import com.pengju.service.IMinioService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/iot")
public class MinioController {
    @Autowired
    private IMinioService minioService;

    @ApiOperation(value = "upload file",notes = "upload file")
    @PostMapping("/upload")
    public R upload(@RequestPart("file") MultipartFile file) {
        return new R(true, minioService.upload(file, "test"));
    }
}
