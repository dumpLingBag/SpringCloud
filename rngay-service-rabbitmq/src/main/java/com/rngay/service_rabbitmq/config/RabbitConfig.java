package com.rngay.service_rabbitmq.config;

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
    * 2.durable       持久化消息队列 ,rabbitmq重启的时候不需要创建新的队列 默认true
    * 3.auto-delete   表示消息队列没有在使用时将被自动删除 默认是false
    * 4.exclusive     表示该消息队列是否只在当前connection生效,默认是false
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

}
