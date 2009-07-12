package org.wicketstuff.ddcalendar;

import org.apache.wicket.ResourceReference;
import org.joda.time.LocalTime;

public class MyCalendarEvent implements CalendarEvent {
	private static final long serialVersionUID = 1L;
	
	private HomePage homePage;
	private String caption;
	private int day;
	private LocalTime start;
	
	public MyCalendarEvent(HomePage homePage, String caption) {
		this.homePage = homePage;
		this.caption = caption;
	}

	public Color getBackgroundColor() {
		return Color.BLUE;
	}

	public String getCaption() {
		return caption;
	}

	public Color getColor() {
		return Color.WHITE;
	}

	public int getDay() {
		return day;
	}

	public String getDescription() {
		return "";
	}

	public int getDuration() {
		return 60;
	}

	public ResourceReference getEventImageResource() {
		return new ResourceReference(getClass(), "item.png");
	}

	public String getEventStyle(int hourHeight) {
		double minuteHeight = (double) (hourHeight/60.0);
		
		double positionTop = 0.0;

		String color = getBackgroundColor().html();
		
		double height = -6.0 + minuteHeight*getDuration();
		
		if(getStart() != null) {
			double startHour = getStart().getHourOfDay();
			double startMinute = getStart().getMinuteOfHour();
			positionTop = 3.0 + hourHeight*startHour + minuteHeight*startMinute;
		}
		else
			return "position: relative; height: "+height+"px; background-color: "+color+";";
		
		String borderStyle = "";
				
		return "top: "+positionTop+"px; height: "+height+"px; background-color: "+color+"; "+borderStyle;
	}

	public LocalTime getStart() {
		return start;
	}

	public void remove() {
		start = null;
		homePage.unschedule(this);
	}

	public void schedule(int dayOfWeek, int hourOfDay) {
		this.day = dayOfWeek;
		this.start = new LocalTime(hourOfDay, 0);
	}
}
