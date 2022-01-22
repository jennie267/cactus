package com.cactus.user.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * CactusUserApi3
 * Class: fileService
 * Created by hapo on 2019-11-29.
 * Description:
 */
public interface FileService {

    String profileUpload(int tableId, String fileName, MultipartFile multipartFile);
}
