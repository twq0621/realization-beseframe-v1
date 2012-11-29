package com.realization.framework.common;

/**
 * @author xiai_fei
 * 
 * @create-time 2012-11-15 下午10:44:00
 * 
 * @version 1.0
 * @description realization-BaseFrame
 * @版权所有 Realization 团队
 */
public class CommandException extends Exception {

	private String msg;

	private short code;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1020960314566080584L;

	public CommandException(short code, String msg) {
		this.msg = msg;
		this.code = code;
	}

	public CommandException(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public short getCode() {
		return code;
	}

	public void setCode(short code) {
		this.code = code;
	}

}
