package com.googlecode.wicket.jquery.ui.samples.data.dao.scheduler;

import java.util.Arrays;
import java.util.Date;

import com.googlecode.wicket.kendo.ui.scheduler.SchedulerEvent;

public class EmployeeEventsDAO extends AbstractSchedulerEventsDAO
{
	public static final String ROOM_ID = "roomId";
	public static final Integer ROOM_1 = 1;
	public static final Integer ROOM_2 = 2;

	public static final String EMPLOYEE_ID = "employeeId";
	public static final String EMPLOYEE_1 = "f5d884f9-7657"; // uuid-like strings
	public static final String EMPLOYEE_2 = "970ad1d7-505d";

	private static EmployeeEventsDAO instance = null;

	public static synchronized EmployeeEventsDAO get()
	{
		if (instance == null)
		{
			instance = new EmployeeEventsDAO();
		}

		return instance;
	}

	protected EmployeeEventsDAO()
	{
		SchedulerEvent event1 = new SchedulerEvent(newId(), "Meeting #1", new Date());
		event1.setValue(ROOM_ID, ROOM_1);
		event1.setValue(EMPLOYEE_ID, Arrays.asList(EMPLOYEE_1, EMPLOYEE_2));
		super.list.add(event1);

		SchedulerEvent event2 = new SchedulerEvent(newId(), "Meeting #2", new Date());
		event2.setValue(ROOM_ID, ROOM_2);
		event2.setValue(EMPLOYEE_ID, Arrays.asList(EMPLOYEE_1, EMPLOYEE_2));
		super.list.add(event2);
	}

	@Override
	public SchedulerEvent update(SchedulerEvent event)
	{
		SchedulerEvent e = super.update(event);

		if (e != null)
		{
			e.setValue(ROOM_ID, event.getValue(ROOM_ID));
			e.setValue(EMPLOYEE_ID, event.getValue(EMPLOYEE_ID));
		}

		return e;
	}
}
