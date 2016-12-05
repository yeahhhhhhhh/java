package com.applinzi.testwhy.dao;

import java.sql.SQLException;
import java.util.List;

import com.applinzi.testwhy.domian.SignUpInfo;
import com.applinzi.testwhy.domian.SignUpMsg;
import com.applinzi.testwhy.domian.SignUpMsgDetail;
import com.applinzi.testwhy.domian.SignUpMsgPage;
import com.applinzi.testwhy.domian.Task;

public interface TaskDao extends Dao {

	/**
	 * ��ҳ��ѯ
	 * @param begin �������ĵ�ǰҳ��begin = (nowPage-1)*listCount
	 * @param listCount Ҫ��ʾ����ĸ���
	 * @return
	 */
	List<Task> taskListByNowPage(Integer begin, Integer listCount) throws SQLException;

	/**
	 * ��ѯ�����ܸ���
	 * @return
	 */
	Integer findTaskCount() throws SQLException;

	/**
	 * �������
	 * @param task
	 */
	void addTask(Task task) throws SQLException;

	/**
	 * ��������tid��������
	 * @param tid ����tid
	 * @return
	 */
	Task findTaskByTid(String tid) throws SQLException ;

	/**
	 * ����uid�����û��ѷ���������
	 * @param uid
	 * @return
	 */
	List<Task> finUserTaskByUid(String uid) throws SQLException;

	/**
	 * ��ӱ�����Ϣ
	 * @param su ������Ϣ
	 */
	void addSignUpInfo(SignUpInfo su) throws SQLException;

	/**
	 * ��ҳ�����û���Ϣ�б�
	 * @param begin ��ʼ
	 * @param sp ����msgListCount��uid
	 * @return ���ҵ���ϢList
	 */
	List<SignUpMsg> findSignUpMsgList(Integer begin, SignUpMsgPage sp) throws SQLException ;

	/**
	 * ���Ҹ��û���Ϣ������
	 * @param sp �����˸��û���uid
	 * @return
	 */
	Integer findSignUpMsgAllCount(SignUpMsgPage sp) throws SQLException ;

	/**
	 * ���Ҹ��û�δ����Ϣ
	 * @param uid �û���uid
	 * @return
	 */
	Integer findNoReadMsgCount(String uid) throws SQLException ;

	/**
	 * ���Ҹ��û��ѱ�������,ֻ�����ȴ�����Ҫ�����ܻ�ܾ�������
	 * @param uid
	 * @return
	 */
	List<Task> findSignUpTask(String uid,String state) throws SQLException ;

	/**
	 * ��������״̬
	 * @param tid
	 * @return
	 */
	String findTaskStateByTid(String tid) throws SQLException;

	/**
	 * ��ֹ��������state�ĳ�1
	 * @param tid
	 */
	void cutOffTask(String tid) throws SQLException;

	/**
	 * �ж��Ƿ��ظ�����
	 * @param su
	 * @return
	 */
	SignUpInfo findSignUpInfo(SignUpInfo su) throws SQLException;

	/**
	 * ������Ϣ��ϸ��Ϣ�����������߸�����Ϣ
	 * @param uid ������uid
	 * @param tid ����tid
	 * @return
	 */
	SignUpMsgDetail findSignUpMsgDetail(String uid, String tid) throws SQLException;

	/**
	 * ������Ϣ״̬
	 * @param uid ������uid
	 * @param tid ����tid
	 */
	void changeMsgState(String uid, String tid) throws SQLException;

	/**
	 * �鿴�ȴ��к��ѱ��ܾ�������
	 * @param uid �û�uid
	 * @param state ״̬0��2
	 * @return
	 */
	List<Task> findSignUpTaskRefuse(String uid, String[] state) throws SQLException;

	/**
	 * �������ߵ���Ϣ
	 * @param uid ������uid
	 * @param tid ����tid
	 * @param state ����״̬�ж��ǽ��ܻ��Ǿܾ�
	 * @return
	 */
	void dealSignUpMsg(String uid, String tid, String state) throws SQLException;

	/**
	 * ��������tid����uid
	 * @param su
	 * @return
	 */
	String findUidByTid(SignUpInfo su) throws SQLException ;

	

	

}
