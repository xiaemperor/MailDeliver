package com.mawenxia.mail.service;

import com.alibaba.fastjson.JSON;
import com.mawenxia.mail.config.database.ReadOnlyConnectionInterceptor;
import com.mawenxia.mail.entity.MailSend;
import com.mawenxia.mail.enumeration.MailStatus;
import com.mawenxia.mail.enumeration.RedisPriorityQueue;
import com.mawenxia.mail.helper.GeneratorMailTemplateHelper;
import com.mawenxia.mail.mapper.MailSend1Mapper;
import com.mawenxia.mail.mapper.MailSend2Mapper;
import com.mawenxia.mail.vo.MailData;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private GeneratorMailTemplateHelper templateHelper;

    public static final Logger LOGGER = LoggerFactory.getLogger(ReadOnlyConnectionInterceptor.class);


    public void sendMessage4Order(MailSend ms) {


        try {

            //1.准备数据
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("exportUrl","www.baidu.com");
            param.put("username",ms.getSendUserId());
            param.put("createDate", DateFormatUtils.format(ms.getUpdateTime(),"yyyy年MM月dd日"));
            MailData maildata = new MailData();
            maildata.setTemplateName("SHEET");
            maildata.setUserId(ms.getSendUserId());
            maildata.setFrom("baihezhuo@163.com");
            maildata.setTo(ms.getSendTo());
            maildata.setSubject("【订单】");
            maildata.setParam(param);
            //2.开始模板渲染发送
            templateHelper.generatorAndSend(maildata);

            //3.成功。使用乐观锁version更新 更新状态
            ms.setSendStatus(MailStatus.NEED_OK.getCode());
            int hashCode = ms.getSendId().hashCode();
            int ret;
            if(hashCode%2 == 0){
                ret = mailSend2Mapper.updateByPrimaryKeyAndVersion(ms);
            }else{
                ret = mailSend1Mapper.updateByPrimaryKeyAndVersion(ms);
            }

            if(ret == 0){

                ms.setSendStatus(MailStatus.DRAFT.getCode());
                LOGGER.info("邮件发送失败,版本号有冲突,等待重新发送,id:{},userid{}",ms.getSendId(),ms.getSendUserId());
                if(hashCode%2 == 0){
                    mailSend2Mapper.updateByPrimaryKey(ms);
                }else{
                    mailSend1Mapper.updateByPrimaryKey(ms);
                }

            }else if(ret ==1){
                LOGGER.info("发送邮件成功,id:{},userID:{}",ms.getSendId(),ms.getSendUserId());
            }

        } catch (Exception e) {
            e.printStackTrace();
            //count次数:5次
            if(ms.getSendCount()>4){
                ms.setSendStatus(MailStatus.NEED_ERR.getCode());
                LOGGER.info("发送邮件失败,id:{},userID:{}",ms.getSendId(),ms.getSendUserId());
            }else{
                ms.setSendStatus(MailStatus.DRAFT.getCode());
                LOGGER.info("发送邮件失败,等待重新发送,id:{},userID:{}",ms.getSendId(),ms.getSendUserId());
            }

            int hashCode = ms.getSendId().hashCode();
            if(hashCode%2 == 0){
                mailSend2Mapper.updateByPrimaryKeyAndVersion(ms);
            }else{
                mailSend1Mapper.updateByPrimaryKeyAndVersion(ms);
            }

            throw new RuntimeException();
        }

    }
}
