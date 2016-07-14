package com.googlecode.wicket.jquery.ui.samples.jqueryui.sortable;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.interaction.selectable.ISelectableListener;
import com.googlecode.wicket.jquery.ui.interaction.selectable.SelectableBehavior;
import com.googlecode.wicket.jquery.ui.interaction.sortable.Sortable;
import com.googlecode.wicket.jquery.ui.interaction.sortable.Sortable.HashListView;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class SelectableSortablePage extends AbstractSortablePage implements ISelectableListener<String>
{
	private static final long serialVersionUID = 1L;	
	private static final String HANDLE = Options.asString(".handle");

	private final FeedbackPanel feedback;

	public SelectableSortablePage()
	{
		final List<String> list = newList("item #1", "item #2", "item #3", "item #4", "item #5", "item #6");

		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		this.add(this.feedback.setOutputMarkupId(true));

		// Sortable //
		final Sortable<String> sortable = new Sortable<String>("sortable", list, new Options("handle", HANDLE)) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onInitialize()
			{
				super.onInitialize();

				this.add(SelectableSortablePage.this.newSelectableBehavior(JQueryWidget.getSelector(this), list));
			}

			@Override
			public void onUpdate(AjaxRequestTarget target, String item, int index)
			{
				// Will update the model object with the new order
				// Remove the call to super if you do not want your model to be updated (or you use a LDM)
				super.onUpdate(target, item, index);

				this.info(String.format("'%s' has moved to position %d", item, index + 1));
				this.info("The list order is now: " + this.getModelObject());

				target.add(feedback);
			}

			@Override
			protected HashListView<String> newListView(IModel<List<String>> model)
			{
				return SelectableSortablePage.newListView("items", model);
			}
		};

		this.add(sortable);
	}

	// ISelectableListener //

	@Override
	public void onSelect(AjaxRequestTarget target, List<String> items)
	{
		info("selected " + items);

		target.add(this.feedback);
	}

	// Factories //

	protected SelectableBehavior<String> newSelectableBehavior(String id, final List<String> list)
	{
		Options options = new Options("cancel", HANDLE);

		return new SelectableBehavior<String>(id, options, this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected String getItemSelector()
			{
				return "li";
			}

			@Override
			protected List<String> getItemList()
			{
				return list;
			}
		};
	}

	// Static factories //

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
