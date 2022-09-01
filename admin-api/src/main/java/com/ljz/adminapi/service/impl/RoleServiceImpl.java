package com.ljz.adminapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljz.adminapi.mapper.RoleMapper;
import com.ljz.adminapi.mapper.UserMapper;
import com.ljz.adminapi.mapper.UserRoleMapper;
import com.ljz.adminapi.pojo.Role;
import com.ljz.adminapi.pojo.User;
import com.ljz.adminapi.pojo.UserRole;
import com.ljz.adminapi.service.RoleService;
import com.ljz.adminapi.vo.RoleQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 根据用户查询角色列表
     *
     * @param page        分页条件
     * @param roleQueryVo 查询条件
     * @return IPage<Role>
     */
    @Override
    public IPage<Role> findRoleListByUserId(IPage<Role> page, RoleQueryVo roleQueryVo) {
        // 条件构造器
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        // 根据角色名称进行模糊查询
        wrapper.like(!ObjectUtils.isEmpty(roleQueryVo.getRoleName()), Role::getRoleName, roleQueryVo.getRoleName());
        // 根据角色id进行排序
        wrapper.orderByAsc(Role::getId);
        // 用户id
        User user = userMapper.selectById(roleQueryVo.getUserId());
        // 如果不是管理员只能查询到自己创建的角色
        if (!ObjectUtils.isEmpty(user) && !ObjectUtils.isEmpty(user.getIsAdmin()) && user.getIsAdmin() != 1) {
            wrapper.eq(Role::getCreateUser, roleQueryVo.getUserId());
        }
        return page(page, wrapper);
    }

    /**
     * 保存角色权限关系
     *
     * @param roleId        角色id
     * @param permissionIds 权限
     * @return boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean saveRolePermission(Long roleId, List<Long> permissionIds) {
        //删除该角色对应的权限信息
        roleMapper.deleteRolePermission(roleId);
        //保存角色权限
        return roleMapper.saveRolePermission(roleId, permissionIds) > 0;
    }

    /**
     * 根据角色 id 查询查询该角色是否有用户在使用
     * @param roleId 角色id
     * @return boolean
     */
    @Override
    public boolean selectUserCountByRoleId(Long roleId) {
        // 条件构造器
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        // 根据角色id查询
        wrapper.eq(UserRole::getRoleId, roleId);
        // 查询角色对应的用户
        Long count = userRoleMapper.selectCount(wrapper);
        // 角色下有用户返回true
        return count > 0;
    }

    /**
     * 根据用户ID查询该用户拥有的角色ID
     *
     * @param userId 用户id
     * @return List<Long>
     */
    @Override
    public List<Long> findRoleIdByUserId(Long userId) {
        return baseMapper.findRoleIdByUserId(userId);
    }
}
