package com.applinzi.testwhy.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.applinzi.testwhy.domian.SignUpInfo;
import com.applinzi.testwhy.domian.SignUpMsg;
import com.applinzi.testwhy.domian.SignUpMsgDetail;
import com.applinzi.testwhy.domian.SignUpMsgPage;
import com.applinzi.testwhy.domian.Task;
import com.applinzi.testwhy.utils.TransactionManager;

public class TaskDaoImpl implements TaskDao {

	public List<Task> taskListByNowPage(Integer begin, Integer listCount) throws SQLException {
		String sql = "select tid,title,content,taskstate,taskdate,money,user.nickname from task,user where user.uid = task.uid and taskstate='0' order by taskdate desc limit ?,?";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		return runner.query(sql, new BeanListHandler<Task>(Task.class),begin,listCount);
	}

	public Integer findTaskCount() throws SQLException {
		String sql = "select count(*) from task where taskstate='0'";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		return ((Number)runner.query(sql,new ScalarHandler())).intValue();
	}

	public void addTask(Task task) throws SQLException {
		String sql = "insert into task values(?,?,?,?,default,null,?,?,?)";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		runner.update(sql,task.getTid(),task.getUid(),task.getTitle(),task.getContent(),task.getMoney(),task.getPhone(),task.getRequirement());
	}

	public Task findTaskByTid(String tid) throws SQLException {
		String sql = "select tid,task.uid,title,content,taskstate,taskdate,money,task.phone,requirement,user.nickname,user.headaddress from task,user where tid=? and user.uid = task.uid";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		return runner.query(sql, new BeanHandler<Task>(Task.class),tid);
	}

	public List<Task> finUserTaskByUid(String uid) throws SQLException {
		String sql = "select * from task where uid = ? order by taskdate desc";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		return runner.query(sql, new BeanListHandler<Task>(Task.class),uid);
	}

	public void addSignUpInfo(SignUpInfo su) throws SQLException {
		String sql = "insert into task_signup(release_uid,receiver_uid,tid) values(?,?,?)";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		runner.update(sql,su.getRelease_uid(),su.getUid(),su.getTid());
	}

	public List<SignUpMsg> findSignUpMsgList(Integer begin, SignUpMsgPage sp) throws SQLException {
		String sql = "SELECT user.`nickname`,user.`username`,task.`title`,task_signup.`signuptime`,task_signup.`msgstate`,task_signup.`state`,user.uid,task.tid "+
					"FROM user,task_signup,task "+ 
					"WHERE user.`uid` = task_signup.`receiver_uid` " +
					"AND task_signup.`tid` = task.`tid` "+
					"AND task_signup.`release_uid` = task.`uid` "+
					"AND task_signup.`release_uid` = ? "+
					"ORDER BY signuptime DESC "+
					"LIMIT ?,?";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		return runner.query(sql, new BeanListHandler<SignUpMsg>(SignUpMsg.class),sp.getUid(),begin,sp.getMsgListCount());
	}

	public Integer findSignUpMsgAllCount(SignUpMsgPage sp) throws SQLException {
		String sql = "select count(*) from task_signup where release_uid = ?";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		return ((Number)runner.query(sql, new ScalarHandler(),sp.getUid())).intValue();
	}

	public Integer findNoReadMsgCount(String uid) throws SQLException {
		String sql = "select count(*) from task_signup where release_uid = ? and msgstate = '0'";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		return ((Number)runner.query(sql, new ScalarHandler(),uid)).intValue();
	}

	public List<Task> findSignUpTask(String uid,String state) throws SQLException {
		String sql = "SELECT task.*,user.nickname,task_signup.state "+
			"FROM task,task_signup,user "+
			"WHERE task_signup.`release_uid` = task.`uid` "+
			"AND task_signup.`receiver_uid` = ? "+
			"AND task_signup.`tid` = task.`tid` " +
			"AND task_signup.`state` = ? "+
			"AND user.uid = task.`uid` "+
			"order by taskdate desc";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		return runner.query(sql, new BeanListHandler<Task>(Task.class),uid,state);
	}

	public String findTaskStateByTid(String tid) throws SQLException {
		String sql = "select taskstate from task where tid =?";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		return (String) runner.query(sql, new ScalarHandler(),tid);
	}

	public void cutOffTask(String tid) throws SQLException {
		String sql = "update task set taskstate = 1 where tid = ?";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		runner.update(sql,tid);
	}

	public SignUpInfo findSignUpInfo(SignUpInfo su) throws SQLException {
		String sql = "select * from task_signup where receiver_uid = ? and tid = ?";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		return runner.query(sql, new BeanHandler<SignUpInfo>(SignUpInfo.class),su.getUid(),su.getTid());
	}

	public SignUpMsgDetail findSignUpMsgDetail(String uid, String tid) throws SQLException {
		String sql = "SELECT user.*,task.`title`,task_signup.`state`,task_signup.`signuptime` "+  
			"FROM user,task_signup,task "+
			"WHERE task_signup.`receiver_uid` = ? "+
			"AND task_signup.`tid` = ? "+
			"AND user.`uid` = task_signup.`receiver_uid` "+
			"AND task.`tid` = task_signup.`tid` ";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		return runner.query(sql, new BeanHandler<SignUpMsgDetail>(SignUpMsgDetail.class),uid,tid);
	}

	public void changeMsgState(String uid, String tid) throws SQLException {
		String sql = "update task_signup set msgstate = '1' where receiver_uid = ? and tid = ?";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		runner.update(sql,uid,tid);
	}

	public List<Task> findSignUpTaskRefuse(String uid, String[] state) throws SQLException {
		String sql = "SELECT task.*,user.nickname,task_signup.state "+
		"FROM task,task_signup,user "+
		"WHERE task_signup.`release_uid` = task.`uid` "+
		"AND task_signup.`receiver_uid` = ? "+
		"AND task_signup.`tid` = task.`tid` " +
		"AND (task_signup.`state` = ? "+
		"or task_signup.`state` = ? ) "+
		"AND user.uid = task.`uid` "+
		"order by taskdate desc";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		return runner.query(sql, new BeanListHandler<Task>(Task.class),uid,state[0],state[1]);
	}

	public void dealSignUpMsg(String uid, String tid, String state)	throws SQLException {
		String sql = "update task_signup set state = ? where receiver_uid = ? and tid = ?";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		runner.update(sql,state,uid,tid);
	}

	public String findUidByTid(SignUpInfo su) throws SQLException {
		String sql = "select uid from task where tid = ?";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		return (String) runner.query(sql, new ScalarHandler(),su.getTid());
	}

	

}
