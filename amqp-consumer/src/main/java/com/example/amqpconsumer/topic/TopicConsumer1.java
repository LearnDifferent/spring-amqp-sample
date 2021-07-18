package com.example.amqpconsumer.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 不用配置类的方法，而是在这里直接使用注解来绑定
 */
@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = TopicConst.QUEUE_ONE,
                durable = "false",
                autoDelete = "false"),
        exchange = @Exchange(value = TopicConst.EXCHANGE_NAME,
                type = ExchangeTypes.TOPIC),
        key = TopicConst.KET_TOPIC_ALL)
)
public class TopicConsumer1 {

    @RabbitHandler
    public void receive(String msg) {
        System.out.println("TopicConsumer1 get a message: " + msg);
    }
}
