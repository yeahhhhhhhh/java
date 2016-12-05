package com.applinzi.testwhy.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class TransactionManager {
	private TransactionManager(){}
	//数据源，整个程序中只有这一个数据源
	private static DataSource source = new ComboPooledDataSource();
	
	//是否开启事务的标志
	private static ThreadLocal<Boolean> isTran_local = new ThreadLocal<Boolean>(){
		@Override
		protected Boolean initialValue() {
			return false;//最开始是false，表明没有开启事务
		}
	};
	//保存真实连接的代理连接，改造了close方法
	private static ThreadLocal<Connection>proxyConn_local = new ThreadLocal<Connection>();
	//保存真实连接
	private static ThreadLocal<Connection>realConn_local = new ThreadLocal<Connection>();
	
	/**
	 * 开启事务
	 * @throws SQLException
	 */
	public static void startTran() throws SQLException{
		//设置事务标记为true
		isTran_local.set(true);
		//创建连接,该线程所用的连接都是该连接
		final Connection conn = source.getConnection();
		//开启事务
		conn.setAutoCommit(false);
		//为了后续关闭连接，保存到当前线程中
		realConn_local.set(conn);
		
		//由于一个事务需要执行多个sql语句，每次执行完后会关闭连接，所以在这里改造close方法,使连接不关闭
		Connection proxyConn = (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(), conn.getClass().getInterfaces()
				, new InvocationHandler(){

					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						if("close".equals(method.getName())){
							return null;
						}else{
							return method.invoke(conn, args);
						}
						
					}
		});
		proxyConn_local.set(proxyConn);
	}
	
	/**
	 * 提交事务
	 */
	public static void commit(){
		DbUtils.commitAndCloseQuietly(proxyConn_local.get());
	}
	
	/**
	 * 回滚事务
	 */
	public static void rollback(){
		DbUtils.rollbackAndCloseQuietly(proxyConn_local.get());
	}
	
	/**
	 * 得到数据源
	 * @return
	 */
	public static DataSource getSource(){
		//如果开启过事务，就返回改造过的DataSource，使每次得到的conn为同一个开启过事务的conn
		if(isTran_local.get()){
			return (DataSource) Proxy.newProxyInstance(source.getClass().getClassLoader(), source.getClass().getInterfaces()
					, new InvocationHandler(){

						public Object invoke(Object proxy, Method method,
								Object[] args) throws Throwable {
							if("getConnection".equals(method.getName())){
								return proxyConn_local.get();
							}else{
								return method.invoke(source, args);
							}
							
						}
				
			});
		}else{//没有开启过事务，返回普通的数据源
			return source;
		}
	}
	
	/**
	 * 释放资源
	 */
	public static void release(){
		DbUtils.closeQuietly(realConn_local.get());
		realConn_local.remove();
		proxyConn_local.remove();
		isTran_local.remove();
	}
}
