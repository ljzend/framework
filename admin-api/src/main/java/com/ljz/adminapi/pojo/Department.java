package com.ljz.adminapi.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
@Getter
@Setter
@TableName("sys_department")
@ApiModel(value = "Department对象", description = "")
public class Department implements Serializable {
    private static final long serialVersionUID= 1L;

    @ApiModelProperty("部门编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("部门名称")
    @TableField("department_name")
    private String departmentName;

    @ApiModelProperty("部门电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("部门地址")
    @TableField("address")
    private String address;

    @ApiModelProperty("所属部门编号")
    @TableField("pid")
    private Long pid;

    @ApiModelProperty("所属部门名称")
    @TableField("parent_name")
    private String parentName;

    @ApiModelProperty("排序")
    @TableField("order_num")
    private Integer orderNum;

    @ApiModelProperty("是否删除(0-未删除 1-已删除)")
    @TableField("is_delete")
    @TableLogic
    private Integer isDelete;


}
