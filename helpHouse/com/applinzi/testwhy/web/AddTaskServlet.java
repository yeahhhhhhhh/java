package com.applinzi.testwhy.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.domian.Task;
import com.applinzi.testwhy.domian.User;
import com.applinzi.testwhy.factory.BasicFactory;
import com.applinzi.testwhy.service.TaskService;

public class AddTaskServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Task task = new Task();
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			BeanUtils.populate(task, request.getParameterMap());
			TaskService service = BasicFactory.getFactory().getService(TaskService.class);
			Redata redata = service.addTask(task);
			String data = objectMapper.writeValueAsString(redata);
			response.getWriter().write(data);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
