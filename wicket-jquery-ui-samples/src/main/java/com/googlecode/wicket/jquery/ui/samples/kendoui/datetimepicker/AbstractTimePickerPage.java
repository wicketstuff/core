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
		return Arrays.asList(
				new DemoLink(KendoDatePickerPage.class, "DatePicker"),
				new DemoLink(LocaleDatePickerPage.class, "DatePicker: using locale"),
				new DemoLink(PatternDatePickerPage.class, "DatePicker: using pattern"),
				new DemoLink(KendoTimePickerPage.class, "TimePicker"),
				new DemoLink(LocaleTimePickerPage.class, "TimePicker: using locale"),
				new DemoLink(PatternTimePickerPage.class, "TimePicker: using pattern"),
				new DemoLink(KendoDateTimePickerPage.class, "DateTimePicker"),
				new DemoLink(LocaleDateTimePickerPage.class, "DateTimePicker: using locale"),
				new DemoLink(PatternDateTimePickerPage.class, "DateTimePicker: using pattern"),
				new DemoLink(AjaxDatePickerPage.class, "AjaxDatePicker"),
				new DemoLink(LocaleAjaxDatePickerPage.class, "AjaxDatePicker: using locale"),
				new DemoLink(PatternAjaxDatePickerPage.class, "AjaxDatePicker: using pattern"),
				new DemoLink(AjaxTimePickerPage.class, "AjaxTimePicker"),
				new DemoLink(LocaleAjaxTimePickerPage.class, "AjaxTimePicker: using locale"),
				new DemoLink(PatternAjaxTimePickerPage.class, "AjaxTimePicker: using pattern"),
				new DemoLink(AjaxDateTimePickerPage.class, "AjaxDateTimePicker"),
				new DemoLink(LocaleAjaxDateTimePickerPage.class, "AjaxDateTimePicker: using locale"),
				new DemoLink(PatternAjaxDateTimePickerPage.class, "AjaxDateTimePicker: using pattern")				
			);
	}
}
