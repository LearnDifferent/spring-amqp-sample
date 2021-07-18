package com.example.amqpproducer.service;

import com.example.amqpproducer.constant.DirectConst;
import com.example.amqpproducer.constant.FanoutConst;
import com.example.amqpproducer.constant.TopicConst;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
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

    public void direct(String msg, Integer id) {

        // routing key 默认为：
        String routingKey = DirectConst.ROUTING_KEY_2;

        if (ObjectUtils.nullSafeEquals(id, 1)) {
            // 如果 id 为 1，就改为：
            routingKey = DirectConst.ROUTING_KEY_1;

            // 这里，演示给具体的消息设置过期时间：
            // 使用 MessagePostProcessor 可以给消息设置过期时间和 UTF-8 编码之类的配置
            MessagePostProcessor mpp = message -> {
                message.getMessageProperties().setExpiration("5000");
                return message;
            };

            // 最后一个参数记得传入消息的配置
            template.convertAndSend(DirectConst.EXCHANGE_NAME,
                    routingKey, msg, mpp);
            return;
        }

        // 默认情况，直接发送（这里没有演示配置消息）
        // direct 模式记得指定第二个参数 routing key
        template.convertAndSend(DirectConst.EXCHANGE_NAME, routingKey, msg);
    }

    public void topic(String msg, String key) {
        if (key == null) key = "";
        template.convertAndSend(TopicConst.EXCHANGE_NAME, key, msg);
    }
}
