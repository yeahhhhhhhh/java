package com.applinzi.testwhy.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.domian.TaskPage;
import com.applinzi.testwhy.factory.BasicFactory;
import com.applinzi.testwhy.service.TaskService;

public class TaskListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Integer nowPage = Integer.parseInt(request.getParameter("nowPage"));
		Integer listCount = Integer.parseInt(request.getParameter("listCount"));
		TaskService service = BasicFactory.getFactory().getService(TaskService.class);
		Redata redata = service.taskListByNowPage(nowPage,listCount);
		String data = objectMapper.writeValueAsString(redata);
		response.getWriter().write(data);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
