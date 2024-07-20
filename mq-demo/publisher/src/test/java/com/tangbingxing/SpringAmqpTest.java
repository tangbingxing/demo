package com.tangbingxing;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;

/**
 * @Classname SpringAmqpTest
 * @Description SpringAmqp测试类
 * @Version 1.0.0
 * @Date 2024/7/19 9:35
 * @Created by m1346
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringAmqpTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //简单队列
    @Test
    public void testSimpleQueue() {
        // 队列名称
        String queueName = "simple.queue";
        // 消息
        String message = "hello, spring amqp!";
        // 发送消息
        rabbitTemplate.convertAndSend(queueName, message);
    }

    //工作队列，模拟消息堆积
    @Test
    public void testWorkQueue() throws InterruptedException {
        // 队列名称
        String queueName = "simple.queue";
        // 消息
        String message = "hello, message_";
        for (int i = 0; i < 50; i++) {
            // 发送消息
            rabbitTemplate.convertAndSend(queueName, message + i);
            Thread.sleep(20);
        }
    }

    //广播模式
    @Test
    public void testFanoutExchange() {
        // 队列名称
        String exchangeName = "tangbingxing.fanout";
        // 消息
        String message = "hello, everyone!";
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }

    //路由模式
    @Test
    public void testSendDirectExchange() {
        // 交换机名称
        String exchangeName = "tangbingxing.direct";
        // 消息
        String message = "红色警报！日本乱排核废水，导致海洋生物变异，惊现哥斯拉！";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "red", message);
    }

    //主题模式
    @Test
    public void testSendTopicExchange() {
        // 交换机名称
        String exchangeName = "tangbingxing.topic";
        // 消息
        String message = "喜报！孙悟空大战哥斯拉，胜!";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "china.news", message);
    }

    //测试死信消息
    @Test
    public void testSendMsgForDeadLetter(){
        rabbitTemplate.convertAndSend("simple.queue", "dead letter test");
    }

    //测试队列延迟
    @Test
    public void testTTLQueue() {
        // 创建消息
        String message = "hello, ttl queue";
        // 发送消息
        rabbitTemplate.convertAndSend("ttl.direct", "ttl", message);
        // 记录日志
        log.debug("发送消息成功");
    }


    //测试消息延迟
    @Test
    public void testTTLMsg() {
        // 创建消息
        String message = "hello, ttl msg";
        Message msg = MessageBuilder.withBody(message.getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                // 字符串5000 = 5s -> 队列10s, 消息是5s
                .setExpiration("5000")
                .build();
        // 发送消息
        rabbitTemplate.convertAndSend("ttl.direct", "ttl", msg);
        // 记录日志
        log.debug("发送消息成功");
    }
}
