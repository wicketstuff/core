package com.googlecode.wicket.jquery.ui.samples.pages.plugins.datepicker;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractRangeDatePickerPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractRangeDatePickerPage()
	{
	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(RangeDatePickerPage.class, "RangeDatePicker"),
				new DemoLink(RangeDatePickerTextFieldPage.class, "RangeDatePickerTextField")
			);
	}
}
