package com.googlecode.wicket.jquery.ui.samples.pages.selectable;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import com.googlecode.wicket.jquery.ui.interaction.Draggable;
import com.googlecode.wicket.jquery.ui.interaction.Droppable;
import com.googlecode.wicket.jquery.ui.interaction.Selectable;
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
			protected void onSelect(AjaxRequestTarget target, List<String> items)
			{
				this.info("items: " + items.toString());
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
		droppable.add(new ListView<String>("items", new PropertyModel<List<String>>(this.selectable, "selectedItems")) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<String> item)
			{
				item.add(new Label("item", item.getModelObject()));
			}
		});
	}

	/**
	 * Gets a new Droppable.
	 * By default 'over' and 'exit' ('out') events are disabled to minimize client/server round-trips.
	 */
	private Droppable<?> newDroppable(String id)
	{
		return new Droppable<Void>(id) {
	
			private static final long serialVersionUID = 1L;

			@Override
			protected void onDrop(AjaxRequestTarget target, Draggable<?> draggable)
			{
				info(String.format("Dropped %s", selectable.getSelectedItems()));

				target.add(feedback);
				target.add(this); //refresh the listview
			}
		};
	}
}
