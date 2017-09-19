package com.mawenxia.mail.mapper;

import com.mawenxia.mail.config.database.BaseMapper;
import com.mawenxia.mail.entity.MstDict;

import java.util.List;

public interface MstDictMapper extends BaseMapper<MstDict> {
    List<MstDict> findByStatus(String status);
}