package com.ljz.adminapi.vo;

import lombok.Data;

import java.util.List;

/**
 * <p></p>
 *
 * @Author : ljz
 * @Date: 2022/8/31  8:41
 */
@Data
public class RolePermissionVo {
    private Long roleId;//角色编号
    private List<Long> list;//权限菜单ID集合
}
