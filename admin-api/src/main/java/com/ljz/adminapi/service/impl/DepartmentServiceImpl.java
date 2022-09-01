package com.ljz.adminapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljz.adminapi.mapper.DepartmentMapper;
import com.ljz.adminapi.mapper.UserMapper;
import com.ljz.adminapi.pojo.Department;
import com.ljz.adminapi.pojo.User;
import com.ljz.adminapi.service.DepartmentService;
import com.ljz.adminapi.utils.DepartmentTree;
import com.ljz.adminapi.vo.DepartmentQueryVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    @Resource
    private UserMapper userMapper;

    /**
     * 查询部门列表
     *
     * @param departmentQueryVo 查询条件
     * @return List<Department>
     */
    @Override
    public List<Department> findDepartmentList(DepartmentQueryVo departmentQueryVo) {
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        // 根据部门名称进行模糊查询
        wrapper.like(!ObjectUtils.isEmpty(departmentQueryVo.getDepartmentName()),
                Department::getDepartmentName, departmentQueryVo.getDepartmentName());
        wrapper.orderByAsc(Department::getOrderNum);
        // 查询部门列表
        List<Department> departmentList = list(wrapper);
        List<Department> departmentTree = new ArrayList<>();
        if (!departmentList.isEmpty()) {  //如果list不为空,即没有条件或者条件正确
            //departmentList.stream().....getAsLong() 返回集合中最小的Pid
            departmentTree = DepartmentTree.makeDepartmentTree(departmentList,
                    departmentList.stream().mapToLong(Department::getPid).min().getAsLong());
        }//如果list为空,即条件错误或无数据的时候,直接返回一个空集合
        //生成部门树
        return departmentTree;
    }

    /**
     * 查询上级部门列表
     *
     * @return List<Department>
     */
    @Override
    public List<Department> findParentDepartment() {
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Department::getOrderNum);
        // 查询部门列表
        List<Department> departmentList = list(wrapper);
        //创建部门对象
        Department department = new Department();
        department.setId(0L);
        department.setDepartmentName("顶级部门");
        department.setPid(-1L);
        departmentList.add(department);
        //生成部门树列表
        return DepartmentTree.makeDepartmentTree(departmentList, -1L);
    }

    /**
     * 判断部门下是否有子部门
     *
     * @param id 部门id
     * @return boolean
     */
    @Override
    public boolean hasChildrenOfDepartment(Long id) {
        //创建条件构造器对象
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Department::getPid, id);
        //如果数量大于0，表示存在
        return count(wrapper) > 0;
    }

    /**
     * 判断部门下是否存在用户
     *
     * @param id 部门id
     * @return boolean
     */
    @Override
    public boolean hasUserOfDepartment(Long id) {
        //创建条件构造器对象
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getDepartmentId, id);
        //如果数量大于0，表示存在
        return userMapper.selectCount(wrapper) > 0;
    }
}
