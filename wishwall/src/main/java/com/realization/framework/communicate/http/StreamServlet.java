package com.realization.framework.communicate.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.realization.framework.communicate.Decoder;
import com.realization.framework.communicate.Marshaller;
import com.realization.framework.communicate.nio.MessageFact;
import com.realization.framework.communicate.nio.MessageFact.EventType;
import com.realization.framework.communicate.nio.impl.ChannelImpl;
import com.realization.framework.io.BufferReader;
import com.realization.framework.messaging.IpcMessage;
import com.realization.framework.messaging.message.Message;
import com.realization.framework.rule.process.MessageProcessor;

/**
 * Servlet implementation class StreamServlet
 */
public class StreamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final Log log =LogFactory.getLog(StreamServlet.class);
	
	private ApplicationContext ctx;
	
	private MessageProcessor messageProcessor;
	
	private Marshaller<Message> messageMarshaller;
	
	private Decoder decoder;
	
    public StreamServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
		com.realization.framework.communicate.nio.Channel channle = new ChannelImpl(out);
		try {
			invoke(channle,request.getInputStream());
		} catch (Exception e) {
			log.error(" occur error on launcher message ", e);
		}finally{
			if(null!=request.getInputStream())try{
				request.getInputStream().close();
			}catch (Exception e2) {
				log.error(" ==== occur error on close inputstream ", e2);
			}
			if(null!=out)try{
				out.close();
			}catch (Exception e2) {
				log.error(" ==== occur error on close outputstream ", e2);
			}
		}
	}


	/**
	*@param channle
	*@param recevieMsg
	 * @throws Exception 
	*/
	
	private void invoke(
			com.realization.framework.communicate.nio.Channel channle,InputStream in
			) throws Exception {
//		byte[] bs = decoder.readBuffer(new BufferReader(in));
		Message recevieMsg  = getRecevieMsg(in);
//		Message recevieMsg = (Message) decoder.decode(bs);
		IpcMessage ipcMsg = messageMarshaller.unMarshal(recevieMsg, null);
		MessageFact fact = new MessageFact(EventType.RECEIVE, channle, ipcMsg);
		messageProcessor.process(fact);
	}


	/**
	*@param request
	*@return
	*@throws IOException
	*/
//	
	private Message getRecevieMsg(InputStream in)
			throws IOException {
//		InputStream in = request.getInputStream();
		BufferedInputStream bf = new BufferedInputStream(in);
		byte[]  bs = new byte[2048];
		int i = 0 ;
		int j = 0 ;
		while((i=bf.read())!=-1){
			bs[j++]=(byte) i;
		}
		byte[]  msgByte = new byte[j];
		System.arraycopy(bs, 0, msgByte, 0, j);
		Message recevieMsg= new Message(msgByte);
		return recevieMsg;
	}


	@SuppressWarnings("unchecked")
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		this.messageProcessor = ctx.getBean(MessageProcessor.class);
		this.messageMarshaller = ctx.getBean(Marshaller.class);
		this.decoder = ctx.getBean(Decoder.class);
	}
	
	
	
}
