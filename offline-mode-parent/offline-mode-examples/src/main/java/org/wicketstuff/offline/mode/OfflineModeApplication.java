package org.wicketstuff.offline.mode;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.caching.NoOpResourceCachingStrategy;

public class OfflineModeApplication extends WebApplication
{

	@Override
	public Class<? extends Page> getHomePage()
	{
		return OfflineModeHomePage.class;
	}

	@Override
	protected void init()
	{
		super.init();

		// disable cache
		getResourceSettings().setCachingStrategy(NoOpResourceCachingStrategy.INSTANCE);
		getResourceSettings().setDefaultCacheDuration(Duration.ZERO);

		// Register all resources / pages which should be available in the offline state
		List<OfflineCacheEntry> offlineCacheEntries = new ArrayList<>();
		offlineCacheEntries.add(new OfflineCacheEntry().setCacheObject(OfflineModeHomePage.class));
		offlineCacheEntries
			.add(new OfflineCacheEntry().setCacheObject(OfflineModeScript.getInstance()));
		OfflineCache.init(this, "OfflineModeExampleCache-1", offlineCacheEntries);

	}
}
