package com.mark.manager.config;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ElasticsearchPool implements PooledObjectFactory<TransportClient> {
    @Override
    public PooledObject<TransportClient> makeObject() throws Exception {
        // 设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "vpro-cluster").put("node.name", "mynode1").build();
        TransportClient transportClient = null;
        try{
            // 创建client, 端口是tcp port,如果是集群，可以不用add
            transportClient = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new TransportAddress(InetAddress.getByName("223.112.88.214"), 9400))
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("223.112.88.214"), 9401));
        }catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return new DefaultPooledObject<TransportClient>(transportClient);
    }

    @Override
    public void destroyObject(PooledObject<TransportClient> pooledObject) throws Exception {
        TransportClient transportClient = pooledObject.getObject();
        transportClient.close();
    }
    // 检测一个对象是否有效。在对象池中的对象必须是有效的，这个有效的概念是，从对象池中拿出的对象是可用的。
    // 比如，如果是socket,那么必须保证socket是连接可用的。
    // 在从对象池获取对象或归还对象到对象池时，会调用这个方法，判断对象是否有效，如果无效就会销毁。
    @Override
    public boolean validateObject(PooledObject<TransportClient> pooledObject) {
        return true;
    }

    // 激活一个对象或者说启动对象的某些操作。如果对象是一个包含参数的对象，可以在这里进行初始化。让使用者感觉这是一个新创建的对象一样。
    @Override
    public void activateObject(PooledObject<TransportClient> pooledObject) throws Exception {
        System.out.println("activate esClient");
    }
    // 钝化一个对象。在向对象池归还一个对象是会调用这个方法。这里可以对对象做一些清理操作。比如清理掉过期的数据，下次获得对象时，不受旧数据的影响。
    @Override
    public void passivateObject(PooledObject<TransportClient> pooledObject) throws Exception {
        System.out.println("passivate Object");
    }
}
