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
 * ���ù����ྭ�н���
 * @author why
 *
 */

public class BasicFactory {
	//����ģʽ
	private static BasicFactory factory = new BasicFactory();
	private static Properties prop = null;
	private static InputStream is = null;
	public static BasicFactory getFactory(){
		return factory;
	}
	
	static{
		try{
			//��ȡ�������ļ���������
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
			//��ȡ�ýӿڵ�����
			String cName = clazz.getSimpleName();
			//���������ļ��ж�Ӧ����Ϣ��ȡʵ�ָýӿڵ��������
			String cImplName = prop.getProperty(cName);
			//���÷��䷵�ظ�ʵ����
			final T service = (T)Class.forName(cImplName).newInstance();
			//--Ϊ��ʵ��APO������service��̬��������ע��ȷ����serviceִ�з���֮ǰ��֮����һЩ���������磺���������¼��־��ϸ����Ȩ�޿���...
			T proxyService = (T)Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces()
					, new InvocationHandler(){

						public Object invoke(Object proxy, Method method,
								Object[] args) throws Throwable {
							//����ע���������,�����ע�⣬���������
							if(method.isAnnotationPresent(Tran.class)){
								try {
									//��������
									TransactionManager.startTran();
									//ִ�������ķ���
									Object obj = method.invoke(service, args);
									//�ύ����
									TransactionManager.commit();
									return obj;								
								} catch (InvocationTargetException e) {
									//�ع�����
									TransactionManager.rollback();
									throw new RuntimeException(e);
								}catch (Exception e) {
									//�ع�����
									TransactionManager.rollback();
									throw new RuntimeException(e);
								}finally{
									//�ͷ���Դ
									System.out.println("���������");
									TransactionManager.release();
								}
							}else{//û��ע�⣬�򲻹�������ֱ��ִ�з���
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
			//��ȡ�ýӿڵ�����
			String cName = clazz.getSimpleName();
			//���������ļ��ж�Ӧ����Ϣ��ȡʵ�ָýӿڵ��������
			String cImplName = prop.getProperty(cName);
			//���÷��䷵�ظ�ʵ����
			return (T)Class.forName(cImplName).newInstance();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
