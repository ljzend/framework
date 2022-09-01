package com.ljz.adminapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljz.adminapi.dto.RolePermissionDTO;
import com.ljz.adminapi.mapper.PermissionMapper;
import com.ljz.adminapi.mapper.UserMapper;
import com.ljz.adminapi.pojo.Permission;
import com.ljz.adminapi.pojo.User;
import com.ljz.adminapi.service.PermissionService;
import com.ljz.adminapi.utils.MenuTree;
import com.ljz.adminapi.vo.PermissionQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 根据用户 id 查询权限列表
     *
     * @param userId 用户id
     * @return List<Permission>
     */
    @Override
    public List<Permission> findPermissionListByUserId(Long userId) {
        return permissionMapper.findPermissionListByUserId(userId);
    }

    /**
     * 查询菜单列表
     *
     * @param permissionQueryVo 查询条件
     * @return List<Permission>
     */
    @Override
    public List<Permission> findPermissionList(PermissionQueryVo permissionQueryVo) {
        // 条件构造器
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(!ObjectUtils.isEmpty(permissionQueryVo.getLabel()),
                Permission::getLabel, permissionQueryVo.getLabel());
        wrapper.orderByAsc(Permission::getOrderNum);
        // 获取菜单列表
        List<Permission> permissionList = list(wrapper);
        List<Permission> permissionTree = new ArrayList<>();
        if (!permissionList.isEmpty()) {  //如果list不为空,即没有条件或者条件正确
            //departmentList.stream().....getAsLong() 返回集合中最小的Pid
            permissionTree = MenuTree.makeMenuTree(permissionList,
                    permissionList.stream().mapToLong(Permission::getParentId).min().getAsLong());
        }//如果list为空,即条件错误或无数据的时候,直接返回一个空集合
        //生成部门树
        return permissionTree;
    }

    /**
     * 查询上级菜单列表
     *
     * @return List<Permission>
     */
    @Override
    public List<Permission> findParentPermissionList() {
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        //只查询type为目录和菜单的数据(type=0或type=1)
        wrapper.in(Permission::getType, Arrays.asList(0, 1));
        //排序
        wrapper.orderByAsc(Permission::getOrderNum);
        //查询菜单数据
        List<Permission> permissionList = baseMapper.selectList(wrapper);
        //构造顶级菜单信息，如果数据库中的菜单表没有数据，选择上级菜单时则显示顶级菜单
        Permission permission = new Permission();
        permission.setId(0L);
        permission.setParentId(-1L);
        permission.setLabel("顶级菜单");
        permissionList.add(permission);//将顶级菜单添加到集合
        //生成菜单数据
        //返回数据
        return MenuTree.makeMenuTree(permissionList, -1L);
    }

    /**
     * 检查菜单是否有子菜单
     *
     * @param id 菜单id
     * @return boolean
     */
    @Override
    public boolean hasChildrenOfPermission(Long id) {
        //创建条件构造器对象
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        //查询父级ID
        wrapper.eq(Permission::getParentId, id);
        //判断数量是否大于0，如果大于0则表示存在
        return baseMapper.selectCount(wrapper) > 0;
    }

    /**
     * 查询分配权限树列表
     *
     * @param userId 用户id
     * @param roleId 角色id
     * @return RolePermissionDTO
     */
    @Override
    public RolePermissionDTO findPermissionTree(Long userId, Long roleId) {
        // 查询用户信息
        User user = userMapper.selectById(userId);
        List<Permission> list;
        //2.判断当前用户角色，如果是管理员，则查询所有权限；如果不是管理员，则只查询自己所拥有的的权限
        if (!ObjectUtils.isEmpty(user.getIsAdmin()) && user.getIsAdmin() == 1) {
            //查询所有权限
            list = list(null);
        } else {
            //根据用户ID查询
            list = findPermissionListByUserId(userId);
        }
        //3.组装成树数据
        List<Permission> permissionList = MenuTree.makeMenuTree(list, 0L);

        //4.查询要分配角色的原有权限
        List<Permission> rolePermissions = permissionMapper.findPermissionListByRoleId(roleId);
        //5.找出该角色存在的数据
        List<Long> listIds = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .stream()
                .filter(Objects::nonNull)
                .forEach(item -> Optional.ofNullable(rolePermissions).orElse(new ArrayList<>())
                        .stream()
                        .filter(Objects::nonNull)
                        .forEach(obj -> {
                            if (item.getId().equals(obj.getId())) {
                                listIds.add(obj.getId());
                                return;
                            }
                        }));
        //创建
        RolePermissionDTO DTO = new RolePermissionDTO();
        DTO.setPermissionList(permissionList);
        DTO.setCheckList(listIds.toArray());
        return DTO;
    }
}
