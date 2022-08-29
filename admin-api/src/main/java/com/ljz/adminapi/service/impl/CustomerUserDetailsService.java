package com.ljz.adminapi.service.impl;

import com.ljz.adminapi.pojo.Permission;
import com.ljz.adminapi.pojo.User;
import com.ljz.adminapi.service.PermissionService;
import com.ljz.adminapi.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>security自定义UserDetailsService</p>
 *
 * @Author : ljz
 * @Date: 2022/8/27  16:17
 */
@Component
public class CustomerUserDetailsService implements UserDetailsService {
    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 调用根据用户名查询用户信息方法
        User user = userService.findUserByUserName(username);
        // user 为空则认证失败
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        // 根据用户 id 查询权限列表
        List<Permission> permissionList = permissionService.findPermissionListByUserId(user.getId());
        // 过滤出对应权限编码,并转成 String 数组
        String[] array = permissionList.stream()
                .filter(Objects::nonNull)
                .map(Permission::getCode)
                .filter(Objects::nonNull)
                .toArray(String[]::new);
        // 生成权限列表
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(array);
        // 给用户设置权限
        user.setAuthorities(authorityList);
        // 设置用户拥有的菜单信息
        user.setPermissionList(permissionList);
        return user;
    }
}
