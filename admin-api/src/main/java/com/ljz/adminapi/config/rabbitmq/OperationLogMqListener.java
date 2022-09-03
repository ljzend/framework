package com.ljz.adminapi.config.rabbitmq;

import cn.hutool.json.JSONUtil;
import com.ljz.adminapi.pojo.OperationLog;
import com.ljz.adminapi.service.OperationLogService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>日志消费者</p>
 *
 * @Author : ljz
 * @Date: 2022/9/3  9:37
 */

@Component
public class OperationLogMqListener {
    @Resource
    private OperationLogService operationLogService;

    @RabbitListener(queues = "success_log_queue")
    public void log(Message message){
        String msg = new String(message.getBody());
        OperationLog operationLog = JSONUtil.toBean(msg, OperationLog.class);
        // System.out.println("======");
        // System.out.println(operationLog);
        operationLogService.save(operationLog);
    }
}
