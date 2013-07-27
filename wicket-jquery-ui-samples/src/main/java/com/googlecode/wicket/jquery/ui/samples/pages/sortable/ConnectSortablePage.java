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

//XXX remove wicket-jquery-ui-6.9.1-SNAPSHOT (description)
public class ConnectSortablePage extends AbstractSortablePage
{
	private static final long serialVersionUID = 1L;

	private final FeedbackPanel feedback;
	private final Sortable<String> sortable1;
	private final Sortable<String> sortable2;

	public ConnectSortablePage()
	{
		List<String> list1 = newList("item #1", "item #2", "item #3", "item #4", "item #5", "item #6");
		List<String> list2 = newList("item #7", "item #8", "item #9", "item #10", "item #11", "item #12");

		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		this.add(this.feedback.setOutputMarkupId(true));

		// Sortables //
		this.sortable1 = this.newSortable("sortable1", list1);
		this.add(this.sortable1);

		this.sortable2 = this.newSortable("sortable2", list2);
		this.add(this.sortable2.connectWith(this.sortable1));
	}

	private Sortable<String> newSortable(final String id, List<String> list)
	{
		return new Sortable<String>(id, list) {

			private static final long serialVersionUID = 1L;

			@Override
			protected HashListView<String> newListView(IModel<List<String>> model)
			{
				return ConnectSortablePage.newListView("items", model);
			}

			@Override
			public void onSort(AjaxRequestTarget target, String item, int index)
			{
				// Will update the model object with the new order
				// Remove the call to super if you do not want your model to be updated (or you use a LDM)
				super.onSort(target, item, index);

				this.info(String.format("'%s' of %s moved to position %d", item, id, index + 1));
				this.info(String.format("%s list order is now: %s", id, this.getModelObject()));

				target.add(feedback);
			}

			@Override
			public void onReceive(AjaxRequestTarget target, String item, int index)
			{
				this.getModelObject().add(index, item); //TODO move to super

				this.info(String.format("'%s' received in %s at position %d", item, id, index + 1));
				this.info(String.format("%s list order is now: %s", id, this.getModelObject()));
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

	private static List<String> newList(String... items)
	{
		ArrayList<String> list = new ArrayList<String>();

		for (String item : items)
		{
			list.add(item);
		}

		return list;
	}
}
