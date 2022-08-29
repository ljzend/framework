package com.ljz.adminapi.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@TableName("sys_role_permission")
@ApiModel(value = "RolePermission对象", description = "")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolePermission implements Serializable {
    private static final long serialVersionUID= 1L;
    @ApiModelProperty("id主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("角色ID")
    @TableField("role_Id")
    private Long roleId;

    @ApiModelProperty("权限ID")
    @TableField("permission_Id")
    private Long permissionId;


}
