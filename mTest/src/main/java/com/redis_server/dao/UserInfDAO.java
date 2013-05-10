package com.redis_server.dao;

import java.util.List;

import com.redis_server.entity.UserInf;

public interface UserInfDAO {

	public UserInf findById(Integer uuid);
	
	public List findAll();
	
	public Integer getUidBySessionId(String sessionId);
	
}
