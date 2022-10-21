package com.pengju;

import com.pengju.config.MinioUtil;
import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private MinioUtil minioUtil;

    @Test
    void contextLoads() {
        System.out.println("Hello World!");
    }

    @Test
    void checkBucketExistance() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioUtil.checkBucketExistence("test");
    }

    @Test
    void testCreateBucket() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioUtil.createBucket("test3");
    }

}
