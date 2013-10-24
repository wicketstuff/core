/**
 * Copyright (C)
 * 	2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 * 	2012 Michael Mosmann <michael@mosmann.de>
 *
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
package org.wicketstuff.pageserializer.common.analyze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * creates an object id for objects with the guarantee that each distinct object has an distinct id
 * System.identityHashCode does not provide such a guarantee
 * @author mosmann
 *
 */
public class ObjectIdFactory
{
	Map<Integer, List<ObjectIdEntry>> map = new HashMap<Integer, List<ObjectIdEntry>>();
	int counter=0;
	
	/**
	 * creates or get ObjectId for Object
	 * @param value
	 * @return
	 */
	public synchronized ObjectId idFor(Object value)
	{
		int idHash = System.identityHashCode(value);
		List<ObjectIdEntry> list = getObjectIdList(idHash);
		for (ObjectIdEntry e : list)
		{
			if (e.object == value)
			{
				return e.id;
			}
		}
		ObjectId id = new ObjectId(counter++);
		list.add(new ObjectIdEntry(value, id));
		return id;
	}

	private List<ObjectIdEntry> getObjectIdList(int idHash)
	{
		List<ObjectIdEntry> list = map.get(idHash);
		if (list == null)
		{
			list = new ArrayList<ObjectIdFactory.ObjectIdEntry>();
			map.put(idHash, list);
		}
		return list;
	}

	static class ObjectIdEntry
	{
		final Object object;
		final ObjectId id;

		public ObjectIdEntry(Object object, ObjectId id)
		{
			this.object = object;
			this.id = id;
		}
	}
}