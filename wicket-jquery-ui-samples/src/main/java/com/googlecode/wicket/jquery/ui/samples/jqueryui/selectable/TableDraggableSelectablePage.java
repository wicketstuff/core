package com.googlecode.wicket.jquery.ui.samples.jqueryui.selectable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import com.googlecode.wicket.jquery.ui.interaction.draggable.Draggable;
import com.googlecode.wicket.jquery.ui.interaction.droppable.Droppable;
import com.googlecode.wicket.jquery.ui.interaction.selectable.Selectable;
import com.googlecode.wicket.jquery.ui.interaction.selectable.SelectableDraggableFactory;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Genre;
import com.googlecode.wicket.jquery.ui.samples.data.dao.GenresDAO;

public class TableDraggableSelectablePage extends AbstractSelectablePage
{
	private static final long serialVersionUID = 1L;
	private final FeedbackPanel feedback;
	private final Selectable<Genre> selectable;

	public TableDraggableSelectablePage()
	{
		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		this.add(this.feedback.setOutputMarkupId(true));

		// Selectable //
		this.selectable = new Selectable<Genre>("selectable", GenresDAO.all()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected String getItemSelector()
			{
				return "TR"; //Gets the selector that identifies the selectable item within the Selectable component
			}

			@Override
			public void onSelect(AjaxRequestTarget target)
			{
				this.info("items: " + this.getDefaultModelObjectAsString());
				target.add(feedback);
			}
		};

		this.add(this.selectable);


		// Selectable ListView, with the default "empty" (ie: with no default icon) selectable-draggable factory //
		final SelectableDraggableFactory factory = new SelectableDraggableFactory();

		this.selectable.add(new ListView<Genre>("items", GenresDAO.all()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Genre> item)
			{
				Genre genre = item.getModelObject();

				// Draggable (non styled container) //
				Draggable<?> draggable = selectable.createDraggable("drag", factory);
				draggable.add(new ContextImage("cover", new PropertyModel<String>(genre, "cover")));
				item.add(draggable);

				// Genre //
				item.add(new Label("name", new PropertyModel<String>(genre, "name")));
			}
		});

		// Droppable //
		Droppable<Genre> droppable = this.newDroppable("droppable");
		this.add(droppable);

		// Droppable ListView //
		droppable.add(new ListView<Genre>("items", this.selectable.getModel()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Genre> item)
			{
				item.add(new ContextImage("cover", item.getModelObject().getCover()));
			}
		});
	}

	/**
	 * Gets a new Droppable.
	 * By default 'over' and 'exit' ('out') events are disabled to minimize client/server round-trips.
	 */
	private Droppable<Genre> newDroppable(String id)
	{
		return new Droppable<Genre>(id) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onDrop(AjaxRequestTarget target, Component component)
			{
				info(String.format("Dropped %s", selectable.getModelObject()));

				target.add(feedback);
				target.add(this); //refresh the listview
			}
		};
	}
}
