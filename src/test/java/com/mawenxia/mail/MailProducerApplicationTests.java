package com.mawenxia.mail;

import com.github.pagehelper.PageHelper;
import com.mawenxia.mail.entity.MstDict;
import com.mawenxia.mail.entity.User;
import com.mawenxia.mail.mapper.MstDictMapper;
import com.mawenxia.mail.service.MstDictService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
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

	@Test
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
}
