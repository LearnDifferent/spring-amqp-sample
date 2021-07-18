package com.example.amqpconsumer.fanout;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

    // 声明交换机
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FanoutConst.EXCHANGE_NAME,
                false, false);
    }

    // 声明队列（这个类的包为 org.springframework.amqp.core.Queue）
    // 这里设定三个队列
    @Bean
    public Queue queueOneFanout() {
        return new Queue(FanoutConst.QUEUE_ONE);
    }

    @Bean
    public Queue queueTwoFanout() {
        return new Queue(FanoutConst.QUEUE_TWO);
    }

    @Bean
    public Queue queueThreeFanout() {
        return new Queue(FanoutConst.QUEUE_THREE);
    }

    // 声明绑定关系：将队列绑定到交换机
    @Bean
    public Binding bindingOneFanout() {
        return BindingBuilder
                .bind(queueOneFanout())
                .to(fanoutExchange());
    }

    @Bean
    public Binding bindingTwoFanout() {
        return BindingBuilder
                .bind(queueTwoFanout())
                .to(fanoutExchange());
    }

    @Bean
    public Binding bindingThreeFanout() {
        return BindingBuilder
                .bind(queueThreeFanout())
                .to(fanoutExchange());
    }
}

