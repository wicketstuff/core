package com.googlecode.wicket.jquery.ui.samples.kendoui.notification;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.widget.notification.Notification;

public class DefaultNotificationPage extends AbstractNotificationPage
{
	private static final long serialVersionUID = 1L;

	public DefaultNotificationPage()
	{
		// Notification //
		final Notification notification = new Notification("notification");
		this.add(notification);

		// Form //
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// Buttons //
		form.add(new AjaxButton("info") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				notification.info(target, "Sample info message");
			}
		});

		form.add(new AjaxButton("success") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				notification.success(target, "Sample success message");
			}
		});

		form.add(new AjaxButton("warning") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				notification.warn(target, "Sample warning message");
			}
		});

		form.add(new AjaxButton("error") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				notification.error(target, "Sample error message");
			}
		});
	}
}
