package com.mawenxia.mail.mapper;

import com.mawenxia.mail.config.database.BaseMapper;
import com.mawenxia.mail.entity.MailSend;

import java.util.List;


public interface MailSend2Mapper {

    int deleteByPrimaryKey(String sendId);

    int insert(MailSend record);

    int insertSelective(MailSend record);

    MailSend selectByPrimaryKey(String sendId);

    int updateByPrimaryKeySelective(MailSend record);

    int updateByPrimaryKey(MailSend record);

    List<MailSend> queryDraftList();
}