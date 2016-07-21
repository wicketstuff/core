/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
var wicketCacheName = '$(cacheName)';

// register cache
self.addEventListener('install', function(event) {
	var offlineCacheEntries = $(offlineCacheEntries);
	console.info("Received the following settings to be cached: "+JSON.stringify(offlineCacheEntries)+", entries with CORS are going to be translated to a Request object");
	
	// Handle CORS settings
	for(var i = 0; i < offlineCacheEntries.length; i++) {
		if(offlineCacheEntries[i].cors) {
			offlineCacheEntries[i] = new Request(offlineCacheEntries[i].url, { mode : offlineCacheEntries[i].cors } );
		}else{
			offlineCacheEntries[i] = offlineCacheEntries[i].url;
		}
	}
	
	event.waitUntil(caches.open(wicketCacheName).then(function(cache) {
		return cache.addAll(offlineCacheEntries);
	}));
});

// fetch entries in the current cache and if not in the cache from server
self.addEventListener('fetch', function(event) {
	event.respondWith(
		caches.open(wicketCacheName).then(function(cache) {
			return cache.match(event.request).then(function(response) {
				// Handles requests
				var responseValue = response || fetch(event.request);
				console.info('Found response in cache:', responseValue);
				return responseValue;
			}).catch(function(error) {
				// Handles exceptions that arise from match() or fetch().
				console.error('Error in fetch handler:', error);
			});
		})
	);
});

// Removing old caches
self.addEventListener('activate', function(event) {
	return event.waitUntil(caches.keys().then(function(keys) {
		return Promise.all(keys.map(function(k) {
			if (k != wicketCacheName) {
				return caches.delete(k);
			} else {
				return Promise.resolve();
			}
		}));
	}));
});
