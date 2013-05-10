package com.redis_server.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisCacheManager  {
	
	private List<JedisPool> readPool;
	private List<JedisPool> writePool;
	
	private String keyPerfix;
	private int refreshPeriod;
	
	public JedisCacheManager() {
		
	}
	
	public List<JedisPool> getReadPool() {
		return readPool;
	}
	
	public void setReadPool(List<JedisPool> readPool) {
		this.readPool = readPool;
	}
	
	public List<JedisPool> getWritePool() {
		return writePool;
	}
	
	public void setWritePool(List<JedisPool> writePool) {
		this.writePool = writePool;
	}
	
	public String getKeyPerfix() {
		return keyPerfix;
	}

	public void setKeyPerfix(String keyPerfix) {
		this.keyPerfix = keyPerfix;
	}

	public int getRefreshPeriod() {
		return refreshPeriod;
	}

	public void setRefreshPeriod(int refreshPeriod) {
		this.refreshPeriod = refreshPeriod;
	}

	private JedisPool getEnableCacheWriter()
	{
		JedisPool pool=null;
		
		for (JedisPool jp:writePool)
		{
			try
			{
				Jedis jedis=null;
				jedis=jp.getResource();
				//jedis.ping();
				jp.returnResource(jedis);
				pool=jp;
				break;
			}catch (Exception e) {
				LogUtils.log4Error("#Error:get Write Pool error",e);
				e.printStackTrace();
			}
			finally{
			
			}
		}
		if (pool==null)
		{
			LogUtils.log4Error("#Error:not find Object from Write Pool");
		}
		return pool;
	}
	
	private JedisPool getEnableCacheReader()
	{
		JedisPool pool=null;
		
		for (JedisPool jp:readPool)
		{
			try
			{
				Jedis jedis=null;
				jedis=jp.getResource();
				//jedis.ping();
				jp.returnResource(jedis);
				pool=jp;
				break;
			}catch (Exception e) {
				LogUtils.log4Error("#Error:get Read Pool error",e);
			}
		}
		if (pool==null)
		{
			LogUtils.log4Error("#Error:not find Object from Read Pool");
		}
		return pool;
		
	}
	
	/**
	 * 对象序列
	 * @param obj
	 * @return
	 */
	protected String ObjectToJSON(Object obj)
	{
		if (obj==null)
		{
			throw new NullPointerException("Can't convert Json from null object");
		}
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		try {
		    ObjectMapper jsonmap=new ObjectMapper();
			jsonmap.writeValue(out, obj);
			return out.toString("utf-8");
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally
		{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected  Object JSONToObject(String json,Class type)
	{
		ByteArrayInputStream bis = null;
		try {
			bis = new ByteArrayInputStream(json.getBytes("utf-8"));
			InputStreamReader input=new InputStreamReader(bis, "utf-8");
			ObjectMapper jsonmap=new ObjectMapper();
			return jsonmap.readValue(input, type);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally
		{
			try {
				if (bis!=null)bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public void set(String key, Object obj) {
		String json=ObjectToJSON(obj);
		JedisPool pool= getEnableCacheWriter();
		Jedis jedis= pool.getResource();
		try
		{
			jedis.set(keyPerfix+"_"+key, json);
		}finally
		{
			if (jedis!=null)
			{
				pool.returnResource(jedis);
			}
		}
		
	}

	public void Set(String key,  Object obj, long second) {
		String json=ObjectToJSON(obj);
		JedisPool pool= getEnableCacheWriter();
		Jedis jedis= pool.getResource();
		
		try
		{
			jedis.setex(keyPerfix+"_"+key,(int)second, json);
		}finally
		{
			if (jedis!=null)
			{
				pool.returnResource(jedis);
			}
		}
		
		
	}

	public void Set(String key, String content) {
		JedisPool pool= getEnableCacheWriter();
		Jedis jedis= pool.getResource();
		try
		{
			jedis.set(keyPerfix+"_"+key, content);
		}finally
		{
			if (jedis!=null)
			{
				pool.returnResource(jedis);
			}
		}
	}

	public void Set(String key, String content, long secode) {
		JedisPool pool= getEnableCacheWriter();
		Jedis jedis= pool.getResource();
		try
		{
			jedis.setex(keyPerfix+"_"+key, refreshPeriod,content);
		}finally
		{
			if (jedis!=null)
			{
				pool.returnResource(jedis);
			}
		}
		
		
		
	}

	public long Decr(String key) {
		JedisPool pool= getEnableCacheWriter();
		Jedis jedis= pool.getResource();
		try
		{
			return jedis.decr(keyPerfix+"_"+key);
		}finally
		{
			if (jedis!=null)
			{
				pool.returnResource(jedis);
			}
		}
		
		
	}

	public long Incr(String key) {
		JedisPool pool= getEnableCacheWriter();
		Jedis jedis= pool.getResource();
		try
		{
			return jedis.incr(keyPerfix+"_"+key);
		}finally
		{
			if (jedis!=null)
			{
				pool.returnResource(jedis);
			}
		}
		
	}

	public boolean Exist(String key) {
		
		JedisPool pool= getEnableCacheWriter();
		Jedis jedis= pool.getResource();
		try
		{
			return jedis.exists(keyPerfix+"_"+key);
		}finally
		{
			if (jedis!=null)
			{
				pool.returnResource(jedis);
			}
		}
		
	}

	public void Del(String key) {
		JedisPool pool= getEnableCacheWriter();
		Jedis jedis= pool.getResource();
		try
		{
			jedis.del(keyPerfix+"_"+key);
		}finally
		{
			if (jedis!=null)
			{
				pool.returnResource(jedis);
			}
		}
		
	}

	public void Del(String[] key) {
		JedisPool pool= getEnableCacheWriter();
		Jedis jedis= pool.getResource();
		try
		{
			for (String k:key)
			{
				jedis.del(keyPerfix+"_"+key);
			}
		}finally
		{
			if (jedis!=null)
			{
				pool.returnResource(jedis);
			}
		}
	}

	public String get(String key) {
		JedisPool pool= getEnableCacheReader();
		Jedis jedis= pool.getResource();
		try
		{
			String json=jedis.get(keyPerfix+"_"+key);
			return json;
		}finally
		{
			if (jedis!=null)
			{
				pool.returnResource(jedis);
			}
		}
		
		
	}

	@SuppressWarnings("unchecked")
	public Object get(String key, Class beanClass) {
		JedisPool pool= getEnableCacheReader();
		Jedis jedis= null;
		String json=null;
		try
		{
			jedis=pool.getResource();
			json=jedis.get(keyPerfix+"_"+key);
			
		}catch (Exception e )
		{
			e.getStackTrace();
			LogUtils.log4Error("#Error : get Object from redis error "+e);
		}
		finally
		{
			if (jedis!=null)
			{
				pool.returnResource(jedis);
			}
			
		}
		if (json==null)
		{
			return null;
		}
		return JSONToObject(json, beanClass);
	
	}
	
	@SuppressWarnings("unchecked")
	public Object hget(String key,String field,Class cls) {
		try {
			JedisPool pool= getEnableCacheReader();
			Jedis jedis = pool.getResource();
			String json=null;
			try {
				json=jedis.hget(keyPerfix+'_'+key,field);
			} finally
			{
				if (jedis!=null)
				{
					pool.returnResource(jedis);
				}
			}
			if (json!=null&&json.indexOf("{")<0) {
				LogUtils.log4Error("#Error : not get correct Value of redis"+json);
				System.out.println("=====relayserver.jecache.hget.json.error===>>"+json);
				return null;
			}
			return this.JSONToObject(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void hdel(String key,String field) {
		try {
			JedisPool pool= getEnableCacheWriter();
			Jedis jedis = pool.getResource();
			try {
				jedis.hdel(keyPerfix+'_'+key,field);
			} finally
			{
				if (jedis!=null)
				{
					pool.returnResource(jedis);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List hvals(String key,Class cls) {
		try {
			JedisPool pool= getEnableCacheReader();
			Jedis jedis = pool.getResource();
			List vals=null;
			try {
				vals=jedis.hvals(keyPerfix+'_'+key);
			} finally
			{
				if (jedis!=null)
				{
					pool.returnResource(jedis);
				}
			}
			return vals;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void hset(String key,String field,Object obj) {
		try {
			String json=this.ObjectToJSON(obj);
			JedisPool pool= getEnableCacheWriter();
			Jedis jedis= pool.getResource();
			try
			{
				jedis.hset(keyPerfix+'_'+key,field ,json);
			}finally
			{
				if (jedis!=null)
				{
					pool.returnResource(jedis);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() throws Exception{
		String key="Test_Function_Key";
		String value="test";
		for (JedisPool jp:this.writePool)
		{
			Jedis jedis=null;
			try
			{
				 jedis=jp.getResource();
				jedis.setex(key, 5, value);
				
			}finally
			{
				if (jedis==null)
				jp.returnResource(jedis);
			}
			
		}
		for (JedisPool jp:readPool)
		{
			Jedis jedis=null;
			try
			{
				jedis=jp.getResource();
				if (!value.equals(jedis.get(key)))
				{
					throw new Exception ("jedis exception");
				}
				
			}finally
			{
				if (jedis==null)
				jp.returnResource(jedis);
			}
		}
	}
	
	public long Ttl(String key) {
		JedisPool pool=null;
		Jedis jedis=null;
		try
		{
			pool= getEnableCacheReader();
			jedis= pool.getResource();
			long json=jedis.ttl(keyPerfix+"_"+key);
			return json;
		}finally
		{
			if (jedis!=null)
			{
				pool.returnResource(jedis);
			}
		}
	}
}

