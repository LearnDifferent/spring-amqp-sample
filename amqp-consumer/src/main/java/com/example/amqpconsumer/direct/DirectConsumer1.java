package com.example.amqpconsumer.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
// Use #{$(bean's name).name} to get the queue name at runtime:
@RabbitListener(queues = "#{autoQueue1.name}")
public class DirectConsumer1 {

    @RabbitHandler
    public void receive(String msg) {
        System.out.println("DirectConsumer1 get a message: " + msg);
    }
}
