package com.realization.framework.messaging.message.utils;

import static com.realization.framework.messaging.message.Message.PARAM_TYPE_ARRAY;
import static com.realization.framework.messaging.message.Message.PARAM_TYPE_OBJECT;
import static com.realization.framework.messaging.message.Message.PARAM_TYPE_COMPLEX;
import static com.realization.framework.messaging.message.Message.PARAM_TYPE_DATE;
import static com.realization.framework.messaging.message.Message.PARAM_TYPE_FLOAT;
import static com.realization.framework.messaging.message.Message.PARAM_TYPE_INTEGER;
import static com.realization.framework.messaging.message.Message.PARAM_TYPE_STRING;
import static com.realization.framework.messaging.message.Message.PARAM_TYPE_TABLE;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.realization.framework.communicate.nio.Bytes;
import com.realization.framework.messaging.message.Element;
import com.realization.framework.messaging.message.Table;
import com.realization.framework.messaging.message.array.ArrayParam;
import com.realization.framework.messaging.message.map.MapParam;
import com.realization.framework.messaging.message.table.TableCell;
import com.realization.framework.messaging.message.table.TableHead;
import com.realization.framework.messaging.message.table.TableParam;

public class MessageUtils {

    public static Object value(Object v, byte type) {
        if (null==v) return null;
        if (v instanceof Element) {
            return ((Element)v).getValue();
        }
        else if (v instanceof Map<?,?>) {
            Map<String,Object> m = new LinkedHashMap<String,Object>();
            for (Map.Entry<?,?> me: ((Map<?,?>)v).entrySet()) {
                m.put((String)me.getKey(), value(me.getValue(),type));
            }
            return m;
        }
        else if (v instanceof Object[]) {
            Object[] array = (Object[])v;
            Object[] result = new Object[array.length];
            for (int i=0; i<array.length; i++) {
                result[i] = value(array[i],type);
            }
            return result;
            
        }
        else if (v instanceof Collection<?>) {
            Collection<?> array = (Collection<?>)v;
            Object[] result = new Object[array.size()];
            int i = 0;
            for (Object o: array) {
                result[i++] = value(o,type);
            }
            return result;
        }
        else if (v instanceof Number && type==PARAM_TYPE_OBJECT) {
            return ((Number)v).byteValue()>(byte)0? Boolean.TRUE: Boolean.FALSE;
        }
        else return v;
    }
    public static Object value(byte type, byte[] buffer) {
        return value(type,buffer,null);
    }
    public static Object value(byte type, byte[] buffer, ByteOrder order) {
        Object value;
        int length = buffer.length;
        if (length==0) return null;
        switch (type) {
        case PARAM_TYPE_STRING:  value = Bytes.toString(buffer); break;
        case PARAM_TYPE_DATE: value = Bytes.toDate(buffer,order); break;
        case PARAM_TYPE_INTEGER:  
            if (length==4) value = Bytes.toInt(buffer,order);
            else if (length==8) value = Bytes.toLong(buffer,order);
            else if (length==2) value = Bytes.toShort(buffer,order);
            else if (length==1) value = buffer[0];
            else throw new IllegalArgumentException("Error for value: "+Bytes.toString(buffer)+" to INT, size: "+length);
            break;
        case PARAM_TYPE_FLOAT: 
            if(length == 4) value = Bytes.toFloat(buffer,order);
            else if(length == 8) value = Bytes.toDouble(buffer,order);
            else throw new IllegalArgumentException("Error for value: "+Bytes.toString(buffer)+" to Float, size: "+length);
            break;
        case PARAM_TYPE_TABLE: value = new TableParam(buffer,order); break;
        case PARAM_TYPE_ARRAY: value = new ArrayParam(buffer,order); break;
        case PARAM_TYPE_COMPLEX: value = new MapParam(buffer,order); break;
        case PARAM_TYPE_OBJECT: {
            if (length==1) value = buffer[0]>(byte)0? Boolean.TRUE: Boolean.FALSE;
            else {//反序列化对象.
                ObjectInputStream ois = null;
                ByteArrayInputStream bis = null;
                try {
                    ois = new ObjectInputStream(bis=new ByteArrayInputStream(buffer));
                    value = ois.readObject();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    value = null;
                }
                finally {
                    if (null!=bis) try {bis.close();} catch(Exception e) {}
                    if (null!=ois) try {ois.close();} catch(Exception e) {}
                }
            }
            break;
        }
        default: value = null; break;
        }
        return value;
    }

    public static byte type(Object... value) {
        for (Object v: value) {
            if (null==v) continue;
            //if (v instanceof Element) {
            //    return ((Element)v).getType();
            //}
            else if (v instanceof String) {
                return PARAM_TYPE_STRING;
            }
            else if (v instanceof Integer || v instanceof Long || v instanceof Short || v instanceof Byte) {
                return PARAM_TYPE_INTEGER;
            }
            else if (v instanceof Date) {
                return PARAM_TYPE_DATE;
            }
            else if (v instanceof Float || v instanceof Double) {
                return PARAM_TYPE_FLOAT;
            }
            else if (v instanceof Map<?,?>) {
                return PARAM_TYPE_COMPLEX;
            }
            else if (v instanceof Table || v instanceof TableParam || v instanceof Map<?,?>[]) {
                return PARAM_TYPE_TABLE;
            }
            else if (v instanceof ArrayParam || v instanceof Collection<?> || v.getClass().isArray()) {
                return PARAM_TYPE_ARRAY;
            }
            else if (v instanceof Boolean || v instanceof Serializable) {
                return PARAM_TYPE_OBJECT;
            }
            /*else if (v instanceof Object[]) {
                if (PARAM_TYPE_COMPLEX==type((Object[])v)) return PARAM_TYPE_COMPLEX;
                return PARAM_TYPE_ARRAY;
            }*/
            else {
                throw new IllegalArgumentException("不支持该参数类型 "+v.getClass());
            }
        }
        return 0;
    }
    
    public static Object wrap(Object value)  {
        if (null==value) return null;
        if (value instanceof Map<?,?> && !(value instanceof MapParam)) {
            @SuppressWarnings("unchecked")
            Map<String,Object> m = (Map<String,Object>)value;
            return new MapParam(m);
        }
        else if (value.getClass().isArray()) {
            Object[] source = (Object[])value;
            /*if (PARAM_TYPE_COMPLEX==type(source)) {
                ArrayList<String> keys = new ArrayList<String>();
                Object[] data = new Object[source.length*((Map<?,?>)source[0]).size()];
                for (int i=0,j; i<source.length; i++) {
                    if (source[i] instanceof Map<?,?>) {
                        Map<?,?> m = (Map<?,?>)source[i];
                        for (Map.Entry<?,?> me: m.entrySet()) {
                            j = keys.indexOf(me.getKey());
                            if (j<0) {
                                j = keys.size();
                                keys.add((String)me.getKey());
                            }
                            data[i*m.size()+j] = me.getValue();
                        }
                    }
                    else throw new IllegalArgumentException("不支持该参数类型 "+source[i]);
                }
                return toTableParam(new Table(data, keys.toArray(new String[keys.size()])));
            }
            else */return new ArrayParam(type(source),source);
        }
        else if (value instanceof Collection<?>) {
            Object[] source = ((Collection<?>)value).toArray();
            return new ArrayParam(type(source),source);
        }
        else if (value instanceof Table) {
            return toTableParam((Table)value);
        }
        else {
            return value;
        }
    }

    /**
     * 把一个Table对象转成一个TableParam对象
     * 
     * @param table 表格对象, 其行数包括表头行.
     * @param name
     * @return
     */
    private static TableParam toTableParam(Table table) {
    	int rows = table.getRows()-1;
    	int cols = table.getCols();
    	Object[][] cells = table.getCells();
    	TableHead[] tableHeads = new TableHead[cols];
        TableCell[] tableCells = new TableCell[rows*cols];
    
        int cellIndex;
    	for (int j=0; j<cols; j++) {
    		cellIndex = j/* + column_num*/;// 每一列DataCell初始的位置
    		byte paramType = table.colType(j);
    		//Object value = cells[1][j];// 每列的第一个元素的类型，默认为该列的类型
    		TableHead th=tableHeads[j] = new TableHead((byte)j, paramType, (String)cells[0][j]);
    		for (int i=1; i<=rows; i++) {
                tableCells[cellIndex] = new TableCell(th, (short)(i-1), cells[i][j]);
                cellIndex += cols;// 指向该列的下一个元素
            }
    	}
    	return new TableParam(tableHeads, tableCells);
    }
}
