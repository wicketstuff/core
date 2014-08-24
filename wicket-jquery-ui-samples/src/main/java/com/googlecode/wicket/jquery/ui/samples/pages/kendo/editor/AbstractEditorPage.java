package com.googlecode.wicket.jquery.ui.samples.pages.kendo.editor;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractEditorPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractEditorPage()
	{
	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultEditorPage.class, "Editor")
			);
	}
}
