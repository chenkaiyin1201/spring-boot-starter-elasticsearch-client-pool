package com.khu.data.es.starter.client;

import com.khu.data.es.starter.pool.RestHighClientGenericObjectPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @Auther: 百里
 * @Date: 2020/3/3
 * @Description:
 */
@Slf4j
@AllArgsConstructor
public class RestHighClient {

    private final RestHighClientGenericObjectPool<RestHighLevelClient> pool;

    public SearchResponse search(SearchRequest request, RequestOptions options) throws Exception {
        RestHighLevelClient client = null;
        try {
            client = pool.borrowObject();
            return client.search(request, options);
        } finally {
            if (client != null) {
                pool.returnObject(client);
            }
        }
    }
}
