package com.khu.data.es.starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 对象池的配置类
 * @Auther: KaiYin Chen
 * @Date: 2020/3/3
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "elasticsearch.pool")
public class ObjectPoolProperties {

    private Integer maxIdle = -1;
    private Integer maxWaitMillis = 10000;
    private Integer maxTotal = -1;
    private Integer minEvictableIdleTimeMillis = 10000;
    private Boolean testOnBorrow = true;
    private Boolean testWhileIdle = true;
    private Integer timeBetweenEvictionRunsMillis = 1800000;

}
