package com.ld.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CheckNumUtil {

	private static String checkNum;
	
	/**
	 * 创建验证码图片
	 * 
	 * @return
	 */
	public static BufferedImage createCheckNumImg() {
		int width = 80;
		int height = 35;
		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.LIGHT_GRAY);// 背景色
		g.fillRect(0, 0, width, height);
		Random random = new Random();
		for (int i = 0; i < 15; i++) {// 画干扰线
			g.setColor(getRandColor(40, 90));// 线条颜色
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			g.drawLine(x, y, x1, y1);
		}
		String a = random.nextInt(10) + "";
		String b = random.nextInt(10) + "";
		String c = random.nextInt(10) + "";
		String d = random.nextInt(10) + "";
		checkNum = a + b + c + d;// 验证码
		g.setFont(new Font("宋体", Font.BOLD, 20));
		g.setColor(getRandColor(205, 255));// 字体颜色
		g.drawString(a, 5, 21);// a
		g.setFont(new Font("TimesRoman", Font.BOLD, 19));
		g.setColor(getRandColor(205, 255));// 字体颜色
		g.drawString(b, 17, 21);// b
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.setColor(getRandColor(205, 255));// 字体颜色
		g.drawString(c, 36, 21);// c
		g.setFont(new Font("宋体", Font.BOLD, 19));
		g.setColor(getRandColor(205, 255));// 字体颜色
		g.drawString(d, 54, 21);// d
		return img;
	}

	// 得到一种随机颜色
	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc < 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	/**
	 * 得到验证码
	 * 
	 * @return
	 */
	public static String getCheckNum(){
		return checkNum;
	}
}
