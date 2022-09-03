package com.ljz.adminapi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljz.adminapi.config.annotation.Log;
import com.ljz.adminapi.dto.R;
import com.ljz.adminapi.dto.RolePermissionDTO;
import com.ljz.adminapi.pojo.Role;
import com.ljz.adminapi.service.PermissionService;
import com.ljz.adminapi.service.RolePermissionService;
import com.ljz.adminapi.service.RoleService;
import com.ljz.adminapi.vo.RolePermissionVo;
import com.ljz.adminapi.vo.RoleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
@RestController
@RequestMapping("/api/role")
@Api(tags = "角色控制类(RoleController)")
public class RoleController {
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 分页查询角色列表
     *
     * @param roleQueryVo 查询条件
     * @return R
     */
    @GetMapping("/list")
    @ApiOperation("分页查询角色列表")
    public R list(RoleQueryVo roleQueryVo) {
        //创建分页对象
        IPage<Role> page = new Page<>(roleQueryVo.getPageNo(), roleQueryVo.getPageSize());
        //调用分页查询方法
        IPage<Role> roleIPage = roleService.findRoleListByUserId(page, roleQueryVo);
        //返回数据
        return R.ok(roleIPage);
    }

    /**
     * 添加角色
     *
     * @param role 角色
     * @return R
     */
    @PostMapping("/add")
    @ApiOperation("添加角色")
    @Log(value = "添加角色")
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = {Exception.class})
    public R add(@RequestBody Role role) {
        if (roleService.save(role)) {
            return R.ok().message("角色添加成功");
        }
        return R.error().message("角色添加失败");
    }

    /**
     * 修改角色
     *
     * @param role 角色
     * @return R
     */
    @PutMapping("/update")
    @ApiOperation("修改角色")
    @Log(value = "修改角色")
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = {Exception.class})
    public R update(@RequestBody Role role) {
        if (roleService.updateById(role)) {
            return R.ok().message("角色修改成功");
        }
        return R.error().message("角色修改失败");
    }

    /**
     * 删除角色
     *
     * @param id 角色
     * @return R
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除角色")
    @Log(value = "删除角色")
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = {Exception.class})
    public R delete(@PathVariable Long id) {
        // TODO : 当用户没有权限时会报错
        if (roleService.removeById(id) && rolePermissionService.deleteRolePermissionByRoleId(id)) {
            return R.ok().message("角色删除成功");
        }
        return R.error().message("角色删除失败");
    }

    @GetMapping("/check/{id}")
    @ApiOperation("判断该角色是否有用户使用")
    public R check(@PathVariable("id") Long id){
        // 该角色下存在用户
        if(roleService.selectUserCountByRoleId(id)){
            return R.exist().message("改角色下有用户,不能删除");
        }
        return R.ok();
    }

    /**
     * 分配权限-查询权限树数据
     *
     * @param userId 用户id
     * @param roleId 角色id
     * @return R
     */
    @GetMapping("/getAssignPermissionTree")
    @ApiOperation("分配权限-查询权限树数据")
    public R getAssignPermissionTree(Long userId, Long roleId) {
        //调用查询权限树数据的方法
        RolePermissionDTO permissionTree = permissionService.findPermissionTree(userId, roleId);
        //返回数据
        return R.ok(permissionTree);
    }

    /**
     * 分配权限-保存权限数据
     *
     * @param rolePermissionVo 数据
     * @return R
     */
    @PostMapping("/saveRoleAssign")
    @ApiOperation("分配权限-保存权限数据")
    @Log(value = "分配权限-保存权限数据")
    public R saveRoleAssign(@RequestBody RolePermissionVo rolePermissionVo) {
        if (roleService.saveRolePermission(rolePermissionVo.getRoleId(),
                rolePermissionVo.getList())) {
            return R.ok().message("权限分配成功");
        } else {
            return R.error().message("权限分配失败");
        }
    }
}
