package com.example.amqpproducer.service;

import com.example.amqpproducer.constant.DirectConst;
import com.example.amqpproducer.constant.FanoutConst;
import com.example.amqpproducer.constant.TopicConst;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * fanout 生产者的 Service
 */
@Service
public class RabbitProducerService {

    @Autowired
    private RabbitTemplate template;

    public void fanout(String message) {
        template.convertAndSend(FanoutConst.EXCHANGE_NAME,
                // fanout 的 routing key 为空
                FanoutConst.ROUTING_KEY,
                message);
    }

    public void direct(String message, Integer id) {

        // routing key 默认为：
        String routingKey = DirectConst.ROUTING_KEY_2;

        if (ObjectUtils.nullSafeEquals(id, 1)) {
            // 如果 id 为 1，就改为：
            routingKey = DirectConst.ROUTING_KEY_1;
        }

        template.convertAndSend(DirectConst.EXCHANGE_NAME,
                // direct 记得指定 routing key
                routingKey,
                message);
    }

    public void topic(String msg, String key) {
        if (key == null) key = "";
        template.convertAndSend(TopicConst.EXCHANGE_NAME, key, msg);
    }
}
