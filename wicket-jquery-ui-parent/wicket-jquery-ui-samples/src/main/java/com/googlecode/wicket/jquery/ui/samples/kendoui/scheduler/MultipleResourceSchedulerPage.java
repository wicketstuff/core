package com.googlecode.wicket.jquery.ui.samples.kendoui.scheduler;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
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

public class MultipleResourceSchedulerPage extends AbstractSchedulerPage // NOSONAR
{
	private static final long serialVersionUID = 1L;

	public MultipleResourceSchedulerPage()
	{
		// Form //
		final Form<Date> form = new Form<Date>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback", new Options());
		form.add(feedback.setOutputMarkupId(true));

		// MultiSelect //
		final MultiSelect<Resource> multiselect = new MultiSelect<Resource>("rooms", Model.ofList(newRoomList()), newRoomList(), new ChoiceRenderer<Resource>("text"));
		form.add(multiselect);

		// Scheduler //
		Options options = new Options();
		options.set("date", "Date.now()");
		options.set("editable", true); // default
		options.set("views", "[ { type: 'day', showWorkHours: true }, { type: 'week', showWorkHours: true, selected: true }, { type: 'month' } ]");
		options.set("workDayStart", "new Date('2014/1/1 08:00 AM')");
		options.set("workDayEnd", "new Date('2014/1/1 6:00 PM')");

		final Scheduler scheduler = new Scheduler("scheduler", newSchedulerModel(), options) { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected void onConfigure()
			{
				super.onConfigure();

				Collection<Resource> rooms = multiselect.getModelObject();

				if (!rooms.isEmpty())
				{
					this.getResourceListModel().clear();
					this.getResourceListModel().add(newRoomList(rooms));
					this.getResourceListModel().add(newEmployeeList());
				}
				else
				{
					this.warn("No room is selected");

					AjaxRequestTarget target = RequestCycleUtils.getAjaxRequestTarget();

					if (target != null)
					{
						feedback.refresh(target, true);
					}
				}
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

		form.add(new AjaxButton("refresh") { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				target.add(scheduler);
			}
		});
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(MultipleResourceSchedulerPage.class));
	}

	// Factories //

	static SchedulerModel newSchedulerModel()
	{
		// ISchedulerVisitor
		return new SchedulerModel() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<SchedulerEvent> load(ZonedDateTime start, ZonedDateTime until)
			{
				return EmployeeEventsDAO.get().getEvents(start, until);
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
		List<Resource> list = Generics.newArrayList();
		list.add(new Resource(EmployeeEventsDAO.ROOM_1, "Room #1", "#6699cc")); // using integer ids
		list.add(new Resource(EmployeeEventsDAO.ROOM_2, "Room #2", "#9966cc"));

		return newRoomList(list);
	}

	static ResourceList newRoomList(Collection<Resource> resources)
	{
		ResourceList list = new ResourceList("Room", EmployeeEventsDAO.ROOM_ID, "Rooms"); // grouping by "Rooms" (optional)

		for (Resource resource : resources)
		{
			list.add(resource);
		}

		return list;
	}

	static ResourceList newEmployeeList()
	{
		ResourceList list = new ResourceList("Employee", EmployeeEventsDAO.EMPLOYEE_ID, true); // true: multiple
		list.add(new Resource(EmployeeEventsDAO.EMPLOYEE_1, "Patrick", "#339966")); // using uuid-like ids
		list.add(new Resource(EmployeeEventsDAO.EMPLOYEE_2, "Sebastien", "#996633"));

		return list;
	}
}
