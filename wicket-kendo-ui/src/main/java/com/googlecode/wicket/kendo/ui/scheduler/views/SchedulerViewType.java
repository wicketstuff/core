package com.googlecode.wicket.kendo.ui.scheduler.views;

/**
 * Provides an enum of available scheduler views
 *
 * @author Patrick Davids - Patrick1701
 */
public enum SchedulerViewType
{
	day, week, workWeek, month, agenda;

	/**
	 * Safely get the {@link SchedulerViewType} corresponding to the supplied view name
	 *
	 * @param viewName the view name
	 * @return null if the view name does not correspond to a view of the enum
	 */
	public static SchedulerViewType get(String viewName)
	{
		try
		{
			return SchedulerViewType.valueOf(viewName);
		}
		catch (IllegalArgumentException e) { /* not handled */ }

		return null;
	}
}
