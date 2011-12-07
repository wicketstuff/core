package org.wicketstuff.datatable_autocomplete.provider.utils;


/**
 * @author mocleiri
 * 
 */
public final class DataProviderUtils
{

	/**
	 * 
	 */
	private DataProviderUtils()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param first
	 * @param count
	 * @return the last index to get, designed for use in sublist() so its actually the ith index.
	 *         so we get elements from first to i-1
	 */


	public static long getLastIndex(long size, long first, long count)
	{

		long last = Math.min(size, first + count + 1);

		return last;
	}
}
