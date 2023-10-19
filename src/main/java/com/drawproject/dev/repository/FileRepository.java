package com.drawproject.dev.repository;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileRepository {
    List<String> listOfFiles(String path);
    ByteArrayResource downloadFile(String fileName);
    boolean deleteFile(String fileName);
    String uploadFile(MultipartFile file, int id, String typeFile, String categoryFile);
}
