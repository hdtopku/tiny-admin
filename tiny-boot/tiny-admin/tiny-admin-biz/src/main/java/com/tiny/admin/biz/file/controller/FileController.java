package com.tiny.admin.biz.file.controller;

import com.tiny.admin.biz.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Created by lxh at 2024-09-02 15:40:56
 */
@RestController
@RequestMapping("file")
@Log4j2
public class FileController {
    @Resource
    private FileService fileService;

    @PostMapping(path="/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "上传文件")
    public Object upload(@RequestParam("file") MultipartFile multipartFile) {
        return fileService.upload(multipartFile);
    }
}
