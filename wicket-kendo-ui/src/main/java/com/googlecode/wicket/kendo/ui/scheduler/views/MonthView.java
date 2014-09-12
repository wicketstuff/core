package com.googlecode.wicket.kendo.ui.scheduler.views;

/**
 * 
 * @author Patrick Davids - Patrick1701
 *
 */
public final class MonthView extends SchedulerView
{

	private static final long serialVersionUID = 1L;

	private MonthView()
	{
		super(SchedulerViewType.month);
	}

	public SchedulerView setDayTemplatePattern(String pattern){
		super.set("dayTemplate", "kendo.template(\"<strong>#=kendo.toString(date, '"+pattern+"')#</strong>\")");
		return this;
	}
	
	public static MonthView newInstance()
	{
		return new MonthView();
	}
}