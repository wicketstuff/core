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
package org.wicketstuff.html5.shape;

import java.util.Arrays;
import java.util.List;

/**
 * Creates a polygon shape
 * 
 * @author Tobias Soloschenko
 *
 */
public class Polygon extends AbstractShape
{

	private List<String> keyValuePairs;

	/**
	 * Creates a polygon shape.
	 * 
	 * @param keyValuePairs
	 *            the key value pairs (x1,y1,x2,y2,x3,y3,..) - the number must be even and there has
	 *            to be at least 3 pairs
	 */
	public Polygon(String... keyValuePairs)
	{
		if (keyValuePairs.length % 2 != 0)
		{
			throw new IllegalStateException("The key value pairs do not have a even number.");
		}
		if (keyValuePairs.length > 6)
		{
			throw new IllegalStateException("There has to be at least 6 values for a polygon.");
		}
		this.keyValuePairs = Arrays.asList(keyValuePairs);
	}

	@Override
	public String getName()
	{
		return "polygon";
	}

	@Override
	public String getValues()
	{
		StringBuilder values = new StringBuilder(50);
		int i = 0;
		for (String value : this.keyValuePairs)
		{
			values.append(value);
			values.append(" ");
			if ((i + 1) % 2 == 0 && i + 1 != this.keyValuePairs.size())
			{
				values.append(",");
			}
			i++;
		}
		return values.toString();
	}
}
