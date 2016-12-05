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
	//����Դ������������ֻ����һ������Դ
	private static DataSource source = new ComboPooledDataSource();
	
	//�Ƿ�������ı�־
	private static ThreadLocal<Boolean> isTran_local = new ThreadLocal<Boolean>(){
		@Override
		protected Boolean initialValue() {
			return false;//�ʼ��false������û�п�������
		}
	};
	//������ʵ���ӵĴ������ӣ�������close����
	private static ThreadLocal<Connection>proxyConn_local = new ThreadLocal<Connection>();
	//������ʵ����
	private static ThreadLocal<Connection>realConn_local = new ThreadLocal<Connection>();
	
	/**
	 * ��������
	 * @throws SQLException
	 */
	public static void startTran() throws SQLException{
		//����������Ϊtrue
		isTran_local.set(true);
		//��������,���߳����õ����Ӷ��Ǹ�����
		final Connection conn = source.getConnection();
		//��������
		conn.setAutoCommit(false);
		//Ϊ�˺����ر����ӣ����浽��ǰ�߳���
		realConn_local.set(conn);
		
		//����һ��������Ҫִ�ж��sql��䣬ÿ��ִ������ر����ӣ��������������close����,ʹ���Ӳ��ر�
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
	 * �ύ����
	 */
	public static void commit(){
		DbUtils.commitAndCloseQuietly(proxyConn_local.get());
	}
	
	/**
	 * �ع�����
	 */
	public static void rollback(){
		DbUtils.rollbackAndCloseQuietly(proxyConn_local.get());
	}
	
	/**
	 * �õ�����Դ
	 * @return
	 */
	public static DataSource getSource(){
		//������������񣬾ͷ��ظ������DataSource��ʹÿ�εõ���connΪͬһ�������������conn
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
		}else{//û�п��������񣬷�����ͨ������Դ
			return source;
		}
	}
	
	/**
	 * �ͷ���Դ
	 */
	public static void release(){
		DbUtils.closeQuietly(realConn_local.get());
		realConn_local.remove();
		proxyConn_local.remove();
		isTran_local.remove();
	}
}
