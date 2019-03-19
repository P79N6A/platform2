package com.springboot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *  Administrator on 2017/3/1 14:57.
 */
@Service
public abstract class IRedisService<T> {
    @Autowired
    protected RedisTemplate<String, Object> redisTemplate=new RedisTemplate<String,Object>();
    @Resource
    protected HashOperations<String, String, T> hashOperations=new HashOperations<String, String, T>() {
        @Override
        public Long delete(String s, Object... objects) {
            return null;
        }

        @Override
        public Boolean hasKey(String s, Object o) {
            return null;
        }

        @Override
        public T get(String s, Object o) {
            return null;
        }

        @Override
        public List<T> multiGet(String s, Collection<String> collection) {
            return null;
        }

        @Override
        public Long increment(String s, String s2, long l) {
            return null;
        }

        @Override
        public Double increment(String s, String s2, double v) {
            return null;
        }

        @Override
        public Set<String> keys(String s) {
            return null;
        }

        @Override
        public Long size(String s) {
            return null;
        }

        @Override
        public void putAll(String s, Map<? extends String, ? extends T> map) {

        }

        @Override
        public void put(String s, String s2, T t) {

        }

        @Override
        public Boolean putIfAbsent(String s, String s2, T t) {
            return null;
        }

        @Override
        public List<T> values(String s) {
            return null;
        }

        @Override
        public Map<String, T> entries(String s) {
            return null;
        }

        @Override
        public Cursor<Map.Entry<String, T>> scan(String s, ScanOptions scanOptions) {
            return null;
        }

        @Override
        public RedisOperations<String, ?> getOperations() {
            return null;
        }
    };

    /**
     * 存入redis中的key
     *
     * @return
     */
    protected abstract String getRedisKey();

    /**
     * 添加
     *
     * @param key    key
     * @param doamin 对象
     * @param expire 过期时间(单位:秒),传入 -1 时表示不设置过期时间
     */
    public void put(String key, T doamin, long expire) {
        hashOperations.put(getRedisKey(), key, doamin);
        if (expire != -1) {
            redisTemplate.expire(getRedisKey(), expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 删除
     *
     * @param key 传入key的名称
     */
    public void remove(String key) {
        hashOperations.delete(getRedisKey(), key);
    }

    /**
     * 查询
     *
     * @param key 查询的key
     * @return
     */
    public T get(String key) {
        return hashOperations.get(getRedisKey(), key);
    }

    /**
     * 获取当前redis库下所有对象
     *
     * @return
     */
    public List<T> getAll() {
        return hashOperations.values(getRedisKey());
    }

    /**
     * 查询查询当前redis库下所有key
     *
     * @return
     */
    public Set<String> getKeys() {
        return hashOperations.keys(getRedisKey());
    }

    /**
     * 判断key是否存在redis中
     *
     * @param key 传入key的名称
     * @return
     */
    public boolean isKeyExists(String key) {
        return hashOperations.hasKey(getRedisKey(), key);
    }

    /**
     * 查询当前key下缓存数量
     *
     * @return
     */
    public long count() {
        return hashOperations.size(getRedisKey());
    }

    /**
     * 清空redis
     */
    public void empty() {
        Set<String> set = hashOperations.keys(getRedisKey());
        set.stream().forEach(key -> hashOperations.delete(getRedisKey(), key));
    }
}
