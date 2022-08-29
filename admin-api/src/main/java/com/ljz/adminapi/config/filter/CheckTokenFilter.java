package com.ljz.adminapi.config.filter;

import com.ljz.adminapi.config.constant.SystemConstant;
import com.ljz.adminapi.config.exception.CustomerAuthenticationException;
import com.ljz.adminapi.config.handler.LoginFailureHandler;
import com.ljz.adminapi.service.impl.CustomerUserDetailsService;
import com.ljz.adminapi.utils.JwtUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>验证token是否过期</p>
 *
 * @Author : ljz
 * @Date: 2022/8/29  8:29
 */

@EqualsAndHashCode(callSuper = true)
@Component
@Data
public class CheckTokenFilter extends OncePerRequestFilter {
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private CustomerUserDetailsService customerUserDetailsService;
    @Resource
    private LoginFailureHandler loginFailureHandler;
    @Value("${login.url}")
    private String loginUrl;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    private static final Set<String> WHITE_LIST = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(
                    "/doc.html",
                    "/v2/api-doc",
                    "/swagger-resources",
                    "/webjars",
                    "/favicon.ico")));

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            // 获取当前请求路径
            String requestURI = request.getRequestURI();
            System.out.println(requestURI);
            // 当前请求不是登录请求,
            if (!shouldNotFilter(request) && !requestURI.equals(loginUrl)) {
                validateToken(request);
            }
        } catch (AuthenticationException e) {
            loginFailureHandler.onAuthenticationFailure(request, response, e);
        }
        // 登录请求直接放行
        filterChain.doFilter(request, response);
    }


    /**
     * <p>验证token</p>
     *
     * @param request 请求
     */
    private void validateToken(HttpServletRequest request) {
        // 从请求头中获取token
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            // 从请求头中获取 token 失败, 则从请求参数中获取
            token = request.getParameter("token");
        }
        // 请求头和请求参数中都没有token直接抛出异常
        if (StringUtils.isBlank(token)) {
            throw new CustomerAuthenticationException("token不存在");
        }
        //判断redis中是否存在该token
        String redisToken = (String) redisTemplate.opsForValue().get(SystemConstant.Token.LOGIN_TOKEN_PREFIX + token);
        //如果redis里面没有token,说明该token失效
        if (StringUtils.isBlank(redisToken)) {
            throw new CustomerAuthenticationException("token已过期");
        }
        //如果token和Redis中的token不一致，则验证失败
        if (!token.equals(redisToken)) {
            throw new CustomerAuthenticationException("token验证失败");
        }
        //如果存在token，则从token中解析出用户名
        String username = jwtUtils.getUsernameFromToken(token);
        //如果用户名为空，则解析失败
        if (StringUtils.isBlank(username)) {
            throw new CustomerAuthenticationException("token解析失败");
        }
        //获取用户信息
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
        //判断用户信息是否为空
        if (userDetails == null) {
            throw new CustomerAuthenticationException("token验证失败");
        }
        //创建身份验证对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //设置到Spring Security上下文
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        //过滤掉swagger路径
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        return WHITE_LIST.stream().anyMatch(path::startsWith) || StringUtils.isBlank(path);
    }
}
