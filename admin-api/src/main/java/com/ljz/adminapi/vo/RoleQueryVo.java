package com.ljz.adminapi.vo;

import com.ljz.adminapi.pojo.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p></p>
 *
 * @Author : ljz
 * @Date: 2022/8/30  19:34
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleQueryVo extends Role {
    private Long pageNo = 1L;//当前页码
    private Long pageSize = 10L;//每页显示数量
    private Long userId;//用户ID
}
