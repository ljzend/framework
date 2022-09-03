package com.ljz.adminapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljz.adminapi.dto.R;
import com.ljz.adminapi.mapper.OperationLogMapper;
import com.ljz.adminapi.pojo.OperationLog;
import com.ljz.adminapi.service.OperationLogService;
import com.ljz.adminapi.vo.OperationLogQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p></p>
 *
 * @Author : ljz
 * @Date: 2022/9/3  9:33
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {
    @Override
    public R getLogByPage(OperationLogQueryVo operationLogQueryVo) {
        Page<OperationLog> page = new Page<>(operationLogQueryVo.getPageNo(), operationLogQueryVo.getPageSize());
        LambdaQueryWrapper<OperationLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(!ObjectUtils.isEmpty(operationLogQueryVo.getOperationName()),
                OperationLog::getOperationName, operationLogQueryVo.getOperationName());

        wrapper.like(!ObjectUtils.isEmpty(operationLogQueryVo.getUserName()),
                OperationLog::getUserName, operationLogQueryVo.getUserName());
        return R.ok(page(page, wrapper));
    }
}
