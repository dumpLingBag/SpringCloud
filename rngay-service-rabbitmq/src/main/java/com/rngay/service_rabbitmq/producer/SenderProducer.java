package com.rngay.service_rabbitmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SenderProducer {

    private Logger logger = LoggerFactory.getLogger(SenderProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void broadcast(String p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("FANOUT_EXCHANGE", "", p, correlationData);
    }

    public void direct(String p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("DIRECT_EXCHANGE", "DIRECT_ROUTING_KEY", p, correlationData);
    }

    public void news(String p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("TOPIC_EXCHANGE", "KAIFENG.NEWS", p, correlationData);
    }

    public void weather(String p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("TOPIC_EXCHANGE", "KAIFENG.WEATHER", p, correlationData);
    }

    public void delay(String p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("DELAY_EXCHANGE", "", p, message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
            message.getMessageProperties().setDelay(2 * (60*1000)); // 毫秒为单位，指定此消息的延时时长
            return message;
        }, correlationData);
    }

    public void max(String p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("MAX_EXCHANGE", "MAX_ROUTING", p, correlationData);
    }

}
