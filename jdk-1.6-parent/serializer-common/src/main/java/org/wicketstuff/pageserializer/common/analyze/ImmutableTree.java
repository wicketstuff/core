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
import java.util.Collections;
import java.util.List;

/**
 * immutable implementation of a serialized tree
 * 
 * @author mosmann
 * 
 */
public final class ImmutableTree implements ISerializedObjectTree
{
	final ObjectId id;
	final Class<?> type;
	final String label;
	final int size;
	final int childSize;

	final List<ISerializedObjectTree> children;

	public ImmutableTree(ObjectId id, Class<?> type, String label, int size,
		List<? extends ISerializedObjectTree> children)
	{
		this.id = id;
		this.type = type;
		this.label = label;
		this.size = size;
		List<ISerializedObjectTree> lchildren = new ArrayList<ISerializedObjectTree>();
		lchildren.addAll(children);
		this.children = Collections.unmodifiableList(lchildren);
		int childSize = 0;
		for (ISerializedObjectTree child : children)
		{
			childSize = childSize + child.childSize() + child.size();
		}
		this.childSize = childSize;
	}
	
	@Override
	public ObjectId id()
	{
		return id;
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public int childSize()
	{
		return childSize;
	}

	@Override
	public Class<? extends Object> type()
	{
		return type;
	}

	@Override
	public String label()
	{
		return label;
	}

	@Override
	public List<? extends ISerializedObjectTree> children()
	{
		return children;
	}
	
	@Override
	public String toString() {
		return "#"+id+" "+type+"("+label+") ["+children().size()+"]";
	}
}