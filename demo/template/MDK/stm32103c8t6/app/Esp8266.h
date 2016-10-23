#ifndef _ESP8266_H
#define _ESP8266_H

#include "USART.h"
#include "GPIO.h"
#include "Delay.h"
#include <stdio.h>
#include <string.h>

/***			Macro Definition		***/

//#define		SSID		"Jason"	
//#define 	PASSWORD	"11303010139"	

//#define		IP_ADDRESS	"115.28.148.178"

#define		IP_ADDRESS	"192.168.137.1"
#define		PORT		9000
#define		LENGTH		20


typedef enum
{
	ESP_NOERR,
	ESP_LINKERR,
	ESP_BUSY,
	ESP_OTHERERR
}WifiErr;

class Esp8266
{
	private:
		USART &mUsart;

	public:
		Esp8266(USART &usart);
		~Esp8266();
		WifiErr Init(char *ssid,char *password);
		bool InitServer();  //作为服务器的AT指令操作
		
		WifiErr SendData(char *data);
		WifiErr SendDataToAPP(char *data,int length);
		
		bool GetDataFromAPP(u8 *rcvbuf);
		bool GetDataFromServ(u8 *rcvbuf);
		USART& GetmUsart();
//		void HardWareReset(GPIO &WifiReset);
};

#endif
