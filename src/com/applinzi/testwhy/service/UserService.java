package com.applinzi.testwhy.service;

import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.domian.User;
import com.applinzi.testwhy.exception.MsgException;

public interface UserService extends Service{

	/**
	 * 根据用户名密码查找用户
	 * @param user 封装了用户名和密码的user对象
	 * @return Redata
	 */
	Redata findUserByUNandPW(User user);

	/**
	 * 添加用户
	 * @param user 封装了用户信息的bean
	 * @return Redata
	 */
	Redata addUser(User user);

	/**
	 * 根据用户uid查找用户
	 * @param uid
	 * @return
	 */
	Redata findUserByUid(String uid);

	/**
	 * 更新用户信息
	 * @param user 用户信息
	 * @return 
	 */
	Redata updataUserInfo(User user);

	/**
	 * 头像上传
	 * @param uid 用户名uid
	 * @param headaddress 头像地址
	 * @return
	 */
	Redata addHeadByUid(String uid, String headaddress);

	/**
	 * 根据uid查找头像地址
	 * @param uid
	 * @return
	 */
	String findHeadByUid(String uid) throws MsgException ;


	





}
