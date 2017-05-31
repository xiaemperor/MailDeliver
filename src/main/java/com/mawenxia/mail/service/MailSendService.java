package com.mawenxia.mail.service;

import com.mawenxia.mail.entity.MailSend;
import com.mawenxia.mail.mapper.MailSend1Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by sam on 2017/5/31.
 * <p>
 * coding like an artist
 */
@Service
public class MailSendService {
    @Resource
    MailSend1Mapper mailSendMapper;

    public int insert(MailSend mailSend){
        return mailSendMapper.insert(mailSend);
    }

}
