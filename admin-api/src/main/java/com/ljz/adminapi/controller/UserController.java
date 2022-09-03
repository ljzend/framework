package com.ljz.adminapi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljz.adminapi.config.annotation.Log;
import com.ljz.adminapi.dto.R;
import com.ljz.adminapi.pojo.Role;
import com.ljz.adminapi.pojo.User;
import com.ljz.adminapi.service.RoleService;
import com.ljz.adminapi.service.UserService;
import com.ljz.adminapi.vo.RoleQueryVo;
import com.ljz.adminapi.vo.UserQueryVo;
import com.ljz.adminapi.vo.UserRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

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
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private RoleService roleService;

    @PostMapping("/refreshToken")
    @ApiOperation("刷新token")
    @Log("刷新token")
    public R refreshToken(HttpServletRequest request) {
        return userService.refreshToken(request);
    }

    @GetMapping("/getInfo")
    @ApiOperation("获取登录用户信息")
    @Log(value = "获取登录用户信息", save = false)
    public R getInfo() {
        return userService.getInfo();
    }

    @GetMapping("/getMenuList")
    @ApiOperation("获取登录用户所拥有的菜单信息")
    @Log(value = "获取登录用户所拥有的菜单信息", save = false)
    public R getMenuList() {
        return userService.getMenuList();
    }

    @PostMapping("/logout")
    @ApiOperation("退出登录")
    @Log("退出登录")
    public R logout(HttpServletRequest request, HttpServletResponse response) {
        return userService.logout(request, response);
    }

    @GetMapping("/list")
    @ApiOperation("查询用户列表")
    @Log(value = "查询用户列表", save = false)
    public R list(UserQueryVo userQueryVo) {
        //创建分页信息
        IPage<User> page = new Page<>(userQueryVo.getPageNo(), userQueryVo.getPageSize());
        //调用分页查询方法
        IPage<User> userIPage = userService.findUserListByPage(page, userQueryVo);
        //返回数据
        return R.ok(userIPage);
    }

    @PostMapping("/add")
    @ApiOperation("添加用户")
    @Log("添加用户")
    @PreAuthorize("hasAuthority('sys:user:add')")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public R add(@RequestBody User user) {
        //查询用户
        User item = userService.findUserByUserName(user.getUsername());
        //判断对象是否为空
        if (item != null) {
            return R.error().message("该登录名称已被使用，请重新输入！");
        }
        //密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //调用保存用户信息的方法
        if (userService.save(user)) {
            return R.ok().message("用户添加成功");
        }
        return R.error().message("用户添加失败");
    }

    @PutMapping("/update")
    @ApiOperation("更新用户信息")
    @Log("更新用户信息")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public R update(@RequestBody User user) {
        //查询用户
        User item = userService.findUserByUserName(user.getUsername());
        //判断对象是否为空,且查询到的用户ID不等于当前编辑的用户ID，表示该名称被占用
        if (item != null && !Objects.equals(item.getId(), user.getId())) {
            return R.error().message("登录名称已被占用！");
        }
        //调用修改用户信息的方法
        if (userService.updateById(user)) {
            return R.ok().message("用户修改成功");
        }
        return R.error().message("用户修改失败");
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除用户")
    @Log("删除用户")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public R delete(@PathVariable Long id) {
        //调用删除用户信息的方法
        if (userService.deleteById(id)) {
            return R.ok().message("用户删除成功");
        }
        return R.error().message("用户删除失败");
    }

    @GetMapping("/getRoleListForAssign")
    @ApiOperation("获取分配角色列表")
    @Log("获取分配角色列表")
    @PreAuthorize("hasAuthority('sys:user:assign')")
    public R getRoleListForAssign(RoleQueryVo roleQueryVo) {
        //创建分页对象
        IPage<Role> page = new Page<>(roleQueryVo.getPageNo(), roleQueryVo.getPageSize());
        //调用查询方法
        roleService.findRoleListByUserId(page, roleQueryVo);
        //返回数据
        return R.ok(page);
    }


    @GetMapping("/getRoleByUserId/{userId}")
    @ApiOperation("根据用户ID查询该用户拥有的角色列表")
    @Log("根据用户ID查询该用户拥有的角色列表")
    @PreAuthorize("hasAuthority('sys:user:assign')")
    public R getRoleByUserId(@PathVariable Long userId) {
        //调用根据用户ID查询该用户拥有的角色ID的方法
        List<Long> roleIds = roleService.findRoleIdByUserId(userId);
        return R.ok(roleIds);
    }

    @PostMapping("/saveUserRole")
    @ApiOperation("分配角色")
    @Log("分配角色")
    @PreAuthorize("hasAuthority('sys:user:assign')")
    public R saveUserRole(@RequestBody UserRoleVo userRoleVo) {
        if (userService.saveUserRole(userRoleVo.getUserId(), userRoleVo.getRoleIds())) {
            return R.ok().message("角色分配成功");
        }
        return R.error().message("角色分配失败");
    }
}
