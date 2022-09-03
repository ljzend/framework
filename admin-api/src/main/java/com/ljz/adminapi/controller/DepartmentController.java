package com.ljz.adminapi.controller;

import com.ljz.adminapi.config.annotation.Log;
import com.ljz.adminapi.dto.R;
import com.ljz.adminapi.pojo.Department;
import com.ljz.adminapi.service.DepartmentService;
import com.ljz.adminapi.vo.DepartmentQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/department")
@Api(tags = "部门控制类(DepartmentController)")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    /**
     * 查询部门列表
     *
     * @param departmentQueryVo 查询条件
     * @return R
     */
    @GetMapping("/list")
    @ApiOperation("查询部门列表")
    public R list(DepartmentQueryVo departmentQueryVo) {
        //调用查询部门列表方法
        List<Department> departmentList = departmentService.findDepartmentList(departmentQueryVo);
        //返回数据
        return R.ok(departmentList);
    }

    /**
     * 查询上级部门列表
     *
     * @return R
     */
    @GetMapping("/parent/list")
    @ApiOperation("查询上级部门列表")
    public R getParentDepartment() {
        //调用查询上级部门列表方法
        List<Department> departmentList = departmentService.findParentDepartment();
        //返回数据
        return R.ok(departmentList);
    }

    /**
     * 添加部门
     *
     * @param department 部门
     * @return R
     */
    @PostMapping("/add")
    @ApiOperation("添加部门")
    @Log(value = "添加部门")
    @PreAuthorize("hasAuthority('sys:department:add')")
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = {Exception.class})
    public R add(@RequestBody Department department) {
        if (departmentService.save(department)) {
            return R.ok().message("部门添加成功");
        }
        return R.error().message("部门添加失败");
    }

    /**
     * 修改部门
     * @param department 部门
     * @return R
     */
    @PutMapping("/update")
    @ApiOperation("修改部门")
    @Log(value = "修改部门")
    @PreAuthorize("hasAuthority('sys:department:edit')")
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = {Exception.class})
    public R update(@RequestBody Department department){
        if(departmentService.updateById(department)){
            return R.ok().message("部门修改成功");
        }
        return R.error().message("部门修改失败");
    }

    /**
     * 查询某个部门下是否存在子部门
     * @param id 部门id
     * @return R
     */
    @GetMapping("/check/{id}")
    @ApiOperation("查询某个部门下是否存在子部门")
    public R check(@PathVariable Long id){
        //调用查询部门下是否存在子部门的方法
        if(departmentService.hasChildrenOfDepartment(id)){
            return R.exist().message("该部门下存在子部门，无法删除");
        }
        //调用查询部门下是否存在用户的方法
        if(departmentService.hasUserOfDepartment(id)){
            return R.exist().message("该部门下存在用户，无法删除");
        }
        return R.ok();
    }
    /**
     * 删除部门
     * @param id 部门id
     * @return R
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除部门")
    @Log(value = "删除部门")
    @PreAuthorize("hasAuthority('sys:department:delete')")
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = {Exception.class})
    public R delete(@PathVariable Long id){
        if(departmentService.removeById(id)){
            return R.ok().message("部门删除成功");
        }
        return R.error().message("部门删除失败");
    }
}
