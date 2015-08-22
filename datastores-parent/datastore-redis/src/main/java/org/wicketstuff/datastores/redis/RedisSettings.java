package org.wicketstuff.datastores.redis;

import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.time.Duration;

/**
 * @see org.wicketstuff.datastores.redis.IRedisSettings
 */
public class RedisSettings implements IRedisSettings {

	private Duration recordTtl = Duration.minutes(30);

	private String hostname = "127.0.0.1";

	private int port = 6379;

	public RedisSettings()
	{
	}

	@Override
	public IRedisSettings setRecordTtl(Duration ttl) {
		this.recordTtl = Args.notNull(ttl, "ttl");
		return this;
	}

	@Override
	public Duration getRecordTtl() {
		return recordTtl;
	}

	@Override
	public String getHostname() {
		return hostname;
	}

	@Override
	public IRedisSettings setHostname(String hostname) {
		this.hostname = Args.notEmpty(hostname, "hostname");
		return this;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public IRedisSettings setPort(int port) {
		this.port = port;
		return this;
	}
}
