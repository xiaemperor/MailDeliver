package com.mawenxia.mail;

import com.mawenxia.mail.config.database.ReadOnlyConnection;
import com.mawenxia.mail.entity.User;
import com.mawenxia.mail.service.api.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailProducerApplicationTests {

	@Resource(name="masterDataSource")
	private DataSource masterDataSource;
	@Resource(name = "slaveDataSource")
	private DataSource slaveDataSource;
	@Resource
	private UserService userService;

	@Test
	public void contextLoads() {
//		try {
//			Connection c1 = masterDataSource.getConnection("root","root");
//			System.err.println(c1.getMetaData().getURL());
//			Connection c2 = slaveDataSource.getConnection("root","root");
//			System.err.println(c2.getMetaData().getURL());

			User user = userService.getUser();

			System.out.println(user.getName());

//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}

}
