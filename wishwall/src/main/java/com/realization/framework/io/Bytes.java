package com.realization.framework.io;

import java.nio.ByteOrder;

/**
 * 可互换的字节码对象.
 * 
 * @author <a href="zhengduan@angelshine.net">zhengduan</a>
 *
 */
public interface Bytes {
    
    /** 转换为字节码 */
    byte[] toBytes();
    
    /** 转换为字节码 */
    byte[] toBytes(ByteOrder order);
}
