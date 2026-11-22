package org.wicketstuff.datastores.ignite;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.util.lang.Args;

public class IgniteSettings implements IIgniteSettings {
	private String tableName = "pagestore";

	private final List<String> addresses = new ArrayList<>();

	@Override
	public IIgniteSettings setTableName(String tableName) {
		this.tableName = Args.notNull(tableName, "tableName").toLowerCase(Locale.ENGLISH);
		return null;
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public List<String> getAddresses() {
		return addresses;
	}
}
