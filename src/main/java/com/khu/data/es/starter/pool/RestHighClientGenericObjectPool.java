package com.khu.data.es.starter.pool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @Auther: KaiYin Chen
 * @Date: 2020/3/3
 * @Description:
 */
@Slf4j
public class RestHighClientGenericObjectPool<T> extends GenericObjectPool<T>{

    public RestHighClientGenericObjectPool(PooledObjectFactory<T> factory, GenericObjectPoolConfig config) {
        super(factory, config);
    }

}
