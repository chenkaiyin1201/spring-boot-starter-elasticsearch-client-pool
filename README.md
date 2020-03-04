# spring-boot-starter-elasticsearch-client-pool
基于restHighLevelClient对象池的starter组件，能够快速接入restHighLevelClient客户端，且提供池化，支持springboot相关项目

> 当前版本为v0.1版本，后续会跟进需求来迭代版本

## 开源组件
common-pool2
restHighLevelClient

> 当前版本elasticsearch为7.0，如果有需要调整可修改pom.xml文件中elasticsearch.version

## 快速开始
> spring boot 项目接入
1. 添加starter组件依赖, ~~目前还未上传到公共仓库仓库，需要自行下载源码build~~
```
<dependency>
    <groupId>com.khu.data</groupId>
    <artifactId>spring-boot-starter-elasticsearch-client-pool</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
2. application.properties添加配置

|属性名|作用|属性值|默认值|
|---|---|---|---|
|elasticsearch.cluster-name|客户端名称||elasticsearch|
|elasticsearch.cluster-host|客户端地址||127.0.0.1|
|elasticsearch.cluster-port|客户端端口||9200|
|elasticsearch.connect-time-out|连接时间||5000|
|elasticsearch.socket-time-out|等待数据时间||5000|
|elasticsearch.connection-request-time-out|获取连接时间||5000|
|elasticsearch.pool.max-idle|最大空闲资源数||-1|
|elasticsearch.pool.max-wait-millis|获取资源最大等待时间||10000|
|elasticsearch.pool.max-total|允许创建资源的最大数量||-1|
|elasticsearch.pool.min-evictable-idle-time-millis|资源最小空闲时间||10000|
|elasticsearch.pool.test-on-borrow|调用时是否测试资源可用||true|
|elasticsearch.pool.test-while-idle|回收时是测试资源可用||true|
|elasticsearch.pool.time-between-eviction-runs-millis|资源回收周期||1800000|

3. 在需要使用的地方引入RestHighClient
```
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ElasticSearchUtils {

    private final RestHighClient client;

    public void init() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("test");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
    }
}
```

## 使用说明
由于该项目目前仅支持作者自己的使用场景，如果有额外的场景需要支持可在issue提出，会在后续的版本迭代中考虑相关需求。

## 捐赠
该项目如有帮助可以支付宝请作者喝咖啡

![khu-alipay](https://s2.ax1x.com/2020/03/04/35JBK1.png)


