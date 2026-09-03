package org.wicketstuff.datastores.cassandra;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.util.lang.Args;

import com.datastax.oss.driver.api.core.metadata.EndPoint;
import com.datastax.oss.driver.internal.core.metadata.DefaultEndPoint;

/**
 * @see org.wicketstuff.datastores.cassandra.ICassandraSettings
 */
public class CassandraSettings implements ICassandraSettings
{
	private String keyspaceName = "wicket";

	private String tableName = "pagestore";

	private Duration recordTtl = Duration.ofMinutes(30);

	private final List<EndPoint> contactPoints = new ArrayList<>();

	public CassandraSettings()
	{
	}

	@Override
	public ICassandraSettings setKeyspaceName(String keyspaceName)
	{
		this.keyspaceName = Args.notNull(keyspaceName, "keyspaceName").toLowerCase(Locale.ENGLISH);
		return this;
	}

	@Override
	public String getKeyspaceName()
	{
		return keyspaceName;
	}

	@Override
	public ICassandraSettings setTableName(String tableName)
	{
		this.tableName = Args.notNull(tableName, "tableName").toLowerCase(Locale.ENGLISH);
		return null;
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public ICassandraSettings setRecordTtl(Duration ttl)
	{
		this.recordTtl = Args.notNull(ttl, "ttl");
		return this;
	}

	@Override
	public Duration getRecordTtl()
	{
		return recordTtl;
	}

	@Override
	@Deprecated(since = "10.11.0", forRemoval = true)
	public List<String> getContactPoints()
	{
		return contactPoints.stream()
				.map(e -> e.resolve().toString())
				.toList();
	}

	@Override
	public ICassandraSettings addContactPoint(String point) {
		String host = null;
		int port = 9042;
		String[] parts = point.split(":");
		if (parts.length == 2) {
			host = parts[0];
			port = Integer.parseInt(parts[1]);
		} else if (parts.length == 1) {
			try {
				port = Integer.parseInt(parts[0]);
			} catch (Exception e) {
				// this should be host
				host = parts[0];
			}
		} else {
			return this;
		}
		contactPoints.add(new DefaultEndPoint(new InetSocketAddress(host, port)));
		return this;
	}

	@Override
	public List<EndPoint> getContactEndPoints() {
		return contactPoints;
	}
}
