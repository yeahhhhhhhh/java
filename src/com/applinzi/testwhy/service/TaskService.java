package com.applinzi.testwhy.service;

import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.domian.SignUpInfo;
import com.applinzi.testwhy.domian.SignUpMsgPage;
import com.applinzi.testwhy.domian.Task;
import com.applinzi.testwhy.domian.TaskPage;

public interface TaskService extends Service {

	/**
	 * ���ݵ�ǰҳ��ѯ�б���Ϣ
	 * @param nowPage ��ǰҳ
	 * @return
	 */
	Redata taskListByNowPage(Integer nowPage,Integer listCount);

	/**
	 * �������
	 * @param task
	 * @return
	 */
	Redata addTask(Task task);

	/**
	 * �鿴��������
	 * @param tid ����tid
	 * @return
	 */
	Redata findTaskByTid(String tid);

	/**
	 * ����uid�����û��ѷ���������
	 * @param uid
	 * @return
	 */
	Redata finUserTaskByUid(String uid);

	/**
	 * ��ӱ�����Ϣ
	 * @param su ������Ϣ
	 * @return
	 */
	Redata addSignUpInfo(SignUpInfo su);

	/**
	 * ��ҳ�����û���Ϣ
	 * @param sp ��װ��uid��msgNowPage��msgListCount
	 * @return
	 */
	Redata findSignUpMsgList(SignUpMsgPage sp);

	/**
	 * ����δ������Ϣ��
	 * @param uid
	 * @return
	 */
	Redata findSignUpMsgNoReadCount(String uid);

	/**
	 * ���Ҹ��û��ѱ�������,ֻ�����ȴ�����Ҫ�����ܻ�ܾ�������
	 * @param uid
	 * @return
	 */
	Redata findSignUpTask(String uid,String state);

	/**
	 * ��ֹ��������state�ĳ�1
	 * @param tid
	 * @return
	 */
	Redata cutOffTask(String tid);

	/**
	 * ��ѯ��Ϣ��ϸ��Ϣ
	 * @param uid
	 * @param tid
	 * @return
	 */
	Redata findSignUpMsgDetail(String uid, String tid);

	/**
	 * �鿴�ȴ��к��ѱ��ܾ�������
	 * @param uid �û�uid
	 * @param state 
	 * @return
	 */
	Redata findSignUpTaskRefuse(String uid, String[] state);

	/**
	 * �������ߵ���Ϣ
	 * @param uid ������uid
	 * @param tid ����tid
	 * @param state ����״̬�ж��ǽ��ܻ��Ǿܾ�
	 * @return
	 */
	Redata dealSignUpMsg(String uid, String tid, String state);

	

	





}
