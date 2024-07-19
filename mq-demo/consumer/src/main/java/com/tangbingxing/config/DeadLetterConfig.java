package com.tangbingxing.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;

/**
 * @Classname DeadLetterConfig
 * @Description 死信演示：消息消息失败时，进入死信
 * @Version 1.0.0
 * @Date 2024/7/19 18:42
 * @Created by m1346
 */

public class DeadLetterConfig {
    /**
     * 创建死信交换机, 用于消费失败造成的死信
     * @return
     */
    @Bean
    public DirectExchange deadLetterExchange(){
        return new DirectExchange("dl.direct");
    }

    /**
     * 创建死信队列, 用于消费失败造成的死信
     * @return Queue 死信队列
     */
    @Bean
    public Queue deadLetterQueue(){
        return new Queue("dl.queue");
    }

    /**
     * 绑定交换机与队列
     * @param deadLetterExchange
     * @param deadLetterQueue
     * @return Binding
     */
    @Bean
    public Binding deadLetterBinding(DirectExchange deadLetterExchange, Queue deadLetterQueue){
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with("dl");
    }

    /**
     * 创建普通队列，用于演示消费者消费失败时造成死信的那个队列
     * 给队列绑定死信交换机与routingKey
     * @return Queue 普通队列
     */
    @Bean
    public Queue simpleQueue(){
        return QueueBuilder.durable("simple.queue")
                // 绑定死信交换机
                .deadLetterExchange("dl.direct")
                // 绑定死信时的routingKey
                .deadLetterRoutingKey("dl").build();
    }
}
