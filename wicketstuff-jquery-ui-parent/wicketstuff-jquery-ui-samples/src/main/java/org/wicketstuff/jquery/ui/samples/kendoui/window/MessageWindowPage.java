package org.wicketstuff.jquery.ui.samples.kendoui.window;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.kendo.ui.KendoIcon;
import org.wicketstuff.kendo.ui.form.button.AjaxButton;
import org.wicketstuff.kendo.ui.panel.KendoFeedbackPanel;
import org.wicketstuff.kendo.ui.widget.window.MessageWindow;
import org.wicketstuff.kendo.ui.widget.window.Window;
import org.wicketstuff.kendo.ui.widget.window.WindowButton;

public class MessageWindowPage extends AbstractWindowPage
{
	private static final long serialVersionUID = 1L;

	private static final List<WindowButton> BUTTONS = Arrays.asList( // lf
			new WindowButton(Window.OK, Window.LBL_OK), // lf
			new WindowButton(Window.CANCEL, Model.of("I don't care")));

	public MessageWindowPage()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Window //
		final MessageWindow window = new MessageWindow("window", "Information", "This is a sample message", BUTTONS, KendoIcon.QUESTION) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, WindowButton button)
			{
				if (button.match(OK))
				{
					this.info("Ok has been clicked");
				}
				else
				{
					this.warn("Cancel has been clicked");
				}

				target.add(feedback);
			}
		};

		this.add(window);

		// Button //
		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected String getIcon()
			{
				return KendoIcon.WINDOW_MAXIMIZE;
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				window.open(target);
			}
		});
	}
}
