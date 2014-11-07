package com.googlecode.wicket.jquery.ui.samples.pages.plugins.datepicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;

import com.googlecode.wicket.jquery.core.utils.DateUtils;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.plugins.datepicker.DateRange;
import com.googlecode.wicket.jquery.ui.plugins.datepicker.RangeDatePickerTextField;

public class RangeDatePickerTextFieldPage extends AbstractRangeDatePickerPage
{
	private static final long serialVersionUID = 1L;

	public RangeDatePickerTextFieldPage()
	{
		// Form //
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// RangeDatePicker //
		long threeDays = Duration.ONE_DAY.getMilliseconds() * 3;
		long threeDaysAgo = new Date().getTime() - threeDays;
		final IModel<DateRange> model = Model.of(DateRange.of(new Date(threeDaysAgo), new Date()));

		final RangeDatePickerTextField datepicker = new RangeDatePickerTextField("datepicker", model);
		form.add(datepicker);

		// Button //
		form.add(new Button("button") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				DateRange range = datepicker.getModelObject();

				if (range != null)
				{
					DateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z");
					df.setTimeZone(DateUtils.UTC); // important

					this.info(String.format("%s - %s", df.format(range.getStart()), df.format(range.getEnd())));
				}
				else
				{
					warn("No date supplied");
				}
			}
		});
	}
}
