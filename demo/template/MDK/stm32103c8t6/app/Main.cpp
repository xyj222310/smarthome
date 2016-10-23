#include "stm32f10x.h"
#include "Configuration.h"
#include "TaskManager.h"
#include "USART.h"
#include "I2C.h"
#include "Timer.h"
#include "ADC.h"
#include "PWM.h"
#include "flash.h"
#include "InputCapture_TIM.h"
#include "InputCapture_EXIT.h"
#include "LED.h"
#include "ESP8266.h"
#include <string.h>

/*状态*/

#define SERVER_IP			  "192.168.137.1"                   //  	"223.104.25.68"   
#define SERVER_COM 			9000   

#define DEVICE_ID 2016

//Timer T1(TIM1,1,2,3); //使用定时器计，溢出时间:1S+2毫秒+3微秒
USART com2(2,115200,false);
USART com1(1,115200,false);
USART com3(3,115200,true);
I2C abc(2); 
PWM pwm2(TIM2,1,1,1,1,20000);  //开启时钟2的4个通道，频率2Whz
PWM pwm3(TIM3,1,1,0,0,20000);  //开启时钟3的2个通道，频率2Whz
PWM pwm4(TIM4,1,1,1,0,20000);  //开启时钟4的3个通道，频率2Whz
//InputCapture_TIM t4(TIM4, 400, true, true, true, true);
InputCapture_EXIT ch1(GPIOB,6);
ADC pressure(1); //PA1读取AD值
flash InfoStore(0x08000000+100*MEMORY_PAGE_SIZE,true);     //flash

GPIO ledRedGPIO(GPIOB,0,GPIO_Mode_Out_PP,GPIO_Speed_50MHz);//LED GPIO
GPIO ledBlueGPIO(GPIOB,1,GPIO_Mode_Out_PP,GPIO_Speed_50MHz);//LED GPIO
LED ledRed(ledRedGPIO);//LED red
LED ledBlue(ledBlueGPIO);//LED blue



GPIO led2GPIO(GPIOC,13,GPIO_Mode_Out_PP,GPIO_Speed_50MHz);//LED GPIO
LED led2(led2GPIO);//LED2
	bool network=false ; //网络连接标识位 
Esp8266 wifi(com3);

int main()
{
	//ledBlue.On();
	//ledRed.Off();
	double record_tmgTest=0; //taskmanager时间 测试
	
	
	char dataFromApp[100];//从手机接受的原始数据
	char dataFromApp2[100];//从手机接受的原始数据2
	char dataFromServ[200];//从服务器接受的原始数据
	char ssid[40]; 
	char password[20];
//	while(1)
//	{
			wifi.GetmUsart() << "+++";					//先退出透传模式
			Delay::Ms(50);
		//	wifi.GetmUsart() << "AT+RST\r\n";
		//	Delay::Ms(3000);
		
//			wifi.InitServer(); //ESP8266作为服务器，手机链接wifi模块
//			Delay::Ms(20);
//	

//			while(1){
//	
//				wifi.SendDataToAPP("ok:id:100:smartsocket",20);//接收到ssid he pwd就发id和name给app
//				Delay::Ms(50);
//				wifi.GetDataFromAPP((u8 *)dataFromApp2);//手机发送ssid 和pwd过来
//				//解析穿过来的数据，但是格式是这样的“+IPD，XXX：ssid|8ge0|passord|00000000;”  
//				if(strstr((char *)dataFromApp2,"ssid"))
//				{break;
//				}
//			}
				//Delay::Ms(200);
//				const char *sep = "|"; //可按多个字符来分割
//				char *p; 
//				p = strtok(dataFromApp2, sep);
//				for(int i=0;p;i++)
//				{  
//					if(i==1){
//						strcpy(ssid,p);
//					}
//					
//					if(i==3){
//							strcpy(password,p);
//					}
//					p = strtok(NULL, sep);
//				}
//			
	

				
			//wifi.GetmUsart() << "AT+RST\r\n";
			//Delay::Ms(3000);
			
		//if(ESP_NOERR ==	wifi.Init(ssid,password)) //ESP8266作为station，主动去连接服务器
		if(!(ESP_NOERR ==	wifi.Init("8ge0","00000000"))) //ESP8266作为station，主动去连接服务器
		{
			//wifi.GetmUsart() << "+++";					//先退出透传模式
			Delay::Ms(50);
			
			//wifi.GetmUsart() << "AT\r\n";
			
		//	Delay::Ms(4000);
			wifi.Init("8ge0","00000000");	
		}
		Delay::Ms(100);
		
		while(1){
			char *s =   ":ok:123:smartsocket:";
			wifi.SendData(s);
			Delay::Ms(5500);
			wifi.GetDataFromServ((u8 *)dataFromServ);//1或者0
			if(strstr((char *)dataFromServ,"true"))
				{
					led2.On();
					//com2.SendData("open",4);
					//吧继电器打开；
				}
			if(strstr((char *)dataFromServ,"false"))
				{
					//吧继电器关了；
				//	led2GPIO.SetLevel(0); //灭了
					led2.Off();
				}
				if(strstr((char *)dataFromServ,"failed"))
				{
					led2.Blink(3,100,true);

				}
					
		}
	//}
}
