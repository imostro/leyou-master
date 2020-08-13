package com.leyou.fastdfs.controller;


import com.leyou.fastdfs.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Gray
 */
@RestController
@RequestMapping("upload/")
public class UploadController {

   @Autowired
    private UploadService uploadService;

   @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file){
       String url = this.uploadService.upload(file);
       if(StringUtils.isEmpty(url)){
           //url为空，上传失败
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
       // 返回200，并且携带url路径
       return ResponseEntity.ok(url);
   }

}
