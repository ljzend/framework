package com.ljz.adminapi.vo;

import com.ljz.adminapi.pojo.OperationLog;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p></p>
 *
 * @Author : ljz
 * @Date: 2022/9/3  11:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OperationLogQueryVo extends OperationLog {
    private Long pageNo = 1L;//当前页码
    private Long pageSize = 10L;//每页显示数量
}
