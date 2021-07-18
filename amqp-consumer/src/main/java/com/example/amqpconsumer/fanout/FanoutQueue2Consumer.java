package com.example.amqpconsumer.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = FanoutConst.QUEUE_TWO)
public class FanoutQueue2Consumer {

    @RabbitHandler
    public void receive(String msg) {
        System.out.println("FanoutQueue2Consumer get a message: " + msg);
    }
}
