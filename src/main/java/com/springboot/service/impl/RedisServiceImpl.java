package com.springboot.service.impl;


import com.springboot.model.RedisModel;
import com.springboot.service.IRedisService;
import org.springframework.stereotype.Service;




@Service("redisServiceImpl")
public class RedisServiceImpl extends IRedisService<RedisModel> {
    private static final String REDIS_KEY = "TEST_REDIS_KEY";

    @Override
    protected String getRedisKey() {
        return REDIS_KEY;
    }
}
