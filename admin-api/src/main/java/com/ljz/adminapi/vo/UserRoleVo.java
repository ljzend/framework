package com.ljz.adminapi.vo;

import lombok.Data;

import java.util.List;

/**
 * <p>用于给用户分配角色时保存选中的角色数据</p>
 *
 * @Author : ljz
 * @Date: 2022/8/31  16:48
 */
@Data
public class UserRoleVo {
    private Long userId;
    private List<Long> roleIds;
}
