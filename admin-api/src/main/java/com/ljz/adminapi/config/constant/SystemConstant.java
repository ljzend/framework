package com.ljz.adminapi.config.constant;

/**
 * <p>常量配置类</p>
 *
 * @Author : ljz
 * @Date: 2022/8/29  9:16
 */
public interface SystemConstant {

    interface Token{
        /**
         * 用户登录token的key的前缀
         */
        String LOGIN_TOKEN_PREFIX = "user:login:token_";
    }

    interface OOS {
        /**
         * 默认头像
         */
        String DEFAULT_AVATAR = "https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/male.jpg";
    }
}
