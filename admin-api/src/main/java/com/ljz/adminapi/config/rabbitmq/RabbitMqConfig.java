package com.ljz.adminapi.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>rabbitmq配置类</p>
 *
 * @Author : ljz
 * @Date: 2022/9/2  15:58
 */

@Configuration
public class RabbitMqConfig {
    /**
     * 交换机名称
     */
    private static final String EXCHANGE_NAME = "log_exchange";
    /**
     * 队列名称
     */
    private static final String QUEUE_NAME = "success_log_queue";

    private static final String QUEUE_NAME1 = "error_log_queue";

    /**
     * 声明一个直接交换机
     * @return DirectExchange
     */
    @Bean("logExchange")
    public DirectExchange logExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_NAME).durable(true).build();
    }

    /**
     * 声明一个队列
     * @return Queue
     */
    @Bean("successLogQueue")
    public Queue successLogQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    /**
     * 声明一个队列
     * @return Queue
     */
    // @Bean("errorLogQueue")
    // public Queue errorLogQueue() {
    //     return QueueBuilder.durable(QUEUE_NAME1).build();
    // }

    /**
     * successLogQueue 队列和 logExchange 交换机绑定
     * @param logExchange 交换机
     * @param successLogQueue 队列
     * @return Binding
     */
    @Bean
    public Binding successLogBinding(@Qualifier("logExchange") Exchange logExchange,
                                     @Qualifier("successLogQueue") Queue successLogQueue) {
        return BindingBuilder.bind(successLogQueue).to(logExchange).with("success").noargs();
    }
}
