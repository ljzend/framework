package com.ljz.adminapi.mapper;

import com.ljz.adminapi.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
