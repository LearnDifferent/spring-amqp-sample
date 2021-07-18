package com.example.amqpconsumer.fanout;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// 监听相应队列
@Component
@RabbitListener(queues = FanoutConst.QUEUE_ONE)
public class FanoutQueue1Consumer {

    @RabbitHandler
    public void receive(String msg) {
        System.out.println("FanoutQueue1Consumer get a message: " + msg);
    }
}
