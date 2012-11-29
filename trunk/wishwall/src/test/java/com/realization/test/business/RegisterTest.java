package com.realization.test.business;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.realization.framework.messaging.message.Message;
import com.realization.test.connection.HttpConnection;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-20   下午09:03:45
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public class RegisterTest extends HttpConnection{

	public static void main(String[] args) {
		RegisterTest test = new RegisterTest();
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
		Message msg= new Message(true , false , new Random().nextInt(),701,31);
		Map<String,Object>map = new HashMap<String, Object>();
		msg.put("n","1760774188");
		msg.put("pwd", "123456");
		System.err.println(msg.toBytes().length);
		System.err.println(msg.toBytes()[0]);
		System.err.println(msg.toBytes()[1]);
		return msg.toBytes();
	}

}
