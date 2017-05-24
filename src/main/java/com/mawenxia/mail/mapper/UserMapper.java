package com.mawenxia.mail.mapper;

import com.mawenxia.mail.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * Created by sam on 2017/5/23.
 */
@Service
public interface UserMapper {

    @Select("select * from user")
    User getUser();
}
