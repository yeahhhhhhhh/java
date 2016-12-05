package com.applinzi.testwhy.factory;

import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Properties;

import com.applinzi.testwhy.annotation.Tran;
import com.applinzi.testwhy.dao.Dao;
import com.applinzi.testwhy.exception.MsgException;
import com.applinzi.testwhy.service.Service;
import com.applinzi.testwhy.utils.TransactionManager;


/**
 * 利用工厂类经行解耦
 * @author why
 *
 */

public class BasicFactory {
	//单列模式
	private static BasicFactory factory = new BasicFactory();
	private static Properties prop = null;
	private static InputStream is = null;
	public static BasicFactory getFactory(){
		return factory;
	}
	
	static{
		try{
			//获取该配置文件的输入流
			is = BasicFactory.class.getClassLoader().getResourceAsStream("config.properties");
			prop = new Properties();
			prop.load(is);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	@SuppressWarnings("unchecked")
	public <T extends Service> T getService(Class<T> clazz){
		try{
			//获取该接口的名字
			String cName = clazz.getSimpleName();
			//利用配置文件中对应的信息获取实现该接口的类的名字
			String cImplName = prop.getProperty(cName);
			//利用反射返回该实现类
			final T service = (T)Class.forName(cImplName).newInstance();
			//--为了实现APO，生成service动态代理，根据注解确定在service执行方法之前和之后做一些操作，比如：事务管理、记录日志、细粒度权限控制...
			T proxyService = (T)Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces()
					, new InvocationHandler(){

						public Object invoke(Object proxy, Method method,
								Object[] args) throws Throwable {
							//根据注解控制事务,如果有注解，则管理事务
							if(method.isAnnotationPresent(Tran.class)){
								try {
									//开启事务
									TransactionManager.startTran();
									//执行真正的方法
									Object obj = method.invoke(service, args);
									//提交事务
									TransactionManager.commit();
									return obj;								
								} catch (InvocationTargetException e) {
									//回滚事务
									TransactionManager.rollback();
									throw new RuntimeException(e);
								}catch (Exception e) {
									//回滚事务
									TransactionManager.rollback();
									throw new RuntimeException(e);
								}finally{
									//释放资源
									System.out.println("事务管理工厂");
									TransactionManager.release();
								}
							}else{//没有注解，则不管理事务，直接执行方法
								try {
									return method.invoke(service, args);
								}catch (InvocationTargetException e) {
									throw new MsgException(e.getTargetException().getMessage());
								} catch (Exception e) {
									throw new RuntimeException(e);
								}
								
							}
						}
				
			});
			return proxyService;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public <T extends Dao> T getDao(Class<T> clazz){
		try{
			//获取该接口的名字
			String cName = clazz.getSimpleName();
			//利用配置文件中对应的信息获取实现该接口的类的名字
			String cImplName = prop.getProperty(cName);
			//利用反射返回该实现类
			return (T)Class.forName(cImplName).newInstance();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
