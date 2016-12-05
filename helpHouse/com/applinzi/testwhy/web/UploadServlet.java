package com.applinzi.testwhy.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.map.ObjectMapper;

import com.applinzi.testwhy.domian.Redata;
import com.applinzi.testwhy.exception.MsgException;
import com.applinzi.testwhy.factory.BasicFactory;
import com.applinzi.testwhy.service.UserService;
import com.applinzi.testwhy.utils.IOUtiles;

public class UploadServlet extends HttpServlet {

	public void doGet(final HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			UserService service = BasicFactory.getFactory().getService(UserService.class);
			String headaddress = "";
			String uid = "";
			//�����ݿ��в�ѯ������ͷ���ַ
			String headaddress2 = "";
			
			//1���ϴ��ļ�
			String upload = null;
			String temp = null;
			Properties pro = System.getProperties();
			String osName = pro.getProperty("os.name");//��õ�ǰ����ϵͳ������
			 if("Linux".equals(osName) || "linux".equals(osName) || "LINUX".equals(osName)){
				 upload = this.getServletContext().getRealPath("/")+"/upload";
				 temp = this.getServletContext().getRealPath("/")+"/temp";
			 }else{
				 upload = this.getServletContext().getRealPath("upload");
				 temp = this.getServletContext().getRealPath("temp");
			 }
			
			System.out.println(this.getServletContext().getRealPath("upload")+"----");
			
			Map<String,String> pmap = new HashMap<String,String>();
			pmap.put("ip", request.getRemoteAddr());//��ȡip������map
			
			DiskFileItemFactory factory = new DiskFileItemFactory();//��������
			factory.setSizeThreshold(1024*100);//�����ڴ滺������С������������ʱ�ļ��н���
			factory.setRepository(new File(temp));//������ʱ�ļ���λ��
			ServletFileUpload fileUpload = new ServletFileUpload(factory);//��ȡ�ļ��ϴ�������
			fileUpload.setHeaderEncoding("utf-8");//�����ļ�������
			fileUpload.setFileSizeMax(1024*1024*10);//���õ����ļ��ϴ���С����
			fileUpload.setSizeMax(1024*1024*20);//�������ļ��ϴ���С����
			
			//���ü���
			fileUpload.setProgressListener(new ProgressListener(){

				public void update(long bytesRead, long contentLength, int items) {
					
					BigDecimal br = new BigDecimal(bytesRead).divide(new BigDecimal(1024),2,BigDecimal.ROUND_HALF_UP);
					BigDecimal cl = new BigDecimal(contentLength).divide(new BigDecimal(1024),2,BigDecimal.ROUND_HALF_UP);
					//�ϴ��ٷֱ�
					BigDecimal per = br.divide(cl,2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
					System.out.println(per);
					request.getSession().setAttribute("per", per.toString());
				}
				
			});
			
			if(!fileUpload.isMultipartContent(request)){//����Ƿ�����ȷ���ļ��ϴ���
				response.getWriter().write(objectMapper.writeValueAsString(new Redata("400","������ȷ�ı������ϴ�")));
				return; 
			}
			List<FileItem> list = fileUpload.parseRequest(request);//����request
			for(FileItem item : list){//����list
				if(item.isFormField()){//�ж��Ƿ�����ͨ�ֶ�
					String name = item.getFieldName();//��ȡ�ֶ���
					String value = item.getString("utf-8");//��ȡ�ֶ�����
					pmap.put(name, value);
				}else{//�ļ��ϴ�
					InputStream is = item.getInputStream();//��ȡ������
					BufferedImage bi = ImageIO.read(is);
					if(bi == null){
						//ɾ����ʱ�ļ�
						item.delete();
						response.getWriter().write(objectMapper.writeValueAsString(new Redata("400","���ϴ���ȷ��ͼƬ")));
						return;
					}
					//��ȡuid
					uid = pmap.get("uid");
					//����uid�����û�ͷ���ַ
					headaddress2 = service.findHeadByUid(uid);
					OutputStream os = null;
					if(headaddress2 == null || "".equals(headaddress2)){
						String realname = item.getName();//��ȡ�ϴ��ļ����ļ���
//						String uuidname = UUID.randomUUID().toString()+"_"+realname;//��ֹ�ļ����ظ�
						String uuidname = UUID.randomUUID().toString()+".jpg";//��ֹ�ļ����ظ�
						pmap.put("uuidname", uuidname);
						pmap.put("realname", realname);
						
						is = item.getInputStream();//��ȡ������
//						String hash = Integer.toHexString(uuidname.hashCode());//��ȡ16���ƵĹ�ϣ��
						String savepath = "/upload";
//						for(char c : hash.toCharArray()){//��Ŀ¼�洢
//							upload += "/"+c;
//							savepath += "/"+c;
//						}
						
						pmap.put("savepath", savepath);
						
						File file = new File(upload);
						if(!file.exists()){
							System.out.println("-------------");
							file.mkdirs();
						}
						headaddress = upload+"/"+uuidname;
						//new File(upload).mkdirs();//����Ŀ¼
						os = new FileOutputStream(new File(headaddress));//�����
						
						System.out.println(upload);
						System.out.println(savepath);
						System.out.println(uuidname);
						headaddress = savepath+"/"+uuidname;
					}else{
						headaddress = this.getServletContext().getRealPath("/")+headaddress2;
						is = item.getInputStream();//��ȡ������
						os = new FileOutputStream(new File(headaddress));//�����
					}
					//���Խ�
					IOUtiles.In2Out(is, os);
					IOUtiles.close(is, os);
					//ɾ����ʱ�ļ�
					item.delete();
				}
			}
			
			//2�������ݿ�����ļ�
			if(headaddress2 == null || "".equals(headaddress2)){
				Redata redata = service.addHeadByUid(uid,headaddress);
				String data = objectMapper.writeValueAsString(redata);
				response.getWriter().write(data);
			}else{
				response.getWriter().write(objectMapper.writeValueAsString(new Redata("200","OK",headaddress2)));
			}
			
		}catch(MsgException e){
			response.getWriter().write(objectMapper.writeValueAsString(new Redata("401",e.getMessage())));
		}catch(SizeLimitExceededException e){
			response.getWriter().write(objectMapper.writeValueAsString(new Redata("400","�ļ�����")));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

}
