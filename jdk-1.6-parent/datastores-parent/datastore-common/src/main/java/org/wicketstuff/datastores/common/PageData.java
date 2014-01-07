package org.wicketstuff.datastores.common;

/**
 * Keeps the information about a stored page
 */
class PageData {
	/**
	 * The page identifier
	 */
	final int pageId;

	/**
	 * The amount of bytes stored for this page
	 */
	final int size;

	/**
	 * Constructor.
	 *
	 * @param pageId  The page identifier
	 * @param size
	 */
	PageData(int pageId, int size) {
		this.pageId = pageId;
		this.size   = size;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PageData pageData = (PageData) o;

		if (pageId != pageData.pageId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return pageId;
	}
}
