package com.mark.manager.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class ConnectionManager {
    private Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    // 本静态对象
    private static ConnectionManager instance = new ConnectionManager();
    // 连接表，保存所有连接
    private Map<String, Connection> connectionTable = Collections.synchronizedMap(new HashMap<>());
    private ConnectionFactory connectionFactory;

    public static ConnectionManager getInstance() {
        return instance;
    }
    private ConnectionManager() {
        initialize();
    }
    // 初始化连接参数
    private boolean initialize() {
        InputStream inputStream = null;
        try {
            // 为了拿到properties
            inputStream = Class.forName(Connection.class.getName()).getResourceAsStream("/conf/rabbitmq.properties");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // /conf/rabbitmq.properties得到内容
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            String host = properties.getProperty("rabbitmq.host");
            String port = properties.getProperty("rabbitmq.port");
            String username = properties.getProperty("rabbitmq.username");
            String password = properties.getProperty("rabbitmq.password");
            connectionFactory = new ConnectionFactory();
            connectionFactory.setUsername(username);
            connectionFactory.setPassword(password);
            connectionFactory.setHost(host);
            connectionFactory.setPort(Integer.parseInt(port));
            connectionFactory.setAutomaticRecoveryEnabled(true);
        } catch (IOException ei) {
            logger.error("load rabbitmq failed.", ei);
        }
        return true;
    }
    public Connection getAndCreateConnection(String connectionName) {
        Connection connection = connectionTable.get(connectionName);
        if (connection != null && connection.isOpen()) {
            System.err.println("connection: "+connection);
            return connection;
        }
        // 同步代码，同一时间只能创建一个连接
        synchronized (this) {
            // 取出该连接名对应的连接
            connection = connectionTable.get(connectionName);
            if (connection != null) {
                return connection;
            }
            // 该连接不存在，需要创建
            // 创建完毕放入map，失败则重试
            try {
                connection = connectionFactory.newConnection(connectionName);
                logger.info(connection.toString());
                this.connectionTable.put(connectionName, connection);
            } catch (IOException e) {
                // TODO 可以发邮件通知消息服务器负责人，不能获取连接
                // 增加计数，当获取连接次数达到一定时，可以重启消息服务器
                e.printStackTrace();
            } catch (TimeoutException e) {
                // TODO 可以发邮件通知消息服务器负责人，不能获取连接
                e.printStackTrace();
            }
        }
        return connection;
    }
    public Connection reConnection(String connectionName) {
        Connection connection = null;
        try {
            // 反复尝试连接
            for(;;){
                // 创建连接
                connection = getAndCreateConnection(connectionName);
                // 连接创建成功，返回
                if (connection.isOpen()) {
                    logger.info("connection is created: {}", connection.toString());
                    break;
                }else {
                    System.err.println("connection not open");
                }
                Thread.sleep(100);
            }
        } catch (Exception e) {

        }
        return connection;
    }
}
