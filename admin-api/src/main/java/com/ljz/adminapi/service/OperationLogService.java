package com.ljz.adminapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljz.adminapi.dto.R;
import com.ljz.adminapi.pojo.OperationLog;
import com.ljz.adminapi.vo.OperationLogQueryVo;

/**
 * <p>日志服务类接口</p>
 *
 * @Author : ljz
 * @Date: 2022/9/3  9:32
 */
public interface OperationLogService extends IService<OperationLog> {
    /**
     * 获取日志列表
     * @param operationLogQueryVo 查询条件
     * @return R
     */
    R getLogByPage(OperationLogQueryVo operationLogQueryVo);
}
