/**
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

package org.wicketstuff.calendarviews.exampleapp;

import java.util.Collection;

import org.wicketstuff.calendarviews.model.IEvent;

/**
 * For testing - allows you to use a set of random test data that won't change
 * with every page refresh or change.  Can be cleared upon request.
 * 
 * @author Jeremy Thomerson
 */
public class PersistentRandomTestEventProvider extends RandomTestEventProvider {

	private static final long serialVersionUID = 1L;
	private static Collection<? extends IEvent> STATIC_COLLECTION = null;

	@Override
	protected Collection<? extends IEvent> load() {
		if (STATIC_COLLECTION == null) {
			STATIC_COLLECTION = super.load();
		}
		return STATIC_COLLECTION;
	}
	
	public static final void clearEventsForFreshReload() {
		STATIC_COLLECTION = null;
	}
}
