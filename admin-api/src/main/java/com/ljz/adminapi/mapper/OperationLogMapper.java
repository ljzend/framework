package com.ljz.adminapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljz.adminapi.pojo.OperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>操作日志类mapper接口</p>
 *
 * @Author : ljz
 * @Date: 2022/9/3  9:21
 */

@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
}
