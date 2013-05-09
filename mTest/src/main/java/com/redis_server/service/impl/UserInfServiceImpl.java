package com.redis_server.service.impl;

import java.util.List;

import com.redis_server.dao.UserInfDAO;
import com.redis_server.entity.UserInf;
import com.redis_server.service.UserInfService;
import com.redis_server.util.RedisCache;

public class UserInfServiceImpl implements UserInfService {

	private UserInfDAO userInfDao;
	private RedisCache redisCache; 
	private final String RCACHE_ALL_USER_KEY="all_user";
	
	public UserInfDAO getUserInfDao() {
		return userInfDao;
	}

	public void setUserInfDao(UserInfDAO userInfDao) {
		this.userInfDao = userInfDao;
	}

	public RedisCache getRedisCache() {
		return redisCache;
	}

	public void setRedisCache(RedisCache redisCache) {
		this.redisCache = redisCache;
	}

	@SuppressWarnings("unchecked")
	public List findAll() {
		try {
			List users=(List)redisCache.get(RCACHE_ALL_USER_KEY, List.class);
			if (users==null) {
				System.out.println("get from Hibernate Dao");
				users=userInfDao.findAll();
				if (users!=null&&users.size()>0) {
					redisCache.set(RCACHE_ALL_USER_KEY, users);
				}
			}
			return users;
		} catch (Exception e) {
			System.out.println("#Error : not excute UserInfServiceImpl.findAll ( "+e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List findAlldoSharded() {
		try {
			List users=(List)redisCache.getShardedJedis("s_"+RCACHE_ALL_USER_KEY, List.class);
			if (users==null) {
				System.out.println("get from Hibernate Dao");
				users=userInfDao.findAll();
				if (users!=null&&users.size()>0) {
					redisCache.setShardedJedis("s_"+RCACHE_ALL_USER_KEY, users);
				}
			}
			return users;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public UserInf findById(Integer uuid) {
		try {
			UserInf userInf =null;
			if (!redisCache.Exists("server_1")&&!redisCache.Exists("server_2")) {
				redisCache.hmset();
			}
			userInf=(UserInf)redisCache.get(uuid.toString(),UserInf.class);
			if (userInf==null) {
				System.out.println("get from Hibernate Dao");
				userInf = userInfDao.findById(uuid);
				if (userInf!=null) {
					redisCache.set(uuid.toString(),userInf);
				}
			}
			return userInf;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Integer getUidBySessionId(String sessionId) {
		try {
			Integer uid=null;
			String strUid=redisCache.get(sessionId);
			if (strUid!=null&&strUid.length()>0) {
				uid=Integer.parseInt(strUid);
			}
			else {
				System.out.println("get from Hibernate Dao");
				uid=userInfDao.getUidBySessionId(sessionId);
				if (uid!=null&&uid>0) {
					redisCache.set(sessionId, uid);
				}
			}
			return uid;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
