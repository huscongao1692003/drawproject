package com.drawproject.dev.service;

import com.drawproject.dev.repository.FileRepository;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FileService implements FileRepository {
    @Value("${gcp.bucket.name}")
    private String bucketName;

    @Autowired
    Storage storage;

    @Override
    public List<String> listOfFiles(String path) {

        List<String> list = new ArrayList<>();
        Page<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix("image/artworks/"));
        //Page<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(path));
        for (Blob blob : blobs.iterateAll()) {
            list.add(blob.getMediaLink());
        }
        return list;
    }

    @Override
    public ByteArrayResource downloadFile(String fileName) {

        Blob blob = storage.get(bucketName, fileName);
        ByteArrayResource resource = new ByteArrayResource(
                blob.getContent());

        return resource;
    }

    @Override
    public boolean deleteFile(String fileName) {

        Blob blob = storage.get(bucketName, fileName);

        return blob.delete();
    }

    @Override
    public void uploadFile(MultipartFile file, String path, String fileName) throws IOException {

        BlobId blobId = BlobId.of(bucketName, "image/artworks/" + file.getOriginalFilename());
        //BlobId blobId = BlobId.of(bucketName, path + fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .setAcl(Collections.singletonList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)))
                .build();
        Blob blob = storage.create(blobInfo, file.getBytes());
    }
}
