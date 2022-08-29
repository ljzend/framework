package com.ljz.adminapi.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@TableName("sys_permission")
@ApiModel(value = "Permission对象", description = "")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Permission implements Serializable {
    private static final long serialVersionUID= 1L;

    @ApiModelProperty("权限编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("权限名称")
    @TableField("label")
    private String label;

    @ApiModelProperty("父权限ID")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("父权限名称")
    @TableField("parent_name")
    private String parentName;

    @ApiModelProperty("授权标识符")
    @TableField("code")
    private String code;

    @ApiModelProperty("路由地址")
    @TableField("path")
    private String path;

    @ApiModelProperty("路由名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("授权路径")
    @TableField("url")
    private String url;

    @ApiModelProperty("权限类型(0-目录 1-菜单 2-按钮)")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("排序")
    @TableField("order_num")
    private Integer orderNum;

    @ApiModelProperty("是否删除(0-未删除，1-已删除)")
    @TableField("is_delete")
    @TableLogic
    private Integer isDelete;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty("子菜单列表")
    @TableField(exist = false)
    private List<Permission> children = new ArrayList<>();

    @ApiModelProperty("用于前端判断是菜单、目录或按钮")
    @TableField(exist = false)
    private String value;

    @ApiModelProperty("是否展开")
    @TableField(exist = false)
    private Boolean open ;


}
