package com.project.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis连接
 * @author SunXiao
 *
 */
public class RedisConnection {
	
	private static final String host = "127.0.0.1";// redis地址
	private static final Integer port = 6379;// redis端口
	
	public static class RedisSource {

		private static JedisPool pool = null;
		
		public static JedisPool getPool() {
			if (null == pool) {
				JedisPoolConfig poolConfig = new JedisPoolConfig();
				poolConfig.setMaxIdle(20);//20 最大空闲连接数
				poolConfig.setMaxWaitMillis(1000);// 最大等待时间
				poolConfig.setTimeBetweenEvictionRunsMillis(180000L);// 逐出扫描的时间间隔，如果为负数不运行逐出扫描，默认-1
				poolConfig.setTestOnBorrow(true);// 获取连接时检查有效性，默认false
				pool = new JedisPool(poolConfig, host, port, 180000);
			}
			return pool;
		}
		
	}
	
	/**
	 * 关闭redis连接
	 */
	public static void shutdown() {
		JedisPool pool = RedisSource.getPool();
		pool.destroy();
	}
	
	/**
	 * 关闭redis
	 * @param jedis
	 */
	@SuppressWarnings("deprecation")
	public static void close(Jedis jedis) {
		
		if(jedis == null){
			return;
		}
		
		JedisPool pool = RedisSource.getPool();
		if(pool == null) jedis.disconnect();
		
		try {
			pool.returnResource(jedis);//返还到连接池
		} catch (Exception e) {
			//释放redis对象
			pool.returnBrokenResource(jedis); 
		}
		jedis = null;
	}
	
	@SuppressWarnings("deprecation")
	public static Jedis getInstance() {
		JedisPool pool = RedisSource.getPool();
		if(pool == null) {
			return null;
		}
		
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			
		} catch (Exception e) {
			if(jedis != null){
				//释放redis对象
				pool.returnBrokenResource(jedis); 
			}
			e.printStackTrace();
		}
		return jedis;
	}
}