package com.ljz.adminapi.service;

import com.ljz.adminapi.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
public interface UserService extends IService<User> {
    /**
     * <p>根据用户名查询用户信息</p>
     * @param userName 用户名
     * @return User
     */
    User findUserByUserName(String userName);
}
