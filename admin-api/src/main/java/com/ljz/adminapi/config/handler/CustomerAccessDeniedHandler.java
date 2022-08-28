package com.ljz.adminapi.config.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ljz.adminapi.dto.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <p>认证用户访问无权限资源时处理器</p>
 *
 * @Author : ljz
 * @Date: 2022/8/27  16:41
 */

@Component
public class CustomerAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 设置客户端的响应的内容类型
        response.setContentType("application/json;charset=UTF-8");
        // 获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        // 消除循环引用
        String result = JSON.toJSONString(R.error().code(700).message("无权限 访问,请联系管理员！"),
                SerializerFeature.DisableCircularReferenceDetect);
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
