package com.googlecode.wicket.jquery.ui.samples.data;

import java.time.LocalDateTime;

import com.googlecode.wicket.jquery.ui.calendar6.CalendarEvent;

public class DemoCalendar6Event extends CalendarEvent
{
	private static final long serialVersionUID = 1L;

	public enum Category
	{
		PUBLIC("public", "#5C9CCC"),
		PRIVATE("private", "#F6A828");

		private final String name;
		private final String color;

		private Category(String name, String color)
		{
			this.name = name;
			this.color = color;
		}

		public String getColor()
		{
			return this.color;
		}

		@Override
		public String toString()
		{
			return this.name;
		}
	}

	private Category category;

	public DemoCalendar6Event(String title, Category category, LocalDateTime date)
	{
		super(title, date);

		this.setCategory(category);
	}

	public DemoCalendar6Event(String title, Category category, LocalDateTime start, LocalDateTime end)
	{
		super(title, start, end);

		this.setCategory(category);
	}

	public DemoCalendar6Event(String id, String title, Category category, LocalDateTime date)
	{
		super(id, title, date);

		this.setCategory(category);
	}

	public DemoCalendar6Event(String id, String title, Category category, LocalDateTime start, LocalDateTime end)
	{
		super(id, title, start, end);

		this.setCategory(category);
	}

	public final void setCategory(Category category)
	{
		this.category = category;

		if (this.category != null)
		{
			this.setColor(this.category.getColor());
			this.setClassName(this.category.toString());
		}
	}

	public Category getCategory()
	{
		return this.category;
	}
}
