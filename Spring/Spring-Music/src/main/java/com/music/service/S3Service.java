package com.music.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.io.IOException;

@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    private final String bucketName = "spring"; // Your bucket name

    public String uploadFile(String key, byte[] fileBytes) {
        // Upload the file to S3
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build(), RequestBody.fromBytes(fileBytes));

        // Return the S3 URL
        return String.format("https://%s/%s/%s", "storage.c2.liara.space", bucketName, key);
    }

    public void deleteFile(String key) {
        // Delete the file from S3
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build());
    }
}
