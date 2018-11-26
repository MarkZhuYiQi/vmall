package com.mark.manager.config;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ElasticRestClientPool implements PooledObjectFactory<RestHighLevelClient> {
  @Override
  public PooledObject<RestHighLevelClient> makeObject() throws Exception {
      RestHighLevelClient client = new RestHighLevelClient(
          RestClient.builder(
              new HttpHost("223.112.88.214", 9200, "http"),
              new HttpHost("223.112.88.214", 9201, "http")));
      return new DefaultPooledObject<RestHighLevelClient>(client);
  }

  @Override
  public void destroyObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
      RestHighLevelClient client = pooledObject.getObject();
      client.close();
  }

  @Override
  public boolean validateObject(PooledObject<RestHighLevelClient> pooledObject) {
    return true;
  }

  @Override
  public void activateObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
      System.out.println("activate esClient");
  }

  @Override
  public void passivateObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
      System.out.println("passivate Object");
  }
}
