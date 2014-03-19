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
package org.wicketstuff.pageserializer.common.analyze.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.common.analyze.ImmutableTree;
import org.wicketstuff.pageserializer.common.analyze.ObjectId;

public class Trees
{
	private Trees()
	{
		// no instance
	}
	
	/**
	 * returns tree from text file
	 * @param clazz package and base filename
	 * @param postFix filename post
	 * @return tree
	 * @throws IOException
	 */
	public static ISerializedObjectTree fromResource(Class<?> clazz, String postFix) throws IOException {
		return TreeReader.fromResource(clazz, postFix+".tree");
	}
	
	public static void assertEqualsTree(ISerializedObjectTree expected, ISerializedObjectTree result)
	{
		Assert.assertEquals(expected+":id", expected.id(), result.id());
		Assert.assertEquals(expected + ":type", expected.type(), result.type());
		Assert.assertEquals(expected+":label", expected.label(), result.label());
		Assert.assertEquals(expected+":size", expected.size(), result.size());
		Assert.assertEquals(expected+":childSize", expected.childSize(), result.childSize());
		Assert.assertEquals(expected+":children", expected.children().size(), result.children().size());
		for (int i = 0, s = expected.children().size(); i < s; i++)
		{
			assertEqualsTree(expected.children().get(i), result.children().get(i));
		}
	}


	public static Builder build(ObjectId id, Class<?> type, int size)
	{
		return build(id, type, null, size);
	}

	public static Builder build(ObjectId id, Class<?> type, String label, int size)
	{
		return new Builder(id, type, label, size);
	}

	public static class Builder
	{
		private final ObjectId id;
		private final Class<?> type;
		private final String label;
		private final int size;
		private final Builder parent;
		private final List<Builder> children = new ArrayList<Trees.Builder>();

		private Builder(ObjectId id, Class<?> type, String label, int size)
		{
			this(null, id, type, label, size);
		}

		private Builder(Builder parent, ObjectId id, Class<?> type, String label, int size)
		{
			this.id = id;
			this.type = type;
			this.label = label;
			this.size = size;
			this.parent = parent;
		}

		public Builder withChild(ObjectId id, Class<?> type, int size)
		{
			return withChild(id, type, null, size);
		}

		public Builder withChild(ObjectId id, Class<?> type, String label, int size)
		{
			Builder child = new Builder(this, id, type, label, size);
			children.add(child);
			return child;
		}

		private Builder withCopy(Builder s)
		{
			Builder child = new Builder(this, s.id, s.type, s.label, s.size);
			for (Builder c : s.children)
			{
				child.withCopy(c);
			}
			children.add(child);
			return child;
		}

		public Builder parent()
		{
			return parent;
		}

		public Builder root()
		{
			if (parent != null)
				return parent.root();
			return this;
		}

		public ISerializedObjectTree asTree()
		{
			return root().buildTree();
		}

		private ISerializedObjectTree buildTree()
		{
			List<ISerializedObjectTree> items = new ArrayList<ISerializedObjectTree>();
			for (Builder b : children)
			{
				items.add(b.buildTree());
			}
			return new ImmutableTree(id, type, label, size, items);
		}

		public Builder multiply(int count)
		{
			for (int i = 0; i < (count - 1); i++)
			{
				parent.withCopy(this);
			}
			return this;
		}

	}
}
