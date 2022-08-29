package com.ljz.adminapi.controller;

import com.ljz.adminapi.dto.R;
import com.ljz.adminapi.dto.RouterDTO;
import com.ljz.adminapi.dto.UserInfoDTO;
import com.ljz.adminapi.pojo.Permission;
import com.ljz.adminapi.pojo.User;
import com.ljz.adminapi.service.UserService;
import com.ljz.adminapi.utils.JwtUtils;
import com.ljz.adminapi.utils.MenuTree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "控制类(UserController)")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("")
    @ApiOperation("获取用户信息")
    public R user() {
        return R.ok(userService.list());
    }

    @PostMapping("/refreshToken")
    @ApiOperation("刷新token")
    public R refreshToken(HttpServletRequest request) {
        return userService.refreshToken(request);
    }

    @GetMapping("/getInfo")
    @ApiOperation("获取登录用户信息")
    public R getInfo() {
        return userService.getInfo();
    }

    @GetMapping("/getMenuList")
    @ApiOperation("获取登录用户所拥有的菜单信息")
    public R getMenuList() {
        return userService.getMenuList();
    }
}
