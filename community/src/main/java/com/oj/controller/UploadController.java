package com.oj.controller;


import com.oj.service.UploadService;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img) throws IOException {

        return uploadService.uploadImg(img);
    }

    @PostMapping("/upload/articlePictures")
    public ResponseResult uploadImgs(MultipartFile[] img) throws IOException {

        return uploadService.uploadImgs(img);
    }
    @DeleteMapping("/upload/delete/articlePictures")
    public ResponseResult deleteImgs(@RequestBody String[] imgs) {
        return  uploadService.deleteImgs(imgs);
    }
}
