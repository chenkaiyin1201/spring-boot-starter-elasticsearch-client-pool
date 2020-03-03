package com.khu.data.es.starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: 百里
 * @Date: 2020/3/3
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchProperties {

    private String clusterName = "elasticsearch";

    private String clusterHost = "127.0.0.1";

    private Integer clusterPort = 9200;

    private Integer connectTimeOut = 50000;

    private Integer socketTimeOut = 5000;

    private Integer connectionRequestTimeOut = 5000;
}
