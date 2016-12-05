package com.applinzi.testwhy.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.domian.SignUpMsgPage;
import com.applinzi.testwhy.factory.BasicFactory;
import com.applinzi.testwhy.service.TaskService;

public class SignUpMsgListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			SignUpMsgPage sp = new SignUpMsgPage();
			//½«uid¡¢msgNowPage¡¢msgListCount·â×°
			BeanUtils.populate(sp, request.getParameterMap());
			ObjectMapper objectMapper = new ObjectMapper();
			TaskService service = BasicFactory.getFactory().getService(TaskService.class);
			Redata redata = service.findSignUpMsgList(sp);
			String data = objectMapper.writeValueAsString(redata);
			response.getWriter().write(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
