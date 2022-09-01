package com.ljz.adminapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljz.adminapi.dto.RolePermissionDTO;
import com.ljz.adminapi.pojo.Permission;
import com.ljz.adminapi.vo.PermissionQueryVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 根据用户 id 查询权限列表
     *
     * @param userId 用户id
     * @return List<Permission>
     */
    List<Permission> findPermissionListByUserId(Long userId);

    /**
     * 查询菜单列表
     *
     * @param permissionQueryVo 查询条件
     * @return List<Permission>
     */
    List<Permission> findPermissionList(PermissionQueryVo permissionQueryVo);

    /**
     * 查询上级菜单列表
     *
     * @return List<Permission>
     */
    List<Permission> findParentPermissionList();

    /**
     * 检查菜单是否有子菜单
     *
     * @param id 菜单id
     * @return boolean
     */
    boolean hasChildrenOfPermission(Long id);

    /**
     * 查询分配权限树列表
     * @param userId 用户id
     * @param roleId 角色id
     * @return RolePermissionDTO
     */
    RolePermissionDTO findPermissionTree(Long userId, Long roleId);
}
