package com.khu.data.es.starter;

import com.khu.data.es.starter.client.RestHighClient;
import com.khu.data.es.starter.pool.RestHighClientFactory;
import com.khu.data.es.starter.pool.RestHighClientGenericObjectPool;
import com.khu.data.es.starter.properties.ElasticSearchProperties;
import com.khu.data.es.starter.properties.ObjectPoolProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Auther: 百里
 * @Date: 2020/3/3
 * @Description:
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({ElasticSearchProperties.class, ObjectPoolProperties.class})
public class ElasticSearchAutoConfiguration implements DisposableBean {

    @Resource
    private ElasticSearchProperties elasticSearchProperties;

    @Resource
    private ObjectPoolProperties objectPoolProperties;

    private RestHighClientGenericObjectPool<RestHighLevelClient> pool;

    @Bean(name = "testRestHighClientAvailability")
    public void testRestHighClientAvailability() {
        log.info("test elasticsearch client availability!");
        RestHighLevelClient client = null;
        try {
            client = new RestHighLevelClient(RestClient.builder(
                    new HttpHost(elasticSearchProperties.getClusterHost(), elasticSearchProperties.getClusterPort())));
            boolean ping = client.ping(RequestOptions.DEFAULT);
            if (!ping) {
                throw new RuntimeException("elasticsearch client[" + elasticSearchProperties.getClusterHost() + ":" + elasticSearchProperties.getClusterHost() + "] connection failed, please check!");
            }
            client.close();
        } catch (Exception e) {
            throw new RuntimeException("elasticsearch client[" + elasticSearchProperties.getClusterHost() + ":" + elasticSearchProperties.getClusterHost() + "] connection failed, please check!");
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    log.warn("elasticsearch client close faild!");
                }
            }
        }
    }

    @Bean(name = "restHighClientGenericObjectPool")
    @DependsOn("testRestHighClientAvailability")
    @ConditionalOnMissingBean(RestHighClientGenericObjectPool.class)
    public RestHighClientGenericObjectPool restHighClientGenericObjectPool() {
        log.info("start elasticsearc client pool!");
        GenericObjectPoolConfig poolConfig = getPoolConfig();
        pool = new RestHighClientGenericObjectPool<>(new RestHighClientFactory(elasticSearchProperties.getClusterHost(),
                elasticSearchProperties.getClusterPort(),
                elasticSearchProperties.getConnectTimeOut(),
                elasticSearchProperties.getSocketTimeOut(),
                elasticSearchProperties.getConnectionRequestTimeOut()), poolConfig);

        return pool;
    }

    @Bean(name = "restHighClient")
    @DependsOn("restHighClientGenericObjectPool")
    @ConditionalOnMissingBean(RestHighClient.class)
    public RestHighClient restHighClient() {
        return new RestHighClient(pool);
    }

    private GenericObjectPoolConfig getPoolConfig() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(objectPoolProperties.getMaxIdle());
        config.setMaxWaitMillis(objectPoolProperties.getMaxWaitMillis());
        config.setMaxTotal(objectPoolProperties.getMaxTotal());
        config.setMinEvictableIdleTimeMillis(objectPoolProperties.getMinEvictableIdleTimeMillis());
        config.setTestOnBorrow(objectPoolProperties.getTestOnBorrow());
        config.setTestWhileIdle(objectPoolProperties.getTestWhileIdle());
        // 设置30分钟空闲对象清理
        config.setTimeBetweenEvictionRunsMillis(objectPoolProperties.getTimeBetweenEvictionRunsMillis());

        // 取消jmx注册，避免MXBean冲突
        config.setJmxEnabled(false);
        return config;
    }

    @Override
    public void destroy() throws Exception {
        log.info("destroy elasticsearc client pool!");
        pool.close();
    }
}
