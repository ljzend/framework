package com.ljz.adminapi.dto;

import com.ljz.adminapi.pojo.Permission;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @Author : ljz
 * @Date: 2022/8/30  21:07
 */
@Data
public class RolePermissionDTO {
    /**
     * 菜单数据
     */
    private List<Permission> permissionList = new ArrayList<>();
    /**
     * 该角色原有分配的菜单数据
     */
    private Object [] checkList;
}
