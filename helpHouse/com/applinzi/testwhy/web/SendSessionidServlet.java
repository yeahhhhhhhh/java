package com.applinzi.testwhy.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.applinzi.testwhy.utils.MySessionContext;

public class SendSessionidServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String sessionid = session.getId();
		Map<String,String> map = new HashMap<String, String>();
		map.put("sessionid", sessionid);
		
		//存入自己的map中
		MySessionContext.AddSession(session);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String data = objectMapper.writeValueAsString(map);
		map.remove("sessionid");
		System.out.println(data);
		response.getWriter().write(data);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
