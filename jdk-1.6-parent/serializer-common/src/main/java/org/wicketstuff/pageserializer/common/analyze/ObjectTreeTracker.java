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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * tree builder
 * @author mosmann
 *
 */
class ObjectTreeTracker
{
	ObjectIdFactory objectIdFactory=new ObjectIdFactory();
	
	int lastPosition = 0;
	private final IObjectLabelizer labelizer;
	private Item currentItem;

	public ObjectTreeTracker(IObjectLabelizer labelizer, Object root)
	{
		this.labelizer = labelizer;
		this.currentItem = new Item(new ItemKey(objectIdFactory.idFor(root),root.getClass(), labelizer.labelFor(root)));
	}

	public void newItem(int position, Object object)
	{
		int diff = updatePositionAndCalculateDiff(position);
		currentItem = currentItem.newItem(diff,
			new ItemKey(objectIdFactory.idFor(object), object.getClass(), labelizer.labelFor(object)));
	}

	public void closeItem(int position, Object object)
	{
		int diff = updatePositionAndCalculateDiff(position);
		currentItem = currentItem.closeItem(diff,
			new ItemKey(objectIdFactory.idFor(object),object.getClass(), labelizer.labelFor(object)));
	}

	private int updatePositionAndCalculateDiff(int position)
	{
		int diff = position - lastPosition;
		lastPosition = position;
		return diff;
	}
	
	private ObjectId idFor(Object object) {
		return null;
	}

	public ISerializedObjectTree end(Object object)
	{
		return asImmutableTree(stripRootNode());
	}

	private Item stripRootNode()
	{
		return currentItem.children().iterator().next();
	}

	private static List<? extends ISerializedObjectTree> cloneList(Collection<Item> source)
	{
		List<ImmutableTree> lchildren = new ArrayList<ImmutableTree>();
		for (Item child : source)
		{
			lchildren.add(asImmutableTree(child));
		}
		return lchildren;
	}

	private static ImmutableTree asImmutableTree(Item child)
	{
		return new ImmutableTree(child.id(), child.type(), child.label(), child.size(),
			cloneList(child.children()));
	}


	static class Item
	{

		private final ItemKey key;
		Map<ItemKey, Item> children = new LinkedHashMap<ItemKey, ObjectTreeTracker.Item>();

		int size = 0;

		Item parent = null;

		public Item(ItemKey key)
		{
			this.key = key;
		}

		public ObjectId id()
		{
			return key.id();
		}

		public Item newItem(int diff, ItemKey key)
		{
			size = size + diff;
			Item item = getOrCreateItem(key);
			return item;
		}

		private Item getOrCreateItem(ItemKey key)
		{
			Item item = children.get(key);
			if (item == null)
			{
				item = new Item(this, key);
				children.put(key, item);
			}
			return item;
		}

		public Item closeItem(int diff, ItemKey key)
		{
			if (!this.key.equals(key))
				throw new IllegalArgumentException("key does not match " + this.key + "!=" + key);
			size = size + diff;
			return parent;
		}

		private Item(Item parent, ItemKey key)
		{
			this(key);
			this.parent = parent;
		}

		public int size()
		{
			return size;
		}

		public Class<? extends Object> type()
		{
			return key.type();
		}

		public String label()
		{
			return key.label();
		}

		public Collection<Item> children()
		{
			return children.values();
		}
	}

	static class ItemKey
	{
		final ObjectId id;
		final Class<?> type;
		final String label;

		public ItemKey(ObjectId id, Class<?> type, String label)
		{
			this.id=id;
			this.type = type;
			this.label = label;
		}
		
		public ObjectId id()
		{
			return id;
		}

		public Class<?> type()
		{
			return type;
		}

		public String label()
		{
			return label;
		}

		@Override
		public String toString()
		{
			return "Key(" + type + "#"+id+"," + label + ")";
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((label == null) ? 0 : label.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ItemKey other = (ItemKey)obj;
			if (id == null)
			{
				if (other.id != null)
					return false;
			}
			else if (!id.equals(other.id))
				return false;
			if (label == null)
			{
				if (other.label != null)
					return false;
			}
			else if (!label.equals(other.label))
				return false;
			if (type == null)
			{
				if (other.type != null)
					return false;
			}
			else if (!type.equals(other.type))
				return false;
			return true;
		}


	}
}
