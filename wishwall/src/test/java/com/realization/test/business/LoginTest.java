package com.realization.test.business;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.realization.framework.messaging.message.Message;
import com.realization.test.connection.HttpConnection;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-14   下午09:29:28
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public class LoginTest  extends HttpConnection{

	public static void main(String[] args) {
		LoginTest test = new LoginTest();
		try {
			test.execute();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	@Override
	public Message readResponse(byte[] bs) {
		Message msg= new Message(bs);
		System.err.println(msg.toString());
		return msg;
	}

	@Override
	protected byte[] makeMessage() {
		Message msg= new Message(true , false , new Random().nextInt(),702,31);
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("n","1760774188");
		map.put("pwd", "123456");
		return msg.toBytes();
	}

}
