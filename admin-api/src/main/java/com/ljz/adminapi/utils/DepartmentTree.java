package com.ljz.adminapi.utils;

import com.ljz.adminapi.pojo.Department;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>部门类生成树工具类</p>
 *
 * @Author : ljz
 * @Date: 2022/8/29  20:45
 */

public class DepartmentTree {
    /**
     * 生成部门树
     *
     * @param deptList 部门列表
     * @param pid 父id
     * @return List<Department>
     */
    public static List<Department> makeDepartmentTree(List<Department> deptList, Long pid) {
        //创建集合保存部门信息
        List<Department> list = new ArrayList<Department>();
        //如果deptList部门列表不为空，则使用部门列表，否则创建集合对象
        Optional.ofNullable(deptList).orElse(new ArrayList<>())
                .stream().filter(item -> item != null && Objects.equals(item.getPid(), pid))
                .forEach(item -> {
                    //创建部门对象
                    Department dept = new Department();
                    //复制属性
                    BeanUtils.copyProperties(item, dept);
                    //获取每一个item的下级部门,递归生成部门树
                    List<Department> children = makeDepartmentTree(deptList, item.getId());
                    //设置子部门
                    dept.setChildren(children);
                    //将部门对象添加到集合
                    list.add(dept);
                });
        return list;
    }
}
