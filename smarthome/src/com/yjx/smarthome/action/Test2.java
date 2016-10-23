package com.yjx.smarthome.action;

import java.util.Scanner;

import org.junit.Test;

public class Test2 {

	@Test
	public void test() {
//		ApplicationContext ctx = 
//				new ClassPathXmlApplicationContext("applicationContext.xml");	
//		UserService userServiceImpl = (UserService) ctx.getBean("userServiceImpl");
//		UserInfo user = new UserInfo();
////		user.setUserId(123);
//		user.setAccount("zss");
//		user.setPassword("0000");
//		userServiceImpl.create(user);
//		fail("Not yet implemented");
		
		/*设有n个正整数，将他们连接成一排，组成一个最大的多位整数。
		如:n=3时，3个整数13,312,343,连成的最大整数为34331213。
		如:n=4时,4个整数7,13,4,246连接成的最大整数为7424613。*/
		//有多组测试样例，每组测试样例包含两行，
		//第一行为一个整数N（N<=100），第二行包含N个数(每个数不超过1000，空格分开)。
		
		//输出：每组数据输出一个表示最大的整数
		
//		给定一个句子（只包含字母和空格）， 将句子中的单词位置反转，单词用空格分割, 单词之间只有一个空格，前后没有空格。
//		比如：
//		（1） “hello xiao mi”-> “mi xiao hello”
	}
		public static void main(String args[]){
			System.out.println("请输入你每一对英雄的数量，");
			Scanner sc = new Scanner(System.in);
			String str  ;
			while(true){
				str = sc.nextLine();
				if(str.length()<1000){
					break;
				}
			}
			String[] data  = str.toString().split(" ");
			for(int i=data.length;i>0;i--){
				System.out.print(data[i-1]+" ");
			}
		}
}
