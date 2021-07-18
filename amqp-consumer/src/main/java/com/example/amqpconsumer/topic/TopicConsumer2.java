package com.example.amqpconsumer.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = TopicConst.QUEUE_TWO, durable = "false", autoDelete = "false"),
        exchange = @Exchange(value = TopicConst.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
        key = {TopicConst.KEY_ONE_TOPIC, TopicConst.KET_TOPIC_SPECIAL_ONE}
))
public class TopicConsumer2 {

    @RabbitHandler
    public void receive(String msg) {
        System.out.println("TopicConsumer2 get a message: " + msg);
    }
}
