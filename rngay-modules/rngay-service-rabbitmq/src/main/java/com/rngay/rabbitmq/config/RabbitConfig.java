package com.rngay.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
    * 
    * @Author pengcheng
    * @Date 2019/8/24
    **/
    @Bean
    public AmqpTemplate amqpTemplate() {
        Logger logger = LoggerFactory.getLogger(RabbitTemplate.class);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");
        // 消息发送失败返回到队列中，yml需要配置 publisher-returns: true
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            logger.info("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
        });
        // 消息确认，yml需要配置 publisher-confirms: true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                logger.info("消息发送到exchange成功,id: {}", correlationData.getId());
            } else {
                logger.info("消息发送到exchange失败,原因: {}", cause);
            }
        });
        return rabbitTemplate;
    }

    /**
    * 声明Direct交换机 支持持久化.
    * @Author pengcheng
    * @Date 2019/8/23
    **/
    @Bean("directExchange")
    public DirectExchange directExchange() {
        return (DirectExchange) ExchangeBuilder.directExchange("DIRECT_EXCHANGE").durable(true).build();
    }

    /**
    * 声明一个队列 Queue 可以有四个参数 支持持久化.
    *
    * 1.队列名
    * 2.durable       持久化消息队列，rabbitmq重启的时候不需要创建新的队列 默认true
    * 3.auto-delete   若没有消费者订阅该队列，队列将被删除 默认是false
    * 4.exclusive     是否声明该队列是否为连接独占，若为独占，连接关闭后队列即被删除 默认是false
    * 5.arguments     可选map类型参数，可以指定队列长度，消息生存时间，镜相设置等
    *
    * @Author pengcheng
    * @Date 2019/8/23
    **/
    @Bean("directQueue")
    public Queue directQueue() {
        return QueueBuilder.durable("DIRECT_QUEUE").build();
    }

    /**
    * 通过绑定键 将指定队列绑定到一个指定交换机
    * @Author pengcheng
    * @Date 2019/8/23
    **/
    @Bean
    public Binding directBinding(@Qualifier("directQueue") Queue queue,
                                 @Qualifier("directExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("DIRECT_ROUTING_KEY").noargs();
    }

    /**
    * 声明 fanout 交换机
    * @Author pengcheng
    * @Date 2019/8/23
    **/
    @Bean("fanoutExchange")
    public FanoutExchange fanoutExchange() {
        return (FanoutExchange) ExchangeBuilder.fanoutExchange("FANOUT_EXCHANGE").durable(true).build();
    }

    @Bean("fanoutQueueA")
    public Queue fanoutQueueA() {
        return QueueBuilder.durable("FANOUT_QUEUE_A").build();
    }

    @Bean("fanoutQueueB")
    public Queue fanoutQueueB() {
        return QueueBuilder.durable("FANOUT_QUEUE_B").build();
    }

    @Bean
    public Binding bindingA(@Qualifier("fanoutQueueA") Queue queue,
                            @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    public Binding bindingB(@Qualifier("fanoutQueueB") Queue queue,
                            @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean("topicExchange")
    public TopicExchange topicExchange() {
        return (TopicExchange) ExchangeBuilder.topicExchange("TOPIC_EXCHANGE").durable(true).build();
    }

    @Bean("topicQueueNews")
    public Queue topicQueueNews() {
            return QueueBuilder.durable("TOPIC_QUEUE_NEWS").build();
    }

    @Bean("topicQueueWeather")
    public Queue topicQueueWeather() {
        return QueueBuilder.durable("TOPIC_QUEUE_WEATHER").build();
    }

    @Bean("topicQueueNewsWeather")
    public Queue topicQueueAB() {
        return QueueBuilder.durable("TOPIC_QUEUE_NEWS_WEATHER").build();
    }

    @Bean
    public Binding bindingTopicA(@Qualifier("topicQueueNews") Queue queue,
                                 @Qualifier("topicExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("*.NEWS").noargs();
    }

    @Bean
    public Binding bindingTopicB(@Qualifier("topicQueueWeather") Queue queue,
                                 @Qualifier("topicExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("*.WEATHER").noargs();
    }

    @Bean
    public Binding bindingTopicC(@Qualifier("topicQueueNewsWeather") Queue queue,
                                 @Qualifier("topicExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("KAIFENG.*").noargs();
    }

    /**
     * 定义延时队列
     *
     * Argument ：
     *      1.x-message-ttl 发送到队列的消息在丢弃之前可以存活多长时间（毫秒）。
     *      2.x-expires 队列在被自动删除（毫秒）之前可以使用多长时间。
     *      3.x-max-length 队列在开始从头部删除之前可以包含多少就绪消息。
     *      4.x-max-length-bytes 队列在开始从头部删除之前可以包含的就绪消息的总体大小。
     *      5.x-dead-letter-exchange 设置队列溢出行为。这决定了在达到队列的最大长度时消息会发生什么。有效值为drop-head或reject-publish。交换的可选名称，如果消息被拒绝或过期，将重新发布这些名称。
     *      6.x-dead-letter-routing-key 可选的替换路由密钥，用于在消息以字母为单位时使用。如果未设置，将使用消息的原始路由密钥。
     *      7.x-max-priority 队列支持的最大优先级数;如果未设置，队列将不支持消息优先级。
     *      8.x-queue-mode 将队列设置为延迟模式，在磁盘上保留尽可能多的消息以减少内存使用;如果未设置，队列将保留内存缓存以尽快传递消息。
     *      9.x-queue-master-locator 将队列设置为主位置模式，确定在节点集群上声明时队列主机所在的规则。
     * */
    @Bean("delayQueue")
    public Queue delayQueue() {
        return QueueBuilder.durable("DELAY_QUEUE").build();
    }

    @Bean("delayExchange")
    public FanoutExchange delayExchange() {
        return (FanoutExchange) ExchangeBuilder.fanoutExchange("DELAY_EXCHANGE").durable(true)
                .delayed().withArgument("x-delayed-type", "direct").build();
    }

    @Bean
    public Binding bindingDelay(@Qualifier("delayQueue") Queue queue,
                                @Qualifier("delayExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    /**
     *
     * 定义指定长度队列
     *
     * */
    @Bean("maxQueue")
    public Queue maxQueue() {
        return QueueBuilder.durable("MAX_QUEUE").build();
    }

    @Bean("maxExchange")
    public Exchange maxExchange() {
        return ExchangeBuilder.directExchange("MAX_EXCHANGE").durable(true).withArgument("x-max-length", 5).build();
    }

    @Bean
    public Binding bindingMaxQueue(@Qualifier("maxQueue") Queue queue,
                                   @Qualifier("maxExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("MAX_ROUTING").noargs();
    }

}
