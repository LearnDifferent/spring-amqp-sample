package com.example.amqpconsumer.direct;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DirectConfig {

    // 声明交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DirectConst.EXCHANGE_NAME,
                false,false);
    }

    // direct 的队列使用随机分配的 auto delete + exclusive 队列
    @Bean
    public Queue autoQueue1() {
        return new AnonymousQueue();
    }

    @Bean
    public Queue autoQueue2() {
        return new AnonymousQueue();
    }

    // 绑定（注意添加 routing key）
    // 这里，让 autoQueue1 只有 ROUTING_KEY_1
    @Bean
    public Binding bindingDirect() {
        return BindingBuilder
                .bind(autoQueue1())
                .to(directExchange())
                .with(DirectConst.ROUTING_KEY_1);
    }

    // 这里，让 autoQueue2 有 ROUTING_KEY_1 和 ROUTING_KEY_1
    @Bean
    public Binding bindingDirect1() {
        return BindingBuilder
                .bind(autoQueue2())
                .to(directExchange())
                .with(DirectConst.ROUTING_KEY_1);
    }
    @Bean
    public Binding bindingDirect2() {
        return BindingBuilder
                .bind(autoQueue2())
                .to(directExchange())
                .with(DirectConst.ROUTING_KEY_2);
    }
}

