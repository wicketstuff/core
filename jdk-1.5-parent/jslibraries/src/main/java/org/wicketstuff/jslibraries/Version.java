/**
 * Copyright (C) 2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
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
package org.wicketstuff.jslibraries;

import java.io.Serializable;
import java.util.Arrays;

import org.wicketstuff.jslibraries.util.Assert;

public class Version implements Comparable<Version>, Serializable
{
	private static final long serialVersionUID = 1L;

	private final int[] mNumbers;

	public Version(int... numbers)
	{
		mNumbers = numbers;
	}

	public int[] getNumbers()
	{
		return mNumbers;
	}

	public int compareTo(Version other)
	{
		Assert.parameterNotNull(other, "other");

		for (int i = 0; i < mNumbers.length; i++)
		{
			if (mNumbers[i] != other.mNumbers[i])
			{
				return mNumbers[i] - other.mNumbers[i];
			}
			int next = i + 1;
			if (next < mNumbers.length && next >= other.mNumbers.length)
			{
				// i have another level and the other doesn't, so I come after
				return +1;
			}
		}
		return 0;
	}

	/**
	 * A version matches another if it has equal or more numbers and all its numbers equal those of
	 * the other.
	 * 
	 * @param other
	 */
	public boolean matches(Version other)
	{
		Assert.parameterNotNull(other, "other");

		if (other.mNumbers.length > mNumbers.length)
		{
			return false;
		}

		for (int i = 0; i < other.mNumbers.length; i++)
		{
			if (mNumbers[i] != other.mNumbers[i])
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString()
	{
		return 'v' + renderVersionNumbers();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(mNumbers);
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
		Version other = (Version)obj;
		if (!Arrays.equals(mNumbers, other.mNumbers))
			return false;
		return true;
	}

	protected String renderVersionNumbers()
	{
		StringBuffer sb = new StringBuffer();
		for (int num : mNumbers)
		{
			if (sb.length() > 0)
			{
				sb.append('.');
			}
			sb.append(num);
		}
		return sb.toString();
	}
}
