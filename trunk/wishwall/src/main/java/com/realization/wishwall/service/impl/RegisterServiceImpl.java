package com.realization.wishwall.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.realization.framework.common.Settings;
import com.realization.framework.common.util.security.Digest;
import com.realization.framework.messaging.IpcMessage;
import com.realization.wishwall.cmds.MessageFactory;
import com.realization.wishwall.common.constant.ResultCode;
import com.realization.wishwall.common.exception.BusinessException;
import com.realization.wishwall.dao.UserDao;
import com.realization.wishwall.models.User;
import com.realization.wishwall.service.RegisterService;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-20   下午09:09:47
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public class RegisterServiceImpl implements RegisterService{
	
	@Resource UserDao userDao;
	
	private static final Log log =LogFactory.getLog(RegisterServiceImpl.class);
	
	@Resource(name="messageFactoryImpl") MessageFactory messageFactory;

	@Override
	public IpcMessage register(IpcMessage msg) throws Exception {
		Map<String,Object> map = msg.getMsgData();
		if(!checkUser(map.get("n").toString())){
			return messageFactory.buildResponseMsg(msg, ResultCode.User_Exist);
		}
		User user = makeUser(map);
		if(!saveUser(user)){
			return messageFactory.buildResponseMsg(msg, ResultCode.Failuer);
		}
		return messageFactory.buildResponseMsg(msg, ResultCode.Success);
	}
	
	private boolean saveUser(User user){
		User result =null;
	try {
		  userDao.pesist(user);
	} catch (Exception e) {
		log.error("  ====  occur error on pesist User : " + user.getUserName(), e);
		return false;
	}
	if(result==null) return false;
	return true ;
	}

	/**
	*@param map
	*@return
	*/
	
	private User makeUser(Map<String, Object> map) {
		User user = new User();
		user.setUserName(map.get("n").toString());
		user.setPwd(Digest.saltSHA(map.get("pwd").toString(), map.get("n").toString()));
		user.setLevel("0");
		user.setLoginTimes(1);
		user.setScore(Integer.parseInt(Settings.getValueByName("score", "10")));
		user.setCreateTime(new Date());
		user.setLastTime(new Date());
		return user;
	}

	/**
	*@param string
	 * @throws BusinessException 
	*/
	
	private boolean checkUser(String userNmae) throws BusinessException {
		User user  = null ;
		try {
			 user = userDao.getUser(userNmae);
		} catch (Exception e) {
			log.error("  ====== occur error on find user by database ", e);
			throw new BusinessException();
		}
		if(user==null) return true ;
		return false ;
		
	}

}
