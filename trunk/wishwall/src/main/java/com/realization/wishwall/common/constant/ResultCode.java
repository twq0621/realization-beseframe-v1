package com.realization.wishwall.common.constant;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-20   下午10:24:45
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public enum ResultCode {

	Success((byte)0,""),
	Failuer((byte)1,"处理失败"),
	User_Exist((byte)2,"用户已经存在");
	
	public final byte result ;
	
	public final String desc ;
	
	ResultCode(byte result , String desc){
		this.result = result;
		this.desc = desc;
	}
}
