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
			//检验验证码是否一致
			String valistr2 = request.getParameter("valistr2");
			//从前端获取sessionid
			String sessionid = request.getParameter("sessionid");
			//获取该session
			HttpSession session = MySessionContext.getSession(sessionid);
			String valistr = (String) session.getAttribute("valistr");
			if(valistr2 == null || "".equals(valistr2)){
				response.getWriter().write(objectMapper.writeValueAsString(new Redata("401","验证码不能为空")));
				return;
			}
			if(valistr == null || "".equals(valistr)){
				response.getWriter().write(objectMapper.writeValueAsString(new Redata("401","验证码已过期")));
				return;
			}
			if(!valistr.equals(valistr2)){
				request.getSession().removeAttribute("valistr");
				Redata redata = new Redata("401","验证码错误");
				data = objectMapper.writeValueAsString(redata);
				response.getWriter().write(data);
				return;
			}
			session.removeAttribute("valistr");
			//将请求参数封装到bean
			BeanUtils.populate(user, request.getParameterMap());
			UserService service = BasicFactory.getFactory().getService(UserService.class);
			Redata redata = service.addUser(user);
			//将redata转成json格式
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
