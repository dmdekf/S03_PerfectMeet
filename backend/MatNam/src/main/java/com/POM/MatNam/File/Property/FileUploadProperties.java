package com.POM.MatNam.File.Property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
@Service
@ConfigurationProperties(prefix="file")
public class FileUploadProperties {
    private String uploadDir;
 
    public String getUploadDir() {
        return uploadDir;
    }
 
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
