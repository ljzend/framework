package com.ljz.adminapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljz.adminapi.pojo.RolePermission;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
public interface RolePermissionService extends IService<RolePermission> {
    /**
     * 根据角色id删除角色对应的权限
     * @param roleId 角色id
     * @return boolean
     */
    boolean deleteRolePermissionByRoleId(Long roleId);
}
