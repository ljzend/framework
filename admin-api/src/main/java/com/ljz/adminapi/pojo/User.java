package com.ljz.adminapi.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
@TableName("sys_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable, UserDetails {
    private static final long serialVersionUID= 1L;

    @ApiModelProperty("用户编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("登录名称(用户名)")
    @TableField("username")
    private String username;

    @ApiModelProperty("登录密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("帐户是否过期(1-未过期，0-已过期)")
    @TableField("is_account_non_expired")
    private boolean isAccountNonExpired = true;

    @ApiModelProperty("帐户是否被锁定(1-未过期，0-已过期)")
    @TableField("is_account_non_locked")
    private boolean isAccountNonLocked = true;

    @ApiModelProperty("密码是否过期(1-未过期，0-已过期)")
    @TableField("is_credentials_non_expired")
    private boolean isCredentialsNonExpired = true;

    @ApiModelProperty("帐户是否可用(1-可用，0-禁用)")
    @TableField("is_enabled")
    private boolean isEnabled = true;

    @ApiModelProperty("真实姓名")
    @TableField("real_name")
    private String realName;

    @ApiModelProperty("昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty("所属部门ID")
    @TableField("department_id")
    private Long departmentId;

    @ApiModelProperty("所属部门名称")
    @TableField("department_name")
    private String departmentName;

    @ApiModelProperty("性别(0-男，1-女)")
    @TableField("gender")
    private Integer gender;

    @ApiModelProperty("电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("用户头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("是否是管理员(1-管理员)")
    @TableField("is_admin")
    private Integer isAdmin;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("是否删除(0-未删除，1-已删除)")
    @TableField("is_delete")
    @TableLogic
    private Integer isDelete;

    /**
     * 权限列表
     */
    @TableField(exist = false)
    Collection<? extends GrantedAuthority> authorities;

    /**
     * 查询用户权限
     */
    @TableField(exist = false)
    private List<Permission> permissionList;
}
