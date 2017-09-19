package com.mawenxia.mail.service;

import com.alibaba.fastjson.JSON;
import com.mawenxia.mail.config.database.ReadOnlyConnection;
import com.mawenxia.mail.config.database.ReadOnlyConnectionInterceptor;
import com.mawenxia.mail.entity.MailSend;
import com.mawenxia.mail.enumeration.MailStatus;
import com.mawenxia.mail.enumeration.RedisPriorityQueue;
import com.mawenxia.mail.mapper.MailSend1Mapper;
import com.mawenxia.mail.mapper.MailSend2Mapper;
import com.mawenxia.mail.util.FastJsonConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 2017/5/31.
 * <p>
 * coding like an artist
 */
@Service
public class MailSendService {
    @Resource
    MailSend1Mapper mailSend1Mapper;
    @Resource
    MailSend2Mapper mailSend2Mapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public static final Logger LOGGER = LoggerFactory.getLogger(ReadOnlyConnectionInterceptor.class);

    public void insert(MailSend mailSend){
        int hashcode = mailSend.getSendId().hashCode();
        if(hashcode%2 ==0){
            mailSend2Mapper.insert(mailSend);
        }else{
            mailSend1Mapper.insert(mailSend);
        }
    }

    public void sendRedis(MailSend mailSend) {


        int hashcode = mailSend.getSendId().hashCode();
        if(hashcode%2 ==0){
           mailSend = mailSend2Mapper.selectByPrimaryKey(mailSend.getSendId());
        }else{
           mailSend = mailSend1Mapper.selectByPrimaryKey(mailSend.getSendId());
        }


        ListOperations<String,String> opsForList = redisTemplate.opsForList();
        Long prioprity = mailSend.getSendPriority();

        Long ret = 0L;
        Long size = 0L;
        if(prioprity<4L){
            //延迟队列1 2 3 //返回的结果是最新容器长度
            ret = opsForList.rightPush(RedisPriorityQueue.DEFER_QUEUE.getCode(),  FastJsonConvertUtil.convertObjectToJSON(mailSend));
            size = opsForList.size(RedisPriorityQueue.DEFER_QUEUE.getCode());
        }else if(prioprity<7L){
            //进入普通队列4 5 6
            ret = opsForList.rightPush(RedisPriorityQueue.NORMAL_QUEUE.getCode(),  FastJsonConvertUtil.convertObjectToJSON(mailSend));
            size = opsForList.size(RedisPriorityQueue.NORMAL_QUEUE.getCode());
        }else {
            //进入紧急队列7 8 9
            ret = opsForList.rightPush(RedisPriorityQueue.FAST_QUEUE.getCode(),  FastJsonConvertUtil.convertObjectToJSON(mailSend));
            size = opsForList.size(RedisPriorityQueue.FAST_QUEUE.getCode());

            System.err.println(ret+"---"+size);
        }
        //只要成功。就+1
        mailSend.setSendCount(mailSend.getSendCount()+1);
        if(ret == size){
            mailSend.setSendStatus(MailStatus.SEND_IN.getCode());
            if(mailSend.getSendId().hashCode()%2 ==0){
                mailSend2Mapper.updateByPrimaryKeySelective(mailSend);
            }else{
                mailSend1Mapper.updateByPrimaryKeySelective(mailSend);
            }
            LOGGER.info("----- 进入队列成功,id:{}---",mailSend.getSendId());
        }else {
            //投递失败
            mailSend.setSendCount(mailSend.getSendCount()+1);

            if(mailSend.getSendId().hashCode()%2 ==0){
                mailSend2Mapper.updateByPrimaryKeySelective(mailSend);
            }else{
                mailSend1Mapper.updateByPrimaryKeySelective(mailSend);
            }

            LOGGER.info("----- 进入队列失败,等待轮询 id:{}---",mailSend.getSendId());
        }
    }

    /**
     * 连接从数据库,读取暂存邮件消息内容
     * @return
     */
    @ReadOnlyConnection
    public List<MailSend> queryDraftList() {
        List<MailSend> list = new ArrayList<>();
        list.addAll(mailSend2Mapper.queryDraftList());
        list.addAll(mailSend1Mapper.queryDraftList());
        return list;
    }
}
