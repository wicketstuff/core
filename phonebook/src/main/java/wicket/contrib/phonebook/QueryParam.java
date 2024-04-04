/*
 * $Id: QueryParam.java 371 2005-10-13 01:21:29 -0700 (Thu, 13 Oct 2005) gwynevans $
 * $Revision: 371 $
 * $Date: 2005-10-13 01:21:29 -0700 (Thu, 13 Oct 2005) $
 *
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.phonebook;

import java.io.Serializable;

/**
 * Encapsulates the Query Paramaters to be passed to {@link ContactDao#find} method.
 * 
 * @author igor
 */
public class QueryParam implements Serializable
{
	private static final long serialVersionUID = 1L;
	private long first;
	private long count;
	private String sort;
	private boolean sortAsc;

	/**
	 * Set to return <tt>count</tt> elements, starting at the <tt>first</tt> element.
	 * 
	 * @param first
	 *            First element to return.
	 * @param count
	 *            Number of elements to return.
	 */
	public QueryParam(long first, long count)
	{
		this(first, count, null, true);
	}

	/**
	 * Set to return <tt>count</tt> sorted elements, starting at the <tt>first</tt> element.
	 * 
	 * @param first
	 *            First element to return.
	 * @param count
	 *            Number of elements to return.
	 * @param sort
	 *            Column to sort on.
	 * @param sortAsc
	 *            Sort ascending or descending.
	 */
	public QueryParam(long first, long count, String sort, boolean sortAsc)
	{
		this.first = first;
		this.count = count;
		this.sort = sort;
		this.sortAsc = sortAsc;
	}

	public void setSort(String sort)
	{
		this.sort = sort;
	}

	public void setSortAsc(boolean sortAsc)
	{
		this.sortAsc = sortAsc;
	}

	public long getCount()
	{
		return count;
	}

	public long getFirst()
	{
		return first;
	}

	public String getSort()
	{
		return sort;
	}

	public boolean isSortAsc()
	{
		return sortAsc;
	}

	public boolean hasSort()
	{
		return sort != null;
	}

	public void setFirst(long first)
	{
		this.first = first;
	}

	public void setCount(long count)
	{
		this.count = count;
	}
}
