package com.applinzi.testwhy.domian;

import java.io.Serializable;

public class SignUpInfo implements Serializable{
	//当前用户uid
	private String uid;
	//发布任务者uid
	private String release_uid;
	//任务tid
	private String tid;
	//消息状态0:未读 1：已读
	private String msgstate;
	//是否被接受状态0:等待 1：接受 2：拒绝
	private String state;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getRelease_uid() {
		return release_uid;
	}
	public void setRelease_uid(String release_uid) {
		this.release_uid = release_uid;
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
}
