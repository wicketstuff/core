package org.wicketstuff.datastores.memcached;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;
import org.apache.wicket.util.time.Duration;
import org.junit.Assert;
import org.junit.Test;

/**
 * A test that a Set created from the map view of the cache
 * has the same evicting capabilities as the Cache itself
 */
public class EvictingSetTest extends Assert
{
	@Test
	public void makeSureEntriesExpireEvenWhenUsingIndirectAccessToCache() {
		Cache<String, Boolean> cache = CacheBuilder.newBuilder()
				.expireAfterAccess(10, TimeUnit.MILLISECONDS)
				.build();

		// create evicting Set out of the Cache data structures
		Set<String> set = Sets.newSetFromMap(cache.asMap());

		// there are no entries initially
		assertEquals(0, set.size());

		// add an entry directly in the Set (do not use Cache APIs)
		set.add("value");

		// make sure the cache sees the new entry
		assertEquals(1, cache.size());

		// sleep a while to expire the entry
		Duration.milliseconds(50).sleep();

		// the entry should be considered expired now
//		assertNull(cache.getIfPresent("value"));
//		assertFalse(set.contains("value"));
		assertFalse(set.iterator().hasNext());
	}
}
