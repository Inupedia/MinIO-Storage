package com.pengju.config;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class MinioUtil {
    @Autowired
    private MinioConfig minioConfig;
    // get MinIO server
    public MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(minioConfig.getEndpoint())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .build();
    }

    public void checkBucketExistence(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // Check whether 'my-bucketname' exists or not.
        // get the connection
        MinioClient minioClient = getMinioClient();
        boolean found =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (found) {
            System.out.println(bucketName + "exist");
        } else {
            System.out.println(bucketName + "does not exist");
        }
    }

    public void createBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean flag = getMinioClient().bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!flag) {
            //if bucket does not exist, create a new one
            getMinioClient().makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
            System.out.println(bucketName + "created successfully");
        } else {
            System.out.println(bucketName + "already exist");
        }
    }


    public void uploadFile(String filePath, InputStream is, String bucketName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // get the connection
        MinioClient minioClient = getMinioClient();
        minioClient.putObject(PutObjectArgs
                .builder()
                .bucket(bucketName)
                .object(filePath)
                .stream(is, is.available(), -1)
                .build());
    }

    public String getFileUrl(String bucketName, String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("response-content-type", "application/json");

        MinioClient minioClient = getMinioClient();

        String url = "";

        url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(objectName)
                        //.expiry(2, TimeUnit.HOURS)
                        //.extraQueryParams(reqParams)
                        .build());

        return url;
    }
}
