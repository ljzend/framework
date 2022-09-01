package com.ljz.adminapi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljz.adminapi.dto.R;
import com.ljz.adminapi.pojo.User;
import com.ljz.adminapi.vo.UserQueryVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    /**
     * <p>用户退出</p>
     * @param request 请求
     * @param response 响应
     * @return R
     */
    R logout(HttpServletRequest request, HttpServletResponse response);

    /**
     * 分页查询用户信息
     * @param page 分页
     * @param userQueryVo 查询条件
     * @return IPage<User>
     */
    IPage<User> findUserListByPage(IPage<User> page, UserQueryVo userQueryVo);

    /**
     * 删除用户信息
     * @param id 用户id
     * @return boolean
     */
    boolean deleteById(Long id);

    /**
     * 分配角色
     * @param userId 用户id
     * @param roleIds 角色id
     * @return boolean
     */
    boolean saveUserRole(Long userId, List<Long> roleIds);
}
