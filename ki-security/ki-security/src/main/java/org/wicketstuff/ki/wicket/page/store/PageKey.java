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
package org.wicketstuff.ki.wicket.page.store;

import org.apache.wicket.IClusterable;

/**
 * Unique Key to lookup a corresponding Page from the PageStore.
 * <p/>
 * continues effort started by Richard Wilkinson (<a
 * href="http://www.nabble.com/file/p18280052/TerracottaPageStore.java">to support TerraCotta</a>,
 * but the architecture has been separated into multiple classes, along with other
 * architectural/stylistic changes.
 * 
 * @author Les Hazlewood
 * @since Feb 13, 2009 8:04:14 PM
 */
public class PageKey implements IClusterable, Comparable<PageKey>
{
	private static final long serialVersionUID = 1L;

	protected static final int PRIME = 31;

	private final int pageId;
	private final int version;
	private final int ajaxVersion;

	public PageKey(int pageId, int version, int ajaxVersion)
	{
		this.pageId = pageId;
		this.version = version;
		this.ajaxVersion = ajaxVersion;
	}

	public int getPageId()
	{
		return pageId;
	}

	public int getVersion()
	{
		return version;
	}

	public int getAjaxVersion()
	{
		return ajaxVersion;
	}

	@SuppressWarnings( { "CloneDoesntDeclareCloneNotSupportedException" })
	@Override
	protected Object clone()
	{
		PageKey clone;
		try
		{
			clone = (PageKey)super.clone();
		}
		catch (CloneNotSupportedException neverHappens)
		{
			// Should never happen since this class is Cloneable and a direct subclass of Object
			throw new InternalError("Unable to clone object of type [" + getClass().getName() + "]");
		}
		return clone;
	}

	public int compareTo(PageKey pk)
	{
		if (pageId != pk.pageId)
		{
			return pageId - pk.pageId;
		}
		else if (version != pk.version)
		{
			return version - pk.version;
		}
		else if (ajaxVersion != pk.ajaxVersion)
		{
			return ajaxVersion - pk.ajaxVersion;
		}
		return 0;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this)
		{
			return true;
		}
		if (o instanceof PageKey)
		{
			PageKey pk = (PageKey)o;
			return (pageId == pk.pageId) && (version == pk.version) &&
				(ajaxVersion == pk.ajaxVersion);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = PRIME * pageId;
		result = PRIME * result + version;
		result = PRIME * result + ajaxVersion;
		return result;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(53);
		sb.append("pageId: ").append(pageId).append(", version: ").append(version).append(
			", ajaxVersion: ").append(ajaxVersion);
		return sb.toString();
	}
}
