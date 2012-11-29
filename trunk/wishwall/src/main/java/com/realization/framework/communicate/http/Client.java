package com.realization.framework.communicate.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.realization.framework.communicate.nio.Bytes;
import com.realization.framework.messaging.message.Message;

public class Client {
    public static Message request(String url, Message input) throws Exception {
        OutputStream os = null;
        InputStream is = null;
        try {
            URL u = new URL(url);
            HttpURLConnection hConn = (HttpURLConnection)u.openConnection();
            hConn.setDoInput(true);
            hConn.setDoOutput(true);
            hConn.setUseCaches(false);
            hConn.setRequestMethod("POST");
            hConn.setConnectTimeout(10000);
            hConn.setReadTimeout(20000);
            hConn.setRequestProperty("Content-Type", "application/octet-stream");
            hConn.connect();
            (os=hConn.getOutputStream()).write(input.toBytes());
            int resCode = hConn.getResponseCode();
            if (200==resCode) {
                byte[] bs = new byte[2];
                (is=hConn.getInputStream()).read(bs);
                short len = Bytes.toShort(bs);
                if (len<24) return null;
                bs = new byte[len-2];
                is.read(bs);
                return new Message(len,bs);
            }
            else throw new Exception("Exception on server, code: "+resCode);
        }
        finally {
            if (null!=os) {
                try {os.flush();}
                catch(Exception e) {}
                finally {
                    try {os.close();}
                    catch(Exception e) {}
                }
            }
            if (null!=is) {
                try {is.close();}
                catch(Exception e) {}
            }
        }
    }
    
}
