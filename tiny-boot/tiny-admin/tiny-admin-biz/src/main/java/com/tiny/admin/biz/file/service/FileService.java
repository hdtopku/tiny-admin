package com.tiny.admin.biz.file.service;

import com.tiny.core.web.Result;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Result<String> upload(MultipartFile multipartFile);
}
