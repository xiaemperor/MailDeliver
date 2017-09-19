package com.mawenxia.mail.config.database;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 继承该方法后不需要写基本的dao方法,mapping中也不需要写具体sql语句
 *
 *
 * Created by sam on 2017/5/27.
 * <p>
 * coding like an artist
 */
public interface BaseMapper<T> extends Mapper<T>,MySqlMapper<T> {
}
