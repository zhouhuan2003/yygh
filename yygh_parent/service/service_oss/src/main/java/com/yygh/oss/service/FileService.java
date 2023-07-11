package com.yygh.oss.service;/*
 *@author 周欢
 *@version 1.0
 */

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    //上传文件到阿里云oss
    String upload(MultipartFile file);
}
