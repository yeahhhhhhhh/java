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
	 * 分页查询
	 * @param begin 处理过后的当前页，begin = (nowPage-1)*listCount
	 * @param listCount 要显示任务的个数
	 * @return
	 */
	List<Task> taskListByNowPage(Integer begin, Integer listCount) throws SQLException;

	/**
	 * 查询任务总个数
	 * @return
	 */
	Integer findTaskCount() throws SQLException;

	/**
	 * 添加任务
	 * @param task
	 */
	void addTask(Task task) throws SQLException;

	/**
	 * 根据任务tid查找任务
	 * @param tid 任务tid
	 * @return
	 */
	Task findTaskByTid(String tid) throws SQLException ;

	/**
	 * 根据uid查找用户已发布的任务
	 * @param uid
	 * @return
	 */
	List<Task> finUserTaskByUid(String uid) throws SQLException;

	/**
	 * 添加报名信息
	 * @param su 报名信息
	 */
	void addSignUpInfo(SignUpInfo su) throws SQLException;

	/**
	 * 分页查找用户消息列表
	 * @param begin 开始
	 * @param sp 包含msgListCount和uid
	 * @return 查找的消息List
	 */
	List<SignUpMsg> findSignUpMsgList(Integer begin, SignUpMsgPage sp) throws SQLException ;

	/**
	 * 查找该用户消息总条数
	 * @param sp 包含了该用户的uid
	 * @return
	 */
	Integer findSignUpMsgAllCount(SignUpMsgPage sp) throws SQLException ;

	/**
	 * 查找该用户未读消息
	 * @param uid 用户的uid
	 * @return
	 */
	Integer findNoReadMsgCount(String uid) throws SQLException ;

	/**
	 * 查找该用户已报名任务,只包括等待中需要被接受或拒绝的任务
	 * @param uid
	 * @return
	 */
	List<Task> findSignUpTask(String uid,String state) throws SQLException ;

	/**
	 * 查找任务状态
	 * @param tid
	 * @return
	 */
	String findTaskStateByTid(String tid) throws SQLException;

	/**
	 * 截止报名，将state改成1
	 * @param tid
	 */
	void cutOffTask(String tid) throws SQLException;

	/**
	 * 判断是否重复报名
	 * @param su
	 * @return
	 */
	SignUpInfo findSignUpInfo(SignUpInfo su) throws SQLException;

	/**
	 * 查找消息详细信息，包括报名者个人信息
	 * @param uid 报名者uid
	 * @param tid 任务tid
	 * @return
	 */
	SignUpMsgDetail findSignUpMsgDetail(String uid, String tid) throws SQLException;

	/**
	 * 更改消息状态
	 * @param uid 报名者uid
	 * @param tid 任务tid
	 */
	void changeMsgState(String uid, String tid) throws SQLException;

	/**
	 * 查看等待中和已被拒绝的任务
	 * @param uid 用户uid
	 * @param state 状态0和2
	 * @return
	 */
	List<Task> findSignUpTaskRefuse(String uid, String[] state) throws SQLException;

	/**
	 * 处理报名者的消息
	 * @param uid 报名者uid
	 * @param tid 任务tid
	 * @param state 根据状态判断是接受还是拒绝
	 * @return
	 */
	void dealSignUpMsg(String uid, String tid, String state) throws SQLException;

	/**
	 * 根据文章tid查找uid
	 * @param su
	 * @return
	 */
	String findUidByTid(SignUpInfo su) throws SQLException ;

	

	

}
