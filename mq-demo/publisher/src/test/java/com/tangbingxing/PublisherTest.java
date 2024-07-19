package com.tangbingxing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class PublisherTest {
    //todo 设置连接参数
    //主机名
    String host = "";
    //端口号
    int port = 5672;
    //vhost
    String virtualHost = "/";
    //用户名
    String userName = "";
    //密码
    String passWord = "";
    //队列名
    String queueName = "simple.queue";
    //消息
    String message = "hello, rabbitmq!";

    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        // 1.建立连接
        ConnectionFactory factory = new ConnectionFactory();
        // 1.1.设置连接参数，分别是：主机名、端口号、vhost、用户名、密码
        factory.setHost(host);
        factory.setPort(port);
        factory.setVirtualHost(virtualHost);
        factory.setUsername(userName);
        factory.setPassword(passWord);
        // 1.2.建立连接
        Connection connection = factory.newConnection();

        // 2.创建通道Channel
        Channel channel = connection.createChannel();

        // 3.创建队列
        channel.queueDeclare(queueName, false, false, false, null);

        // 4.发送消息
        channel.basicPublish("", queueName, null, message.getBytes());
        System.out.println("发送消息成功：【" + message + "】");

        // 5.关闭通道和连接
        channel.close();
        connection.close();

    }
}
