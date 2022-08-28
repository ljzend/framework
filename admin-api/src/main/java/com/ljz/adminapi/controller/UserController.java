package com.ljz.adminapi.controller;

import com.ljz.adminapi.dto.R;
import com.ljz.adminapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
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
    public R user(){
        return R.ok(userService.list());
    }
}
