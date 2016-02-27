package com.googlecode.wicket.jquery.ui.samples.pages.kendo.multiselect;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractMultiSelectPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractMultiSelectPage()
	{
	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultMultiSelectPage.class, "MultiSelect"),
				new DemoLink(LazyMultiSelectPage.class, "MultiSelect: lazy load"),
				new DemoLink(AjaxMultiSelectPage.class, "AjaxMultiSelect: lazy load")
			);
	}
}
