package com.applinzi.testwhy.domian;

public class SignUpMsgDetail {
	private String title;
	private String state;
	private String signuptime;
	//1.用户uid
	private String uid;
	//2.用户名
	private String username;
	//5.昵称
	private String nickname;
	//6.性别
	private String gender;
	//7.电话
	private String phone;
	//8.头像地址
	private String headaddress;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
}
