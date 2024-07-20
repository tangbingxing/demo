package com.tangbingxing.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname TTLConfig
 * @Description 测试由消息超时 造成的死信
 * @Version 1.0.0
 * @Date 2024/7/19 20:20
 * @Created by m1346
 */

@Configuration
public class TTLConfig {
    /**
     * 创建超时测试用的交换机
     * @return
     */
    @Bean
    public DirectExchange ttlExchange(){
        return new DirectExchange("ttl.direct");
    }

    /**
     * 创建超时队列
     * 超时队列要绑定死信交换机与routingKey
     * @return
     */
    @Bean
    public Queue ttlQueue(){
        return QueueBuilder.durable("ttl.queue") // 指定队列名称，并持久化
                .ttl(10000) // 设置队列的超时时间，10秒
                .deadLetterExchange("dl.ttl.direct") // 指定死信交换机
                .deadLetterRoutingKey("dl") //绑定死信routingkey
                .build();
    }

    /**
     * 绑定测试交换机与测试用的队列
     * @return
     */
    @Bean
    public Binding ttlBinding(){
        return BindingBuilder.bind(ttlQueue()).to(ttlExchange()).with("ttl");
    }

    /**
     * 创建超时测试用的【死信交换机】
     * @return
     */
    @Bean
    public DirectExchange dlTTLDirect(){
        return new DirectExchange("dl.ttl.direct");
    }

    /**
     * 创建超时测试用的【死信队列】
     * 超时队列要绑定死信交换机与routingKey
     * @return
     */
    @Bean
    public Queue dlTTLQueue(){
        return new Queue("dl.ttl.queue");
    }

    /**
     * 绑定测试交换机与测试用的队列
     * @return
     */
    @Bean
    public Binding dlTTLBinding(DirectExchange dlTTLDirect, Queue dlTTLQueue){
        return BindingBuilder.bind(dlTTLQueue).to(dlTTLDirect).with("dl");
    }
}
