package com.googlecode.wicket.jquery.ui.samples.pages.selectable;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.ui.interaction.Selectable;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.SamplePage;

public class SelectablePage extends SamplePage
{
	private static final long serialVersionUID = 1L;
	
	public SelectablePage()
	{
		List<String> list = Arrays.asList("item #1", "item #2", "item #3", "item #4", "item #5", "item #6");
		
		// FeedbackPanel //
		final FeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
		this.add(feedbackPanel.setOutputMarkupId(true));

		// Selectable //
		Selectable<String> selectable = new Selectable<String>("selectable", list) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSelect(AjaxRequestTarget target, List<String> items)
			{
				this.info("items: " + items.toString());
				target.add(feedbackPanel);
			}
		};
		
		this.add(selectable);
		
		selectable.add(new ListView<String>("items", list) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<String> item)
			{
//				item.setOutputMarkupId(true);
				item.add(new Label("item", item.getModelObject()));
			}
		});
	}
}
