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

	public SchedulerView setDateHeaderTemplatePattern(String pattern){
		super.set("dateHeaderTemplate", "kendo.template(\"<strong>#=kendo.toString(date, '"+pattern+"')#</strong>\")");
		return this;
	}
	
}