package com.leyou.fastdfs.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * @Author: Gray
 */
@Service
@Log4j2
public class UploadService {

    /**
     * 支持的文件格式
     */
    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg");

    @Autowired
    private FastFileStorageClient storageClient;

    public String upload(MultipartFile file) {
        try{
            // 1、图片信息校验
            // 1)校验文件类型
            String type = file.getContentType();
            if(!suffixes.contains(type)){
                log.info("上传失败，文件类型不匹配：{}");
                return null;
            }
            // 2)校验图片内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null){
                log.info("上传失败，文件内容不符合要求");
                return null;
            }

            // 2、将图片上传到FastDFS
            // 2.1、获取文件后缀名
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");

            //2.2上传
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            //2.3返回完整路径
            return "http://image.leyou.com/" + storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public void  deleteImage(String url){
        if(!StringUtils.isNotEmpty(url)){
            storageClient.deleteFile("group1", url);
        }
    }
}
