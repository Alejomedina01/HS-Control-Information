package com.hs.hscontrolinformation.services;

import com.amazonaws.services.s3.model.*;
import com.hs.hscontrolinformation.domain.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.net.URL;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.HttpMethod;
import com.hs.hscontrolinformation.util.AwsS3Service;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;


@Service
public class AwsServiceImpl implements AwsS3Service{

    private static final Logger logger= LoggerFactory.getLogger(AwsServiceImpl.class);

    @Autowired
    private AmazonS3 amazonS3;
    
    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Override
    public void uploadFile(MultipartFile file,String newFileName) {
        File newFile=new File(file.getOriginalFilename());
        try {
            FileOutputStream stream=new FileOutputStream(newFile);
            stream.write(file.getBytes());
            logger.info("subiendo archivo con el nombre:"+newFileName);
            PutObjectRequest request=new PutObjectRequest(bucketName,newFileName , newFile);
            amazonS3.putObject(request);
            stream.close();
            newFile.delete();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
    @Override
    public void generatePresignedUrl(Document document) {
        try {
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = Instant.now().toEpochMilli();
            expTimeMillis += 1000 * 60 * 360;
            expiration.setTime(expTimeMillis);
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, document.getFullName())
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
            document.setPresignedUrl(url.toString());
            document.setExpirationDate(String.valueOf(expTimeMillis));
            System.out.println("la URL prefirmada que se genero : " + url.toString());
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<String> getObjectsFroms3() {
        ListObjectsV2Result result= amazonS3.listObjectsV2(bucketName);
        List<S3ObjectSummary> objects=result.getObjectSummaries();
        List<String> list=objects.stream().map(item -> {
            return item.getKey();
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public InputStream downLoadFile(String keyName) {
        S3Object object=amazonS3.getObject(bucketName, keyName);
        return object.getObjectContent();
    }
    @Override
    public void deleteFile(String KeyName) {
        amazonS3.deleteObject(bucketName, KeyName);
    }
    
}
