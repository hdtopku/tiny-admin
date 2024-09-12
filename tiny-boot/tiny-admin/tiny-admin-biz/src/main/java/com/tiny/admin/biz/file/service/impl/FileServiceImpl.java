package com.tiny.admin.biz.file.service.impl;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.tiny.admin.biz.file.service.FileService;
import com.tiny.core.web.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Created by lxh at 2024-09-02 15:46:00
 */
@Service
public class FileServiceImpl implements FileService {
    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/%s?alt=media";
    @Resource
    private Storage storage;
    @Override
    public Result<String> upload(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();                        // to get original file name
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.
            BlobInfo blobInfo = BlobInfo.newBuilder("tiny-blog-1a77f.appspot.com", fileName)
                .setContentType(multipartFile.getContentType())  // 设置文件的内容类型
                .build();
            storage.create(blobInfo, multipartFile.getBytes());
            return Result.success(String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8)));
        } catch (Exception e) {
            return Result.failure(500, "Unsuccessfully Uploaded!"+e);
        }
    }
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
