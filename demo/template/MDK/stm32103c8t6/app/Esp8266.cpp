#include "Esp8266.h"
#include <stdlib.h>
#include <stdio.h>

#define PORT_AS_SERVER 8888
Esp8266::Esp8266(USART &usart):mUsart(usart)
{

}

Esp8266::~Esp8266()
{

}

bool Esp8266::InitServer(){
	u8 s[150],ErrorCount = 0;
	WifiErr status = ESP_NOERR;
	
	mUsart.ClearReceiveBuffer();
	mUsart << "AT+CWMODE=2\r\n";			//AP+模式
	Delay::Ms(10);	
	
	mUsart << "AT+CIPMUX=1\r\n";			//AP+模式
	Delay::Ms(10);	
	mUsart.ClearReceiveBuffer();
	
	
	mUsart << "AT+CIPSERVER=1," << PORT_AS_SERVER << "\r\n";			//AP+station模式
	
	return true;
}

WifiErr Esp8266::Init(char *ssid,char *password)
{
	u8 s[150],ErrorCount = 0;
	WifiErr status = ESP_NOERR;
	
	mUsart << "AT+CWMODE=1\r\n";			//station模式
	Delay::Ms(5);	
	
	mUsart.ClearReceiveBuffer();
	
	mUsart << "AT+CIPSTATUS\r\n";				//获得连接状态
	Delay::Ms(10);
	mUsart.GetReceivedData(s,mUsart.ReceiveBufferSize());
	if( !strstr((char *)s,"STATUS:3") )			//如果未获得传输层连接				
	{
		if(!strstr((char *)s,"STATUS:2"))		//如果未连上wifi
		{
			mUsart.ClearReceiveBuffer();
			mUsart << "AT+CWJAP=" << "\"" << ssid << "\"" << "," << "\"" << password << "\"\r\n";
			Delay::Ms(2500);
			
			mUsart.GetReceivedData(s,mUsart.ReceiveBufferSize());
			while(!strstr((char *)s,"OK"))
			{
				ErrorCount++;
				mUsart << "AT+CWJAP=" << "\"" << ssid << "\"" << "," << "\"" << password << "\"\r\n";
				Delay::Ms(2500);
				mUsart.GetReceivedData(s,mUsart.ReceiveBufferSize());
				if(ErrorCount > 2)
				{
					ErrorCount = 0;	
					status = ESP_LINKERR;	
					break;
				}
			}
		}
		
		if(status != ESP_LINKERR)
		{
			mUsart.ClearReceiveBuffer();
			
			mUsart << "AT+CIPMUX=0\r\n";				//设置单链接
		//	mUsart << "AT+CIPMUX=1\r\n";				//设置多链接
			Delay::Ms(5);
			
			mUsart.ClearReceiveBuffer();		
			mUsart << "AT+CIPSTART=\"TCP\"" << "," << "\"" << IP_ADDRESS << "\"" << "," << PORT << "\r\n";	//连接远程服务器
			Delay::Ms(2000);

			mUsart.GetReceivedData(s,mUsart.ReceiveBufferSize());
			while(!(strstr((char *)s,"OK") && !strstr((char *)s,"ALREADY")))
			{
				ErrorCount++;
				mUsart << "AT+CIPSTART=\"TCP\"" << "," << "\"" << IP_ADDRESS << "\"" << "," << PORT << "\r\n";	//连接远程服务器
				Delay::Ms(2000);
				mUsart.GetReceivedData(s,mUsart.ReceiveBufferSize());
				if(ErrorCount > 2)
				{
					ErrorCount = 0;	
					status = ESP_LINKERR;
					break;
				}
			}
		}	
	}
	
	mUsart << "AT+CIPMODE=1\r\n";				//设置透传模式
	Delay::Ms(50);
	
	if(status != ESP_LINKERR)
	{
		mUsart << "AT+CIPSEND\r\n";				//开始发送信息
		Delay::Ms(1000);
	}	
	
	mUsart.ClearReceiveBuffer();
	
	return status;
}


WifiErr Esp8266::SendData(char *data)
{
	WifiErr status= ESP_NOERR;
	//strcat((char *)data,"\n");

	mUsart.SendData((u8 *)data,strlen((char *)data));
	
	return status;

}

WifiErr Esp8266::SendDataToAPP(char *data,int length)
{
	WifiErr status= ESP_NOERR;
	//strcat((char *)data,"\n");
	mUsart.ClearReceiveBuffer();
	mUsart << "AT+CIPSTATUS\r\n";
	char s2[150];
	
	Delay::Ms(200);
	mUsart.GetReceivedData((u8 *)s2,mUsart.ReceiveBufferSize());
		int id=100;
//AT+CIPSTATUS
//
//STATUS:3
//+CIPSTATUS:0,"TCP","192.168.4.2",51824,1
//
//OK
	int count=0;
	for(int i=0;i<100;i++)
	{
		if(s2[i] == ':'){
			count++;
		}
		if(count>=2){
			id = s2[i+1]-'0';
			break;
		}
	}
	mUsart.ClearReceiveBuffer();
	
	mUsart.ClearSendBuffer();
	Delay::Ms(50);
	//ok|id|10|smartsocket
	
	mUsart << "AT+CIPSEND="<<id<<","<<length << "\r\n";
	Delay::Ms(50);
	
	mUsart.SendData((u8 *)data,length);
	Delay::Ms(50);
	return status;
}


bool Esp8266::GetDataFromAPP(u8 *rcvbuf)
{
	if(mUsart.ReceiveBufferSize() < 5)
	{
		return false;
	}
	else
	{
		Delay::Ms(5);
		mUsart.GetReceivedData(rcvbuf,mUsart.ReceiveBufferSize());
		//if(rcvbuf[3] != '0' || rcvbuf[3] != '1')		//local address (in this simple protocol)
		//{	return false;}
		return true;
	}
	
	 
}

bool Esp8266::GetDataFromServ(u8 *rcvbuf)
{
	if(mUsart.ReceiveBufferSize() < 1)
	{
		return false;
	}
	else
	{
		Delay::Ms(5);
		mUsart.GetReceivedData(rcvbuf,mUsart.ReceiveBufferSize());
		//if(rcvbuf[3] != '0' || rcvbuf[3] != '1')		//local address (in this simple protocol)
		//{	return false;}
		return true;
	}
	 
}

//得到所使用的USART对象
USART& Esp8266::GetmUsart()
{
	return mUsart;
}

/*
void Esp8266::HardWareReset(GPIO &WifiReset)
{
	WifiReset.SetLevel(0);
	tskmgr.DelayMs(10);
	WifiReset.SetLevel(1);
	tskmgr.DelayMs(100);
}
*/

