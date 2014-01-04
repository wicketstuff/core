package org.wicketstuff.datastores.common;

/**
*
*/
class PageData
{
	final int pageId;
	final int size;

	PageData(int pageId, int size)
	{
		this.pageId = pageId;
		this.size   = size;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PageData pageData = (PageData) o;

		if (pageId != pageData.pageId) return false;
		if (size != pageData.size) return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = pageId;
		result = 31 * result + size;
		return result;
	}
}
