package com.applinzi.testwhy.test;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.applinzi.testwhy.dao.UserDao;
import com.applinzi.testwhy.dao.UserDaoImpl;
import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.domian.User;
import com.applinzi.testwhy.factory.BasicFactory;
import com.applinzi.testwhy.service.UserService;

public class JsonTest {
	@Test
	public void testJson() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		Redata redata = new Redata("200","OK");
		User user= new User();
		user.setUsername("你好");
		redata.setBody(user);
		String msg = objectMapper.writeValueAsString(redata);
		System.out.println(msg);
		
		List<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");
		String listjson = objectMapper.writeValueAsString(list);
		System.out.println(listjson);
	}
	
	@Test
	public void testService() throws Exception {
		UserDao dao = new UserDaoImpl();
		User user = new User();
		user.setUsername("why");
		user = dao.findUserByUsername(user);
		System.out.println(user.getUserdate());
	}
	
	@Test
	public void testTime() throws Exception {
		UserService service = BasicFactory.getFactory().getService(UserService.class);
		User user = new User();
		user.setNickname("阿斯达斯的");
		user.setGender("男");
		user.setHeadaddress("213213");
		//user.setPhone("123456");
		user.setUid("a8a9111e-a552-43a0-bd75-e31bb48e2c46");
		Redata data = service.updataUserInfo(user);
		System.out.println(data);
		
	}
	@Test
	public void testHead() throws Exception {
		UserService service = BasicFactory.getFactory().getService(UserService.class);
		ObjectMapper objectMapper = new ObjectMapper();
		Redata redata = new Redata("200","OK","hhhhhhh");
		String json = objectMapper.writeValueAsString(redata);
		System.out.println(json);
	}
	@Test
	public void testwer() throws Exception {
		String phone = "11111111111";
		boolean b = !phone.matches("^1[0-9]{10}$");
		System.out.println(b);
	}
}
