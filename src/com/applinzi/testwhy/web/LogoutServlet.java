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
		//��ȡ�ͻ��˴�����uid
		String uid = request.getParameter("uid");
		//��ȡ��Ӧ��sessionid
		String sessionid = MySessionContext.getSessionid(uid);
		//��ȡ��Ӧ��session
		HttpSession session = MySessionContext.getSession(sessionid);
		if(session != null){
			//��map���Ƴ�
			MySessionContext.DelSession(session);
			//ɱ��session
			session.invalidate();
			//���uid��sessionid�İ�
			MySessionContext.removeUidAndSessionid(uid);
			response.getWriter().write(objectMapper.writeValueAsString(new Redata("200","OK")));
		}else{
			response.getWriter().write(objectMapper.writeValueAsString(new Redata("1003","�û�δ��¼")));
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
