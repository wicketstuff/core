package com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker;

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
				new DemoLink(KendoDatePickerPage.class, "DatePicker"),
				new DemoLink(LocaleDatePickerPage.class, "DatePicker: using locale"),
				new DemoLink(PatternDatePickerPage.class, "DatePicker: using pattern"),
				new DemoLink(DefaultTimePickerPage.class, "TimePicker"),
				new DemoLink(LocaleTimePickerPage.class, "TimePicker: using locale"),
				new DemoLink(PatternTimePickerPage.class, "TimePicker: using pattern"),
				new DemoLink(DateTimePickerPage.class, "DateTimePicker"),
				new DemoLink(LocaleDateTimePickerPage.class, "DateTimePicker: using locale"),
				new DemoLink(PatternDateTimePickerPage.class, "DateTimePicker: using pattern")
			);
	}
}
