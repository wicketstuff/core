/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.plugins.datepicker;

import java.util.Arrays;
import java.util.List;

import org.wicketstuff.jquery.ui.samples.JQuerySamplePage;

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
