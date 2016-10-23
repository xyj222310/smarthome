package com.xieyingjie.smartsocket.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http请求的工具类
 * 
 * @author XieYingjie
 * 
 */
public class HttpUtils
{

	private static final int TIMEOUT_IN_MILLIONS = 4000;

	public interface CallBack
	{
		void onRequestComplete(String result) ;
		void onRequestError(String result);
	}
	/**
	 * 异步的Get请求
	 *
	 * @param urlStr
	 * @param callBack
	 */
	public static void doGetAsyn(final String urlStr, final CallBack callBack)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					String result = doGet(urlStr);
					if (callBack != null)
					{
						callBack.onRequestComplete(result);
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}

			};
		}.start();
	}

	/**
	 * 异步的Post请求
	 * @param urlStr
	 * @param params
	 * @param callBack
	 * @throws Exception
	 */
	public static String doPostAsyn(final String urlStr, final String params,
									final CallBack callBack){
		new Thread(new Runnable() {
			public void run() {
				String result;
				result = doPost(urlStr, params);
//				Log.i("来看看.", "onRequestError: " + result);
				System.out.print(result+"fsdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				if (result!=null && !("".equals(result))){//如果没有exception，post里面的exception令result=""
					if (callBack != null) {//绝对是请求成功,因为result有返回值如果没有的话肯定是null
						callBack.onRequestComplete(result);//解析数据成功变跳转，不成功唔觉得可以返回错误信息；
					}
				}else{
					result = "ConnectionFailed";
					if (callBack != null) {
						callBack.onRequestError(result);
					}
				}
			}
		}).start();
		return "InValid Account or Password";
	}
	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 * @throws Exception
	 */
	public static String doPost(String url, String param)
	{
		PrintWriter out = null;
		BufferedReader in = null;
		String result ="";
		try
		{
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl
					.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setUseCaches(false);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
			if (param != null && !param.trim().equals(""))
			{
				// 获取URLConnection对象对应的输出流
				out = new PrintWriter(conn.getOutputStream());
				// 发送请求参数
				out.print(param);
				// flush输出流的缓冲
				out.flush();
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null)
			{
				result += line;
			}
		} catch (Exception e)
		{
			result = "";
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally
		{
			try
			{
				if (out != null)
				{
					out.close();
				}
				if (in != null)
				{
					in.close();
				}
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Get请求，获得返回数据
	 *
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String urlStr)
	{
		URL url = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try
		{
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			if (conn.getResponseCode() == 200)
			{
				is = conn.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] buf = new byte[128];

				while ((len = is.read(buf)) != -1)
				{
					baos.write(buf, 0, len);
				}
				baos.flush();
				return baos.toString();
			} else
			{
				throw new RuntimeException(" responseCode is not 200 ... ");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (is != null)
					is.close();
			} catch (IOException e)
			{
			}
			try
			{
				if (baos != null)
					baos.close();
			} catch (IOException e)
			{
			}
			conn.disconnect();
		}

		return null ;

	}

}
//public class HttpUtils
//{
//	private static final int TIMEOUT_IN_MILLIONS = 5000;
//
//	public interface CallBack
//	{
//		String onRequestComplete(String result) throws JSONException, Exception;
//		/**
//		* 数据请求失败
//	 	*
//	 	*            返回错误信息
//	 	*/
//		String onRequestError();
//
////		//开启联网请求
////		public abstract String onRequestStart();
////		//联网请求结束
////		public abstract void onRequestFinish(Object obj);
////		//联网请求中
////		public abstract void onRequestLoading(int total,int current);
////		//联网请求失败
////		public abstract void onRequestFailed(String msg);
//	}
//
//	/**
//	 * 异步的Get请求
//	 *
//	 * @param urlStr
//	 * @param callBack
//	 */
//	public static void doGetAsyn(final String urlStr, final CallBack callBack)
//	{
//		new Thread()
//		{
//			public void run()
//			{
//				try
//				{
//					String result = null;
////					String result = doGet(urlStr);
//					URL url = null;
//					HttpURLConnection conn = null;
//					InputStream is = null;
//					ByteArrayOutputStream baos = null;
//					try
//					{
//						url = new URL(urlStr);
//						conn = (HttpURLConnection) url.openConnection();
//						conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
//						conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
//						conn.setRequestMethod("GET");
//						conn.setRequestProperty("accept", "*/*");
//						conn.setRequestProperty("connection", "Keep-Alive");
//						if (conn.getResponseCode() == 200)
//						{
//							is = conn.getInputStream();
//							baos = new ByteArrayOutputStream();
//							int len = -1;
//							byte[] buf = new byte[128];
//
//							while ((len = is.read(buf)) != -1)
//							{
//								baos.write(buf, 0, len);
//							}
//							baos.flush();
//							 baos.toString();
//						} else
//						{
//							throw new RuntimeException(" responseCode is not 200 ... ");
//						}
//
//					} catch (Exception e)
//					{
//						e.printStackTrace();
//					} finally
//					{
//						try
//						{
//							if (is != null)
//								is.close();
//						} catch (IOException e)
//						{
//						}
//						try
//						{
//							if (baos != null)
//								baos.close();
//						} catch (IOException e)
//						{
//						}
//						conn.disconnect();
//					}
//					if (callBack != null)
//					{
//						callBack.onRequestComplete(result);
//					}
//				} catch (Exception e)
//				{
//					if (callBack != null)
//					{
//						callBack.onRequestError();
//					}
//					e.printStackTrace();
//				}
//			};
//		}.start();
//	}
//	/**
//	 * 异步的Post请求
//	 * @param urlStr
//	 * @param callBack
//	 * @throws Exception
//	 */
//	public static String doPostAsyn(final String urlStr,final String param,
//			final CallBack callBack) throws Exception{
//		String msg = null;
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				HttpURLConnection conn = null;
//				try {
//					URL realUrl = new URL(urlStr);
//					conn = (HttpURLConnection) realUrl
//							.openConnection();
//					// 设置通用的请求属性
//					conn.setRequestProperty("accept", "*/*");
//					conn.setRequestProperty("connection", "Keep-Alive");
//					conn.setRequestMethod("POST");
//					conn.setRequestProperty("Content-Type",
//							"application/x-www-form-urlencoded");
//					conn.setRequestProperty("charset", "utf-8");
//					conn.setUseCaches(false);
//					// 发送POST请求必须设置如下两行
//					conn.setDoOutput(true);
//					conn.setDoInput(true);
//					conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
//					conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
//					// 定义BufferedReader输入流来读取URL的响应
//					if(conn.getResponseCode()==HttpURLConnection.HTTP_OK ){
//						if (param != null && !param.trim().equals(""))
//						{
//							// 获取URLConnection对象对应的输出流
//							out = new PrintWriter(conn.getOutputStream());
//							// 发送请求参数
//							out.print(param);
//							// flush输出流的缓冲
//							out.flush();
//						}
//						// 定义BufferedReader输入流来读取URL的响应
//						in = new BufferedReader(
//								new InputStreamReader(conn.getInputStream()));
//						String line;
//						while ((line = in.readLine()) != null)
//						{
//							result += line;
//						}
//						// 创建高效流对象
//						BufferedReader reader = new BufferedReader(
//								new InputStreamReader(in));
//						// 创建StringBuilder对象存储数据
//						StringBuilder result = new StringBuilder();
//						String line;// 一次读取一行
//						while ((line = reader.readLine()) != null) {
//							result.append(line);// 得到的数据存入StringBuilder
//						}
//						//回调
//						if (callBack != null)
//						{
//							callBack.onRequestComplete(result.toString());
//						}
//					}
//				} catch (Exception e) {
//					if (callBack != null) {
//						// 回调onError()方法
//						 callBack.onRequestError();
//					}
//					msg = e.toString();
//					e.printStackTrace();
//				} finally {
//					if (conn != null) {// 通道不为null
//						conn.disconnect();// 关闭通道
//					}
//				}
//			}
//		}).start();
//		return msg;
//	}
//}
