package com.realization.framework.messaging.message.map;

import static com.realization.framework.messaging.message.Message.PARAM_TYPE_COMPLEX;

import java.nio.ByteOrder;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.realization.framework.communicate.nio.Bytes;
import com.realization.framework.messaging.message.Element;
import com.realization.framework.messaging.message.utils.ArrayUtils;
import com.realization.framework.messaging.message.utils.MessageUtils;

public class MapParam extends Bytes implements Map<String,MapElement>,Element {

    private short length;
    private byte[] buffer;
    
	protected final Map<String,MapElement> bodys = new LinkedHashMap<String,MapElement>();

    public MapParam(Map<String,Object> value) {
        super();
        if (null!=value) {
            byte i = 0;
            for (Map.Entry<String,Object> me: value.entrySet()) {
                bodys.put(me.getKey(), new MapElement(i++,me.getKey(),me.getValue()));
            }
        }
    }
    public MapParam(byte[] bs) {
        this(bs,null);
    }

    public MapParam(byte[] bs, ByteOrder order) {
        int position = 0;
        byte type,paramIndex,nameLength;
        short valueLength;
        String name;
        while(position < bs.length) {
            byte complex = bs[position];
            type = (byte)((complex >> 5) & 0x07);
            paramIndex = (byte)(complex & 0x1f);
            nameLength = bs[1+position];
            valueLength = getShort(bs,position+2,order);
            name = getString(bs, position+4,nameLength);
            byte[] buffer = getBytes(bs,position+4+nameLength,valueLength);//new byte[valueLength];
            bodys.put(name, new MapElement(type, paramIndex, nameLength, valueLength, name, MessageUtils.value(type,buffer,order)));
            position += (4+nameLength+valueLength);
        }

    }
    @Override
    public short getLength(){
        if (length==(byte)0) {
            if (null==buffer) buffer = toBytes();
            if (null!=buffer) length = (short)buffer.length;
        }
        return length;
    }
    @Override
    public byte getType() {
        return PARAM_TYPE_COMPLEX;
    }
    @Override
    public Object getValue() {
        Map<String,Object> m = new LinkedHashMap<String,Object>();
        for (Map.Entry<String,MapElement> me: bodys.entrySet()) {
            m.put(me.getKey(), me.getValue());
        }
        return m;
    }

    @Override
    public byte[] toBytes() {
        return concat(bodys.values().toArray());
    }
    @Override
    public byte[] toBytes(ByteOrder order) {
        if (null!=order) buffer = concat(ArrayUtils.addFirst(bodys.values().toArray(),order));
        if (null==buffer) buffer = concat(bodys.values().toArray());
        return buffer;
    }

    @Override
    public int size() {
        return bodys.size();
    }


    @Override
    public boolean isEmpty() {
        return bodys.isEmpty();
    }


    @Override
    public boolean containsKey(Object key) {
        return bodys.containsKey(key);
    }


    @Override
    public boolean containsValue(Object value) {
        return bodys.containsValue(value);
    }


    @Override
    public MapElement get(Object key) {
        return bodys.get(key);
    }


    @Override
    public MapElement put(String key, MapElement value) {
        return bodys.put(key, value);
    }


    @Override
    public MapElement remove(Object key) {
        return bodys.remove(key);
    }


    @Override
    public void putAll(Map<? extends String, ? extends MapElement> m) {
        bodys.putAll(m);
    }


    @Override
    public void clear() {
        bodys.clear();
    }


    @Override
    public Set<String> keySet() {
        return bodys.keySet();
    }


    @Override
    public Collection<MapElement> values() {
        return bodys.values();
    }


    @Override
    public Set<java.util.Map.Entry<String, MapElement>> entrySet() {
        return bodys.entrySet();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + bodys.hashCode();
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MapParam other = (MapParam) obj;
        return bodys.equals(other.bodys);
    }
    @Override
    public String toString() {
        return "MapParam [bodys=" + bodys + "]";
    }
	
}
