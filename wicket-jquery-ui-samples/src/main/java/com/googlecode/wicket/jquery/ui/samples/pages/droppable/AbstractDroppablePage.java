package com.googlecode.wicket.jquery.ui.samples.pages.droppable;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractDroppablePage extends SamplePage
{
	private static final long serialVersionUID = 1L;
	
	public AbstractDroppablePage()
	{
		
	}
	
	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultDroppablePage.class, "Droppable"),
				new DemoLink(ShoppingDroppablePage.class, "<b>Demo:</b> Shopping Card")
			);
	}
}
