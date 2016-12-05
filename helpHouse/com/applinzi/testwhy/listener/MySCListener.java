package com.applinzi.testwhy.listener;

import java.util.HashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

public class MySCListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("map",new HashMap<String, HttpSession>());
	}

}
