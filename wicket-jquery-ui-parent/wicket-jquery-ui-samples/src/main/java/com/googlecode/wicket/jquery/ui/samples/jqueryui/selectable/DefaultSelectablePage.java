package com.googlecode.wicket.jquery.ui.samples.jqueryui.selectable;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.ui.interaction.selectable.Selectable;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class DefaultSelectablePage extends AbstractSelectablePage
{
	private static final long serialVersionUID = 1L;

	public DefaultSelectablePage()
	{
		List<String> list = Arrays.asList("item #1", "item #2", "item #3", "item #4", "item #5", "item #6");
		List<String> selected = Arrays.asList("item #2", "item #3");

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		// Selectable //
		final Selectable<String> selectable = new Selectable<String>("selectable", Model.ofList(selected), list) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSelect(AjaxRequestTarget target)
			{
				this.info("items: " + this.getDefaultModelObjectAsString());
				target.add(feedback);
			}
		};

		this.add(selectable);

		// ListView //
		selectable.add(new ListView<String>("items", list) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<String> item)
			{
				item.add(new Label("item", item.getModelObject()));
			}
		});
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(DefaultSelectablePage.class));
	}
}
