package com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.datepicker;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

/**
 * @deprecated seems to not work with lastest jquery/jquery-ui, and the js plugin seems not maintained anymore 
 */
@Deprecated
abstract class AbstractRangeDatePickerPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(RangeDatePickerPage.class, "RangeDatePicker"), // lf
				new DemoLink(RangeDatePickerTextFieldPage.class, "RangeDatePickerTextField") // lf
		);
	}
}
