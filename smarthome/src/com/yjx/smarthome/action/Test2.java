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
		
		/*����n�������������������ӳ�һ�ţ����һ�����Ķ�λ������
		��:n=3ʱ��3������13,312,343,���ɵ��������Ϊ34331213��
		��:n=4ʱ,4������7,13,4,246���ӳɵ��������Ϊ7424613��*/
		//�ж������������ÿ����������������У�
		//��һ��Ϊһ������N��N<=100�����ڶ��а���N����(ÿ����������1000���ո�ֿ�)��
		
		//�����ÿ���������һ����ʾ��������
		
//		����һ�����ӣ�ֻ������ĸ�Ϳո񣩣� �������еĵ���λ�÷�ת�������ÿո�ָ�, ����֮��ֻ��һ���ո�ǰ��û�пո�
//		���磺
//		��1�� ��hello xiao mi��-> ��mi xiao hello��
	}
		public static void main(String args[]){
			System.out.println("��������ÿһ��Ӣ�۵�������");
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
