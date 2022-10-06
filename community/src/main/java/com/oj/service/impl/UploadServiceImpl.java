package com.oj.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.oj.constants.UploadConstants;
import com.oj.enums.ResultCode;
import com.oj.exceptions.SystemException;
import com.oj.pojo.vo.ArticleImgVo;
import com.oj.service.UploadService;
import com.oj.utils.PathUtils;
import com.oj.utils.ResponseResult;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service("UploadService")
@Data
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {

    private String bucketName;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;


    @Override
    public ResponseResult uploadImg(MultipartFile img) throws IOException {
        //判断文件类型或者文件大小
        String originalFilename = img.getOriginalFilename();
        if (!originalFilename.endsWith(".jpg")) {
            throw new SystemException(ResultCode.FILE_TYPE_ERROR);
        }

        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOss(img, filePath);
        //弱或判断通过上传文件到oss
        return ResponseResult.okResult(url);
    }

    @Override
    public ResponseResult uploadImgs(MultipartFile[] imgs) {

        if (Objects.isNull(imgs)) {
            throw new RuntimeException();
        }
        ArticleImgVo articleImgVo = null;
        String originalFilename = null;
        String filePath = null;
        String url = null;
        List<ArticleImgVo> urls = new ArrayList<>();

        for (int i =0; i < imgs.length; i ++) {
             articleImgVo = new ArticleImgVo();
             originalFilename = imgs[i].getOriginalFilename();
             filePath = PathUtils.generateFilePath(originalFilename);
            try {
                url = uploadOss(imgs[i], filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            articleImgVo.setPos(i+1);
            articleImgVo.setUrl(url);
            urls.add(articleImgVo);
        }


//        List<String> urls = Arrays.stream(imgs).map(img -> {
//
//
//            String originalFilename = img.getOriginalFilename();
//            String filePath = PathUtils.generateFilePath(originalFilename);
//            String url = null;
//            try {
//                url = uploadOss(img, filePath);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return url;
//        }).collect(Collectors.toList());

        return new ResponseResult<List<ArticleImgVo>>().ok(urls);
    }

    @Override
    public ResponseResult deleteImgs(String[] imgs) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = sdf.format(new Date());
        for (String img : imgs) {
            String path = new StringBuilder().append(datePath).append(img).toString();
            boolean exist = ossClient.doesObjectExist(bucketName, path);
            ossClient.deleteObject(bucketName, path);
        }
        return ResponseResult.okResult();
    }

    private String uploadOss(MultipartFile imgFile, String filePath) throws IOException {

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

//        String fileName = filePath.split("/")[3];
        // 简单上传
        if (!Objects.isNull(imgFile)) {
            ossClient.putObject(
                    new PutObjectRequest(bucketName, filePath, imgFile.getInputStream()));
        }
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        String host =
                "https://" + bucketName + "." + endpoint;

        try {
            return host + "/" + key;
        } catch (Exception ex) {
            //ignore
        }
        return "www.baidu.com";
    }
}
