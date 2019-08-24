package com.rngay.service_rabbitmq.config;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Receiver {
    /**
     * basicAck接收两个参数
     *
     * deliveryTag（唯一标识ID）它代表了 RabbitMQ 向该 Channel 投递的这条消息的唯一标识 ID，是一个单调递增的正整数，delivery tag 的范围仅限于 Channel
     * multiple 为了减少网络流量，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
     * */

    private static Logger logger = LoggerFactory.getLogger(Receiver.class);

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

}
