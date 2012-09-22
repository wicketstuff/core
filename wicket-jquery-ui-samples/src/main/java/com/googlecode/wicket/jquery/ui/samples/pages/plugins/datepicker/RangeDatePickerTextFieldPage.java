package com.googlecode.wicket.jquery.ui.samples.pages.plugins.datepicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;

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
		final FeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
		form.add(feedbackPanel.setOutputMarkupId(true));

		// RangeDatePicker //
		final IModel<DateRange> model = new Model<DateRange>(new DateRange(new Date(new Date().getTime() - Duration.ONE_DAY.getMilliseconds() * 3), new Date()));

		final RangeDatePickerTextField datepicker = new RangeDatePickerTextField("datepicker", model);
//		datepicker.setRequired(true);
		form.add(datepicker);

		// Button //
		form.add(new Button("button") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				DateRange dateRange = datepicker.getModelObject();

				if (dateRange != null)
				{
					DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
					info(String.format("From %s to %s", df.format(dateRange.getStart()), df.format(dateRange.getEnd())));
				}
				else
				{
					warn("No date supplied");
				}
			}
		});
	}
}
