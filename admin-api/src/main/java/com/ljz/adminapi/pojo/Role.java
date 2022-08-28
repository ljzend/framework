package com.ljz.adminapi.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

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
@TableName("sys_role")
@ApiModel(value = "Role对象", description = "")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("角色编码")
    @TableField("role_code")
    private String roleCode;

    @ApiModelProperty("角色名称")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty("创建人")
    @TableField("create_user")
    private Long createUser;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("是否删除(0-未删除，1-已删除)")
    @TableField("is_delete")
    @TableLogic
    private Integer isDelete;


}
