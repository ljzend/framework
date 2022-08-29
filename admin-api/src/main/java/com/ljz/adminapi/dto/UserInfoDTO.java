package com.ljz.adminapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>封装要返回的用户信息</p>
 *
 * @Author : ljz
 * @Date: 2022/8/29  11:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户信息封装")
@Accessors(chain = true)
public class UserInfoDTO implements Serializable {
    @ApiModelProperty("用户ID")
    private Long id;//用户ID
    @ApiModelProperty("用户名称")
    private String name;//用户名称
    @ApiModelProperty("头像")
    private String avatar;//头像
    @ApiModelProperty("介绍")
    private String introduction;//介绍
    @ApiModelProperty("角色权限集合")
    private Object[] roles;//角色权限集合
}
