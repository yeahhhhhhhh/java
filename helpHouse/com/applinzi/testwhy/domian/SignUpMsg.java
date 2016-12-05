package com.applinzi.testwhy.domian;

import java.io.Serializable;

public class SignUpMsg implements Serializable{
	//报名者用户名
	private String username;
	//报名者昵称
	private String nickname;
	//报名者ｕｉｄ
	private String uid;
	//报名任务tid
	private String tid;
	//报名任务标题
	private String title;
	//消息状态
	private String msgstate;
	//是否被接受状态0:等待 1：接受 2：拒绝
	private String state;
	//报名时间
	private String signuptime;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getMsgstate() {
		return msgstate;
	}
	public void setMsgstate(String msgstate) {
		this.msgstate = msgstate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSignuptime() {
		return signuptime;
	}
	public void setSignuptime(String signuptime) {
		this.signuptime = signuptime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
}
