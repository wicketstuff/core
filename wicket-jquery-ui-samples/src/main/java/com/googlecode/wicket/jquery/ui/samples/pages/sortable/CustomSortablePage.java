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
import org.apache.wicket.model.LoadableDetachableModel;

import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.interaction.sortable.Sortable;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

//XXX remove wicket-jquery-ui-6.9.1-SNAPSHOT (description)
public class CustomSortablePage extends AbstractSortablePage
{
	private static final long serialVersionUID = 1L;

	public CustomSortablePage()
	{
		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		// Sortable //
		final Sortable<Item> sortable = new Sortable<Item>("sortable", newSortableModel()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected SortableListView<Item> newListView(IModel<List<Item>> model)
			{
				return new SortableListView<Item>("items", model) {

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

			@Override
			public void onSort(AjaxRequestTarget target, Item item, int position)
			{
				if (item != null)
				{
					this.info(String.format("'%s' has moved to position %d", item, position + 1));
				}

				target.add(feedback);
			}
		};

		this.add(sortable);
	}

	private static IModel<List<Item>> newSortableModel()
	{
		return new LoadableDetachableModel<List<Item>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Item> load()
			{
				return Items.asList("item #1", "item #2", "item #3", "item #4", "item #5", "item #6");
			}
		};
	}

	private static class Items
	{
		static List<Item> asList(String... names)
		{
			List<Item> list = new ArrayList<Item>();

			for (String name : names)
			{
				list.add(new Item(name));
			}

			return list;

		}
	}

	/**
	 * non-serializable item
	 */
	private static class Item
	{
		final String name;

		public Item(String name)
		{
			this.name = name;
		}

		@Override
		public int hashCode()
		{
			return this.name.hashCode(); //String hashCode is predictable
		}

		@Override
		public String toString()
		{
			return this.name;
		}
	}
}
