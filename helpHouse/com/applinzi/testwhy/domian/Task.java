package com.applinzi.testwhy.domian;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.applinzi.testwhy.exception.MsgException;


public class Task implements Serializable {
	//1������tid
	private String tid;
	//2���û�uid
	private String uid;
	//3���û��ǳ�
	private String nickname;
	//4���������
	private String title;
	//5����������
	private String content;
	//6������״̬
	private String taskstate;
	//7���û�ͷ���ַ
	private String headaddress;
	//8�����񷢲�����
	private String taskdate;
	//9�������ͽ�
	private double money;
	//10����ϵ�绰
	private String phone;
	//11������Ҫ��
	private String requirement;
	//�Ƿ�ͬ���ܾ�
	private String state;
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTaskstate() {
		return taskstate;
	}
	public void setTaskstate(String taskstate) {
		this.taskstate = taskstate;
	}
	public String getTaskdate() {
		if(taskdate != null)
			return taskdate.substring(0,19);
		return taskdate;
	}
	public void setTaskdate(String taskdate) {
		this.taskdate = taskdate;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeadaddress() {
		return headaddress;
	}
	public void setHeadaddress(String headaddress) {
		this.headaddress = headaddress;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public void checkValues() throws MsgException{
		if(title == null || "".equals(title))
			throw new MsgException("���ⲻ��Ϊ�գ�");
		if(content == null || "".equals(content))
			throw new MsgException("���ݲ���Ϊ�գ�");
		if(requirement == null || "".equals(requirement))
			throw new MsgException("Ҫ����Ϊ�գ�");
		if(money < 0)
			throw new MsgException("�ͽ���С��0");
		if(money > 1000)
			throw new MsgException("�ͽ��ܴ���1000");
		if(!phone.matches("^1[0-9]{10}$"))
			throw new MsgException("������11Ϊ��Ч�ĵ绰���룡");
	}
}
