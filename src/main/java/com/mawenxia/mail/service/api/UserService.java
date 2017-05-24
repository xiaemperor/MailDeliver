package com.mawenxia.mail.service.api;

import com.mawenxia.mail.config.database.ReadOnlyConnection;
import com.mawenxia.mail.entity.User;
import com.mawenxia.mail.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by sam on 2017/5/23.
 */
@Service
public class UserService {
    @Resource
    UserMapper userMapper;

    @ReadOnlyConnection
    public User getUser(){
        User user = userMapper.getUser();
        return user;
    }
}
