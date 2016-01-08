package com.googlecode.wicket.jquery.ui.samples.pages.kendo.scheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.util.ListModel;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.samples.data.dao.scheduler.EmployeeEventsDAO;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.form.multiselect.MultiSelect;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.scheduler.Scheduler;
import com.googlecode.wicket.kendo.ui.scheduler.SchedulerEvent;
import com.googlecode.wicket.kendo.ui.scheduler.SchedulerModel;
import com.googlecode.wicket.kendo.ui.scheduler.resource.Resource;
import com.googlecode.wicket.kendo.ui.scheduler.resource.ResourceList;
import com.googlecode.wicket.kendo.ui.scheduler.resource.ResourceListModel;

public class MultipleResourceSchedulerPage extends AbstractSchedulerPage
{
	private static final long serialVersionUID = 1L;

	public MultipleResourceSchedulerPage()
	{
		// Form //
		final Form<Date> form = new Form<Date>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// MultiSelect //
		final MultiSelect<Resource> multiselect = new MultiSelect<Resource>("rooms", new ListModel<Resource>(newRoomList()), newRoomList(), new ChoiceRenderer<Resource>("text"));
		form.add(multiselect);

		// Scheduler //
		Options options = new Options();
		options.set("date", "Date.now()");
		options.set("editable", true); // default
		options.set("views", "[ { type: 'day', showWorkHours: true }, { type: 'week', showWorkHours: true, selected: true }, { type: 'month' } ]");
		options.set("workDayStart", "new Date('2014/1/1 08:00 AM')");
		options.set("workDayEnd", "new Date('2014/1/1 6:00 PM')");

		final Scheduler scheduler = new Scheduler("scheduler", newSchedulerModel(), options) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onConfigure()
			{
				super.onConfigure();

				this.getResourceListModel().clear();
				this.getResourceListModel().add(newRoomList(multiselect.getModelObject()));
				this.getResourceListModel().add(newEmployeeList());
			}

			@Override
			public void onCreate(AjaxRequestTarget target, SchedulerEvent event)
			{
				EmployeeEventsDAO.get().create(event);

				this.info("Created: " + event);
				target.add(feedback);
			}

			@Override
			public void onUpdate(AjaxRequestTarget target, SchedulerEvent event)
			{
				EmployeeEventsDAO.get().update(event);

				this.info("Updated: " + event);
				target.add(feedback);
			}

			@Override
			public void onDelete(AjaxRequestTarget target, SchedulerEvent event)
			{
				EmployeeEventsDAO.get().delete(event);

				this.info("Deleted: " + event);
				target.add(feedback);
			}
		};

		form.add(scheduler);

		// Buttons //

		form.add(new AjaxButton("refresh") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				target.add(scheduler);
			}
		});
	}

	// Factories //

	static SchedulerModel newSchedulerModel()
	{
		// ISchedulerVisitor
		return new SchedulerModel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<SchedulerEvent> load()
			{
				return EmployeeEventsDAO.get().getEvents(this.getStart(), this.getEnd());
			}
		};
	}

	static ResourceListModel newResourceListModel()
	{
		ResourceListModel listModel = new ResourceListModel();

		listModel.add(newRoomList());
		listModel.add(newEmployeeList());

		return listModel;
	}

	static ResourceList newRoomList()
	{
		List<Resource> list = new ArrayList<Resource>();
		list.add(new Resource(1, "Room #1", "#6699cc"));
		list.add(new Resource(2, "Room #2", "#9966cc"));

		return newRoomList(list);
	}

	static ResourceList newRoomList(Collection<Resource> resources)
	{
		ResourceList list = new ResourceList("Room", "roomId", "Rooms"); // grouping by "Rooms" (optional)

		for (Resource resource : resources)
		{
			list.add(resource);
		}

		return list;
	}

	static ResourceList newEmployeeList()
	{
		ResourceList list = new ResourceList("Employee", "employeeId", true); // true: multiple
		list.add(new Resource(EmployeeEventsDAO.EMPLOYEE_1, "Patrick", "#339966")); // using EMPLOYEE_X/uuid like (String)
		list.add(new Resource(EmployeeEventsDAO.EMPLOYEE_2, "Sebastien", "#996633"));

		return list;
	}
}
