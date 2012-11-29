package com.realization.framework.communicate.nio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Date;


public abstract class Bytes implements com.realization.framework.io.Bytes {
    public static byte[] toBytes(int value) {
        return toBytes(value,null);
	}
    public static byte[] putInt(byte[] bs, int index, int value) {
        return putInt(bs,index,value,null);
    }
    public static byte[] toBytes(int value, ByteOrder order) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        if (null!=order) bb.order(order);
        bb.putInt(value);
        return bb.array();
    }
    public static byte[] putInt(byte[] bs, int index, int value, ByteOrder order) {
        System.arraycopy(toBytes(value,order),0,bs,index,4);
        return bs;
    }
	public static int toInt(byte[] bs) {
	    return toInt(bs,null);
	}
    public static int getInt(byte[] bs, int index) {
        return getInt(bs,index,null);
    }
    public static int toInt(byte[] bs, ByteOrder order) {
        ByteBuffer bb = ByteBuffer.wrap(bs);
        if (null!=order) bb.order(order);
        return bb.getInt();
    }
    public static int getInt(byte[] bs, int index, ByteOrder order) {
        ByteBuffer bb = ByteBuffer.wrap(copyOfRange(bs,index,index+4));
        if (null!=order) bb.order(order);
        return bb.getInt();
    }
	public static byte[] toBytes(long value) {
        return toBytes(value,null);
	}
    public static byte[] putLong(byte[] bs, int index, long value) {
        return putLong(bs,index,value,null);
    }
    public static byte[] toBytes(long value, ByteOrder order) {
        ByteBuffer bb = ByteBuffer.allocate(8);
        if (null!=order) bb.order(order);
        bb.putLong(value);
        return bb.array();
    }
    public static byte[] putLong(byte[] bs, int index, long value, ByteOrder order) {
        System.arraycopy(toBytes(value,order),0,bs,index,8);
        return bs;
    }
	public static long toLong(byte[] bs) {
        return toLong(bs,null);
	}
    public static long getLong(byte[] bs, int index) {
        return getLong(bs,index,null);
    }
    public static long toLong(byte[] bs, ByteOrder order) {
        ByteBuffer bb = ByteBuffer.wrap(bs);
        if (null!=order) bb.order(order);
        return bb.getLong();
    }
    public static long getLong(byte[] bs, int index, ByteOrder order) {
        ByteBuffer bb = ByteBuffer.wrap(copyOfRange(bs,index,index+8));
        if (null!=order) bb.order(order);
        return bb.getLong();
    }
	public static byte[] toBytes(short value) {
        return toBytes(value,null);
	}
    public static byte[] putShort(byte[] bs, int index, short value) {
        return putShort(bs,index,value,null);
    }
    public static byte[] toBytes(short value, ByteOrder order) {
        ByteBuffer bb = ByteBuffer.allocate(2);
        if (null!=order) bb.order(order);
        bb.putShort(value);
        return bb.array();
    }
    public static byte[] putShort(byte[] bs, int index, short value, ByteOrder order) {
        System.arraycopy(toBytes(value,order),0,bs,index,2);
        return bs;
    }
	public static short toShort(byte[] bs) {
        return toShort(bs,null);
	}
    public static short getShort(byte[] bs, int index) {
        return getShort(bs,index,null);
    }
    public static short toShort(byte[] bs, ByteOrder order) {
        ByteBuffer bb = ByteBuffer.wrap(bs);
        if (null!=order) bb.order(order);
        return bb.getShort();
    }
    public static short getShort(byte[] bs, int index, ByteOrder order) {
        ByteBuffer bb = ByteBuffer.wrap(copyOfRange(bs,index,index+2));//把数组放到缓冲区中，那么缓冲区在哪？从缓冲区取的时候怎么知道是对应的数据
        if (null!=order) bb.order(order);	// 修改此缓冲区的字节顺序。
        return bb.getShort();
    }
    public static String toString(byte[] bs) {
        if (null==bs) return null;
        if (bs.length<1) return "";
        try {
            return new String(bs,"UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            new IllegalArgumentException("Error for value: "+Arrays.toString(bs)+" toString by UTF-8");
        }
        return null;
    }
    public static String getString(byte[] bs, int index, int length) {
        return toString(copyOfRange(bs,index,index+length));
    }
    public static byte[] putString(byte[] bs, int index, String value) {
        if (null!=value && value.length()>0) {
            try {
                byte[] src = value.getBytes("UTF-8");
                System.arraycopy(src,0,bs,index,src.length);
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return bs;
    }
    public static byte[] toBytes(Date value) {
        return toBytes(value,null);
    }
    public static byte[] putDate(byte[] bs, int index, Date value) {
        return putDate(bs,index,value,null);
    }
    public static byte[] toBytes(Date value, ByteOrder order) {
        if (null==value) return null;
        return toBytes(value.getTime(),order);
    }
    public static byte[] putDate(byte[] bs, int index, Date value, ByteOrder order) {
        byte[] src = toBytes(value,order);
        if (null!=src) System.arraycopy(src,0,bs,index,8);
        return bs;
    }
    public static Date toDate(byte[] bs) {
        return toDate(bs,null);
    }
    public static Date getDate(byte[] bs, int index) {
        return getDate(bs,index,null);
    }
    public static Date toDate(byte[] bs, ByteOrder order) {
        if (null==bs) return null;
        return new Date(toLong(bs,order));
    }
    public static Date getDate(byte[] bs, int index, ByteOrder order) {
        return toDate(copyOfRange(bs,index,index+8),order);
    }
	public static byte[] toBytes(float value) {
        return toBytes(value,null);
	}
    public static byte[] putFloat(byte[] bs, int index, float value) {
        return putFloat(bs,index,value,null);
    }
    public static byte[] toBytes(float value, ByteOrder order) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        if (null!=order) bb.order(order);
        bb.putFloat(value);
        return bb.array();
    }
    public static byte[] putFloat(byte[] bs, int index, float value, ByteOrder order) {
        System.arraycopy(toBytes(value,order),0,bs,index,4);
        return bs;
    }
	public static byte[] toBytes(double value) {
        return toBytes(value,null);
	}
    public static byte[] putDouble(byte[] bs, int index, double value) {
        return putDouble(bs,index,value,null);
    }
    public static byte[] toBytes(double value, ByteOrder order) {
        ByteBuffer bb = ByteBuffer.allocate(8);
        if (null!=order) bb.order(order);
        bb.putDouble(value);
        return bb.array();
    }
    public static byte[] putDouble(byte[] bs, int index, double value, ByteOrder order) {
        System.arraycopy(toBytes(value,order),0,bs,index,8);
        return bs;
    }
    public static byte[] toBytes(Object value) {
        return toBytes(value, 0, null);
    }
    public static byte[] toBytes(Object value, ByteOrder order) {
        return toBytes(value, 0, order);
    }
    public static byte[] toBytes(Object value, int size) {
        return toBytes(value, size, null);
    }
    public static byte[] putObject(byte[] bs, int index, Object value) {
        return putObject(bs,index,value,null);
    }
    public static byte[] putObject(byte[] bs, int index, Object value, ByteOrder order) {
        byte[] src = toBytes(value,order);
        if (null!=src && src.length>0) System.arraycopy(src,0,bs,index,src.length);
        return bs;
    }
    public static byte[] toBytes(Object value, int size, ByteOrder order) {
        if (null==value) return size>-1? new byte[size]: null;
        if (value instanceof byte[]) return (byte[])value;
        else if (value instanceof com.realization.framework.io.Bytes) {
            return null==order? ((com.realization.framework.io.Bytes)value).toBytes():
                ((com.realization.framework.io.Bytes)value).toBytes(order);
        }
        else if (value instanceof Integer || value instanceof Short || value instanceof Long || value instanceof Byte) {
            if (size==2 || value instanceof Short) {
                short v = ((Number)value).shortValue();
                return null==order? toBytes(v): toBytes(v, order);
            }
            else if (size==4 || value instanceof Integer) {
                int v = ((Number)value).intValue();
                return null==order? toBytes(v): toBytes(v, order);
            }
            else if (size==1 || value instanceof Byte) {
                return new byte[] {((Number)value).byteValue()};
            }
            else if (size==8 || value instanceof Long) {
                long v = ((Number)value).longValue();
                return null==order? toBytes(v): toBytes(v, order);
            }
            throw new IllegalArgumentException("Error for value: "+value+", size: "+size);
        }
        else if (value instanceof Boolean) {
            return new byte[] {Boolean.TRUE.equals(value)? (byte)1: (byte)0};
        }
        else if (value instanceof Date) {
            long v = ((Date)value).getTime();
            return null==order? toBytes(v): toBytes(v, order);
        }
        else if (value instanceof Float) {
            if (size==8) {
                double v = ((Number)value).doubleValue();
                return null==order? toBytes(v): toBytes(v, order);
            }
            else {
                float v = ((Number)value).floatValue();
                return null==order? toBytes(v): toBytes(v, order);
            }
        }
        else if (value instanceof Double) {
            if (size==4) {
                float v = ((Number)value).floatValue();
                return null==order? toBytes(v): toBytes(v, order);
            }
            else {
                double v = ((Number)value).doubleValue();
                return null==order? toBytes(v): toBytes(v, order);
            }
        }
        else if (value instanceof String) {
            try {
                return ((String)value).getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException("Error for value: "+value+", size: "+size);
            }
        }
        else if (value instanceof Byte) {
            return new byte[] {(Byte)value};
        }
        else if (value instanceof Serializable) {
            ObjectOutputStream oos = null;
            ByteArrayOutputStream bos = null;
            try {
                bos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(bos);
                oos.writeObject(value);
                return bos.toByteArray();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            finally {
                if (null!=bos) try {bos.close();} catch(Exception e) {}
                if (null!=oos) try {oos.close();} catch(Exception e) {}
            }
        }
        else return new byte[size];
    }
    public static Object toObject(byte[] bs) {
        return toObject(bs,null);
    }
    public static Object toObject(byte[] bs, ByteOrder order) {
        if (null!=bs && bs.length>0) {
            ByteArrayInputStream bis = null;
            ObjectInputStream ois = null;
            try {
                bis = new ByteArrayInputStream(bs);
                ois = new ObjectInputStream(bis);
                return ois.readObject();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            finally {
                if (null!=ois) try {ois.close();} catch(Exception e) {}
                if (null!=bis) try {bis.close();} catch(Exception e) {}
            }
            
        }
        return null;
    }
    public static Object getObject(byte[] bs, int index, int length) {
        return toObject(copyOfRange(bs,index,index+length));
    }
	public static float toFloat(byte[] bs) {
        return toFloat(bs,null);
	}
    public static float getFloat(byte[] bs, int index) {
        return getFloat(bs,index,null);
    }
    public static float toFloat(byte[] bs, ByteOrder order) {
        ByteBuffer bb = ByteBuffer.wrap(bs);
        if (null!=order) bb.order(order);
        return bb.getFloat();
    }
    public static float getFloat(byte[] bs, int index, ByteOrder order) {
        return toFloat(copyOfRange(bs,index,index+4),order);
    }
	public static double toDouble(byte[] bs) {
        return toDouble(bs,null);
	}
    public static double getDouble(byte[] bs, int index) {
        return getDouble(bs,index,null);
    }
    public static double toDouble(byte[] bs, ByteOrder order) {
        ByteBuffer bb = ByteBuffer.wrap(bs);
        if (null!=order) bb.order(order);
        return bb.getDouble();
    }
    public static double getDouble(byte[] bs, int index, ByteOrder order) {
        return toDouble(copyOfRange(bs,index,index+8),order);
    }
    public static byte[] getBytes(byte[] bs, int index) {
        return copyOfRange(bs, index, bs.length);
    }
    public static byte[] getBytes(byte[] bs, int index, int length) {
        return copyOfRange(bs, index, index+length);
    }
    public static byte[] putBytes(byte[] bs, int index, byte[] value) {
        if (null!=value && value.length>0) {
            System.arraycopy(value, 0, bs, index, value.length);
        }
        return bs;
    }
	public static byte[] concat(byte[]... arrays) {
	    if (null!=arrays && arrays.length>0) {
            byte[] bs = null;
            for (byte[] b: arrays) {
                if (null==b || b.length<1) continue;
                if (null==bs) bs = b;
                else {
                    byte[] temp = new byte[bs.length+b.length];
                    putBytes(temp, 0, bs);
                    putBytes(temp, bs.length, b);
                    bs = temp;
                }
            }
            return bs;
	    }
	    return null;
	}
	
	/**
	 * 杩炴帴瀵硅薄.
	 * @param arrays 绗竴涓厓绱犲彲浠ユ槸ByteOrder.
	 * @return
	 */
    public static byte[] concat(Object... arrays) {
        if (null!=arrays && arrays.length>0) {
            ByteOrder order = null;
            byte[] bs = null;
            for (Object b: arrays) {
                if (null==b) continue;
                if (null==bs) {
                    if (b instanceof ByteOrder) order = (ByteOrder)b;
                    else bs = null==order? toBytes(b): toBytes(b,order);
                }
                else {
                    byte[] value = null==order? toBytes(b): toBytes(b,order);
                    byte[] temp = new byte[bs.length+value.length];
                    putBytes(temp, 0, bs);
                    putBytes(temp, bs.length, value);
                    bs = temp;
                }
            }
            return bs;
        }
        return null;
    }
    
    /**
     * 这里与System.arraycopy不同的是对长度进行了处理
     * 		如果要求copy的长度超出数组的长度，则取可以copy的范围
    *@param original
    *@param from
    *@param to
    *@return
     */
    public static byte[] copyOfRange(byte[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0) throw new IllegalArgumentException(from + " > " + to);
        byte[] copy = new byte[newLength];
        System.arraycopy(original, from, copy, 0,
                         Math.min(original.length - from, newLength));
        return copy;
    }
    public static byte[] copyOf(byte[] original, int newLength) {
        byte[] copy = new byte[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

}
