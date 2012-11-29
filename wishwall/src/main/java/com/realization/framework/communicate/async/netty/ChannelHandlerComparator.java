package com.realization.framework.communicate.async.netty;

import java.util.Comparator;

import org.jboss.netty.channel.ChannelHandler;
import org.springframework.stereotype.Service;

import com.realization.framework.core.context.Description;
import com.realization.framework.core.context.InstanceClass;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-25   下午12:08:19
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service("channelHandlerComparator")
public class ChannelHandlerComparator implements Comparator<InstanceClass<ChannelHandler>>{

//	@Override
//	public int compare(ChannelHandler o1, ChannelHandler o2) {
//		Description d1 = o1.getClass().getAnnotation(Description.class);
//		Description d2 = o2.getClass().getAnnotation(Description.class);
//		if(d1.value()>d2.value())return 1;
//		return -1;
//	}

	@Override
	public int compare(InstanceClass<ChannelHandler> o1,
			InstanceClass<ChannelHandler> o2) {
		Description d1 = o1.instanceClass.getAnnotation(Description.class);
		Description d2 = o2.instanceClass.getAnnotation(Description.class);
		if(d1.value()<d2.value())return 1;	//如果返回大于1，则o2排在o1前面
		return -1;//如果返回小于1，则o1排在o2前面
	}

}
