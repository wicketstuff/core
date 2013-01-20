package com.googlecode.wicket.jquery.ui.samples.pages.selectable;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.IClusterable;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import com.googlecode.wicket.jquery.ui.form.autocomplete.AutoCompleteUtils;
import com.googlecode.wicket.jquery.ui.interaction.Draggable;
import com.googlecode.wicket.jquery.ui.interaction.Droppable;
import com.googlecode.wicket.jquery.ui.interaction.Selectable;
import com.googlecode.wicket.jquery.ui.interaction.SelectableDraggableFactory;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

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
		this.selectable = new Selectable<Genre>("selectable", GENRES) {

			private static final long serialVersionUID = 1L;

			@Override
			protected String getItemSelector()
			{
				return "TR"; //Gets the selector that identifies the selectable item within the Selectable component
			}

			@Override
			protected void onSelect(AjaxRequestTarget target, List<Genre> items)
			{
				this.info("items: " + items.toString());
				target.add(feedback);
			}
		};

		this.add(this.selectable);
		
		
		// Selectable ListView, with the default "empty" (ie: with no default icon) selectable-draggable factory //
		final SelectableDraggableFactory factory = new SelectableDraggableFactory();
		
		this.selectable.add(new ListView<Genre>("items", GENRES) {

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
		droppable.add(new ListView<Genre>("items", new PropertyModel<List<Genre>>(this.selectable, "selectedItems")) {

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
			protected void onDrop(AjaxRequestTarget target, Draggable<?> draggable)
			{
				info(String.format("Dropped %s", selectable.getSelectedItems()));

				target.add(feedback);
				target.add(this); //refresh the listview
			}
		};
	}

	
	// List of Genre(s) //
	static final List<Genre> GENRES = Arrays.asList(
			new Genre("Black Metal", "cover-black-metal.png"),
			new Genre("Death Metal", "cover-death-metal.png"),
			new Genre("Doom Metal", "cover-doom-metal.png"),
			new Genre("Folk Metal", "cover-folk-metal.png"),
			new Genre("Gothic Metal", "cover-gothic-metal.png"),
			new Genre("Heavy Metal", "cover-heavy-metal.png"),
			new Genre("Power Metal", "cover-power-metal.png"),
			new Genre("Symphonic Metal", "cover-symphonic-metal.png"),
			new Genre("Trash Metal", "cover-trash-metal.png"),
			new Genre("Vicking Metal", "cover-vicking-metal.png")); 

	
	// Bean //
	static class Genre implements IClusterable
	{
		private static final long serialVersionUID = 1L;

		public static Genre emptyGenre()
		{
			return new Genre("", "cover-empty.png");
		}

		private final String name;
		private final String cover;
		
		public Genre(final String name, final String cover)
		{
			this.name = name;
			this.cover = cover;
		}
		
		public String getName()
		{
			return this.name;
		}
		
		public String getCover()
		{
			return "images/" + this.cover;
		}
		
		/**
		 * #toString() needs to be overridden if no renderer is provided.
		 * #toString() is also used by {@link AutoCompleteUtils#contains(List, String)} method.
		 */
		@Override
		public String toString()
		{
			return this.name;
		}
	}	
}
