package com.ljz.adminapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljz.adminapi.pojo.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 删除角色权限关系
     *
     * @param roleId 角色id
     */
    @Delete("delete from sys_role_permission where role_id = #{roleId}")
    void deleteRolePermission(Long roleId);

    /**
     * 保存角色权限关系
     *
     * @param roleId 角色id
     * @param permissionIds 权限
     * @return int
     */
    int saveRolePermission(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);

    /**
     * 根据用户ID查询该用户拥有的角色ID
     * @param userId 用户id
     * @return List<Long>
     */
    @Select("select role_id from `sys_user_role` where user_id = #{userId}")
    List<Long> findRoleIdByUserId(Long userId);
}
