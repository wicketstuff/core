package com.googlecode.wicket.jquery.ui.samples.pages.sortable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;

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
			protected HashListView<String> newListView(IModel<List<String>> model)
			{
				return new HashListView<String>("items", model) {

					private static final long serialVersionUID = 1L;

					@Override
					protected void populateItem(ListItem<String> item)
					{
						item.add(new EmptyPanel("icon").add(AttributeModifier.append("class", "ui-icon " + JQueryIcon.ARROW_2_N_S)));
						item.add(new Label("item", item.getModelObject()));
						item.add(AttributeModifier.append("class", "ui-state-default"));
					}
				};
			}

			@Override
			public void onSort(AjaxRequestTarget target, String item, int index)
			{
				this.move(item, index);
				this.info(String.format("'%s' has moved to position %d", item, index + 1));
				this.info("The list order is now: " + this.getModelObject());

				target.add(feedback);
			}

			/**
			 * Helper method to move the item at its new position in the list
			 *
			 * @param item the item
			 * @param index the position to move to
			 */
			private void move(String item, int index)
			{
				if (item != null)
				{
					List<String> list = this.getModelObject();
					Collections.rotate(list.subList(list.indexOf(item), index + 1), -1);
				}
			}
		};

		this.add(sortable);
	}
}
