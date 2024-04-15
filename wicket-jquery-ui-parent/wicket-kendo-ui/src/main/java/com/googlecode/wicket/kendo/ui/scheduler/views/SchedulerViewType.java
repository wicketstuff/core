package com.googlecode.wicket.kendo.ui.scheduler.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides an enum of available scheduler views
 *
 * @author Patrick Davids - Patrick1701
 */
public enum SchedulerViewType
{
	day, week, workWeek, month, agenda, timeline, timelineWeek, timelineWorkWeek, timelineMonth;

	private static final Logger LOG = LoggerFactory.getLogger(SchedulerViewType.class);

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
		catch (IllegalArgumentException e)
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug(e.getMessage(), e);
			}
		}

		return null;
	}
}
