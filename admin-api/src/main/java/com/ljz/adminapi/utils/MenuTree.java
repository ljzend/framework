package com.ljz.adminapi.utils;

import com.ljz.adminapi.dto.RouterDTO;
import com.ljz.adminapi.pojo.Permission;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>menu转换为树状工具类</p>
 *
 * @Author : ljz
 * @Date: 2022/8/29  11:34
 */

public class MenuTree {
    /**
     * 生成路由
     *
     * @param meuList 菜单列表
     * @param pid     父级菜单
     * @return List<RouterDTO>
     */
    public static List<RouterDTO> makeRouter(List<Permission> meuList, Long pid) {
        //创建集合保存路由列表
        List<RouterDTO> routerList = new ArrayList<>();
        //如果menuList菜单列表不为空，则使用菜单列表，否则创建集合对象
        Optional.ofNullable(meuList).orElse(new ArrayList<>())
                //筛选不为空的菜单及菜单父id相同的数据
                .stream().filter(item -> item != null && Objects.equals(item.getParentId(), pid))
                .forEach(item -> {
                    //创建路由对象
                    RouterDTO routerDTO = new RouterDTO();
                    routerDTO.setName(item.getName());//路由名称
                    routerDTO.setPath(item.getPath());//路由地址
                    //判断是否是一级菜单
                    if (item.getParentId() == 0L) {
                        routerDTO.setComponent("Layout");//一级菜单组件
                        routerDTO.setAlwaysShow(true);//显示路由
                        routerDTO.setRedirect("/");
                    } else {
                        routerDTO.setComponent(item.getUrl());//具体的组件
                        routerDTO.setAlwaysShow(false);//折叠路由
                    }
                    //设置meta信息
                    routerDTO.setMeta(routerDTO.new Meta(item.getLabel(),
                            item.getIcon(),
                            item.getCode().split(",")));
                    //递归生成路由
                    List<RouterDTO> children = makeRouter(meuList, item.getId());
                    routerDTO.setChildren(children);//设置子路由到路由对象中
                    //将路由信息添加到集合中
                    routerList.add(routerDTO);
                });
        return routerList;
    }

    /**
     * 生成菜单树
     *
     * @param meuList 菜单列表
     * @param pid 父id
     * @return List<Permission>
     */
    public static List<Permission> makeMenuTree(List<Permission> meuList, Long pid) {
        //创建集合保存菜单
        List<Permission> permissionList = new ArrayList<>();
        //如果menuList菜单列表不为空，则使用菜单列表，否则创建集合对象
        Optional.ofNullable(meuList).orElse(new ArrayList<>())
                .stream().filter(item -> item != null && Objects.equals(item.getParentId(), pid))
                .forEach(item -> {
                    //创建菜单权限对象
                    Permission permission = new Permission();
                    //复制属性
                    BeanUtils.copyProperties(item, permission);
                    //获取每一个item的下级菜单,递归生成菜单树
                    List<Permission> children = makeMenuTree(meuList, item.getId());
                    //设置子菜单
                    permission.setChildren(children);
                        //将菜单对象添加到集合
                    permissionList.add(permission);
                });
        return permissionList;
    }

}
