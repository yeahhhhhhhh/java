package com.applinzi.testwhy.service;

import java.util.List;
import java.util.UUID;

import com.applinzi.testwhy.dao.TaskDao;
import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.domian.SignUpInfo;
import com.applinzi.testwhy.domian.SignUpMsg;
import com.applinzi.testwhy.domian.SignUpMsgDetail;
import com.applinzi.testwhy.domian.SignUpMsgPage;
import com.applinzi.testwhy.domian.Task;
import com.applinzi.testwhy.domian.TaskPage;
import com.applinzi.testwhy.exception.MsgException;
import com.applinzi.testwhy.factory.BasicFactory;

public class TaskServiceImpl implements TaskService {
	private TaskDao dao = BasicFactory.getFactory().getDao(TaskDao.class);

	public Redata taskListByNowPage(Integer nowPage,Integer listCount) {
		try {
			if(nowPage == null || listCount == null || "".equals(nowPage) || "".equals(listCount))
				return new Redata("400","INVALID REQUEST");
			Integer begin = (nowPage-1)*listCount;
			List<Task>taskList = dao.taskListByNowPage(begin,listCount);
			Integer taskCount = dao.findTaskCount();
			Integer pageCount = (taskCount%listCount == 0)?taskCount/listCount : taskCount/listCount+1;
			return new Redata("200","OK",new TaskPage(nowPage,pageCount,taskCount,taskList));
		} catch (Exception e) {
			e.printStackTrace();
			return new Redata("400","INVALID REQUEST");
		}
	}

	public Redata addTask(Task task) {
		try {
			task.checkValues();
			task.setTid(UUID.randomUUID().toString());
			dao.addTask(task);
			return new Redata("200","OK");
		}catch(MsgException e){
			return new Redata("400",e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Redata findTaskByTid(String tid) {
		try {
			if(tid == null || "".equals(tid))
				return new Redata("400","INVALID REQUEST");
			Task task = dao.findTaskByTid(tid);
			return new Redata("200","OK",task);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Redata finUserTaskByUid(String uid) {
		try {
			List<Task>taskList = dao.finUserTaskByUid(uid);
			return new Redata("200","OK",taskList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Redata addSignUpInfo(SignUpInfo su) {
		try {
			if(su.getRelease_uid() == null || "".equals(su.getRelease_uid()) || su.getTid() == null || "".equals(su.getTid()))
				return new Redata("400","INVALID REQUEST");
			String state = dao.findTaskStateByTid(su.getTid());
			if(state.equals("1"))
				return new Redata("401","报名已截止");

			//判断是否重复报名
			SignUpInfo info = dao.findSignUpInfo(su);
			if(info != null)
				return new Redata("400","请勿重复报名");
			//判断是否自己报名自己发布的任务
			String myuid = dao.findUidByTid(su);
			if(myuid != null && myuid.equals(su.getUid()))
				return new Redata("400","请勿报名自己发布的任务");
			dao.addSignUpInfo(su);
			return new Redata("200","Ok");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Redata findSignUpMsgList(SignUpMsgPage sp) {
		try {
			if(sp.getMsgNowPage() == null || sp.getMsgListCount() == null || sp.getMsgNowPage()<=0 || sp.getMsgListCount()<0)
				return new Redata("400","INVALID REQUEST");
			Integer begin = (sp.getMsgNowPage() - 1)*sp.getMsgListCount();
			//分页查找消息列表
			List<SignUpMsg> suList = dao.findSignUpMsgList(begin,sp);
			//查找全部消息条数
			Integer msgAllCount = dao.findSignUpMsgAllCount(sp);
			//计算消息总页数
			Integer msgPageCount = (msgAllCount%sp.getMsgListCount() == 0) ? msgAllCount/sp.getMsgListCount() : msgAllCount/sp.getMsgListCount()+1;
			//查找未读消息
			Integer noReadMsgCount = dao.findNoReadMsgCount(sp.getUid());
			//计算已读消息
			Integer yesReadMsgCount = msgAllCount - noReadMsgCount;
			sp.setSuList(suList);
			sp.setMsgPageCount(msgPageCount);
			sp.setNoReadMsgCount(noReadMsgCount);
			sp.setYesReadMsgCount(yesReadMsgCount);
			return new Redata("200","OK",sp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Redata findSignUpMsgNoReadCount(String uid) {
		try {
			Integer noReadMsgCount = dao.findNoReadMsgCount(uid);
			return new Redata("200","OK",noReadMsgCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Redata findSignUpTask(String uid,String state) {
		try {
			List<Task> taskList = dao.findSignUpTask(uid,state);
			return new Redata("200","OK",taskList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Redata cutOffTask(String tid) {
		try {
			if(tid == null || "".equals(tid))
				return new Redata("400","INVALID REQUEST");
			dao.cutOffTask(tid);
			return new Redata("200","OK");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Redata findSignUpMsgDetail(String uid, String tid) {
		try {
			if(uid == null || "".equals(uid) || tid == null || "".equals(tid))
				return new Redata("400","INVALID REQUEST");
			//查询详细信息
			SignUpMsgDetail sud = dao.findSignUpMsgDetail(uid,tid);
			//将消息状态改成1
			dao.changeMsgState(uid,tid);
			return new Redata("200","OK",sud);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Redata findSignUpTaskRefuse(String uid, String[] state) {
		try {
			List<Task> taskList = dao.findSignUpTaskRefuse(uid,state);
			return new Redata("200","OK",taskList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Redata dealSignUpMsg(String uid, String tid, String state) {
		try {
			if(uid == null || "".equals(uid) || tid == null || "".equals(tid))
				return new Redata("400","INVALID REQUEST");
			dao.dealSignUpMsg(uid,tid,state);
			return new Redata("200","OK");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	

	

	

}
