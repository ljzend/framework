package com.ljz.adminapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>登录返回对象封装</p>
 *
 * @Author : ljz
 * @Date: 2022/8/27  17:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel("登录成功返回对象")
public class LoginDTO {
    @ApiModelProperty("用户编号")
    private Long id;
    @ApiModelProperty("状态码")
    private int code;
    @ApiModelProperty("token令牌")
    private String token;
    @ApiModelProperty("token过期时间")
    private Long expireTime;
}
