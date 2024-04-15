package com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.KendoSamplePage;

abstract class AbstractTimePickerPage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(KendoDatePickerPage.class, "DatePicker"), // lf
				new DemoLink(LocaleDatePickerPage.class, "DatePicker: using locale"), // lf
				new DemoLink(PatternDatePickerPage.class, "DatePicker: using pattern"), // lf
				new DemoLink(KendoTimePickerPage.class, "TimePicker"), // lf
				new DemoLink(LocaleTimePickerPage.class, "TimePicker: using locale"), // lf
				new DemoLink(PatternTimePickerPage.class, "TimePicker: using pattern"), // lf
				new DemoLink(KendoDateTimePickerPage.class, "DateTimePicker"), // lf
				new DemoLink(LocaleDateTimePickerPage.class, "DateTimePicker: using locale"), // lf
				new DemoLink(PatternDateTimePickerPage.class, "DateTimePicker: using pattern"), // lf
				new DemoLink(AjaxDatePickerPage.class, "AjaxDatePicker"), // lf
				new DemoLink(LocaleAjaxDatePickerPage.class, "AjaxDatePicker: using locale"), // lf
				new DemoLink(PatternAjaxDatePickerPage.class, "AjaxDatePicker: using pattern"), // lf
				new DemoLink(AjaxTimePickerPage.class, "AjaxTimePicker"), // lf
				new DemoLink(LocaleAjaxTimePickerPage.class, "AjaxTimePicker: using locale"), // lf
				new DemoLink(PatternAjaxTimePickerPage.class, "AjaxTimePicker: using pattern"), // lf
				new DemoLink(AjaxDateTimePickerPage.class, "AjaxDateTimePicker"), // lf
				new DemoLink(LocaleAjaxDateTimePickerPage.class, "AjaxDateTimePicker: using locale"), // lf
				new DemoLink(PatternAjaxDateTimePickerPage.class, "AjaxDateTimePicker: using pattern") // lf
			);
	}
}
