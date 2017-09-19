package com.mawenxia.mail.service;

import com.mawenxia.mail.config.database.ReadOnlyConnection;
import com.mawenxia.mail.entity.MstDict;
import com.mawenxia.mail.mapper.MstDictMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by sam on 2017/5/27.
 * <p>
 * coding like an artist
 */
@Service
public class MstDictService {
    @Resource
    private MstDictMapper mstDictMapper;

    @ReadOnlyConnection
    public List<MstDict> findByStatus(String status) throws Exception{
        return this.mstDictMapper.findByStatus(status);
    }
}
