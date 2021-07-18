package com.example.amqpconsumer.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "#{autoQueue2.name}")
public class DirectConsumer2 {

    @RabbitHandler
    public void receive(String msg) {
        System.out.println("DirectConsumer2 get a message: " + msg);
    }
}
