package com.khu.data.es.starter.pool;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * Created by baili on 2019-05-31 17:54.
 */
@Slf4j
@AllArgsConstructor
public class RestHighClientFactory implements PooledObjectFactory<RestHighLevelClient> {

    private String host;
    private int port;
    private int connectTimeOut;
    private int socketTimeOut;
    private int connectionRequestTimeOut;

    @Override
    public PooledObject<RestHighLevelClient> makeObject() throws Exception {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(host, port)).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                @Override
                public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                     requestConfigBuilder.setConnectTimeout(connectTimeOut);
                     requestConfigBuilder.setSocketTimeout(socketTimeOut);
                     requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
                     return requestConfigBuilder;
                 }
             }
        ));

        return new DefaultPooledObject<RestHighLevelClient>(client);
    }

    @Override
    public void passivateObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
        // nothing
    }

    @Override
    public void activateObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
        // nothing
    }

    /**
     * 销毁客户端校验, 如果客户端不为null, 释放客户端
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void destroyObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
        RestHighLevelClient client = pooledObject.getObject();
        if (client != null) {
            client.close();
        }
    }

    @Override
    public boolean validateObject(PooledObject<RestHighLevelClient> pooledObject) {
        RestHighLevelClient client = pooledObject.getObject();
        try {
            return client.ping(RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("test client failed{}", e.getMessage(), e);
            return false;
        }
    }
}
