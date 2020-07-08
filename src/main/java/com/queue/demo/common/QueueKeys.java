package com.queue.demo.common;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.queue.demo.entity.Queue;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zxx
 * @create 2020-07-02 15:13
 */
@Component
public class QueueKeys {


    private static RedisTemplate<String,Object> redisTemplate;

    @Resource
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        QueueKeys.redisTemplate = redisTemplate;
    }

    // 游戏列表
    public static final String[] games = {"g1","g2","g3","g4","g5"};
    // 云机列表
    public static final String[] ys = {"y1","y2","y3","y4","y5","y6","y7","y8","y9","y10"};
    // 游戏对应云机组合
//    public static final Map<String, List<String>> yuns = new HashMap<String, List<String>>();

    public static final String YUNSREDISKEY = "yunsRedisKey";

    private static ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(50,100);

//    public static Map<String, LinkedList<Queue>> queue = new Hashtable();

    @PostConstruct
    public void init(){
        createYuns();
        createQueue();
        createThread();
    }

    public void createYuns() {
        List<String> a =new ArrayList<>();
        a.add("g1");
        a.add("g2");
        a.add("g3");
        redisTemplate.opsForHash().put(YUNSREDISKEY,"y1",a);
        List<String> a1 =new ArrayList<>();
        a1.add("g2");
        a1.add("g3");
        a1.add("g4");
        redisTemplate.opsForHash().put(YUNSREDISKEY,"y2",a1);
        List<String> a2 =new ArrayList<>();
        a2.add("g3");
        a2.add("g4");
        a2.add("g5");
        redisTemplate.opsForHash().put(YUNSREDISKEY,"y3",a2);
        List<String> a3 =new ArrayList<>();
        a3.add("g2");
        a3.add("g3");
        a3.add("g5");
        redisTemplate.opsForHash().put(YUNSREDISKEY,"y4",a3);
        List<String> a4 =new ArrayList<>();
        a4.add("g1");
        a4.add("g2");
        a4.add("g3");
        a4.add("g4");
        a4.add("g5");
        redisTemplate.opsForHash().put(YUNSREDISKEY,"y5",a4);
        List<String> a5 =new ArrayList<>();
        a5.add("g1");
        a5.add("g2");
        a5.add("g4");
        a5.add("g5");
        redisTemplate.opsForHash().put(YUNSREDISKEY,"y6",a5);
        List<String> a6 =new ArrayList<>();
        a6.add("g1");
        a6.add("g2");
        a6.add("g3");
        a6.add("g4");
        redisTemplate.opsForHash().put(YUNSREDISKEY,"y7",a6);
        List<String> a7 =new ArrayList<>();
        a7.add("g1");
        a7.add("g4");
        a7.add("g5");
        redisTemplate.opsForHash().put(YUNSREDISKEY,"y8",a7);
        List<String> a8 =new ArrayList<>();
        a8.add("g1");
        a8.add("g3");
        a8.add("g4");
        a8.add("g5");
        redisTemplate.opsForHash().put(YUNSREDISKEY,"y9",a8);
        List<String> a9 =new ArrayList<>();
        a9.add("g1");
        a9.add("g3");
        a9.add("g5");
        redisTemplate.opsForHash().put(YUNSREDISKEY,"y10",a9);
    }

    // 开始创建线程（云机）
    public void createThread(){
        for (String y: ys) {
            threadPoolExecutor.execute(new QueueRunnable(y));
        }
    }
    // 开始创建队列
    public void createQueue(){
//        for (String a: games) {
//            LinkedList<Queue> linkedList = new LinkedList<Queue>();
//            queue.put(a,linkedList);
//        }
    }

    /**
     * 获取队列中的头一位
     * @return
     */
    public static synchronized Queue getQueue(String name){
        List<String> list = (List<String>) redisTemplate.opsForHash().get(YUNSREDISKEY,name);
        String queueName = "";
        ZSetOperations.TypedTuple<Object> a = null;
        for (String g : list) {
            Set<ZSetOperations.TypedTuple<Object>> b = redisTemplate.opsForZSet().rangeWithScores(g,0,0);
            if(b.size()>0) {
                if (a != null) {
                    if (a.getScore().longValue() > b.iterator().next().getScore().longValue()) {
                        a = b.iterator().next();
                        queueName = g;
                    }
                } else {
                    a =b.iterator().next();
                    queueName = g;
                }
            }
        }
        if(a == null){
            return null;
        }
        redisTemplate.opsForZSet().remove(queueName,a.getValue());
        Queue queue = new Queue(a.getValue().toString(),a.getScore().longValue());
        return queue;
    }
}