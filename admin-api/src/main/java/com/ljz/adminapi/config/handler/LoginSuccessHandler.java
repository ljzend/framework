package com.ljz.adminapi.config.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ljz.adminapi.config.constant.SystemConstant;
import com.ljz.adminapi.dto.LoginDTO;
import com.ljz.adminapi.dto.ResultCode;
import com.ljz.adminapi.pojo.User;
import com.ljz.adminapi.utils.JwtUtils;
import io.jsonwebtoken.Jwts;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * <p>Security登录认证成功处理器</p>
 *
 * @Author : ljz
 * @Date: 2022/8/27  16:23
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 设置响应类型
        response.setContentType("application/json;charset=UTF-8");
        // 获取当前登录用户信息
        User user = (User) authentication.getPrincipal();
        // 生成token
        String token = jwtUtils.generateToken(user);
        // token 放入redis
        redisTemplate.opsForValue().set(SystemConstant.Token.LOGIN_TOKEN_PREFIX + token, token,
                jwtUtils.getExpiration() / 1000, TimeUnit.SECONDS);

        long expireTime = Jwts.parser()
                .setSigningKey(jwtUtils.getSecret()) // 设置解析秘钥
                .parseClaimsJws(token.replace("jwt_", ""))
                .getBody()
                // 取得过期时间
                .getExpiration().getTime();
        // 封装返回对象
        LoginDTO loginDTO = new LoginDTO().setId(user.getId())
                .setCode(ResultCode.SUCCESS.getCode())
                .setToken(token)
                .setExpireTime(expireTime);
        // 转 json
        String result = JSON.toJSONString(loginDTO, SerializerFeature.DisableCircularReferenceDetect);
        // 获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
