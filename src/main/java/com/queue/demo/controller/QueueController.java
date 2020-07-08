package com.queue.demo.controller;

import com.queue.demo.common.QueueKeys;
import com.queue.demo.entity.Queue;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zxx
 * @create 2020-06-30 16:02
 */
@RestController
@RequestMapping("/queue")
public class QueueController {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @RequestMapping("/start")
    public String startQueue(String game,String name){
        Queue queue = new Queue(name,System.currentTimeMillis());
        // 当前所在位置
        Long position = redisTemplate.opsForZSet().rank(game,name);
        // 只有没有在当前队列才添加到当前队列里面
        if(position == null || position<0) {
            System.out.println(name);
            redisTemplate.opsForZSet().add(game,name,queue.getDate());
        }
        position = redisTemplate.opsForZSet().rank(game,name);
        return position.toString();
    }

    @RequestMapping("/check")
    public String checkQueue(String game,String name){
        Queue queue = new Queue(name,System.currentTimeMillis());
        // 当前所在位置
        Long position = redisTemplate.opsForZSet().rank(game,name);
        if(position == null){
            position = -1L;
        }
        return position.toString();
    }

    @RequestMapping("/cancel")
    public boolean cancelQueue(String game,String name){
        redisTemplate.opsForZSet().remove(game,name);
        return true;
    }

    @RequestMapping("/games")
    public String[] getGames(){
        return QueueKeys.games;
    }
}