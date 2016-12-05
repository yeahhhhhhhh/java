package com.applinzi.testwhy.domian;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import com.applinzi.testwhy.exception.MsgException;

public class User implements Serializable {
	//1.用户uid
	private String uid;
	//2.用户名
	private String username;
	//3.用户密码
	private String password;
	//4.确认密码
	private String password2;
	//5.昵称
	private String nickname;
	//6.性别
	private String gender;
	//7.电话
	private String phone;
	//8.头像地址
	private String headaddress;
	//9.注册时间
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
			throw new MsgException("用户名不能为空");
		if(password == null || "".equals(password.trim()))
			throw new MsgException("密码不能为空");
	}
	
	public void checkRegistValue() throws MsgException{
		checkLoginValue();
		if(password2 == null || "".equals(password2.trim()))
			throw new MsgException("确认不能密码为空");
		if(!password.equals(password2))
			throw new MsgException("两次密码不一致");
		if(username.length()>8)
			throw new MsgException("用户名过长");
		if(password.length()<3 || password.length()>15)
			throw new MsgException("密码格式不正确");
	}
}
