package com.applinzi.testwhy.exception;

public class MsgException extends Exception {

	public MsgException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MsgException(String message){
		super(message);
	}
	public MsgException(MsgException e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

	
}
