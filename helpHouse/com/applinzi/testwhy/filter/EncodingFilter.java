package com.applinzi.testwhy.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class EncodingFilter implements Filter {
	private ServletContext context = null;
	private String encoding = null;
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		//����
		res.setHeader("Access-Control-Allow-Origin", "*");
		//��Ӧ����
		res.setContentType("text/html;charset="+encoding);
		//��������
		arg2.doFilter(new MyHttpServletRequest((HttpServletRequest)request), res);

	}

	public void init(FilterConfig arg0) throws ServletException {
		this.context = arg0.getServletContext();
		this.encoding = context.getInitParameter("encoding");
	}
	
	private class MyHttpServletRequest extends HttpServletRequestWrapper{
		private HttpServletRequest request = null;
		private boolean isNotCoding = true;
		public MyHttpServletRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
		}
		@Override
		public Map<String,String[]> getParameterMap() {
			try {
				if(request.getMethod().equalsIgnoreCase("post")){
					//post�ύ
					request.setCharacterEncoding(encoding);
					return request.getParameterMap();
				}else if(request.getMethod().equalsIgnoreCase("get")){
					//get�ύ
					Map<String,String[]>map = request.getParameterMap();
					if(isNotCoding){
						//����map
						for(Map.Entry<String, String[]> entry : map.entrySet()){
							String[] vs = entry.getValue();
							for(int i = 0; i<vs.length; i++){
								vs[i] = new String(vs[i].getBytes("iso8859-1"),encoding);
							}
						}
						//����false���ڶ��β��ܽ���
						isNotCoding = false;
					}
					return map;
				}else{
					return request.getParameterMap();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		@Override
		public String[] getParameterValues(String name) {
			
			return (String[]) getParameterMap().get(name);
		}
		@Override
		public String getParameter(String name) {
			
			return getParameterValues(name) == null ? null : getParameterValues(name)[0];
		}
	}

}
