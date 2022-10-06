package com.oj.service;


import com.oj.utils.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    ResponseResult uploadImg(MultipartFile img) throws IOException;

    ResponseResult uploadImgs(MultipartFile[] imgs);

    ResponseResult deleteImgs(String[] imgs);
}
