package com.applinzi.testwhy.domian;

import java.io.Serializable;

public class SignUpMsg implements Serializable{
	//�������û���
	private String username;
	//�������ǳ�
	private String nickname;
	//�����ߣ����
	private String uid;
	//��������tid
	private String tid;
	//�����������
	private String title;
	//��Ϣ״̬
	private String msgstate;
	//�Ƿ񱻽���״̬0:�ȴ� 1������ 2���ܾ�
	private String state;
	//����ʱ��
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
