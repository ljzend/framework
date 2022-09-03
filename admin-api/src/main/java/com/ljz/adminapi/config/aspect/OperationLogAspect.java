package com.ljz.adminapi.config.aspect;

import cn.hutool.json.JSONUtil;
import com.ljz.adminapi.config.annotation.Log;
import com.ljz.adminapi.dto.R;
import com.ljz.adminapi.pojo.OperationLog;
import com.ljz.adminapi.pojo.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>日志记录切面类</p>
 *
 * @Author : ljz
 * @Date: 2022/9/2  15:01
 */

@Component
@Aspect
public class OperationLogAspect {
    ThreadLocal<Date> currentTime = new ThreadLocal<>();
    ThreadLocal<String> username = new ThreadLocal<>();

    private final RabbitTemplate rabbitTemplate;

    public OperationLogAspect(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Pointcut(value = "@annotation(com.ljz.adminapi.config.annotation.Log)")
    public void pointCut() {

    }

    @Around(value = "pointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取操作用户
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object result;
        currentTime.set(new Date());
        System.out.println("currentTime" + currentTime.get().toString());
        username.set(user.getUsername());
        result = joinPoint.proceed();
        OperationLog operationLog = handle(joinPoint, result);
        System.out.println("operationLog" + operationLog);
        if (operationLog.isSave()) {
            rabbitTemplate.convertAndSend("log_exchange", "success", JSONUtil.toJsonStr(operationLog));
        }
        currentTime.remove();
        username.remove();
        return result;
    }

    @AfterThrowing(pointcut = "pointCut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        OperationLog operationLog = handle1(joinPoint, e);
        System.out.println("operationLog" + operationLog);
        if (operationLog.isSave()) {
            rabbitTemplate.convertAndSend("log_exchange", "success", JSONUtil.toJsonStr(operationLog));
        }
        currentTime.remove();
        username.remove();
    }


    private OperationLog handle(JoinPoint joinPoint, Object result) {
        R r = (R) result;
        Integer code = r.getCode();
        Map<String, String> map = parseParamsJson(joinPoint);
        OperationLog operationLog = new OperationLog();
        operationLog.setUserName(username.get())
                .setOperationName(map.get("operationName"))
                .setSave(map.get("save").equals("true"))
                .setRequestUri(map.get("requestUri"))
                .setMethodType(map.get("methodType"))
                .setRequestParam(map.get("requestParam"))
                .setMethodName(map.get("methodName"))
                .setCreateTime(currentTime.get())
                .setCode(code)
                .setResponseParam(JSONUtil.toJsonStr(r));
        return operationLog;
    }

    private OperationLog handle1(JoinPoint joinPoint, Throwable e) {
        Map<String, String> map = parseParamsJson(joinPoint);
        OperationLog operationLog = new OperationLog();
        operationLog.setUserName(username.get())
                .setOperationName(map.get("operationName"))
                .setSave(map.get("save").equals("true"))
                .setRequestUri(map.get("requestUri"))
                .setMethodType(map.get("methodType"))
                .setRequestParam(map.get("requestParam"))
                .setMethodName(map.get("methodName"))
                .setCreateTime(currentTime.get())
                .setCode(404)
                .setExceptionMsg(e.getMessage());

        return operationLog;
    }


    private Map<String, String> parseParamsJson(JoinPoint joinPoint) {
        Map<String, String> map = new HashMap<>();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获取方法名
        String methodName = method.getDeclaringClass().getName() + "." + method.getName();
        map.put("methodName", methodName);
        // 获取自定义日志注解
        Log annotation = method.getAnnotation(Log.class);
        String operationName = annotation.value();
        map.put("operationName", operationName);
        boolean save = annotation.save();
        map.put("save", save + "");
        // 获取request
        ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        // 请求的路径
        String requestURI = request.getRequestURI();
        map.put("requestUri", requestURI);
        // 请求方法的类型
        String methodType = request.getMethod();
        map.put("methodType", methodType);

        Object[] args = joinPoint.getArgs();
        String[] parameterNames = signature.getParameterNames();
        // 通过map封装参数和参数值
        HashMap<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }
        boolean isContains = paramMap.containsKey("request");
        if (isContains) paramMap.remove("request");
        boolean isContains1 = paramMap.containsKey("response");
        if (isContains1) paramMap.remove("response");
        // 获取请求的参数
        String requestParam = JSONUtil.toJsonStr(paramMap);
        System.out.println("requestParam" + requestParam);
        map.put("requestParam", requestParam);
        return map;
    }
}
