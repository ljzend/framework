package com.ljz.adminapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljz.adminapi.pojo.Department;
import com.ljz.adminapi.vo.DepartmentQueryVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
public interface DepartmentService extends IService<Department> {

    /**
     * 查询部门列表
     *
     * @param departmentQueryVo 查询条件
     * @return List<Department>
     */
    List<Department> findDepartmentList(DepartmentQueryVo departmentQueryVo);

    /**
     * 查询上级部门列表
     *
     * @return List<Department>
     */
    List<Department> findParentDepartment();

    /**
     * 判断部门下是否有子部门
     *
     * @param id 部门id
     * @return boolean
     */
    boolean hasChildrenOfDepartment(Long id);

    /**
     * 判断部门下是否存在用户
     *
     * @param id 部门id
     * @return boolean
     */
    boolean hasUserOfDepartment(Long id);
}
