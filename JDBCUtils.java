package com.applinzi.wanghaoyu2016.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
	private static Properties prop = null;
	private JDBCUtils(){}
	
	//建立连接 123
	public static Connection getConn(String url,String name,String password) throws ClassNotFoundException, SQLException{
		//注册数据库驱动
		Class.forName("com.mysql.jdbc.Driver");
		//取得连接
		return DriverManager.getConnection(url,name,password);
	}
	
	//关闭连接
	public static void close(ResultSet rs,Statement stat,Connection conn){
		if(rs != null){
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				rs = null;
			}
		}

		if(stat != null){
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				stat = null;
			}
		}
		if(conn != null){
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				conn = null;
			}
		}
	}
}
