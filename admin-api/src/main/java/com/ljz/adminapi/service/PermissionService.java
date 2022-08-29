package com.ljz.adminapi.service;

import com.ljz.adminapi.pojo.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 根据用户 id 查询权限列表
     * @param userId 用户id
     * @return List<Permission>
     */
    List<Permission> findPermissionListByUserId(Long userId);
}
