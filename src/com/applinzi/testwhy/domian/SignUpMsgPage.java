package com.applinzi.testwhy.domian;

import java.util.List;

public class SignUpMsgPage {
	//���û�uid
	private String uid;
	//��ǰҳ
	private Integer msgNowPage;
	//һҳ����Ϣ����
	private Integer msgListCount;
	//��ҳ��
	private Integer msgPageCount;
	//δ����Ϣ����
	private Integer noReadMsgCount;
	//�Ѷ���Ϣ����
	private Integer yesReadMsgCount;
	//��Ϣ������Ϣ
	private List<SignUpMsg> suList;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public List<SignUpMsg> getSuList() {
		return suList;
	}
	public void setSuList(List<SignUpMsg> suList) {
		this.suList = suList;
	}
	public Integer getMsgNowPage() {
		return msgNowPage;
	}
	public void setMsgNowPage(Integer msgNowPage) {
		this.msgNowPage = msgNowPage;
	}
	public Integer getMsgListCount() {
		return msgListCount;
	}
	public void setMsgListCount(Integer msgListCount) {
		this.msgListCount = msgListCount;
	}
	public Integer getMsgPageCount() {
		return msgPageCount;
	}
	public void setMsgPageCount(Integer msgPageCount) {
		this.msgPageCount = msgPageCount;
	}
	public Integer getNoReadMsgCount() {
		return noReadMsgCount;
	}
	public void setNoReadMsgCount(Integer noReadMsgCount) {
		this.noReadMsgCount = noReadMsgCount;
	}
	public Integer getYesReadMsgCount() {
		return yesReadMsgCount;
	}
	public void setYesReadMsgCount(Integer yesReadMsgCount) {
		this.yesReadMsgCount = yesReadMsgCount;
	}
	
}
