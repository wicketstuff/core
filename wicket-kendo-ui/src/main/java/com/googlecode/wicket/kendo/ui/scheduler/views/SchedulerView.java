package com.googlecode.wicket.kendo.ui.scheduler.views;

import com.googlecode.wicket.jquery.core.Options;

/**
 * 
 * @author Patrick Davids - Patrick1701
 *
 */
public abstract class SchedulerView extends Options
{

	private static final long serialVersionUID = 1L;

	protected SchedulerView(SchedulerViewType type)
	{
		super.set("type", Options.asString(type.name()));
	}
	
	public SchedulerViewType getType()
	{
		return get("type");
	}
	
	public SchedulerView setSelected(boolean selected){
		super.set("selected", selected);
		return this;
	}
	
	public static void main(String[] args) {
		
		Options o = new Options();
		o.set("foo", new Options("foo1","value1"), new Options("foo2",Options.asString("value2")));
		
		System.out.println(o);
		
		Options options = new Options();
		options.set("views", "["+DayView.newInstance().setSelected(true)+","+WeekView.newInstance()+"]");

		System.out.println(options);
//		options.set("views", DayView.newInstance().setSelected(true).toString(), WeekView.newInstance().toString());
		System.out.println(options);
	}
}