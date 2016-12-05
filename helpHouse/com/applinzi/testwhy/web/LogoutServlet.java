package com.applinzi.testwhy.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.utils.MySessionContext;

public class LogoutServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		//获取客户端传来的uid
		String uid = request.getParameter("uid");
		//获取对应的sessionid
		String sessionid = MySessionContext.getSessionid(uid);
		//获取对应的session
		HttpSession session = MySessionContext.getSession(sessionid);
		if(session != null){
			//从map中移除
			MySessionContext.DelSession(session);
			//杀死session
			session.invalidate();
			//解除uid与sessionid的绑定
			MySessionContext.removeUidAndSessionid(uid);
			response.getWriter().write(objectMapper.writeValueAsString(new Redata("200","OK")));
		}else{
			response.getWriter().write(objectMapper.writeValueAsString(new Redata("1003","用户未登录")));
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
