package com.example.amqpconsumer.dlx;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Dead Letter Exchange 相关配置
 */
@Configuration
public class DeadLetterConfig {

    // 创建任意类型的交换机作为 Dead Letter Exchange（这里选用了 direct）
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DlxConst.EXCHANGE_NAME);
    }

    // 创建 Dead Letter Exchange 的队列
    @Bean
    public Queue dlxQueue() {
        return new Queue(DlxConst.QUEUE);
    }

    // 将队列绑定到 Dead Letter Exchange
    @Bean
    public Binding dlxBinding() {
        return BindingBuilder
                .bind(dlxQueue())
                .to(deadLetterExchange())
                .with(DlxConst.ROUTING_KEY);
    }
}
