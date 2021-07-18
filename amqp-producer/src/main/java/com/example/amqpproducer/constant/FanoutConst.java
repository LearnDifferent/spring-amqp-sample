package com.example.amqpproducer.constant;

public class FanoutConst {

    public static final String EXCHANGE_NAME = "fanout_exchange";
    // fanout 的 routing key 为空
    public static final String ROUTING_KEY = "";
    // 队列
    public static final String QUEUE_ONE = "fanout.queue.one";
    public static final String QUEUE_TWO = "fanout.queue.two";
    public static final String QUEUE_THREE = "fanout.queue.three";
}
