package com.mark.manager.serviceImpl;

import com.mark.manager.service.TestService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class TestServiceImpl implements TestService {
    private final static String QUEUE_NAME = "hello";

    @Override
    public Long directExchange() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("223.112.88.214");
        factory.setPort(8672);
        factory.setPassword("7777777y");
        factory.setUsername("mark");
        // try-with-resource 语法糖，将创建过程写在try里头，如果他实现了AutoCloseable接口，自动关闭连接
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            // queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
            // 队列名称 | 是否持久化 | 是否为独占队列（创建者自用，退出就删除） | 所有消费者退出后是否删除队列 | 队列其他参数
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "hello world";
            // basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("Sent: " + message);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (long)1;
    }

    @Override
    public Long directExchangeReceiving() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("223.112.88.214");
        factory.setPort(8672);
        factory.setPassword("7777777y");
        factory.setUsername("mark");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("received: " + message);
        };
        // basicConsume(String queue, boolean autoAck, DeliverCallback deliverCallback, CancelCallback cancelCallback)
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag-> {});
        return (long)1;
    }
}
