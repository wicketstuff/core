package com.googlecode.wicket.jquery.ui.samples.pages.datepicker;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractDatePickerPage extends SamplePage
{
	private static final long serialVersionUID = 1L;
	
	public AbstractDatePickerPage()
	{
		
	}
	
	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultDatePickerPage.class, "DatePicker"),
				new DemoLink(AjaxDatePickerPage.class, "Ajax DatePicker")
			);
	}
}
