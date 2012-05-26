package com.googlecode.wicket.jquery.ui.samples.pages.kendo.timepicker;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.pages.kendo.AbstractKendoPage;



abstract class AbstractTimePickerPage extends AbstractKendoPage
{
	private static final long serialVersionUID = 1L;
	
	public AbstractTimePickerPage()
	{
	}
	
	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultTimePickerPage.class, "TimePicker"),
				new DemoLink(PatternTimePickerPage.class, "TimePicker: using pattern")
			);
	}
}
