package com.ljz.adminapi.service;

import com.ljz.adminapi.dto.R;
import com.ljz.adminapi.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * <p>刷新token</p>
     * @param request 请求
     * @return R
     */
    R refreshToken(HttpServletRequest request);

    /**
     * <p>获取登录用户信息</p>
     * @return R
     */
    R getInfo();

    /**
     * <p>获取登录用户所拥有的菜单信息</p>
     * @return R
     */
    R getMenuList();
}
