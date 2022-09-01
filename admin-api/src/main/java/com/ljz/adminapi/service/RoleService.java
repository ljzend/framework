package com.ljz.adminapi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljz.adminapi.pojo.Role;
import com.ljz.adminapi.vo.RoleQueryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
public interface RoleService extends IService<Role> {
    /**
     * 根据用户查询角色列表
     * @param page 分页条件
     * @param roleQueryVo 查询条件
     * @return IPage<Role>
     */
    IPage<Role> findRoleListByUserId(IPage<Role> page, RoleQueryVo roleQueryVo);

    /**
     * 保存角色权限关系
     * @param roleId 角色id
     * @param permissionIds 权限
     * @return boolean
     */
    boolean saveRolePermission(Long roleId, List<Long> permissionIds);

    /**
     * 根据角色 id 查询查询该角色是否有用户在使用
     * @param roleId 角色id
     * @return boolean
     */
    boolean selectUserCountByRoleId(Long roleId);

    /**
     * 根据用户ID查询该用户拥有的角色ID
     * @param userId 用户id
     * @return List<Long>
     */
    List<Long> findRoleIdByUserId(Long userId);
}
