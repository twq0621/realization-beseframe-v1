package com.realization.test.connection;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.realization.framework.messaging.message.Message;


public   abstract class HttpConnection {

	protected static HttpURLConnection hConn = null;
	
	private static final Log log = LogFactory.getLog(HttpConnection.class);
	
	protected static  String HTTP_URL = "http://localhost:8080/wishwall/stream.servlet"; 

	protected  void init() throws Exception {
		URL url = new URL(HTTP_URL);
		hConn = (HttpURLConnection) url.openConnection();

		hConn.setDoInput(true);
		hConn.setDoOutput(true);
		// hConn.setUseCaches(false);
		// 设置请求方法
		hConn.setRequestMethod("POST");
		// 设置连接超时
		hConn.setConnectTimeout(10000);
		// 设置读取超时
		hConn.setReadTimeout(200000);
		hConn.addRequestProperty("Content-Type", "application/octet-stream");
	}

	
	public  void execute() throws Exception{
			init();
			byte[] m1 = makeMessage();
			log.debug("the message send : " + m1.toString());
			hConn.connect();
			hConn.getOutputStream().write(m1);
			hConn.getOutputStream().flush();
			hConn.getOutputStream().close();

			int code = hConn.getResponseCode();
			if (code != 200) {
				throw new Exception("connect=error：" + code);
			}
			log.debug("the contentLength :"+hConn.getContentLength());
			byte[] bs = new byte[hConn.getContentLength()];
			hConn.getInputStream().read(bs);
			readResponse(bs);

	}
	
	/**
	 * 处理后台系统返回的消息
	 * @param bs	后台系统返回的消息
	 */
	public abstract Message readResponse(byte[] bs) ;

	/**
	 * 需要发送的消息
	 */
	protected abstract byte[] makeMessage() ;

}
