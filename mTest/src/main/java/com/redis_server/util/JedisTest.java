package com.redis_server.util;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class JedisTest {

	    private Jedis jedis;

	    private JedisPool jedisPool;

	    private ShardedJedis shardedJedis;

	    private ShardedJedisPool shardedJedisPool;

	    public JedisTest()
	    {
	        initialPool();
	        initialShardedPool();
	        shardedJedis = shardedJedisPool.getResource();
	        jedis = jedisPool.getResource();
	    }

	    private void initialPool()
	    {
	        JedisPoolConfig config = new JedisPoolConfig();
	        config.setMaxActive(20);
	        config.setMaxIdle(5);
	        config.setMaxWait(1000l);
	        config.setTestOnBorrow(false);

	        jedisPool = new JedisPool(config, "192.168.0.199", 6379);
	    }

	    private void initialShardedPool()
	    {
	        JedisPoolConfig config = new JedisPoolConfig();
	        config.setMaxActive(20);
	        config.setMaxIdle(5);
	        config.setMaxWait(1000l);
	        config.setTestOnBorrow(false);
	        // slave
	        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
	        shards.add(new JedisShardInfo("192.168.0.199", 6379, "master"));

	        shardedJedisPool = new ShardedJedisPool(config, shards);
	    }

	    public void show()
	    {
	        testKey();
	        testString();
	        testList();
	        testSet();
	        testSortedSet();
	        testHash();
	        shardedJedisPool.returnResource(shardedJedis);
	    }

	    private void testKey()
	    {
	        System.out.println("=============key==========================");
	        System.out.println(jedis.flushDB());
	        System.out.println(jedis.echo("foo"));
	        System.out.println(shardedJedis.exists("foo"));
	        shardedJedis.set("key", "values");
	        System.out.println(shardedJedis.exists("key"));
	    }

	    private void testString()
	    {
	        System.out.println("=============String==========================");
	        System.out.println(jedis.flushDB());
	        shardedJedis.set("foo", "bar");
	        System.out.println(shardedJedis.get("foo"));
	        shardedJedis.setnx("foo", "foo not exits");
	        System.out.println(shardedJedis.get("foo"));
	        shardedJedis.set("foo", "foo update");
	        System.out.println(shardedJedis.get("foo"));
	        shardedJedis.append("foo", " hello, world");
	        System.out.println(shardedJedis.get("foo"));
	        shardedJedis.setex("foo", 2, "foo not exits");
	        System.out.println(shardedJedis.get("foo"));
	        try
	        {
	            Thread.sleep(3000);
	        }
	        catch (InterruptedException e)
	        {
	        }
	        System.out.println(shardedJedis.get("foo"));
	        shardedJedis.set("foo", "foo update");
	        System.out.println(shardedJedis.getSet("foo", "foo modify"));
	        System.out.println(shardedJedis.getrange("foo", 1, 3));
	        System.out.println(jedis.mset("mset1", "mvalue1", "mset2", "mvalue2", "mset3", "mvalue3", "mset4", "mvalue4"));
	        System.out.println(jedis.mget("mset1", "mset2", "mset3", "mset4"));
	        System.out.println(jedis.del(new String[]
	        {
	            "foo", "foo1", "foo3"
	        }));
	    }

	    private void testList()
	    {
	        System.out.println("=============list==========================");
	        System.out.println(jedis.flushDB());
	        shardedJedis.lpush("lists", "vector");
	        shardedJedis.lpush("lists", "ArrayList");
	        shardedJedis.lpush("lists", "LinkedList");
	        System.out.println(shardedJedis.llen("lists"));
	        System.out.println(shardedJedis.sort("lists"));
	        System.out.println(shardedJedis.lrange("lists", 0, 3));
	        shardedJedis.lset("lists", 0, "hello list!");
	        System.out.println(shardedJedis.lindex("lists", 1));
	        System.out.println(shardedJedis.lrem("lists", 1, "vector"));
	        System.out.println(shardedJedis.ltrim("lists", 0, 1));
	        System.out.println(shardedJedis.lpop("lists"));
	        System.out.println(shardedJedis.lrange("lists", 0, -1));

	    }

	    private void testSet()
	    {
	        System.out.println("=============set==========================");
	        System.out.println(jedis.flushDB());
	        shardedJedis.sadd("sets", "HashSet");
	        shardedJedis.sadd("sets", "SortedSet");
	        shardedJedis.sadd("sets", "TreeSet");
	        System.out.println(shardedJedis.sismember("sets", "TreeSet"));;
	        System.out.println(shardedJedis.smembers("sets"));
	        System.out.println(shardedJedis.srem("sets", "SortedSet"));
	        System.out.println(shardedJedis.spop("sets"));
	        System.out.println(shardedJedis.smembers("sets"));
	        //
	        shardedJedis.sadd("sets1", "HashSet1");
	        shardedJedis.sadd("sets1", "SortedSet1");
	        shardedJedis.sadd("sets1", "TreeSet");
	        shardedJedis.sadd("sets2", "HashSet2");
	        shardedJedis.sadd("sets2", "SortedSet1");
	        shardedJedis.sadd("sets2", "TreeSet1");
	        System.out.println(jedis.sinter("sets1", "sets2"));
	        System.out.println(jedis.sunion("sets1", "sets2"));
	        System.out.println(jedis.sdiff("sets1", "sets2"));
	    }

	    private void testSortedSet()
	    {
	        System.out.println("=============zset==========================");
	        System.out.println(jedis.flushDB());
	        shardedJedis.zadd("zset", 10.1, "hello");
	        shardedJedis.zadd("zset", 10.0, ":");
	        shardedJedis.zadd("zset", 9.0, "zset");
	        shardedJedis.zadd("zset", 11.0, "zset!");
	        System.out.println(shardedJedis.zcard("zset"));
	        System.out.println(shardedJedis.zscore("zset", "zset"));
	        System.out.println(shardedJedis.zrange("zset", 0, -1));
	        System.out.println(shardedJedis.zrem("zset", "zset!"));
	        System.out.println(shardedJedis.zcount("zset", 9.5, 10.5));
	        System.out.println(shardedJedis.zrange("zset", 0, -1));
	    }

	    private void testHash()
	    {
	        System.out.println("=============hash==========================");
	        System.out.println(jedis.flushDB());
	        shardedJedis.hset("hashs", "entryKey", "entryValue");
	        System.out.println(shardedJedis.hexists("hashs", "entryKey"));
	        System.out.println(shardedJedis.hget("hashs", "entryKey"));
	        System.out.println(shardedJedis.hmget("hashs", "entryKey", "entryKey1"));
	        System.out.println(shardedJedis.hdel("hashs", "entryKey"));
	        System.out.println(shardedJedis.hincrBy("hashs", "entryKey", 123l));
	        System.out.println(shardedJedis.hkeys("hashs"));
	        System.out.println(shardedJedis.hvals("hashs"));
	    }

	    /**
	     * @param args
	     */
	    public static void main(String[] args)
	    {
	        new JedisTest().show();
	    }
}
