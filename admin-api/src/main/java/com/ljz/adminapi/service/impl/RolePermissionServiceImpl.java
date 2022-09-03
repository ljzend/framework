package com.ljz.adminapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljz.adminapi.mapper.RolePermissionMapper;
import com.ljz.adminapi.pojo.RolePermission;
import com.ljz.adminapi.service.RolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    /**
     * 根据角色id删除角色对应的权限
     * @param roleId 角色id
     * @return boolean
     */
    @Override
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean deleteRolePermissionByRoleId(Long roleId) {
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId);
        // 判断该角色是否有权限
        if(list(wrapper).size() == 0){
            return true;
        }
        return remove(wrapper);
    }
}
