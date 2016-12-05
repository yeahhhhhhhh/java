package com.applinzi.testwhy.domian;

import java.io.Serializable;

public class Redata implements Serializable {
	private String code;
	private String message;
	private Object body;
	
	public Redata() {}
	public Redata(String code, String message, Object body) {
		this.code = code;
		this.message = message;
		this.body = body;
	}
	public Redata(String code, String message) {
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}


}
