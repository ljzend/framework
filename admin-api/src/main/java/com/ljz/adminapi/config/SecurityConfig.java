package com.ljz.adminapi.config;

import com.ljz.adminapi.config.filter.CheckTokenFilter;
import com.ljz.adminapi.config.handler.AnonymousAuthenticationHandler;
import com.ljz.adminapi.config.handler.CustomerAccessDeniedHandler;
import com.ljz.adminapi.config.handler.LoginFailureHandler;
import com.ljz.adminapi.config.handler.LoginSuccessHandler;
import com.ljz.adminapi.service.impl.CustomerUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * <p>security配置类</p>
 * @Author : ljz
 * @Date: 2022/8/27  16:46
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final String[] whiteList = {
            "/api/user/login",
            "/doc.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/v2/**",
            "/api/**",
            "/favicon.ico"
    };
    @Resource
    private CustomerUserDetailsService customerUserDetailsService;
    @Resource
    private LoginSuccessHandler loginSuccessHandler;
    @Resource
    private LoginFailureHandler loginFailureHandler;
    @Resource
    private AnonymousAuthenticationHandler anonymousAuthenticationHandler;
    @Resource
    private CustomerAccessDeniedHandler customerAccessDeniedHandler;
    @Resource
    private CheckTokenFilter checkTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 添加过滤器
        http.addFilterBefore(checkTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //登录前进行过滤
        http.formLogin()
                //　设置登录验证成功或失败后的的跳转地址
                .loginProcessingUrl("/api/user/login")
                // 登录认证成功处理器
                .successHandler(loginSuccessHandler)
                // 登录认证失败处理器
                .failureHandler(loginFailureHandler)
                // 禁用csrf防御机制
                .and()
                .csrf()
                .disable()
                // security 禁用 session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 白名单放行
                .antMatchers(whiteList)
                .permitAll()
                // 其他请求全部需要认证
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                // 匿名用户无法访问
                .authenticationEntryPoint(anonymousAuthenticationHandler)
                // 认证用户无权限访问
                .accessDeniedHandler(customerAccessDeniedHandler)
                .and().cors();//开启跨域配置
    }

    /**
     * 配置认证处理器
     * @param auth 权限验证构造器
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
