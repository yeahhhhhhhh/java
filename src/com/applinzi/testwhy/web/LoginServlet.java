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
import com.applinzi.testwhy.factory.BasicFactory;
import com.applinzi.testwhy.service.UserService;
import com.applinzi.testwhy.utils.MySessionContext;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = new User();
		Redata redata;
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			//ʹ�ù�������UserService
			UserService service = BasicFactory.getFactory().getService(UserService.class);
			//�����ݷ�װ��bean��
			BeanUtils.populate(user, request.getParameterMap());
			redata = service.findUserByUNandPW(user);
			
			//��ȡ�µ�sessionid
			String sessionid = request.getParameter("sessionid");
			
			if(redata.getBody() != null){
				//��ȡ��user
				User user2 = (User) redata.getBody();
				//��ȡ��uid
				String uid = user2.getUid();
				//��ȡ���uid�󶨵�sessionid
				String sessionid2 = MySessionContext.getSessionid(uid);
				if(!sessionid.equals(sessionid2)){
					//��ȡ��ǰ��session
					HttpSession session2 = MySessionContext.getSession(sessionid2);
					//�Ƴ���ǰ���uid�󶨵�sessionid
					MySessionContext.removeUidAndSessionid(uid);
					if(session2 != null)//ɱ��session
						session2.invalidate();
					
					//�������µ�sessionid��
					MySessionContext.addUidAndSessionid(uid, sessionid);
					//��ȡ�µ�session
					HttpSession session = MySessionContext.getSession(sessionid);
					MySessionContext.AddSession(session);
					//�����û�����session��
					session.setAttribute("user", user2);
				}
				
			}
			//��redataת��json��ʽ
			String data = objectMapper.writeValueAsString(redata);
			response.getWriter().write(data);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
