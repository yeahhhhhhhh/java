package com.applinzi.testwhy.utils;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public class MySessionContext {
	private static HashMap<String,HttpSession> mymap = new HashMap<String,HttpSession>();
	private static HashMap<String,String> mymap2 = new HashMap<String,String>();

	public static synchronized void AddSession(HttpSession session) {  
			if (session != null) 
				mymap.put(session.getId(), session);  
		}  
		  
		public static synchronized void DelSession(HttpSession session) {  
			if (session != null)
				mymap.remove(session.getId());  
		}  
		  
		public static synchronized HttpSession getSession(String sessionid) {  
			if (sessionid == null) return null;  
			return (HttpSession) mymap.get(sessionid);  
		}

		public static void addUidAndSessionid(String uid, String sessionid) {
			if (sessionid != null)
				mymap2.put(uid, sessionid);
		}  
		
		public static String getSessionid(String uid){
			if(uid == null)
				return null;
			return (String) mymap2.get(uid);
		}
		
		public static void removeUidAndSessionid(String uid){
			if(uid != null)
				mymap2.remove(uid);
		}
    
}
