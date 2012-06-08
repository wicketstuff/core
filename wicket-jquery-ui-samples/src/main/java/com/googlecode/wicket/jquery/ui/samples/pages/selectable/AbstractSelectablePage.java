package com.googlecode.wicket.jquery.ui.samples.pages.selectable;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractSelectablePage extends SamplePage
{
	private static final long serialVersionUID = 1L;
	
	public AbstractSelectablePage()
	{
		
	}
	
	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultSelectablePage.class, "Selectable"),
				new DemoLink(DraggableSelectablePage.class, "Selectable, with draggable (on list items)"),
				new DemoLink(TableDraggableSelectablePage.class, "Selectable, with customized draggable (on table rows)")
			);
	}
}
