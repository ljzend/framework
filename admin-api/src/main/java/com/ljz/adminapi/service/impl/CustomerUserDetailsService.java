package com.ljz.adminapi.service.impl;

import com.ljz.adminapi.pojo.User;
import com.ljz.adminapi.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 调用根据用户名查询用户信息方法
        User user = userService.findUserByUserName(username);
        // user 为空则认证失败
        if(ObjectUtils.isEmpty(user)){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return user;
    }
}
