package com.applinzi.testwhy.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.domian.User;
import com.applinzi.testwhy.exception.MsgException;
import com.applinzi.testwhy.factory.BasicFactory;
import com.applinzi.testwhy.service.UserService;
import com.applinzi.testwhy.utils.MySessionContext;

public class RegistServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = new User();
		ObjectMapper objectMapper = new ObjectMapper();
		String data = "";
		try {
			//������֤���Ƿ�һ��
			String valistr2 = request.getParameter("valistr2");
			//��ǰ�˻�ȡsessionid
			String sessionid = request.getParameter("sessionid");
			//��ȡ��session
			HttpSession session = MySessionContext.getSession(sessionid);
			String valistr = (String) session.getAttribute("valistr");
			if(valistr2 == null || "".equals(valistr2)){
				response.getWriter().write(objectMapper.writeValueAsString(new Redata("401","��֤�벻��Ϊ��")));
				return;
			}
			if(valistr == null || "".equals(valistr)){
				response.getWriter().write(objectMapper.writeValueAsString(new Redata("401","��֤���ѹ���")));
				return;
			}
			if(!valistr.equals(valistr2)){
				request.getSession().removeAttribute("valistr");
				Redata redata = new Redata("401","��֤�����");
				data = objectMapper.writeValueAsString(redata);
				response.getWriter().write(data);
				return;
			}
			session.removeAttribute("valistr");
			//�����������װ��bean
			BeanUtils.populate(user, request.getParameterMap());
			UserService service = BasicFactory.getFactory().getService(UserService.class);
			Redata redata = service.addUser(user);
			//��redataת��json��ʽ
			data = objectMapper.writeValueAsString(redata);
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
