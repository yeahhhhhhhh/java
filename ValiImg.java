package web;

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

public class ValiImg extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setDateHeader("Expires",-1);
		response.setHeader("Cache-Control", "no-catch");
		response.setHeader("Program","no-catch");
		int height = 30;
		int width = 120;
		//���ڴ��й�����һ��ͼƬ
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//��ȡͼƬ�ϵĻ���
		Graphics2D g = (Graphics2D) img.getGraphics();
		//���ñ���ɫ
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0,0, width, height);
		//���ñ߿�
		g.setColor(Color.BLUE);
		g.drawRect(0,0, width-1, height-1);
		//��������
		for(int i = 0; i<4; i++){
			g.setColor(Color.red);		
			g.drawLine(randNum(0,width),randNum(0,height),randNum(0,width),randNum(0,height));
		}
		//д��֤��
		String s = "";
		for(int i = 0; i<5; i++){
			String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" ;
			g.setColor(new Color(randNum(0,255),randNum(0,255),randNum(0,255)));
			g.setFont(new Font("����",Font.BOLD,25));
			int r = randNum(-45,45);
			String vali = base.charAt(randNum(0,52))+"";
			g.rotate(1.0*r/180*Math.PI , 5+24*i , 20);
			g.drawString(vali , 5+24*i, 22);
			g.rotate(-1.0*r/180*Math.PI , 5+24*i , 20);
			
			
			
			s = s+vali;
		}
		System.out.println(s);
		
		
		
		
		//��ͼƬ����������
		ImageIO.write(img,"jpg",response.getOutputStream());
	}
	private Random rand = new Random();
	/**
	 * �����������begin-end֮��
	 * @param begin
	 * @param end
	 * @return
	 */
	private int randNum(int begin,int end){
		return rand.nextInt(end-begin)+begin;
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
	}

}
