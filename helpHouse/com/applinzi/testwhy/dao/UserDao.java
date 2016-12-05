package com.applinzi.testwhy.dao;

import java.sql.SQLException;

import com.applinzi.testwhy.domian.User;

public interface UserDao extends Dao{

	/**
	 * 根据用户名密码查找用户
	 * @param user 封装了用户名密码的用户信息
	 * @return 该用户的基本信息
	 */
	User findUserByUNandPW(User user) throws SQLException;

	/**
	 * 添加用户
	 * @param user  用户信息
	 */
	void addUser(User user) throws SQLException;

	User findUserByUsername(User user) throws SQLException;

	/**
	 * 根据用户uid查找用户
	 * @param uid
	 * @return
	 */
	User findUserByUid(String uid) throws SQLException;

	/**
	 * 更新用户信息
	 * @param user
	 */
	void updataUserInfo(User user) throws SQLException;

	/**
	 * 上传头像
	 * @param uid 用户uid
	 * @param headaddress 头像地址
	 */
	void addHeadByUid(String uid, String headaddress) throws SQLException;

	/**
	 * 根据uid查找用户头像地址
	 * @param uid
	 * @return
	 */
	String findHeadByUid(String uid) throws SQLException;

}
