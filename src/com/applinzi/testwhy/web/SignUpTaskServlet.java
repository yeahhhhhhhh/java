package com.applinzi.testwhy.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.domian.TaskPage;
import com.applinzi.testwhy.factory.BasicFactory;
import com.applinzi.testwhy.service.TaskService;

public class SignUpTaskServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TaskService service = BasicFactory.getFactory().getService(TaskService.class);
		ObjectMapper objectMapper = new ObjectMapper();
		String uid = request.getParameter("uid");
		String[] state = request.getParameterValues("state");
		System.out.println(state);
		if(state[0].equals("1")){//查找进行中的任务
			Redata redata = service.findSignUpTask(uid,state[0]);
			String data = objectMapper.writeValueAsString(redata);
			response.getWriter().write(data);
		}else if(state.length == 2 && state[0].equals("0") && state[1].equals("2")){
			Redata redata = service.findSignUpTaskRefuse(uid,state);
			String data = objectMapper.writeValueAsString(redata);
			response.getWriter().write(data);
		}else{
			response.getWriter().write(objectMapper.writeValueAsString(new Redata("400","state错误")));
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
