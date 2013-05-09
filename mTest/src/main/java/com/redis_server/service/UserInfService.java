package com.redis_server.service;

import java.util.List;

import com.redis_server.entity.UserInf;

public interface UserInfService {

	public UserInf findById(Integer uuid);
	
	public List findAll();
	
	public List findAlldoSharded();
	
	public Integer getUidBySessionId(String sessionId);
}
