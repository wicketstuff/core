package com.googlecode.wicket.jquery.ui.samples.pages.sortable;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.interaction.sortable.Sortable;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

//XXX remove wicket-jquery-ui-6.9.1-SNAPSHOT (description)
public class DefaultSortablePage extends AbstractSortablePage
{
	private static final long serialVersionUID = 1L;

	public DefaultSortablePage()
	{
		List<String> list = Arrays.asList("item #1", "item #2", "item #3", "item #4", "item #5", "item #6");

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		// Sortable //
		final Sortable<String> sortable = new Sortable<String>("sortable", list) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSort(AjaxRequestTarget target, int index, int position)
			{
				List<String> list = this.getModelObject();

				if (index < list.size())
				{
					this.info(String.format("'%s' has moved to position %d", list.get(index), position));
				}

				target.add(feedback);
			}
		};

		this.add(sortable);

		// ListView //
		final ListView<String> listView = new ListView<String>("items", list) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<String> item)
			{
				item.add(new EmptyPanel("icon").add(AttributeModifier.append("class", "ui-icon " + JQueryIcon.ARROW_2_N_S)));
				item.add(new Label("item", item.getModelObject()));
				item.add(AttributeModifier.append("class", "ui-state-default"));
			}
		};

		sortable.add(listView);
	}
}
