package com.applinzi.testwhy.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.factory.BasicFactory;
import com.applinzi.testwhy.service.TaskService;

public class DealSignUpMsgServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��ȡ�����ߵ�uid
		String uid = request.getParameter("uid");
		//��ȡ����tid
		String tid = request.getParameter("tid");
		//��ȡstate,�ж��ǽ��ܻ��Ǿܾ�
		String state = request.getParameter("state");
		ObjectMapper objectMapper = new ObjectMapper();
		TaskService service = BasicFactory.getFactory().getService(TaskService.class);
		if(state.equals("1") || state.equals("2")){
			Redata redata = service.dealSignUpMsg(uid,tid,state);
			String data = objectMapper.writeValueAsString(redata);
			response.getWriter().write(data);
		}else{
			response.getWriter().write(objectMapper.writeValueAsString(new Redata("400","state����")));
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
