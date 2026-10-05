/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License") +  you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wicketstuff.select2.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.util.string.Strings;

/**
 * @author lexx
 */
public final class ValueSplitter
{

	/**
	 * Split select2 value into parts. Supports <tt>JSON</tt>-style input format.
	 *
	 * @param input
	 * 		select2 raw input
	 * @return array of parts
	 */
	public static String[] split(String input)
	{
		if (input.startsWith("{") && input.endsWith("}"))
		{
			// Assume we're using JSON IDs
			List<String> result = new ArrayList<>();
			int openBracket = 0;
			Integer lastStartIdx = null;
			for (int i = 0; i < input.length(); i++)
			{
				char c = input.charAt(i);
				if (c == '{')
				{
					openBracket++;
					if (lastStartIdx == null)
					{
						lastStartIdx = i;
					}
				}
				if (c == '}')
				{
					openBracket--;
					if (openBracket == 0 && lastStartIdx != null)
					{
						String substring = input.substring(lastStartIdx, i + 1);
						result.add(substring);
						lastStartIdx = null;
					}
				}
			}
			return result.toArray(new String[result.size()]);
		}
		return Strings.split(input, ','); // select2 uses comma as value separator
	}

	private ValueSplitter()
	{
		// nop
	}
}
