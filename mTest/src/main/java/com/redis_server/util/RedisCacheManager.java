package com.redis_server.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisCacheManager implements RedisCache {

//	private JedisPool pool ;
	private List<JedisPool> readPool;
	private List<JedisPool> writePool;
	private ShardedJedisPool shardedJedisPool;
	private String keyPerfix;
	private int refreshPeriod;
	
//	private Logger log = Logger.getLogger(RedisCacheManager.class); 
	
//	public JedisPool getPool() {
//		return pool;
//	}
//	
//	public void setPool(JedisPool pool) {
//		this.pool = pool;
//	}
	
	public RedisCacheManager()
	{
		super();
		System.out.println("====Redis.CacheManager====");
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

	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
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
				e.printStackTrace();
				LogUtils.log4Error("#Error : writer pool error ("+e+")");
			}
			finally{
			
			}
		}
		if (pool==null)
		{
			LogUtils.log4Error("#Warning : writer pool is null");
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
				LogUtils.log4Error("#Error : reader pool error ("+e+")");
			}
		}
		if (pool==null)
		{
			LogUtils.log4Error("#Warning : reader pool is null");
		}
		return pool;
		
	}
	
	protected String ObjectToJSON(Object obj)
	{
		if (obj==null)
		{
			LogUtils.log4Error("#Warning : can't convert to json from null Object : "+obj);
			throw new NullPointerException("can't convert to json from null Object : "+obj);
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
			if (json==null||json.length()<1) {
				return null;
			}
			bis = new ByteArrayInputStream(json.getBytes("utf-8"));
			InputStreamReader input=new InputStreamReader(bis, "utf-8");
			ObjectMapper jsonmap=new ObjectMapper();
			return jsonmap.readValue(input, type);
		} catch (UnsupportedEncodingException e) {
			LogUtils.log4Error("#Error: Json to Object Exception '"+json+"' to "+type.getName());
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
	
	@SuppressWarnings("unchecked")
	public <T> T getRedisCacheInfo(String key) {
		try {
			System.out.println("get from rediscache");
			JedisPool pool= getEnableCacheReader();
			Jedis jedis = pool.getResource();
			pool.returnResource(jedis);
			return (T)jedis.get(keyPerfix+'_'+key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String get(String key) {
		try {
			System.out.println("get from rediscache");
			JedisPool pool= getEnableCacheReader();
			Jedis jedis = pool.getResource();
			pool.returnResource(jedis);
			return jedis.get(keyPerfix+'_'+key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Object get(String key,Class beanClass) {
		try {
			System.out.println("get from rediscache");
			JedisPool pool= getEnableCacheReader();
			Jedis jedis = pool.getResource();
			
//			Pipeline p = jedis.pipelined();
////			p.set(key,value);
//			Response<String> jj= p.get(keyPerfix+'_'+key);
//			p.sync();
//			jedis.disconnect();
			pool.returnResource(jedis);
			String json=jedis.get(keyPerfix+'_'+key);
			return this.JSONToObject(json, beanClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean Exists(String key) {
		try {
			System.out.println("exists from rediscache");
			JedisPool pool= getEnableCacheWriter();
			Jedis jedis = pool.getResource();
			pool.returnResource(jedis);
			return jedis.exists(keyPerfix+'_'+key);
		} catch (Exception e) {
			LogUtils.log4Error("#Error : redis exists not excute "+e);
			throw new RuntimeException(e);
		}
	}
 
	public void hmset() {
		try {
			JedisPool pool= getEnableCacheWriter();
			Jedis jedis= pool.getResource();
			System.out.println("add to rediscache");
			try
			{
				Map<String, String> hm=new HashMap<String, String>();
				hm.put("user", "user_2");
				hm.put("mobile", "mobile_2");
				jedis.hmset(keyPerfix+"_server_1", hm);
				jedis.lpush(keyPerfix+"_server_3", "user_4");
				jedis.hset(keyPerfix+"_server_2", "user", "user_1");
				jedis.hset(keyPerfix+"_server_2", "mobile", "mobile_1");
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

	public Object hget(String key,String field,Class cls) {
		try {
			System.out.println("get from rediscache");
			JedisPool pool= getEnableCacheReader();
			Jedis jedis = pool.getResource();
			pool.returnResource(jedis);
			String json=jedis.hget(keyPerfix+'_'+key,field);
			return this.JSONToObject(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void hdel(String key,String field) {
		try {
			System.out.println("get from rediscache");
			JedisPool pool= getEnableCacheReader();
			Jedis jedis = pool.getResource();
			pool.returnResource(jedis);
			jedis.hdel(keyPerfix+'_'+key,field);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void hset(String key,String field,Object obj) {
		try {
			String json=this.ObjectToJSON(obj);
			JedisPool pool= getEnableCacheWriter();
			Jedis jedis= pool.getResource();
			System.out.println("add to rediscache");
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
	
	public int hlen(String key) {
		try {
			JedisPool pool= getEnableCacheReader();
			Jedis jedis = pool.getResource();
			Long len=null;
			try {
				len=jedis.hlen(keyPerfix+'_'+key);
			} finally
			{
				if (jedis!=null)
				{
					pool.returnResource(jedis);
				}
			}
			return len.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public List hgetAll(String key,Class cls) {
		try {
			JedisPool pool= getEnableCacheReader();
			Jedis jedis = pool.getResource();
			Map<String,String> res=null;
			try {
				res=jedis.hgetAll(key);
//				res=jedis.hgetAll(keyPerfix+'_'+key);
			} finally
			{
				if (jedis!=null)
				{
					pool.returnResource(jedis);
				}
			}
			
			Object[] objs=res.values().toArray();
			List list=new ArrayList();
//			int i=0;
			for (Object obj : objs)
			{
//				if (i==5)
//				{
//					break;
//				}
				System.out.println("====>>"+obj.toString());
				list.add(JSONToObject(obj.toString(), cls));
//				i++;
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean set(String key, Object obj) {
		try {
			String json=this.ObjectToJSON(obj);
			JedisPool pool= getEnableCacheWriter();
			Jedis jedis= pool.getResource();
			System.out.println("add to rediscache");
			try
			{
				jedis.set(keyPerfix+'_'+key, json);
			}finally
			{
				if (jedis!=null)
				{
					pool.returnResource(jedis);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean set(String key, String value) {
		try {
			System.out.println("add to rediscache");
			JedisPool pool= getEnableCacheWriter();
			Jedis jedis = pool.getResource();
			jedis.set(keyPerfix+'_'+key, (String)value);
			pool.returnResource(jedis);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean setRS(String key, Object obj) {
		try {
			System.out.println("add to rediscache");
			String json=this.ObjectToJSON(obj);
			JedisPool pool= getEnableCacheWriter();
			Jedis jedis = pool.getResource();
			jedis.setex(keyPerfix+'_'+key, refreshPeriod, json);
			pool.returnResource(jedis);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean setRS(String key, String value) {
		try {
			System.out.println("add to rediscache");
			JedisPool pool= getEnableCacheWriter();
			Jedis jedis = pool.getResource();
			jedis.setex(keyPerfix+'_'+key, refreshPeriod, (String)value);
			pool.returnResource(jedis);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void remove(String key){
		try {
			System.out.println("remove to rediscache");
			JedisPool pool= getEnableCacheWriter();
			Jedis jedis = pool.getResource();
			jedis.del(keyPerfix+'_'+key);
			pool.returnResource(jedis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void removeAll(){
		JedisPool pool= getEnableCacheWriter();
		Jedis jedis=pool.getResource();
		jedis.flushAll();
		pool.returnResource(jedis);
	}
	
	public long Ttl(String key) {
		JedisPool pool=null;
		Jedis jedis=null;
		try
		{
			pool= getEnableCacheReader();
			jedis= pool.getResource();
			long json=jedis.ttl(key);
			return json;
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
			return jedis.incr(key);
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
			return jedis.decr(key);
		}finally
		{
			if (jedis!=null)
			{
				pool.returnResource(jedis);
			}
		}
		
		
	}
	
	public boolean setShardedJedis(String key, Object obj){
		try {
			String json = this.ObjectToJSON(obj);
			System.out.println("add to rediscache");
			ShardedJedis shardedJedis = shardedJedisPool.getResource();
			shardedJedis.set(keyPerfix+'_'+key, json);
			shardedJedisPool.returnBrokenResource(shardedJedis);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public Object getShardedJedis(String key,Class cls){
		try {
			System.out.println("get from rediscache");
			ShardedJedis shardedJedis = shardedJedisPool.getResource();
			String json=shardedJedis.get(keyPerfix+'_'+key);
			shardedJedisPool.returnBrokenResource(shardedJedis);
			return this.JSONToObject(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		new RedisCacheManager().setRS("12345", "asdfg");
	}

}
