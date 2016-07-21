package org.wicketstuff.datastores.redis.demo;

import org.apache.wicket.pageStore.IDataStore;
import org.wicketstuff.datastores.common.app.BaseDataStoreApplication;
import org.wicketstuff.datastores.redis.IRedisSettings;
import org.wicketstuff.datastores.redis.RedisDataStore;
import org.wicketstuff.datastores.redis.RedisSettings;

public class RedisDataStoreApplication extends BaseDataStoreApplication {
	
	@Override
	protected IDataStore createDataStore() {
		IRedisSettings settings = new RedisSettings();
		return new RedisDataStore(settings);
	}
}
