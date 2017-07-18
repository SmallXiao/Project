package com.project.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * redis集群连接
 * 
 * @author SunXiao
 *
 */
public class RedisClusterConnection {

	private static Logger LOG = LogManager
			.getLogger(RedisClusterConnection.class);

	private static JedisCluster jedisCluster;

	private static Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");

	private static Set<HostAndPort> parseHostAndPort() {
		InputStream in = null;
		try {
			Properties properties = new Properties();
			in = ClassLoader.getSystemClassLoader().getResourceAsStream(
					"redis-config.properties");
			properties.load(in);
			Set<HostAndPort> haps = new HashSet<HostAndPort>();
			for (Object key : properties.keySet()) {
				if (!key.toString().startsWith("redis.address")) {
					continue;
				}
				String val = properties.get(key).toString();
				boolean isIpPort = p.matcher(val).matches();
				if (!isIpPort) {
					throw new IllegalArgumentException("ip 或 port 不合法");
				}

				String[] ipAndPort = val.split(":");
				HostAndPort hap = new HostAndPort(ipAndPort[0],
						Integer.parseInt(ipAndPort[1]));
				haps.add(hap);
			}
			return haps;
		} catch (InvalidPropertiesFormatException e) {
			LOG.error(e);
		} catch (IOException e1) {
			LOG.error(e1);
		} catch (Exception e2) {
			LOG.error(e2);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static JedisCluster getInstance() {
		Set<HostAndPort> haps = parseHostAndPort();
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxTotal(1024);
		poolConfig.setMaxIdle(10);
		poolConfig.setMaxWaitMillis(1800000L);
		poolConfig.setTimeBetweenEvictionRunsMillis(180000L);
		poolConfig.setTestOnBorrow(true);

		jedisCluster = new JedisCluster(haps, poolConfig);
		return jedisCluster;
	}

	public static void main(String[] args) {
		Set<HostAndPort> set = parseHostAndPort();
		Iterator<HostAndPort> iterator = set.iterator();
		while (iterator.hasNext()) {
			HostAndPort hap = iterator.next();
			System.out.println(hap.getHost() + ":" + hap.getPort());
		}

	}
}
