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

public class CutOffTaskServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取任务tid
		String tid = request.getParameter("tid");
		TaskService service = BasicFactory.getFactory().getService(TaskService.class);
		ObjectMapper objectMapper = new ObjectMapper();
		Redata redata = service.cutOffTask(tid);
		String data = objectMapper.writeValueAsString(redata);
		response.getWriter().write(data);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
