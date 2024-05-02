package org.wicketstuff.jquery.ui.samples.jqueryui.plugins.datepicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jquery.core.resource.StyleSheetPackageHeaderItem;
import org.wicketstuff.jquery.core.utils.DateUtils;
import org.wicketstuff.jquery.ui.form.button.Button;
import org.wicketstuff.jquery.ui.panel.JQueryFeedbackPanel;
import org.wicketstuff.jquery.ui.plugins.datepicker.DateRange;
import org.wicketstuff.jquery.ui.plugins.datepicker.RangeDatePickerTextField;

/**
 * @deprecated seems to not work with lastest jquery/jquery-ui, and the js plugin seems not maintained anymore 
 */
@Deprecated
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
		long threeDays = Duration.ofDays(3).toMillis();
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

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(RangeDatePickerTextFieldPage.class));
	}
}
