package com.applinzi.testwhy.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.domian.User;
import com.applinzi.testwhy.factory.BasicFactory;
import com.applinzi.testwhy.service.UserService;

public class FindUserInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		//获取用户uid
		String uid = request.getParameter("uid");
		
		UserService service = BasicFactory.getFactory().getService(UserService.class);
		Redata redata = service.findUserByUid(uid);
		String data = objectMapper.writeValueAsString(redata);
		response.getWriter().write(data);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
