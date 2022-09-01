package com.ljz.adminapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljz.adminapi.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据用户 id 查询权限列表
     * @param userId 用户id
     * @return List<Permission>
     */
    List<Permission> findPermissionListByUserId(Long userId);

    /**
     * 根据角色ID查询权限列表
     * @param roleId 角色id
     * @return List<Permission>
     */
    List<Permission> findPermissionListByRoleId(Long roleId);
}
