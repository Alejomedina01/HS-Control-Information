package com.hs.hscontrolinformation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Builder;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3;

import org.springframework.beans.factory.annotation.Value;

@Configuration
public class AWSConfig {

    @Value("${aws.access_key_id}")
    private String accessKeyId;

    @Value("${aws.secret_access_key}")
    private String accessSecretKey;

    @Value("${aws.s3.region}")
    private String region;

    @Bean
    public AmazonS3 getS3Client(){
        BasicAWSCredentials credentials=new BasicAWSCredentials(this.accessKeyId, this.accessSecretKey);
        return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }
}