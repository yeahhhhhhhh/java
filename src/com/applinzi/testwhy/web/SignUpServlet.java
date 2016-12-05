package com.applinzi.testwhy.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.domian.SignUpInfo;
import com.applinzi.testwhy.factory.BasicFactory;
import com.applinzi.testwhy.service.TaskService;
/**
 * ±¨Ãû
 * @author why
 *
 */
public class SignUpServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SignUpInfo su = new SignUpInfo();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			BeanUtils.populate(su, request.getParameterMap());
			TaskService service = BasicFactory.getFactory().getService(TaskService.class);
			Redata redata = service.addSignUpInfo(su);
			String data = objectMapper.writeValueAsString(redata);
			response.getWriter().write(data);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
