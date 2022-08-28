package com.ljz.adminapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ljz.adminapi.pojo.User;
import com.ljz.adminapi.mapper.UserMapper;
import com.ljz.adminapi.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * <p>根据用户名查询用户信息</p>
     * @param userName 用户名
     * @return User
     */
    @Override
    public User findUserByUserName(String userName) {
        // 创建条件构造器
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        // 封装查询条件
        wrapper.eq(User::getUsername, userName);
        return getOne(wrapper);
    }
}
