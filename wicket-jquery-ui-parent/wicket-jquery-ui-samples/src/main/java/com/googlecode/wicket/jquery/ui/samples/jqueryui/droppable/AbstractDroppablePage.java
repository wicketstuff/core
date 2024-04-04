package com.googlecode.wicket.jquery.ui.samples.jqueryui.droppable;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractDroppablePage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultDroppablePage.class, "Droppable"), // lf
				new DemoLink(ShoppingDroppablePage.class, "<b>Demo:</b> Shopping Card") // lf
		);
	}
}
