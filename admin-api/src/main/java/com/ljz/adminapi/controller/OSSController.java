package com.ljz.adminapi.controller;

import com.ljz.adminapi.config.annotation.Log;
import com.ljz.adminapi.dto.R;
import com.ljz.adminapi.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>对象存储控制类</p>
 *
 * @Author : ljz
 * @Date: 2022/8/31  15:22
 */
@RestController
@RequestMapping("/api/oss/file")
@Api(tags = "对象存储控制类(OSSController)")
public class OSSController {
    @Resource
    private FileService fileService;

    /**
     * 文件上传
     *
     * @param file 文件
     * @param module 模块
     * @return R
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    @Log(value = "文件上传")
    public R upload(MultipartFile file, String module) {
        //返回上传到oss的路径
        String url = fileService.upload(file, module);
        return R.ok(url).message("文件上传成功");
    }
}
