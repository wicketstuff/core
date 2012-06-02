package com.googlecode.wicket.jquery.ui.samples.data;

import java.util.Date;

import com.googlecode.wicket.jquery.ui.calendar.CalendarEvent;

public class DemoCalendarEvent extends CalendarEvent
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

	public DemoCalendarEvent(int id, String title, Category category, Date date)
	{
		super(id, title, date);

		this.setCategory(category);
	}

	public DemoCalendarEvent(int id, String title, Category category, Date start, Date end)
	{
		super(id, title, start, end);

		this.setCategory(category);
	}

	public void setCategory(Category category)
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
