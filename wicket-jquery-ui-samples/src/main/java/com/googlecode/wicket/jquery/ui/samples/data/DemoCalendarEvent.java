package com.googlecode.wicket.jquery.ui.samples.data;

import java.util.Date;

import com.googlecode.wicket.jquery.ui.calendar.CalendarEvent;

public class DemoCalendarEvent extends CalendarEvent
{
	private static final long serialVersionUID = 1L;
	
	public enum Category
	{
		RED("red"),
		BLUE("blue");
		
		private final String color;

		private Category(String color)
		{
			this.color = color;
		}
		
		@Override
		public String toString()
		{
			return this.color;
		}
	}

	private Category category;

	public DemoCalendarEvent(int id, String title, Category category, Date date)
	{
		super(id, title, date);

		this.category = category;
	}

	public DemoCalendarEvent(int id, String title, Category category, Date start, Date end)
	{
		super(id, title, start, end);
		
		this.category = category;
	}
	
	public void setCategory(Category category)
	{
		this.category = category;
	}
	
	public Category getCategory()
	{
		return this.category;
	}
}
