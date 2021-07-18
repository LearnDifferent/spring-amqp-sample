package com.example.amqpconsumer.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = FanoutConst.QUEUE_THREE)
public class FanoutQueue3Consumer {

    @RabbitHandler
    public void receive(String msg) {
        System.out.println("FanoutQueue3Consumer get a message: " + msg);
    }
}
