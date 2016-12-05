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
			//从数据库中查询出来的头像地址
			String headaddress2 = "";
			
			//1、上传文件
			String upload = null;
			String temp = null;
			Properties pro = System.getProperties();
			String osName = pro.getProperty("os.name");//获得当前操作系统的名称
			 if("Linux".equals(osName) || "linux".equals(osName) || "LINUX".equals(osName)){
				 upload = this.getServletContext().getRealPath("/")+"/upload";
				 temp = this.getServletContext().getRealPath("/")+"/temp";
			 }else{
				 upload = this.getServletContext().getRealPath("upload");
				 temp = this.getServletContext().getRealPath("temp");
			 }
			
			System.out.println(this.getServletContext().getRealPath("upload")+"----");
			
			Map<String,String> pmap = new HashMap<String,String>();
			pmap.put("ip", request.getRemoteAddr());//获取ip，存入map
			
			DiskFileItemFactory factory = new DiskFileItemFactory();//创建工厂
			factory.setSizeThreshold(1024*100);//设置内存缓冲区大小，超过将在临时文件夹进行
			factory.setRepository(new File(temp));//设置临时文件夹位置
			ServletFileUpload fileUpload = new ServletFileUpload(factory);//获取文件上传核心类
			fileUpload.setHeaderEncoding("utf-8");//设置文件名编码
			fileUpload.setFileSizeMax(1024*1024*10);//设置单个文件上传大小限制
			fileUpload.setSizeMax(1024*1024*20);//设置总文件上传大小限制
			
			//设置监听
			fileUpload.setProgressListener(new ProgressListener(){

				public void update(long bytesRead, long contentLength, int items) {
					
					BigDecimal br = new BigDecimal(bytesRead).divide(new BigDecimal(1024),2,BigDecimal.ROUND_HALF_UP);
					BigDecimal cl = new BigDecimal(contentLength).divide(new BigDecimal(1024),2,BigDecimal.ROUND_HALF_UP);
					//上传百分比
					BigDecimal per = br.divide(cl,2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
					System.out.println(per);
					request.getSession().setAttribute("per", per.toString());
				}
				
			});
			
			if(!fileUpload.isMultipartContent(request)){//检查是否是正确的文件上传表单
				response.getWriter().write(objectMapper.writeValueAsString(new Redata("400","请用正确的表单进行上传")));
				return; 
			}
			List<FileItem> list = fileUpload.parseRequest(request);//解析request
			for(FileItem item : list){//遍历list
				if(item.isFormField()){//判断是否是普通字段
					String name = item.getFieldName();//获取字段名
					String value = item.getString("utf-8");//获取字段内容
					pmap.put(name, value);
				}else{//文件上传
					InputStream is = item.getInputStream();//获取输入流
					BufferedImage bi = ImageIO.read(is);
					if(bi == null){
						//删除临时文件
						item.delete();
						response.getWriter().write(objectMapper.writeValueAsString(new Redata("400","请上传正确的图片")));
						return;
					}
					//获取uid
					uid = pmap.get("uid");
					//根据uid查找用户头像地址
					headaddress2 = service.findHeadByUid(uid);
					OutputStream os = null;
					if(headaddress2 == null || "".equals(headaddress2)){
						String realname = item.getName();//获取上传文件的文件名
//						String uuidname = UUID.randomUUID().toString()+"_"+realname;//防止文件名重复
						String uuidname = UUID.randomUUID().toString()+".jpg";//防止文件名重复
						pmap.put("uuidname", uuidname);
						pmap.put("realname", realname);
						
						is = item.getInputStream();//获取输入流
//						String hash = Integer.toHexString(uuidname.hashCode());//获取16进制的哈希码
						String savepath = "/upload";
//						for(char c : hash.toCharArray()){//分目录存储
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
						//new File(upload).mkdirs();//创建目录
						os = new FileOutputStream(new File(headaddress));//输出流
						
						System.out.println(upload);
						System.out.println(savepath);
						System.out.println(uuidname);
						headaddress = savepath+"/"+uuidname;
					}else{
						headaddress = this.getServletContext().getRealPath("/")+headaddress2;
						is = item.getInputStream();//获取输入流
						os = new FileOutputStream(new File(headaddress));//输出流
					}
					//流对接
					IOUtiles.In2Out(is, os);
					IOUtiles.close(is, os);
					//删除临时文件
					item.delete();
				}
			}
			
			//2、向数据库插入文件
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
			response.getWriter().write(objectMapper.writeValueAsString(new Redata("400","文件过大")));
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
