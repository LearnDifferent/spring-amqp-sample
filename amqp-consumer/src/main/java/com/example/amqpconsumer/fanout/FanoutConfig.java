package com.example.amqpconsumer.fanout;

import com.example.amqpconsumer.dlx.DlxConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FanoutConfig {

    // 声明交换机
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FanoutConst.EXCHANGE_NAME,
                false, false);
    }

    // 声明队列（这个队列为 org.springframework.amqp.core.Queue）

    /**
     * 这个队列比较特殊，设置了队列内消息的 TTL。如果消息到期后，会被
     *
     * @return 队列
     */
    @Bean
    public Queue queueOneFanout() {

        // 可以传入一些 args 来配置队列。
        Map<String, Object> args = new HashMap<>();

        /*
        这里放入消息过期时间的参数：

        key 也就是 argument 的名称，必须为 x-message-ttl
        value 也就是过期时间，必须为 int 类型
        .put("x-message-ttl", 5000) 表示过期时间为 5000 毫秒
        */
        args.put("x-message-ttl", 5000);

        /*
        如果消息过期了，就将消息转移到 Dead Letter Exchange 中：

        key 是固定的：x-dead-letter-exchange
        value 是 Dead Letter Exchange 的名称
        */
        args.put("x-dead-letter-exchange", DlxConst.EXCHANGE_NAME);

        /*
        如果 Dead Letter Exchange 需要 routing key 的话
        （除了 fanout，都需要），也要放入其中：

        key 是：x-dead-letter-routing-key
        value 是：相应的 routing key
        */
        args.put("x-dead-letter-routing-key", DlxConst.ROUTING_KEY);

        // 最后，记得在最后一个 parameter 中，传入 args
        return new Queue(FanoutConst.QUEUE_ONE,
                false, false, false, args);
    }

    @Bean
    public Queue queueTwoFanout() {
        return new Queue(FanoutConst.QUEUE_TWO);
    }

    @Bean
    public Queue queueThreeFanout() {
        return new Queue(FanoutConst.QUEUE_THREE);
    }

    // 声明绑定关系：将队列绑定到交换机
    @Bean
    public Binding bindingOneFanout() {
        return BindingBuilder
                .bind(queueOneFanout())
                .to(fanoutExchange());
    }

    @Bean
    public Binding bindingTwoFanout() {
        return BindingBuilder
                .bind(queueTwoFanout())
                .to(fanoutExchange());
    }

    @Bean
    public Binding bindingThreeFanout() {
        return BindingBuilder
                .bind(queueThreeFanout())
                .to(fanoutExchange());
    }
}

