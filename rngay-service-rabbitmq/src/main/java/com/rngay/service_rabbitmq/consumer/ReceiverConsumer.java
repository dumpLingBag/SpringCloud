package com.rngay.service_rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ReceiverConsumer {
    /**
     * basicAck接收两个参数
     *
     * deliveryTag（唯一标识ID）它代表了 RabbitMQ 向该 Channel 投递的这条消息的唯一标识 ID，是一个单调递增的正整数，delivery tag 的范围仅限于 Channel
     * multiple 为了减少网络流量，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
     * */

    private static Logger logger = LoggerFactory.getLogger(ReceiverConsumer.class);

    @RabbitListener(queues = {"FANOUT_QUEUE_A"})
    public void one(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        logger.info("FANOUT_QUEUE_A " + new String(message.getBody()));
    }

    @RabbitListener(queues = {"FANOUT_QUEUE_B"})
    public void two(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        logger.info("FANOUT_QUEUE_B " + new String(message.getBody()));
    }

    @RabbitListener(queues = {"DIRECT_QUEUE"})
    public void dict(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        logger.info("DIRECT " + new String(message.getBody()));
    }

    @RabbitListener(queues = {"TOPIC_QUEUE_NEWS"})
    public void news(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        logger.info("TOPIC_QUEUE_NEWS " + new String(message.getBody()));
    }

    @RabbitListener(queues = {"TOPIC_QUEUE_WEATHER"})
    public void weather(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        logger.info("TOPIC_QUEUE_WEATHER " + new String(message.getBody()));
    }

    @RabbitListener(queues = {"TOPIC_QUEUE_NEWS_WEATHER"})
    public void newsWeather(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        logger.info("TOPIC_QUEUE_NEWS_WEATHER " + new String(message.getBody()));
    }

    @RabbitListener(queues = {"DELAY_QUEUE"})
    public void delay(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        logger.info("DELAY_QUEUE" + new String(message.getBody()));
    }

}
