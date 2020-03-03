package com.khu.data.es.starter.pool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Created by baili on 2019-05-31 18:05.
 */
@Slf4j
public class RestHighClientGenericObjectPool<T> extends GenericObjectPool<T>{

    public RestHighClientGenericObjectPool(PooledObjectFactory<T> factory, GenericObjectPoolConfig config) {
        super(factory, config);
    }

}
