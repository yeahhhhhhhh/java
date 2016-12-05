package com.applinzi.testwhy.dao;

import java.sql.SQLException;

import com.applinzi.testwhy.domian.User;

public interface UserDao extends Dao{

	/**
	 * �����û�����������û�
	 * @param user ��װ���û���������û���Ϣ
	 * @return ���û��Ļ�����Ϣ
	 */
	User findUserByUNandPW(User user) throws SQLException;

	/**
	 * ����û�
	 * @param user  �û���Ϣ
	 */
	void addUser(User user) throws SQLException;

	User findUserByUsername(User user) throws SQLException;

	/**
	 * �����û�uid�����û�
	 * @param uid
	 * @return
	 */
	User findUserByUid(String uid) throws SQLException;

	/**
	 * �����û���Ϣ
	 * @param user
	 */
	void updataUserInfo(User user) throws SQLException;

	/**
	 * �ϴ�ͷ��
	 * @param uid �û�uid
	 * @param headaddress ͷ���ַ
	 */
	void addHeadByUid(String uid, String headaddress) throws SQLException;

	/**
	 * ����uid�����û�ͷ���ַ
	 * @param uid
	 * @return
	 */
	String findHeadByUid(String uid) throws SQLException;

}
