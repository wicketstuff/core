package org.wicketstuff.datastores.hazelcast.demo;

import org.apache.wicket.pageStore.IPageStore;
import org.wicketstuff.datastores.common.app.BaseDataStoreApplication;
import org.wicketstuff.datastores.hazelcast.HazelcastDataStore;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastDataStoreApplication extends BaseDataStoreApplication {
	
	@Override
	protected IPageStore createDataStore() {
		HazelcastInstance instance = Hazelcast.newHazelcastInstance();
		return new HazelcastDataStore(getName(), instance);
	}
}
