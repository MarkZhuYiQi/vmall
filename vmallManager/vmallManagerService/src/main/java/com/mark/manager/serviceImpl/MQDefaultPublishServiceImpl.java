package com.mark.manager.serviceImpl;

import com.mark.manager.config.ConnectionManager;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class MQDefaultPublishServiceImpl implements ShutdownListener {
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private String queueName = "vmallExpiredFIeld";
    private final Object channelLock = new Object();
    private Map<String, Channel> channelTable = Collections.synchronizedMap(new HashMap<String, Channel>());

    public MQDefaultPublishServiceImpl() {}

    public void sendMsg(byte[] body) throws IOException {
        sendMsg(body, queueName);
    }
    public void sendMsg(byte[] body, String queueName) throws IOException {
        // TODO Auto-generated method stub
        Channel channel = getChannel(queueName);
        try {
            channel.basicPublish("", queueName, null, body);
        } catch (IOException | AlreadyClosedException e) {
//            System.err.println(UtilAll.timeMillisToHumanString()+": sendMsg failed");
            System.err.println("sendMsg failed");
        }
    }
    private Channel getChannel(String queueName) throws IOException {
        Channel channel = channelTable.get(queueName);
        if (channel == null) {
            synchronized (channelLock) {
                channel = channelTable.get(queueName);
                if (channel == null) {
                    Connection connection = connectionManager.reConnection(getConnectionName());
                    if (connection != null) {
                        channel = connection.createChannel();
                        channel.queueDeclare(queueName, false, false, false, null);
                        channel.addShutdownListener(this);
                        channelTable.put(queueName, channel);
                    }
                }
            }
        }
        return channel;
    }
    String getConnectionName() {
        return "vmallProducer";
    }
    private void clearChannels() throws IOException, TimeoutException {
        try {
            for (Channel channel:channelTable.values()) {
                channel.removeShutdownListener(this);
                channel.close();
                channel=null;
            }
        } finally {
            channelTable.clear();
        }
    }

    @Override
    public void shutdownCompleted(ShutdownSignalException e) {
        if (e.getReference() instanceof Channel) {
            try {
                clearChannels();
            } catch (TimeoutException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
