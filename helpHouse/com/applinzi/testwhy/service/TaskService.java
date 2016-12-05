package com.applinzi.testwhy.service;

import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.domian.SignUpInfo;
import com.applinzi.testwhy.domian.SignUpMsgPage;
import com.applinzi.testwhy.domian.Task;
import com.applinzi.testwhy.domian.TaskPage;

public interface TaskService extends Service {

	/**
	 * 根据当前页查询列表信息
	 * @param nowPage 当前页
	 * @return
	 */
	Redata taskListByNowPage(Integer nowPage,Integer listCount);

	/**
	 * 添加任务
	 * @param task
	 * @return
	 */
	Redata addTask(Task task);

	/**
	 * 查看任务详情
	 * @param tid 任务tid
	 * @return
	 */
	Redata findTaskByTid(String tid);

	/**
	 * 根据uid查找用户已发布的任务
	 * @param uid
	 * @return
	 */
	Redata finUserTaskByUid(String uid);

	/**
	 * 添加报名信息
	 * @param su 报名信息
	 * @return
	 */
	Redata addSignUpInfo(SignUpInfo su);

	/**
	 * 分页查找用户消息
	 * @param sp 封装了uid、msgNowPage、msgListCount
	 * @return
	 */
	Redata findSignUpMsgList(SignUpMsgPage sp);

	/**
	 * 查找未读的消息数
	 * @param uid
	 * @return
	 */
	Redata findSignUpMsgNoReadCount(String uid);

	/**
	 * 查找该用户已报名任务,只包括等待中需要被接受或拒绝的任务
	 * @param uid
	 * @return
	 */
	Redata findSignUpTask(String uid,String state);

	/**
	 * 截止报名，将state改成1
	 * @param tid
	 * @return
	 */
	Redata cutOffTask(String tid);

	/**
	 * 查询消息详细信息
	 * @param uid
	 * @param tid
	 * @return
	 */
	Redata findSignUpMsgDetail(String uid, String tid);

	/**
	 * 查看等待中和已被拒绝的任务
	 * @param uid 用户uid
	 * @param state 
	 * @return
	 */
	Redata findSignUpTaskRefuse(String uid, String[] state);

	/**
	 * 处理报名者的消息
	 * @param uid 报名者uid
	 * @param tid 任务tid
	 * @param state 根据状态判断是接受还是拒绝
	 * @return
	 */
	Redata dealSignUpMsg(String uid, String tid, String state);

	

	





}
