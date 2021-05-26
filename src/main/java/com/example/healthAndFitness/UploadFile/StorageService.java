package com.example.healthAndFitness.UploadFile;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();
    String store(MultipartFile file , String FileName);
    Path load(String filename);
    void deleteAll();
    Resource loadAsResource(String filename);



}