package com.realization.framework.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.realization.framework.communicate.nio.Buffer;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-23   下午09:46:37
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public class BufferReader implements Buffer{	//我貌似没有指定大小端模式
	
	private static final Log log =LogFactory.getLog(BufferReader.class);
	
	private InputStream in ;
	
	public BufferReader(InputStream in){
		this.in = in ;
	}

	@Override
	@Deprecated
	public int read(byte[] buffer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int read(byte[] buffer, int offset, int length) {
		int i = 0 ;
		for(;i<length;i++){
			try {
				if(in.read()==-1)break;
				buffer[i] = (byte) in.read();
			} catch (IOException e) {
				log.error(" ==== occur error on read buffer ", e);
				return -1 ;
			}
		}
		return i;
	}

	@Override
	@Deprecated
	public byte[] read(int length) {
		
	
		return null;
	}

	@Override
	@Deprecated
	public int getInt() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 如果出现读异常则返回-127
	 */
	@Override
	public short getShort() {
		byte[] bs = new byte[2];
		try {
			in.read(bs);
		} catch (IOException e) {
			log.error("  ==== occur error on read short", e);
			return -127 ;
		}
		short r = (short) ((bs[0]<<4)+bs[1]); //以后这些运算记得加括号，因为+号的运算级比<<高
		return r;
	}

	
	@Override
	@Deprecated
	public long getLong() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Deprecated
	public char getChar() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Deprecated
	public byte getByte() {
		return 0;
	}

	@Override
	@Deprecated
	public float getFloat() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Deprecated
	public double getDouble() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Deprecated
	public int getInt(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Deprecated
	public short getShort(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Deprecated
	public byte getByte(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Deprecated
	public long getLong(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] getBytes(int position, int length) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ByteOrder getByteOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setByteOrder(ByteOrder order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ByteBuffer toByteBuffer() {
		// TODO Auto-generated method stub
		return null;
	}

}
