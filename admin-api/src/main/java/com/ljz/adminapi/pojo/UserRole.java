package com.ljz.adminapi.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2022-08-27 14-25-34
 */
@Getter
@Setter
@TableName("sys_user_role")
@ApiModel(value = "UserRole对象", description = "")
public class UserRole implements Serializable {
    private static final long serialVersionUID= 1L;
    @ApiModelProperty("id主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户编号")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("角色编号")
    @TableField("role_id")
    private Long roleId;


}
