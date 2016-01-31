package com.googlecode.wicket.jquery.ui.samples.data.dao.scheduler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.googlecode.wicket.kendo.ui.scheduler.SchedulerEvent;

public class EmployeeEventsDAO extends AbstractSchedulerEventsDAO
{
	private static final String ROOM_ID = "roomId";
	private static final String EMPLOYEE_ID = "employeeId";

	private static EmployeeEventsDAO instance = null;

	public static synchronized EmployeeEventsDAO get()
	{
		if (instance == null)
		{
			instance = new EmployeeEventsDAO();
		}

		return instance;
	}

	public static final String EMPLOYEE_1 = "123-456";
	public static final String EMPLOYEE_2 = "456-789";

	protected EmployeeEventsDAO()
	{
		super();

		SchedulerEvent event1 = new SchedulerEvent(this.newId(), "Meeting #1", new Date());
		event1.setResource(ROOM_ID, 1);
		event1.setResource(EMPLOYEE_ID, Arrays.asList(EMPLOYEE_1, EMPLOYEE_2));
		super.list.add(event1);

		SchedulerEvent event2 = new SchedulerEvent(this.newId(), "Meeting #2", new Date());
		event2.setResource(ROOM_ID, 2);
		event2.setResource(EMPLOYEE_ID, Arrays.asList(EMPLOYEE_1, EMPLOYEE_2));
		super.list.add(event2);
	}

	@Override
	public SchedulerEvent update(SchedulerEvent event)
	{
		SchedulerEvent e = super.update(event);

		if (e != null)
		{
			e.setResource(ROOM_ID, event.getValue(ROOM_ID, Integer.class));
			e.setResource(EMPLOYEE_ID, event.getValue(EMPLOYEE_ID, List.class));
		}

		return e;
	}
}
