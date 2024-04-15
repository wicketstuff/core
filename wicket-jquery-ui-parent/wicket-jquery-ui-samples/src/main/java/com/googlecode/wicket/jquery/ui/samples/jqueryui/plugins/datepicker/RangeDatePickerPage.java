package com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.datepicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.utils.DateUtils;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.plugins.datepicker.DateRange;
import com.googlecode.wicket.jquery.ui.plugins.datepicker.RangeDatePicker;

/**
 * @deprecated seems to not work with lastest jquery/jquery-ui, and the js plugin seems not maintained anymore 
 */
@Deprecated
public class RangeDatePickerPage extends AbstractRangeDatePickerPage
{
	private static final long serialVersionUID = 1L;

	public RangeDatePickerPage()
	{
		// Options //
		final Options options = new Options();
		options.set("inline", true); //important, in order to display the datepicker
		options.set("calendars", 3);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		// RangeDatePicker //
		this.add(new RangeDatePicker("datepicker", Model.of(DateRange.today()), options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onValueChanged(IPartialPageRequestHandler handler)
			{
				DateRange range = this.getModelObject();

				DateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z");
				df.setTimeZone(DateUtils.UTC); // important

				this.info(String.format("%s - %s", df.format(range.getStart()), df.format(range.getEnd())));
				handler.add(feedback);
			}
		});
	}
}
