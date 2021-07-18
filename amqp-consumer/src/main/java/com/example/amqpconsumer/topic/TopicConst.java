package com.example.amqpconsumer.topic;

public class TopicConst {
    public static final String EXCHANGE_NAME = "topic_exchange";
    public static final String QUEUE_ONE = "topic.queue.one";
    public static final String QUEUE_TWO = "topic.queue.two";
    // 这里使用了：topic.零或多个词
    public static final String KET_TOPIC_ALL = "topic.#";
    // 这里是：恰好一个词.topic
    public static final String KEY_ONE_TOPIC = "*.topic";
    // 这里是：topic.special.恰好一个词
    public static final String KET_TOPIC_SPECIAL_ONE = "topic.special.*";
}
