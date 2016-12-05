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
			//使用工厂创建UserService
			UserService service = BasicFactory.getFactory().getService(UserService.class);
			//将数据封装到bean中
			BeanUtils.populate(user, request.getParameterMap());
			redata = service.findUserByUNandPW(user);
			
			//获取新的sessionid
			String sessionid = request.getParameter("sessionid");
			
			if(redata.getBody() != null){
				//获取该user
				User user2 = (User) redata.getBody();
				//获取该uid
				String uid = user2.getUid();
				//获取与该uid绑定的sessionid
				String sessionid2 = MySessionContext.getSessionid(uid);
				if(!sessionid.equals(sessionid2)){
					//获取以前的session
					HttpSession session2 = MySessionContext.getSession(sessionid2);
					//移除以前与给uid绑定的sessionid
					MySessionContext.removeUidAndSessionid(uid);
					if(session2 != null)//杀死session
						session2.invalidate();
					
					//重新与新的sessionid绑定
					MySessionContext.addUidAndSessionid(uid, sessionid);
					//获取新的session
					HttpSession session = MySessionContext.getSession(sessionid);
					MySessionContext.AddSession(session);
					//将该用户存入session域
					session.setAttribute("user", user2);
				}
				
			}
			//将redata转成json格式
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
