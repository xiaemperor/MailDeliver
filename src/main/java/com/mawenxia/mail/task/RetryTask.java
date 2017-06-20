package com.mawenxia.mail.task;

import com.mawenxia.mail.config.database.ReadOnlyConnectionInterceptor;
import com.mawenxia.mail.entity.MailSend;
import com.mawenxia.mail.enumeration.RedisPriorityQueue;
import com.mawenxia.mail.service.MailSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by sam on 2017/6/7.
 * <p>
 * coding like an artist
 */
@Component
public class RetryTask {

    public static final Logger LOGGER = LoggerFactory.getLogger(ReadOnlyConnectionInterceptor.class);

    @Autowired
    private MailSendService mailSendService;

    @Scheduled(initialDelay = 5000,fixedDelay = 10000)
    public void retry(){

        LOGGER.info("------开始邮件重发--------");

        List<MailSend> list = mailSendService.queryDraftList();


        //重发
        for(MailSend mailSend: list){
            mailSend.setSendPriority(8L);
            mailSendService.sendRedis(mailSend);
        }


    }
}
