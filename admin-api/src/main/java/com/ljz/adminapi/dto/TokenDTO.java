package com.ljz.adminapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>token封装</p>
 *
 * @Author : ljz
 * @Date: 2022/8/29  10:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    //过期时间
    private Long expireTime;
    //token
    private String token;
}
