package com.applinzi.testwhy.domian;

import java.io.Serializable;

public class SignUpInfo implements Serializable{
	//��ǰ�û�uid
	private String uid;
	//����������uid
	private String release_uid;
	//����tid
	private String tid;
	//��Ϣ״̬0:δ�� 1���Ѷ�
	private String msgstate;
	//�Ƿ񱻽���״̬0:�ȴ� 1������ 2���ܾ�
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
