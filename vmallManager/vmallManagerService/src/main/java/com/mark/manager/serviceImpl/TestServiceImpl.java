package com.mark.manager.serviceImpl;

import com.mark.manager.config.ConnectionManager;
import com.mark.manager.service.TestService;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class TestServiceImpl implements TestService {
    private Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);
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





    // 连接管理器
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    // 频道列表
    private Map<String, Channel> channelMap = Collections.synchronizedMap(new HashMap<>());
    private String connectionName = "workQueuesProducer";

    /**
     * 频道获取
     * @param queueName 队列名称
     * @param durable 是否持久化消息
     * @return
     * @throws IOException
     */
    private Channel getChannel(String queueName, boolean durable) throws IOException {
        Channel channel = channelMap.get(queueName);
        // 频道不存在
        if (channel == null) {
            // 单线程执行
            synchronized (this) {
                // 再次尝试判断是否存在以防在此时发生改变
                channel = channelMap.get(queueName);
                // 确认没有
                if (channel == null) {
                    Connection connection = connectionManager.reConnection(connectionName);
                    if (connection != null) {
                        channel = connection.createChannel();
                        channel.queueDeclare(queueName, durable, false, false, null);
                        channelMap.put(queueName, channel);
                    }
                }
            }
        }
        return  channel;
    }
    private Channel getChannelWithoutDeclare() throws IOException {
        Channel channel = null;
        // 单线程执行
        synchronized (this) {
            // 确认没有
            if (channel == null) {
                Connection connection = connectionManager.reConnection(connectionName);
                if (connection != null) {
                    channel = connection.createChannel();
                }
            }
        }
        return  channel;
    }

    @Override
    public void workQueues(String message) {
        String mes = String.join(" ", message);
        try {
            Channel channel = getChannel(QUEUE_NAME, true);
//            Channel channel = getChannelWithoutDeclare();
//            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            logger.info(channel.toString());
            // basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, mes.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + mes + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 多个消费者，默认是轮询模式，round-robin-dispatching
     * 消费者轮流获得发布的工作对象
     * Ack：acknowledgments, 消费者成功接受信息后会返回一个信息给生产者
     * 如果没有收到ack，rabbitmq会认为该消息发送失败，重新分配
     * 设置autAck = false，改成手动发送确认信息，再任务处理完成后再告诉生产者这个消息处理成功。
     */
    @Override
    public void workQueuesReceiving() {
        Channel channel = null;
        try {
            channel = getChannel(QUEUE_NAME, true);
//            channel = getChannelWithoutDeclare();
//            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            // 同一时间只有一条内容在处理
            channel.basicQos(1);
            logger.info("channel: {}", channel.toString());
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            Channel finalChannel = channel;
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("received: " + message);
                try {
                    doWork(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("done");
                    logger.info("receiving completed!");
                    // basicAck忘记是一件致命的事情，越来越多的未确认信息会被堆积
                    finalChannel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };
            // basicConsume(String queue, boolean autoAck, DeliverCallback deliverCallback, CancelCallback cancelCallback)
            boolean autoAck = false;
            // 关闭自动确认消息，需要手动确认，通过上面的basicAck确认
            channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag-> {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void doWork(String task) throws InterruptedException {
        for(char c : task.toCharArray()) {
            if (c == '.') TimeUnit.SECONDS.sleep(1);
        }
    }

    private static final String EXCHANGE_NAME = "logs";
    @Override
    public void publish() {
        try {
            Channel channel = getChannelWithoutDeclare();
            // 开启一个交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String message = "info: hello world!";
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribe() {
        try {
            Channel channel= getChannelWithoutDeclare();
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            // 自动产生的队列名称。
            String queueName = channel.queueDeclare().getQueue();
            logger.info("subscribe queue name: {}", queueName);
            channel.queueBind(queueName, EXCHANGE_NAME, "");
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private final static String TOPIC_EXCHANGE = "topics";
    @Override
    public void topic() {
        try {
            Channel channel = getChannelWithoutDeclare();
            channel.exchangeDeclare(TOPIC_EXCHANGE, "topic");
            String routingKey = "vmallExpired";
            String message = "66666";
            channel.basicPublish(TOPIC_EXCHANGE, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
            logger.info("message has beeen sent");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void topicReceiving() {
        try {
            Channel channel = getChannelWithoutDeclare();
            channel.exchangeDeclare(TOPIC_EXCHANGE, "topic");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, TOPIC_EXCHANGE, "#");
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" +
                        delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
