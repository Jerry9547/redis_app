package com.redis_server.util;

public interface RedisCache {

	public <T> T getRedisCacheInfo(String key);
	
	public String get(String key);
	
	public Object get(String key,Class beanClass);

	public boolean Exists(String key);

	public boolean set(String key, Object obj);
	
	public boolean set(String key, String value);
	
	public boolean setRS(String key, Object obj);
	
	public boolean setRS(String key, String value);
	
	public void remove(String key);
	
	public void removeAll();
	
	public Object getShardedJedis(String key,Class cls);
	
	public boolean setShardedJedis(String key, Object obj);
	
	public void hmset();
}
