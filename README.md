# 创建项目及配置文件

使用 IDEA 创建 Spring Boot 项目，勾选 Web 的 Spring Web 和 Messaging 的 Spring for RabbitMQ。

因为需要创建生产者和消费者两个模块，所以在创建好后，要删除包括 src 在内的其他文件，只保留 pom.xml 和 .idea 文件夹。

然后分别创建 Spring Boot 子模块 amqp-producer 和 amqp-consumer，并修改 [amqp-producer 的 pom.xml](./amqp-producer/pom.xml) 和 [amqp-consumer 的 pom.xml](./amqp-consumer/pom.xml) （具体的修改需要点开文件查看）

YAML 配置文件中配置了 RabbitMQ 相关信息和端口号，其中 amqp-producer 模块的端口号为 8091，amqp-consumer 模块的端口号为 8090。

# 项目相关说明

## 配置类说明

因为这个项目的 Configuration 类只在 amqp-consumer 模块中有（比如 [DirectConfig.java](./amqp-consumer/src/main/java/com/example/amqpconsumer/direct/DirectConfig.java) 和 [FanoutConfig.java](./amqp-consumer/src/main/java/com/example/amqpconsumer/fanout/FanoutConfig.java) 等配置类都在 amqp-consumer 模块中），所以要先启动该模块才会加载配置类去创建交换机和队列。

同理，如果只在 amqp-producer 模块中有配置类的话，就先启动该模块。

也可以在全部模块内，使用内容相同的 Configuration 类，这样就不用考虑启动顺序了。

## 演示功能

配置方式：

- fanout 和 direct 模式使用的是配置类的方式
- topic 模式使用的注解方式

TTL 和 Dead Letter Exchange：

- 设置队列内的消息的过期时间的方法：
	- 参考 [FanoutConfig.java](./amqp-consumer/src/main/java/com/example/amqpconsumer/fanout/FanoutConfig.java) 中的 `public Queue queueOneFanout()` 
	- 例子中有 Dead Letter 相关的设置，如果该队列内的消息过期后，会放入 Dead Letter 的队列中
- 设置具体某条消息的过期时间的方法：
	- 参考 [RabbitProducerService.java](./amqp-producer/src/main/java/com/example/amqpproducer/service/RabbitProducerService.java) 中的 `public void direct(String msg, Integer id)` 的 if 判断语句里面的内容
	- 这个例子中没有 Dead Letter 相关的设置，所以过期后会直接被抛弃

- Dead Letter Exchange 相关配置，可以查看 [DeadLetterConfig.java](./amqp-consumer/src/main/java/com/example/amqpconsumer/dlx/DeadLetterConfig.java)

> 测试 TTL 的时候，需要复制相应的配置类到生产者的模块（因为生产者模块没有配置类），然后启动生产者模块，发送消息，观察消息是否到期后会失效

# TTL 和 Dead Letter Exchange 相关知识补充

## TTL 过期时间

TTL：

1. 队列内的消息设置过期时间：消息在队列中存活的时间，时间到了就失效
2. 具体某条消息的过期时间：某条消息在时间到了之后，自己失效

## Dead Letter Exchange

下文摘抄自[官方教程](https://www.rabbitmq.com/dlx.html)

Messages from a queue can be "dead-lettered"; that is, republished to an exchange when any of the following events occur:

- 消息被拒绝：The message is [negatively acknowledged](https://www.rabbitmq.com/confirms.html) by a consumer using basic.reject or basic.nack with requeue parameter set to false
- 消息过期（参数名为：x-message-ttl）：The message expires due to [per-message TTL](https://www.rabbitmq.com/ttl.html)
- 消息超过最大长度（参数名为：x-max-length）：The message is dropped because its queue exceeded a [length limit](https://www.rabbitmq.com/maxlength.html)

Note that expiration of a queue will not dead letter the messages in it.（因为队列失效的消息，不计入中）

Dead letter exchanges (DLXs) are normal exchanges. They can be any of the usual types and are declared as usual.

For any given queue, a DLX can be defined by clients using the [queue's arguments](https://www.rabbitmq.com/queues.html#optional-arguments), or in the server using [policies](https://www.rabbitmq.com/parameters.html#policies). In the case where both policy and arguments specify a DLX, the one specified in arguments overrules the one specified in policy.

等消息被转移到 Dead Letter Exchange 的死信队列后，可以像平时监听普通队列的消费者那样，使用 `@RabbitListener` 和 `@RabbitHandler` 去消费 Dead Letter 相关的消息。

# RabbitMQ 相关知识

GitHub 链接：

- [RabbitMQ.md](https://github.com/LearnDifferent/my-notes/blob/master/RabbitMQ%E7%AC%94%E8%AE%B0.md)
- [my-rabbitmq-java-exercises](https://github.com/LearnDifferent/my-rabbitmq-java-exercises)