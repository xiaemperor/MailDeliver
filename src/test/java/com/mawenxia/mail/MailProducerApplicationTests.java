package com.mawenxia.mail;

import com.github.pagehelper.PageHelper;
import com.mawenxia.mail.entity.MstDict;
import com.mawenxia.mail.mapper.MstDictMapper;
import com.mawenxia.mail.service.MstDictService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailProducerApplicationTests {

	@Resource(name="masterDataSource")
	private DataSource masterDataSource;
	@Resource(name = "slaveDataSource")
	private DataSource slaveDataSource;

	@Resource
	private MstDictMapper mstDictMapper;
	@Resource
	private MstDictService mstDictService;

	@Test //分页测试
	public void test1() throws Exception{
		PageHelper.startPage(1,2);
		List<MstDict> list = mstDictMapper.selectAll();
		list.forEach((mstDict)->{
			System.err.println(mstDict.getName());
		});
	}

	@Test
	public void test2() throws Exception{
		List<MstDict> list = mstDictService.findByStatus("1");
		list.forEach((mstDict)->{
			System.err.println(mstDict.getName());
		});
	}

	@Autowired
	private RedisTemplate<String,String> redisTemplate;

	@Test
	public void test3() throws Exception{
		ValueOperations<String,String> opsForValue = redisTemplate.opsForValue();
//		opsForValue.set("age","26");
		System.err.println(opsForValue.get("age"));
		redisTemplate.delete("age");
	}
}
