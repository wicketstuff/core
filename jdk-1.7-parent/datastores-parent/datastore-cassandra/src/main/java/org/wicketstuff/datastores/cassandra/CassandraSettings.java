package org.wicketstuff.datastores.cassandra;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.time.Duration;

/**
 * @see org.wicketstuff.datastores.cassandra.ICassandraSettings
 */
public class CassandraSettings implements ICassandraSettings
{
	private String keyspaceName = "wicket";

	private String tableName = "pagestore";

	private Duration recordTtl = Duration.minutes(30);

	private final List<String> contactPoints = new ArrayList<String>();

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
	public List<String> getContactPoints()
	{
		return contactPoints;
	}

}
