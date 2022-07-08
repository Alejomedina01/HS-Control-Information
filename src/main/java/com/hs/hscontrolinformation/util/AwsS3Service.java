package com.hs.hscontrolinformation.util;

import java.io.InputStream;
import java.util.List;

import com.hs.hscontrolinformation.domain.Document;
import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {
    void uploadFile(MultipartFile file,String newFileName);
    List<String> getObjectsFroms3();
    InputStream downLoadFile(String keyName);
    void deleteFile(String KeyName);
    void generatePresignedUrl(Document document);

}
