package com.googlecode.wicket.jquery.ui.samples.jqueryui.datepicker;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractDatePickerPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultDatePickerPage.class, "DatePicker"), // lf
				new DemoLink(AjaxDatePickerPage.class, "Ajax DatePicker") // lf
		);
	}
}
