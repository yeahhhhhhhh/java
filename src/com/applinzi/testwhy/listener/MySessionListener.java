package com.applinzi.testwhy.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.applinzi.testwhy.utils.MySessionContext;

public class MySessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		 HttpSession session = httpSessionEvent.getSession();
	     MySessionContext.DelSession(session);
	     
	}

}
