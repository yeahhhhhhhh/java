package com.applinzi.testwhy.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.domian.User;
import com.applinzi.testwhy.utils.MySessionContext;

public class IsLoginFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		ObjectMapper objectMapper = new ObjectMapper();
		//��ȡ�û�������sessionid
		String sessionid = request.getParameter("sessionid");
		HttpSession session = MySessionContext.getSession(sessionid);
		//�ж��û��Ƿ��¼
		if(session == null || session.getAttribute("user") == null){
			response.getWriter().write(objectMapper.writeValueAsString(new Redata("401","�Ҳ�����Ӧ��session")));
			return;
		}
		//��ȡ�û�uid
		String uid = request.getParameter("uid");
		if(!((User)session.getAttribute("user")).getUid().equals(uid)){
			response.getWriter().write(objectMapper.writeValueAsString(new Redata("401","Unauthorized")));
			return;
		}
		arg2.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
