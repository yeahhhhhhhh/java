package com.applinzi.testwhy.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.applinzi.testwhy.domian.User;
import com.applinzi.testwhy.utils.TransactionManager;
import com.sun.faces.renderkit.html_basic.ListboxRenderer;

public class UserDaoImpl implements UserDao {

	public User findUserByUNandPW(User user) throws SQLException {
		String sql = "select * from user where username=? and password=?";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		return runner.query(sql,new BeanHandler<User>(User.class),user.getUsername(),user.getPassword());
	}

	public void addUser(User user) throws SQLException {
		String sql = "insert into user(uid,username,password,nickname) values(?,?,?,?)";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		runner.update(sql,user.getUid(),user.getUsername(),user.getPassword(),user.getNickname());
	}

	public User findUserByUsername(User user) throws SQLException {
		String sql = "select * from user where username=?";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		return runner.query(sql, new BeanHandler<User>(User.class),user.getUsername()); 
	}

	public User findUserByUid(String uid) throws SQLException {
		String sql = "select * from user where uid=?";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		return runner.query(sql, new BeanHandler<User>(User.class),uid);
	}

	public void updataUserInfo(User user) throws SQLException {
		String sql = "update user set nickname=?,gender=?,phone=? where uid=?";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		runner.update(sql,user.getNickname(),user.getGender(),user.getPhone(),user.getUid());
	}

	public void addHeadByUid(String uid, String headaddress) throws SQLException {
		String sql = "update user set headaddress = ? where uid = ?";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		runner.update(sql,headaddress,uid);
	}

	public String findHeadByUid(String uid) throws SQLException {
		String sql = "select headaddress from user where uid = ?";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		return (String) runner.query(sql, new ScalarHandler(),uid);
	}
	

}
