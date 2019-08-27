package com.rngay.service_rabbitmq;

import com.rngay.service_rabbitmq.producer.SenderProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SenderProducerTest {

    @Autowired
    private SenderProducer senderProducer;

    @Test
    public void testCache() {
        // 测试广播模式
        //senderProducer.broadcast("同学们集合啦！");
        // 测试Direct模式
        //senderProducer.direct("定点消息");

        //senderProducer.news("开封今年粮食产量提升10%");
        //senderProducer.weather("开封明天白天多云15℃");
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        senderProducer.delay("现在是北京时间：" + format);
    }

}
