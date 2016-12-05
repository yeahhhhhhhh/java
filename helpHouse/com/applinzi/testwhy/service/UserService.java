package com.applinzi.testwhy.service;

import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.domian.User;
import com.applinzi.testwhy.exception.MsgException;

public interface UserService extends Service{

	/**
	 * �����û�����������û�
	 * @param user ��װ���û����������user����
	 * @return Redata
	 */
	Redata findUserByUNandPW(User user);

	/**
	 * ����û�
	 * @param user ��װ���û���Ϣ��bean
	 * @return Redata
	 */
	Redata addUser(User user);

	/**
	 * �����û�uid�����û�
	 * @param uid
	 * @return
	 */
	Redata findUserByUid(String uid);

	/**
	 * �����û���Ϣ
	 * @param user �û���Ϣ
	 * @return 
	 */
	Redata updataUserInfo(User user);

	/**
	 * ͷ���ϴ�
	 * @param uid �û���uid
	 * @param headaddress ͷ���ַ
	 * @return
	 */
	Redata addHeadByUid(String uid, String headaddress);

	/**
	 * ����uid����ͷ���ַ
	 * @param uid
	 * @return
	 */
	String findHeadByUid(String uid) throws MsgException ;


	





}
