package org.wicketstuff.jquery.ui.samples.jqueryui.button;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Generics;
import org.wicketstuff.jquery.ui.JQueryIcon;
import org.wicketstuff.jquery.ui.form.button.SplitButton;
import org.wicketstuff.jquery.ui.panel.JQueryFeedbackPanel;
import org.wicketstuff.jquery.ui.widget.menu.IMenuItem;
import org.wicketstuff.jquery.ui.widget.menu.MenuItem;

public class SplitButtonPage extends AbstractButtonPage
{
	private static final long serialVersionUID = 1L;

	private final FeedbackPanel feedback;

	public SplitButtonPage()
	{
		final Form<Void> form = new Form<Void>("form") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit()
			{
				info("Form submitted");
			}
		};

		this.add(form);

		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		form.add(this.feedback.setOutputMarkupId(true));

		// TextField //
		final RequiredTextField<String> textField = new RequiredTextField<String>("text", new Model<String>());
		form.add(textField);

		// Buttons //
		form.add(new SplitButton("button", newMenuList()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError()
			{
				this.error("Validation failed!");
			}

			@Override
			protected void onSubmit(IMenuItem item)
			{
				this.info(String.format("%s: %s",item.getTitle().getObject(), textField.getModelObject()));
			}
		});
	}

	private List<IMenuItem> newMenuList()
	{
		List<IMenuItem> list = Generics.newArrayList();

		list.add(new MenuItem("View", JQueryIcon.SEARCH) {

			private static final long serialVersionUID = 1L;
			
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				info("Selected " + this.getTitle().getObject());
				target.add(feedback);
			}
		});

		list.add(new MenuItem("Edit", JQueryIcon.WRENCH) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				info("Selected " + this.getTitle().getObject());
				target.add(feedback);
			}
		});

		list.add(new MenuItem("Delete", JQueryIcon.NOTICE) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				info("Selected " + this.getTitle().getObject());
				target.add(feedback);
			}
		});

		return list;
	}
}
