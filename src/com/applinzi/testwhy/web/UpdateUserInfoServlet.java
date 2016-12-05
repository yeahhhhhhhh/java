package com.applinzi.testwhy.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.domian.User;
import com.applinzi.testwhy.factory.BasicFactory;
import com.applinzi.testwhy.service.UserService;

public class UpdateUserInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = new User();
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			//�����ݷ�װ��bean��
			BeanUtils.populate(user, request.getParameterMap());
			//ʹ�ù�������UserService
			UserService service = BasicFactory.getFactory().getService(UserService.class);
			Redata redata = service.updataUserInfo(user);
			String data = objectMapper.writeValueAsString(redata);
			response.getWriter().write(data);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
