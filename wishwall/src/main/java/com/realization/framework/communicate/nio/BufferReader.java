package com.realization.framework.communicate.nio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.jboss.netty.buffer.ChannelBuffer;

public class BufferReader implements Buffer {
    final private ChannelBuffer channelBuffer;
    public BufferReader(ChannelBuffer buffer) {
        this.channelBuffer = buffer;
    }

    @Override
    public int read(byte[] buffer) {
        if (null!=buffer && buffer.length>0) {
            if (channelBuffer.readableBytes() < buffer.length) {
                return -1;
            }
            channelBuffer.readBytes(buffer);
            return buffer.length;
        }
        return -1;
    }

    @Override
    public int read(byte[] buffer, int offset, int length) {
        if (null==buffer || offset<0 || length<1 || buffer.length<(offset+length) || channelBuffer.readableBytes()<length) {
            return -1;
        }
        channelBuffer.readBytes(buffer,offset,length);
        return buffer.length;
    }

    @Override
    public byte[] read(int length) {
        if (null==channelBuffer || length<1 || channelBuffer.readableBytes()<length) {
            return null;
        }
        byte[] bs = new byte[length];
        channelBuffer.readBytes(bs);
        return bs;
    }

    @Override
    public int getInt() {
        if (null==channelBuffer || channelBuffer.readableBytes()<4) {
            return Integer.MIN_VALUE;
        }
        return channelBuffer.readInt();
    }

    @Override
    public short getShort() {
        if (null==channelBuffer || channelBuffer.readableBytes()<2) {
            return Short.MIN_VALUE;
        }
        return channelBuffer.readShort();
    }

    @Override
    public long getLong() {
        if (null==channelBuffer || channelBuffer.readableBytes()<8) {
            return Long.MIN_VALUE;
        }
        return channelBuffer.readLong();
    }

    @Override
    public char getChar() {
        if (null==channelBuffer || channelBuffer.readableBytes()<2) {
            return Character.MIN_VALUE;
        }
        return channelBuffer.readChar();
    }

    @Override
    public byte getByte() {
        if (null==channelBuffer || channelBuffer.readableBytes()<1) {
            return Byte.MIN_VALUE;
        }
        return channelBuffer.readByte();
    }

    @Override
    public float getFloat() {
        if (null==channelBuffer || channelBuffer.readableBytes()<4) {
            return Float.NaN;
        }
        return channelBuffer.readFloat();
    }

    @Override
    public double getDouble() {
        if (null==channelBuffer || channelBuffer.readableBytes()<8) {
            return Double.NaN;
        }
        return channelBuffer.readDouble();
    }

    @Override
    public int getInt(int position) {
        if (channelBuffer.readableBytes()<position+4) return Integer.MIN_VALUE;
        return channelBuffer.getInt(position);
    }

    @Override
    public short getShort(int position) {
        if (channelBuffer.readableBytes()<position+2) return Short.MIN_VALUE;
        return channelBuffer.getShort(position);
    }

    @Override
    public byte getByte(int position) {
        if (channelBuffer.readableBytes()<position+1) return Byte.MIN_VALUE;
        return channelBuffer.getByte(position);
    }

    @Override
    public long getLong(int position) {
        if (channelBuffer.readableBytes()<position+8) return Long.MIN_VALUE;
        return channelBuffer.getLong(position);
    }
    
    @Override
    public byte[] getBytes(int position, int length) {
        if (channelBuffer.readableBytes()<position+length) return null;
        byte[] buffer = new byte[length];
        channelBuffer.getBytes(position,buffer);
        return buffer;
    }

    @Override
    public ByteOrder getByteOrder() {
        if (null==channelBuffer) return null;
        return channelBuffer.order();
    }

    @Override
    public void setByteOrder(ByteOrder order) {
        if (null!=channelBuffer) {
            throw new UnsupportedOperationException("No supported operation in netty framework!");
        }
    }

    @Override
    public ByteBuffer toByteBuffer() {
        if (null==channelBuffer) return null;
        return channelBuffer.toByteBuffer();
    }
    
}
