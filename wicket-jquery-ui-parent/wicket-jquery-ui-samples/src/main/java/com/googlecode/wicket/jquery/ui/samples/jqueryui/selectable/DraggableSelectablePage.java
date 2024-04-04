package com.googlecode.wicket.jquery.ui.samples.jqueryui.selectable;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.ui.interaction.draggable.Draggable;
import com.googlecode.wicket.jquery.ui.interaction.droppable.Droppable;
import com.googlecode.wicket.jquery.ui.interaction.selectable.Selectable;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class DraggableSelectablePage extends AbstractSelectablePage
{
	private static final long serialVersionUID = 1L;
	private final FeedbackPanel feedback;
	private final Selectable<String> selectable;

	public DraggableSelectablePage()
	{
		List<String> list = Arrays.asList("item #1", "item #2", "item #3", "item #4", "item #5", "item #6");

		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		this.add(this.feedback.setOutputMarkupId(true));

		// Selectable //
		this.selectable = new Selectable<String>("selectable", list) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSelect(AjaxRequestTarget target)
			{
				this.info("items: " + this.getDefaultModelObjectAsString());
				target.add(feedback);
			}
		};

		this.add(this.selectable);

		// Selectable ListView //
		this.selectable.add(new ListView<String>("items", list) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<String> item)
			{
				// Draggable //
				Draggable<?> draggable = selectable.createDraggable("drag");
				item.add(draggable);

				// Label //
				Label label = new Label("item", item.getModelObject());
				label.add(AttributeModifier.append("style", "position: relative; top: 2px; vertical-align: top;"));
				item.add(label);
			}
		});

		// Droppable //
		Droppable<?> droppable = this.newDroppable("droppable");
		this.add(droppable);

		// Droppable ListView //
		droppable.add(new ListView<String>("items", this.selectable.getModel()) {

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

		response.render(new StyleSheetPackageHeaderItem(DraggableSelectablePage.class));
	}

	// Factories //

	/**
	 * Gets a new Droppable.
	 * By default 'over' and 'exit' ('out') events are disabled to minimize client/server round-trips.
	 */
	private Droppable<?> newDroppable(String id)
	{
		return new Droppable<Void>(id) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onDrop(AjaxRequestTarget target, Component component)
			{
				info(String.format("Dropped %s", selectable.getModelObject()));

				target.add(feedback);
				target.add(this); // refresh the listview
			}
		};
	}
}
