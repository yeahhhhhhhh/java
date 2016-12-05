package com.applinzi.testwhy.domian;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import com.applinzi.testwhy.exception.MsgException;

public class User implements Serializable {
	//1.�û�uid
	private String uid;
	//2.�û���
	private String username;
	//3.�û�����
	private String password;
	//4.ȷ������
	private String password2;
	//5.�ǳ�
	private String nickname;
	//6.�Ա�
	private String gender;
	//7.�绰
	private String phone;
	//8.ͷ���ַ
	private String headaddress;
	//9.ע��ʱ��
	private String userdate;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getHeadaddress() {
		return headaddress;
	}
	public void setHeadaddress(String headaddress) {
		this.headaddress = headaddress;
	}
	public String getUserdate() {
		return userdate;
	}
	public void setUserdate(String userdate) {
		this.userdate = userdate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final User other = (User) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	public void checkLoginValue() throws MsgException{
		if(username == null || "".equals(username.trim()))
			throw new MsgException("�û�������Ϊ��");
		if(password == null || "".equals(password.trim()))
			throw new MsgException("���벻��Ϊ��");
	}
	
	public void checkRegistValue() throws MsgException{
		checkLoginValue();
		if(password2 == null || "".equals(password2.trim()))
			throw new MsgException("ȷ�ϲ�������Ϊ��");
		if(!password.equals(password2))
			throw new MsgException("�������벻һ��");
		if(username.length()>8)
			throw new MsgException("�û�������");
		if(password.length()<3 || password.length()>15)
			throw new MsgException("�����ʽ����ȷ");
	}
}
