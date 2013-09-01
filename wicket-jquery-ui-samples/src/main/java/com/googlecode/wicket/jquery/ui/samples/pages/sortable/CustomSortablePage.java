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
import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.interaction.sortable.Sortable;
import com.googlecode.wicket.jquery.ui.interaction.sortable.Sortable.HashListView;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class CustomSortablePage extends AbstractSortablePage
{
	private static final long serialVersionUID = 1L;

	public CustomSortablePage()
	{
		final List<Item> list = newList("item #1", "item #2", "item #3", "item #4", "item #5", "item #6");

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		// Sortable //
		final Sortable<Item> sortable = new Sortable<Item>("sortable", list) {

			private static final long serialVersionUID = 1L;

			@Override
			protected HashListView<Item> newListView(IModel<List<Item>> model)
			{
				return CustomSortablePage.newListView("items", model);
			}

			@Override
			public void onUpdate(AjaxRequestTarget target, Item item, int index)
			{
				// Will update the model object with the new order
				// Remove the call to super if you do not want your model to be updated (or you use a LDM)
				super.onUpdate(target, item, index);

				if (item != null)
				{
					this.info(String.format("'%s' has moved to position %d", item, index + 1));
					this.info("The list order is now: " + this.getModelObject());
				}

				target.add(feedback);
			}
		};

		this.add(sortable);
	}

	protected static HashListView<Item> newListView(String id, IModel<List<Item>> model)
	{
		return new HashListView<Item>(id, model) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Item> item)
			{
				item.add(new EmptyPanel("icon").add(AttributeModifier.append("class", "ui-icon " + JQueryIcon.ARROW_2_N_S)));
				item.add(new Label("item", item.getDefaultModelObjectAsString()));
				item.add(AttributeModifier.append("class", "ui-state-default"));
			}
		};
	}


	/**
	 * Gets a new <i>modifiable</i> list
	 */
	private static List<Item> newList(String... names)
	{
		List<Item> list = new ArrayList<Item>();

		for (String name : names)
		{
			list.add(new Item(name));
		}

		return list;
	}

	/**
	 * custom bean
	 */
	private static class Item implements IClusterable
	{
		private static final long serialVersionUID = 1L;

		final String name;

		public Item(String name)
		{
			this.name = name;
		}

		@Override
		public int hashCode()
		{
			return this.name.hashCode(); //String#hashCode() is deterministic
		}

		@Override
		public String toString()
		{
			return this.name;
		}
	}
}
