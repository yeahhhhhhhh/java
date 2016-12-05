package com.applinzi.testwhy.service;

import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;

import com.applinzi.testwhy.dao.UserDao;
import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.domian.User;
import com.applinzi.testwhy.exception.MsgException;
import com.applinzi.testwhy.factory.BasicFactory;

public class UserServiceImpl implements UserService {
	private UserDao dao = BasicFactory.getFactory().getDao(UserDao.class);
	

	public Redata findUserByUNandPW(User user) {
		try {
			//����û��������Ƿ�Ϊ��
			user.checkLoginValue();
			user = dao.findUserByUNandPW(user);
			if(user != null){
				user.setPassword("");
				return new Redata("200","OK",user);
			}else{
				return new Redata("401","�û����������");
			}
		}catch(MsgException e){
			return new Redata("401",e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Redata addUser(User user) {
		try {
			//���ע����Ϣ
			user.checkRegistValue();
			//�ж��û����Ƿ��ظ�
			if(dao.findUserByUsername(user) != null){
				return new Redata("401","�û����Ѵ���");
			}
			user.setUid(UUID.randomUUID().toString());
			dao.addUser(user);
			return new Redata("200","OK");
		}catch(MsgException e){
			return new Redata("401",e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Redata findUserByUid(String uid) {
		try {
			if(uid == null || "".equals(uid.trim()))
				return new Redata("1003","���ȵ�¼");
			User user = dao.findUserByUid(uid);
			if(user == null)
				return new Redata("1000","�û�������");
			return new Redata("200","OK",user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Redata updataUserInfo(User user) {
		try {
			dao.updataUserInfo(user);
			User user2 = dao.findUserByUid(user.getUid());
			return new Redata("200","OK",user2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Redata addHeadByUid(String uid, String headaddress) {
		try {
			if(uid == null || "".equals(uid)){
				return new Redata("1003","���ȵ�¼");
			}
			dao.addHeadByUid(uid,headaddress);
			return new Redata("200","OK",headaddress);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public String findHeadByUid(String uid) throws MsgException {
		try {
			if(uid == null || "".equals(uid))
				throw new MsgException("�û�������");
			return dao.findHeadByUid(uid);
		}catch(MsgException e){
			throw new MsgException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	




}
