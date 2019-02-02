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
    private static ConnectionManager instance = new ConnectionManager();
    private Map<String, Connection> connectionTable = Collections.synchronizedMap(new HashMap<>());
    private ConnectionFactory connectionFactory;

    public static ConnectionManager getInstance() {
        return instance;
    }
    private ConnectionManager() {
        initialize();
    }
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
        synchronized (this) {
            connection = connectionTable.get(connectionName);
            if (connection != null) {
                return connection;
            }
            try {
                connection = connectionFactory.newConnection(connectionName);
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
            for(;;){
                connection = getAndCreateConnection(connectionName);
                if (connection.isOpen()) {
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
