package com.realization.framework.communicate.nio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 通讯通道的缓存区.
 *  @author xiai_fei
 *
 *  @create-time	2012-11-23   下午09:38:13
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface Buffer {

    /**
     * 读取通讯通道中的字节数组.
     * 
     * @param buffer 读取通讯数据的目标地址.
     * 
     * @return 返回实际读取的字节数, 如果返回-1则表示读取失败.
     * 
     */
    int read(byte[] buffer);

    /**
     * 读取通讯通道中的字节数组.
     * 
     * @param buffer 读取通讯数据的目标地址.
     * @param offset 读入buffer的偏移位置.
     * @param length 读取长度
     * 
     * @return 返回实际读取的字节数, 如果返回-1则表示读取失败.
     * 
     */
    int read(byte[] buffer, int offset, int length);
    
    /** 在当前位置读取指定长度的字节数组 */
    byte[] read(int length);
    
    /** 在当前位置读取一个整型数字 */
    int getInt();
    
    /** 在当前位置读取一个短整型数字 */
    short getShort();
    
    /** 在当前位置读取一个长整型数字 */
    long getLong();
    
    /** 在当前位置读取一个字符 */
    char getChar();
    
    /** 在当前位置读取一个字节 */
    byte getByte();
    
    /** 在当前位置读取一个浮点类型数值 */
    float getFloat();
    
    /** 在当前位置读取一个双精度浮点类型数值 */
    double getDouble();
    
    /**
     * 在指定位置读取数值.
     * 
     * @param position 指定位置.
     * 
     */
    int getInt(int position);
    
    /**
     * 在指定位置读取数值.
     * 
     * @param position 指定位置.
     * 
     */
    short getShort(int position);
    
    /**
     * 在指定位置读取数值.
     * 
     * @param position 指定位置.
     * 
     */
    byte getByte(int position);
    
    /**
     * 在指定位置读取数值.
     * 
     * @param position 指定位置.
     * 
     */
    long getLong(int position);
    
    /**
     * 在指定位置读取数值.
     * 
     * @param position 指定位置.
     * 
     */
    byte[] getBytes(int position, int length);
    
    /** 取回当前的字节排序方式 */
    ByteOrder getByteOrder();
    
    /** 设置当前的字节排序方式 */
    void setByteOrder(ByteOrder order);
    
    /** 转换为{@link java.nio.ByteBuffer} */
    ByteBuffer toByteBuffer();
}
