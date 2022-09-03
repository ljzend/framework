package com.ljz.adminapi.controller;

import com.ljz.adminapi.dto.R;
import com.ljz.adminapi.service.OperationLogService;
import com.ljz.adminapi.vo.OperationLogQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>操作日志控制类</p>
 *
 * @Author : ljz
 * @Date: 2022/9/2  20:52
 */

@RestController
@RequestMapping("/api/log/operationLog")
@Api(tags = "操作日志控制类(OperationLogController)")
public class OperationLogController {
    @Resource
    private OperationLogService operationLogService;

    @GetMapping("/list")
    @ApiOperation("获取日志列表")
    public R getLogByPage(OperationLogQueryVo operationLogQueryVo){
        return operationLogService.getLogByPage(operationLogQueryVo);
    }
}
