package com.applinzi.testwhy.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.applinzi.testwhy.utils.MySessionContext;
/**
 * 一张验证码
 * @author why
 *
 */
public class ValiImg extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//不缓存
		response.setDateHeader("Expires",-1);
		response.setHeader("Cache-Control", "no-catch");
		response.setHeader("Program","no-catch");
		int height = 30;
		int width = 120;
		//在内存中构建出一张图片
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//获取图片上的画布
		Graphics2D g = (Graphics2D) img.getGraphics();
		//设置背景色
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0,0, width, height);
		//设置边框
		g.setColor(Color.BLUE);
		g.drawRect(0,0, width-1, height-1);
		//画干扰线
		for(int i = 0; i<4; i++){
			g.setColor(Color.red);		
			g.drawLine(randNum(0,width),randNum(0,height),randNum(0,width),randNum(0,height));
		}
		//写验证码
		String s = "";
		for(int i = 0; i<5; i++){
			String base = "0123456789" ;
			g.setColor(new Color(randNum(40,80),randNum(40,80),randNum(40,80)));
			g.setFont(new Font("黑体",Font.BOLD,25));
			int r = randNum(-45,45);
			String vali = base.charAt(randNum(0,base.length()))+"";
			g.rotate(1.0*r/180*Math.PI , 5+24*i , 20);
			g.drawString(vali , 5+24*i, 22);
			g.rotate(-1.0*r/180*Math.PI , 5+24*i , 20);		
			s = s+vali;
		}
		//为方便开发，将验证码打印到控制台
		System.out.println(s);
		//获取前端发来的sessionid
		String sessionid = request.getParameter("sessionid");
		//获取该session
		HttpSession session = MySessionContext.getSession(sessionid);
		//将验证码存入到session
		session.setAttribute("valistr",s);	
		
		//将图片输出到浏览器
		ImageIO.write(img,"jpg",response.getOutputStream());
	}
	private Random rand = new Random();
	/**
	 * 设置随机数从begin-end之间
	 * @param begin
	 * @param end
	 * @return
	 */
	private int randNum(int begin,int end){
		return rand.nextInt(end-begin)+begin;
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
		
	}

}
