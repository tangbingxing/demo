package com.tangbingxing.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Classname SpringRabbitListener
 * @Description 监听者
 * @Version 1.0.0
 * @Date 2024/7/19 9:38
 * @Created by m1346
 */
@Component
public class SpringRabbitListener {
    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueueMessage(String msg) {
        System.out.println("spring 消费者接收到消息：【" + msg + "】");
    }
}
