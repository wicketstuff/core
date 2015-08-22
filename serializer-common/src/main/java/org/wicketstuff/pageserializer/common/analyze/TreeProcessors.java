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

/**
 * tree processor utility class
 * @author mosmann
 *
 */
public final class TreeProcessors
{
	private TreeProcessors()
	{
		// no instance
	}

	/**
	 * a list of tree processors listener as tree processor 
	 * @param processors list of processors
	 * @return wrap around the list
	 */
	public static ISerializedObjectTreeProcessor listOf(
		final ISerializedObjectTreeProcessor... processors)
	{
		return new ISerializedObjectTreeProcessor()
		{
			@Override
			public void process(ISerializedObjectTree tree)
			{
				for (ISerializedObjectTreeProcessor p : processors)
				{
					p.process(tree);
				}
			}
		};
	}
}
