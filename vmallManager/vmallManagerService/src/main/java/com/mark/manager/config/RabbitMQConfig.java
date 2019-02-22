package com.mark.manager.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mark.common.exception.CategoryException;
import com.mark.common.exception.CourseException;
import com.mark.common.util.Utils;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.Courses;
import com.mark.manager.mq.ExpiredMessageListener;
import com.mark.manager.service.CategoryService;
import com.mark.manager.service.CourseService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Mark
 * @version 1.0
 */
@Configuration
public class RabbitMQConfig {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);
    @Value("${rabbitmq.host}")
    String host;

    @Value("${rabbitmq.port}")
    String port;

    @Value("${rabbitmq.username}")
    String username;

    @Value("${rabbitmq.password}")
    String password;

    @Value("${rabbitmq.directExchange}")
    String directExchange;

    @Value("${rabbitmq.topicExchange}")
    String topicExchange;

    @Value("${rabbitmq.channel}")
    String channel;

    @Value("${rabbitmq.bindingkey}")
    String bindingKey;

    @Value("${rabbitmq.topickey}")
    String topickey;

    @Value("${rabbitmq.topicQueue}")
    String topicQueue;

    @Value("${rabbitmq.directQueue}")
    String queueName;

    @Value("${rabbitmq.queuenames}")
    String queueNames;

    @Value("${rabbitmq.listener.class}")
    String listenerClass;

    /**
     * @return CachingConnectionFactory
     */
    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory(host, Integer.valueOf(port));
        factory.setUsername(username);
        factory.setPassword(password);
//        factory.setPublisherConfirms(true);
//        factory.setPublisherReturns(true);
        return factory;
    }
    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    //AmqpTemplate配置，AmqpTemplate接口定义了发送和接收消息的基本操作
    public RabbitTemplate rabbitTemplate() {
        CachingConnectionFactory connectionFactory = connectionFactory();
        connectionFactory.setPublisherReturns(true);
        connectionFactory.setPublisherConfirms(true);
        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                logger.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                logger.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
            }
        });
        // retry相关配置
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        rabbitTemplate.setRetryTemplate(retryTemplate);
        rabbitTemplate.setRoutingKey(queueName);
        return  rabbitTemplate;
    }

    // queue initialize
    @Bean
    public Queue myQueue() {
        return new Queue(queueName);
    }

    @Bean
    public Queue topicQueue() {
        return new Queue(topicQueue);
    }
    // exchange initialize
    @Bean
    public TopicExchange vmallTopicExchange() {
        return new TopicExchange(topicExchange, true, false);
    }
    // binding topic
    @Bean
    public Binding topicBinding() {
        return BindingBuilder.bind(topicQueue()).to(vmallTopicExchange()).with(topickey);
    }
    // directExchange
    @Bean
    public DirectExchange vmallExpiredExchange() {
        return new DirectExchange(directExchange, true, false);
    }
    // bindingkey
    @Bean
    public Binding expiredBinding() {
        return BindingBuilder.bind(myQueue()).to(vmallExpiredExchange()).with(bindingKey);
    }
    @Bean
    public ChannelAwareMessageListener channelAwareMessageListener() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return  (ChannelAwareMessageListener) Class.forName(listenerClass).newInstance();
    }
    @Bean
//    queue listener  观察 监听模式 当有消息到达时会通知监听在对应的队列上的监听对象
    // 这是一个container，作为exchange和queue的桥接
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, ChannelAwareMessageListener channelAwareMessageListener){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        container.setMessageListener(channelAwareMessageListener);
//        批量关注队列的方法
//        Queue[] queues=new Queue[queueNames.split(",").length];
//        for (int i = 0; i < queues.length; i++) {
//            System.out.println(queueNames.split(",")[i]);
//            Queue queue=new Queue(queueNames.split(",")[i]);
//            queues[i]=queue;
//        }
//        container.setQueues(queues);
        container.setQueues(myQueue());
        container.setConsumerArguments(Collections. <String, Object> singletonMap("x-priority",
                Integer.valueOf(10)));
        return container;
    }

    @Autowired
    private ExpiredMessageListener expiredMessageListener;

    /**
     * @return SimpleMessageListenerContainer
     */
    @Bean
    public SimpleMessageListenerContainer topicContainer() {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory());
        simpleMessageListenerContainer.setMaxConcurrentConsumers(1);
        simpleMessageListenerContainer.setConcurrentConsumers(1);
        // 设置队列
        simpleMessageListenerContainer.setQueues(topicQueue());
        // 设置结果知晓模式
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // 设置监听器
//        simpleMessageListenerContainer.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                byte[] body = message.getBody();
//                System.out.println("receive message from topicQueue: " + new String(body));
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//            }
//        });
        simpleMessageListenerContainer.setMessageListener(expiredMessageListener);
        return simpleMessageListenerContainer;
    }
}
