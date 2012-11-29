package com.realization.wishwall.dao;

import com.realization.framework.dao.DAO;
import com.realization.wishwall.models.User;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-20   下午09:15:23
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface UserDao extends DAO{

	public User getUser(String userNmae ) throws Exception;
}
