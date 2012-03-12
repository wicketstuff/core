package com.inmethod.grid.toolbar.paging;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;

/**
 * Paging navigator with default size of 11
 * 
 * @author Matej Knopp
 */
public class PagingNavigator extends AjaxPagingNavigator
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param pageable
	 * @param labelProvider
	 */
	public PagingNavigator(String id, IPageable pageable, IPagingLabelProvider labelProvider)
	{
		super(id, pageable, labelProvider);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param pageable
	 */
	public PagingNavigator(String id, IPageable pageable)
	{
		super(id, pageable);
	}

	@Override
	protected PagingNavigation newNavigation(final String id, IPageable pageable,
		IPagingLabelProvider labelProvider)
	{
		PagingNavigation navigation = super.newNavigation(id, pageable, labelProvider);
		navigation.setViewSize(11);
		return navigation;
	}
}
