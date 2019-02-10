package com.mark.manager.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChannelManager {

    private Logger logger = LoggerFactory.getLogger(ChannelManager.class);
    // 连接管理器
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    // 频道列表
    private Map<String, Channel> channelMap = Collections.synchronizedMap(new HashMap<>());
    private String connectionName = "vmallExpiredProducer";

    public Map<String, Channel> getChannelMap() {
        return channelMap;
    }

    public void setChannelMap(Map<String, Channel> channelMap) {
        this.channelMap = channelMap;
    }

    /**
     * 频道获取
     * @param queueName 队列名称
     * @return
     * @throws IOException
     */
    private Channel getChannel(String queueName) throws IOException {
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
                    }
                }
            }
        }
        return  channel;
    }
}
