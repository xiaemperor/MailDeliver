package com.mawenxia.mail.task;

import com.alibaba.fastjson.JSON;
import com.mawenxia.mail.entity.MailSend;
import com.mawenxia.mail.enumeration.RedisPriorityQueue;
import com.mawenxia.mail.service.MailSendService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by sam on 2017/6/4.
 * <p>
 * coding like an artist
 */
@Component
public class ConsumerMailTask {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private MailSendService mailSendService;

    @Scheduled(initialDelay = 5000,fixedDelay = 2000)
    public void intervalFast(){
        ListOperations<String,String> opsForLsit = redisTemplate.opsForList();
        String ret = opsForLsit.leftPop(RedisPriorityQueue.FAST_QUEUE.getCode());
        System.err.println(ret);
        if(!StringUtils.isBlank(ret)){
            System.err.println(ret);
            MailSend ms = JSON.parseObject(ret,MailSend.class);
            mailSendService.sendMessage4Order(ms);
        }
        System.err.println(" fast running -----");
    }

//    @Scheduled(initialDelay = 5000,fixedDelay = 20000)
//    public void intervalNormal(){
//        System.err.println(" normal running -----");
//    }
//
//    @Scheduled(initialDelay = 5000,fixedDelay = 40000)
//    public void intervalDelay(){
//        System.err.println(" delay running -----");
//    }

}
