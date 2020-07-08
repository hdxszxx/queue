package com.queue.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import javax.annotation.Resource;
import java.util.Set;

@SpringBootTest
class DemoApplicationTests {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    void contextLoads() {
        Long  l = System.currentTimeMillis();
        redisTemplate.opsForZSet().add("myzset","zzz", l);
        System.out.println(l);
        Set<ZSetOperations.TypedTuple<Object>> a = redisTemplate.opsForZSet().rangeWithScores("myzset",0,0);
        for (ZSetOperations.TypedTuple<Object> b:a) {
            System.out.println(b.getScore().longValue());
            System.out.println(b.getValue());
        }
    }

}
