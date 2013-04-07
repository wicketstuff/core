package com.googlecode.wicket.jquery.ui.samples.pages.droppable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.interaction.draggable.Draggable;
import com.googlecode.wicket.jquery.ui.interaction.droppable.Droppable;

public class ShoppingDroppablePage extends AbstractDroppablePage
{
	private static final long serialVersionUID = 1L;
	private final List<Book> books;
	private final List<Book> orders;

	public ShoppingDroppablePage()
	{
		this.books = this.newBookList();
		this.orders = new ArrayList<Book>();

		this.init();
	}

	private void init()
	{
		// Shopping card //
		final Form<List<Book>> form = new Form<List<Book>>("form", new ListModel<Book>(this.orders));
		this.add(form);

		// the droppable area
		final Droppable<Void> droppable = new Droppable<Void>("card") {

			private static final long serialVersionUID = 1L;

			@Override
			//XXX: report as changed: protected void onDrop(AjaxRequestTarget target, Draggable<?> draggable) to:
			public void onDrop(AjaxRequestTarget target, Component component)
			{
				if (component != null)
				{
					Object object = component.getDefaultModelObject();

					if (object instanceof Book)
					{
						orders.add((Book)object);
					}
				}

				target.add(form);
			}
		};

		// the card list view
		droppable.add(new ListView<Book>("card-items", form.getModel()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Book> item)
			{
				final Book book = item.getModelObject();

				item.add(new Label("label", new PropertyModel<Book>(book, "label")));
				item.add(new Label("price", new PropertyModel<Book>(book, "price")));
				item.add(new AjaxButton("remove") {

					private static final long serialVersionUID = 1L;

					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form)
					{
						orders.remove(book);
						target.add(form);
					}
				});
			}
		});

		// the card total amount
		droppable.add(new Label("card-amount", new AbstractReadOnlyModel<String>() {

			private static final long serialVersionUID = 1L;

			@Override
			public String getObject()
			{
				float amount = 0;

				for (Book book : orders)
				{
					amount += book.getPrice();
				}

				return String.format("%.2f", amount);
			}
		}));

		form.add(droppable);


		// Book Store //
		this.add(new ListView<Book>("books", this.books) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Book> item)
			{
				IModel<Book> model = item.getModel();

				Draggable<Book> draggable = new Draggable<Book>("draggable", model);
				draggable.add(new BookFragment("book", model));

				item.add(draggable.setRevert(true));
			}
		});
	}

	private List<Book> newBookList()
	{
		return Arrays.asList(new Book("Wicket In Action", 25.64f, "book1.png"),
				new Book("Pro Wicket", 31.59f, "book2.png"),
				new Book("Wicket (German)", 29.21f, "book3.png"),
				new Book("Wicket (Japanese)", 30.22f, "book4.png"));
	}

	// Book fragment //
	class BookFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public BookFragment(String id, IModel<Book> model)
		{
			super(id, "book-fragment", ShoppingDroppablePage.this, model);
			this.init();
		}

		private void init()
		{
			Book book = (Book) this.getDefaultModelObject();

			this.add(new ContextImage("cover", book.getCover()));
			this.add(new Label("label", new PropertyModel<Book>(book, "label")));
			this.add(new Label("price", new PropertyModel<Book>(book, "price")));
		}
	}

	class Book implements IClusterable, Comparable<Book>
	{
		private static final long serialVersionUID = 1L;

		private final String cover;
		private final String label;
		private final float price;

		public Book(String label, float price, String cover)
		{
			this.label = label;
			this.price = price;
			this.cover = cover;
		}

		public String getLabel()
		{
			return this.label;
		}

		public float getPrice()
		{
			return this.price;
		}

		public String getCover()
		{
			return "images/" + this.cover;
		}

		@Override
		public int compareTo(Book book)
		{
			return this.label.compareTo(book.label);
		}
	}
}
