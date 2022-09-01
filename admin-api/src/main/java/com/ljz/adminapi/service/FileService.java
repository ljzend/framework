package com.ljz.adminapi.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>文件上传接口</p>
 *
 * @Author : ljz
 * @Date: 2022/8/31  15:08
 */

public interface FileService {
    /**
     * 文件上传
     * @param file 文件上传对象
     * @param module 文件夹名称
     * @return String
     */
    String upload(MultipartFile file, String module);
    /**
     * 删除文件
     * @param url 路径
     */
    void deleteFile(String url);
}
