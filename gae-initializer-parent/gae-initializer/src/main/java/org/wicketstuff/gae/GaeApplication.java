package org.wicketstuff.gae;

import org.apache.wicket.pageStore.memory.IDataStoreEvictionStrategy;

public interface GaeApplication
{

	IDataStoreEvictionStrategy getEvictionStrategy();
}
