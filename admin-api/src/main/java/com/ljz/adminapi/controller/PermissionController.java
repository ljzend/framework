package com.ljz.adminapi.controller;

import com.ljz.adminapi.config.annotation.Log;
import com.ljz.adminapi.dto.R;
import com.ljz.adminapi.pojo.Permission;
import com.ljz.adminapi.service.PermissionService;
import com.ljz.adminapi.vo.PermissionQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
@RestController
@RequestMapping("/api/permission")
@Api(tags = "菜单权限控制类(PermissionController)")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    /**
     * 查询菜单列表
     *
     * @return R
     */
    @ApiOperation("查询菜单列表")
    @GetMapping("/list")
    public R getMenuList(PermissionQueryVo permissionQueryVo) {
        //查询菜单列表
        List<Permission> permissionList = permissionService.findPermissionList(permissionQueryVo);
        //返回数据
        return R.ok(permissionList);
    }

    /**
     * 查询上级菜单列表
     *
     * @return R
     */
    @GetMapping("/parent/list")
    @ApiOperation("查询上级菜单列表")
    public R getParentList() {
        //查询上级菜单列表
        List<Permission> permissionList = permissionService.findParentPermissionList();
        //返回数据
        return R.ok(permissionList);
    }

    /**
     * 根据id查询菜单信息
     *
     * @param id 菜单id
     * @return R
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜单信息")
    public R getMenuById(@PathVariable Long id) {
        return R.ok(permissionService.getById(id));
    }

    /**
     * 添加菜单
     *
     * @param permission 菜单
     * @return R
     */
    @PostMapping("/add")
    @ApiOperation("添加菜单")
    @Log(value = "添加菜单")
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = {Exception.class})
    public R add(@RequestBody Permission permission) {
        if (permissionService.save(permission)) {
            return R.ok().message("菜单添加成功");
        }
        return R.error().message("菜单添加失败");
    }

    /**
     * 修改菜单
     *
     * @param permission 菜单
     * @return R
     */
    @ApiOperation("修改菜单")
    @PutMapping("/update")
    @Log(value = "修改菜单")
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = {Exception.class})
    public R update(@RequestBody Permission permission) {
        if (permissionService.updateById(permission)) {
            return R.ok().message("菜单修改成功");
        }
        return R.error().message("菜单修改失败");
    }

    /**
     * 删除菜单
     *
     * @param id 菜单id
     * @return R
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除菜单")
    @Log(value = "删除菜单")
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = {Exception.class})
    public R delete(@PathVariable Long id) {
        if (permissionService.removeById(id)) {
            return R.ok().message("菜单删除成功");
        }
        return R.error().message("菜单删除失败");
    }

    /**
     * 检查菜单下是否有子菜单
     *
     * @param id 菜单id
     * @return R
     */
    @GetMapping("/check/{id}")
    @ApiOperation("检查菜单下是否有子菜单")
    public R check(@PathVariable Long id) {
        //判断菜单是否有子菜单
        if (permissionService.hasChildrenOfPermission(id)) {
            return R.exist().message("该菜单下有子菜单，无法删除");
        }
        return R.ok();
    }
}
