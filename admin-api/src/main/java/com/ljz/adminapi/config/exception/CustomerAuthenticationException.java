package com.ljz.adminapi.config.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * <p>自定义验证异常类</p>
 *
 * @Author : ljz
 * @Date: 2022/8/29  8:46
 */

public class CustomerAuthenticationException extends AuthenticationException {

    public CustomerAuthenticationException(String msg) {
        super(msg);
    }
}
