package com.googlecode.wicket.jquery.ui.samples.pages.sortable;

import java.util.ArrayList;
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
import com.googlecode.wicket.jquery.ui.interaction.sortable.Sortable.HashListView;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class ConnectSortablePage extends AbstractSortablePage
{
	private static final long serialVersionUID = 1L;

	private final FeedbackPanel feedback;

	public ConnectSortablePage()
	{
		List<String> list1 = newList("item #1", "item #2", "item #3", "item #4", "item #5", "item #6");
		List<String> list2 = newList("item #7", "item #8", "item #9", "item #10", "item #11", "item #12");

		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		this.add(this.feedback.setOutputMarkupId(true));

		// Sortables //
		final Sortable<String> sortable1 = this.newSortable("sortable1", list1);
		this.add(sortable1);

		final Sortable<String> sortable2 = this.newSortable("sortable2", list2);
		this.add(sortable2);

		// Dual connect the sortables
		sortable1.connectWith(sortable2);
		sortable2.connectWith(sortable1);
	}

	private Sortable<String> newSortable(final String id, final List<String> list)
	{
		return new Sortable<String>(id, list) {

			private static final long serialVersionUID = 1L;

			@Override
			protected HashListView<String> newListView(IModel<List<String>> model)
			{
				return ConnectSortablePage.newListView("items", model);
			}

			@Override
			public void onUpdate(AjaxRequestTarget target, String item, int index)
			{
				// Will update the model object with the new order
				// Remove the call to super if you do not want your model to be updated (is read-only or you use a LDM)
				super.onUpdate(target, item, index);

				this.info(String.format("%s updated %s to position %d", id, item, index + 1));
				this.info(String.format("%s list is now: %s", id, this.getModelObject()));

				// Update is always the last event to be thrown
				target.add(feedback);
			}

			@Override
			public void onReceive(AjaxRequestTarget target, String item, int index)
			{
				// Will update the model object with the new received item
				// Remove the call to super if you do not want your model to be updated
				super.onReceive(target, item, index);

				this.info(String.format("%s received %s at position %d", id, item, index + 1));
			}

			@Override
			public void onRemove(AjaxRequestTarget target, String item)
			{
				// Will removes the item from the model object
				// Remove the call to super if you do not want your model to be updated
				super.onRemove(target, item);

				this.info(String.format("%s has removed %s", id, item));
				this.info(String.format("%s list is now: %s", id, this.getModelObject()));
			}
		};
	}

	protected static HashListView<String> newListView(String id, IModel<List<String>> model)
	{
		return new HashListView<String>(id, model) {

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

	/**
	 * Gets a new <i>modifiable</i> list
	 */
	private static List<String> newList(String... items)
	{
		List<String> list = new ArrayList<String>();

		for (String item : items)
		{
			list.add(item);
		}

		return list;
	}
}
