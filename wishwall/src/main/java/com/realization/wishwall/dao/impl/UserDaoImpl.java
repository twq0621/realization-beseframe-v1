package com.realization.wishwall.dao.impl;

import org.springframework.stereotype.Service;

import com.realization.wishwall.dao.BaseDao;
import com.realization.wishwall.dao.UserDao;
import com.realization.wishwall.models.User;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-20   下午09:16:35
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public class UserDaoImpl extends BaseDao<User>implements UserDao{

	@Override
	public User getUser(String userNmae) throws Exception {
		String sql = " FROM User where userName = ? ";
		return find(sql, User.class, userNmae);
	}

}
