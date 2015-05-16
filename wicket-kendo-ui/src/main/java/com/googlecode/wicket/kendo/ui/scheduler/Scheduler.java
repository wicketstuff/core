/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.wicket.kendo.ui.scheduler;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryContainer;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.kendo.ui.KendoTemplateBehavior;
import com.googlecode.wicket.kendo.ui.scheduler.resource.ResourceList;
import com.googlecode.wicket.kendo.ui.scheduler.resource.ResourceListModel;
import com.googlecode.wicket.kendo.ui.scheduler.views.SchedulerViewType;

/**
 * Provides the Kendo UI Scheduler
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class Scheduler extends JQueryContainer implements ISchedulerListener
{
	private static final long serialVersionUID = 1L;

	/** Provides an enum of available group orientation */
	protected enum GroupOrientation
	{
		horizontal, vertical
	}

	private final Options options;
	private SchedulerModelBehavior modelBehavior; // load events

	private final ResourceListModel resourceListModel = new ResourceListModel();

	// template //
	private final IJQueryTemplate template;
	private KendoTemplateBehavior templateBehavior = null;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public Scheduler(String id)
	{
		this(id, null, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options {@link Options}
	 */
	public Scheduler(String id, Options options)
	{
		this(id, null, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link SchedulerModel}
	 */
	public Scheduler(String id, SchedulerModel model)
	{
		this(id, model, new Options());
	}

	/**
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param model the {@link SchedulerModel}
	 * @param options {@link Options}
	 */
	public Scheduler(String id, SchedulerModel model, Options options)
	{
		super(id, model);

		this.options = Args.notNull(options, "options");
		this.template = this.newTemplate();
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link SchedulerModel}
	 * @param resourceList a {@link ResourceList}
	 */
	public Scheduler(String id, SchedulerModel model, ResourceList resourceList)
	{
		this(id, model, resourceList, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link SchedulerModel}
	 * @param resourceList a {@link ResourceList}
	 * @param options {@link Options}
	 */
	public Scheduler(String id, SchedulerModel model, ResourceList resourceList, Options options)
	{
		this(id, model, options);

		this.resourceListModel.add(resourceList);
	}

	// Methods //

	public void add(ResourceList resourceList)
	{
		this.resourceListModel.add(resourceList);
	}

	/**
	 * Gets the Kendo (jQuery) object
	 *
	 * @return the jQuery object
	 */
	protected String widget()
	{
		return String.format("jQuery('%s').data('%s')", JQueryWidget.getSelector(this), SchedulerBehavior.METHOD);
	}

	/**
	 * Refreshes the events currently available in the selected view.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void refresh(AjaxRequestTarget target)
	{
		target.appendJavaScript(String.format("var widget = %s; widget.dataSource.read(); widget.refresh();", this.widget()));
	}

	// Properties //

	@Override
	public boolean isEditEnabled()
	{
		return false;
	}

	/**
	 * Gets the calendar's model
	 *
	 * @return a {@link SchedulerModel}
	 */
	public SchedulerModel getModel()
	{
		return (SchedulerModel) this.getDefaultModel();
	}

	/**
	 * Gets the orientation of the group headers
	 *
	 * @return {@value GroupOrientation#horizontal} by default
	 */
	protected GroupOrientation getGroupOrientation()
	{
		return GroupOrientation.horizontal;
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.modelBehavior = this.newSchedulerModelBehavior(this.getModel());
		this.add(this.modelBehavior);

		if (this.template != null)
		{
			this.templateBehavior = new KendoTemplateBehavior(this.template);
			this.add(this.templateBehavior);
		}
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		super.onConfigure(behavior);

		behavior.setOption("timezone", Options.asString("Etc/UTC"));

		// groups //
		List<String> groups = this.resourceListModel.getGroups();

		if (!groups.isEmpty())
		{
			Options options = new Options();
			options.set("resources", Options.asString(groups));
			options.set("orientation", Options.asString(this.getGroupOrientation()));
			behavior.setOption("group", options);
		}

		// set template (if any) //
		if (this.templateBehavior != null)
		{
			behavior.setOption("eventTemplate", String.format("jQuery('#%s').html()", this.templateBehavior.getToken()));
		}
	}

	@Override
	public void onEdit(AjaxRequestTarget target, SchedulerEvent event, SchedulerViewType view)
	{
		// noop
	}

	@Override
	public void onNavigate(AjaxRequestTarget target, SchedulerViewType view)
	{
		this.refresh(target);
	}

	@Override
	public void onCreate(AjaxRequestTarget target, SchedulerEvent event)
	{
		// noop
	}

	@Override
	public void onUpdate(AjaxRequestTarget target, SchedulerEvent event)
	{
		// noop
	}

	@Override
	public void onDelete(AjaxRequestTarget target, SchedulerEvent event)
	{
		// noop
	}

	// IJQueryWidget //

	/**
	 * see {@link JQueryContainer#newWidgetBehavior(String)}
	 */
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new SchedulerBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			// Properties //

			@Override
			public boolean isEditEnabled()
			{
				return Scheduler.this.isEditEnabled();
			}

			@Override
			protected CharSequence getDataSourceUrl()
			{
				return Scheduler.this.modelBehavior.getCallbackUrl();
			}

			@Override
			protected ResourceListModel getResourceListModel()
			{
				return Scheduler.this.resourceListModel;
			}

			// Events //

			@Override
			public void onEdit(AjaxRequestTarget target, SchedulerEvent event, SchedulerViewType view)
			{
				Scheduler.this.onEdit(target, event, view);
			}

			@Override
			public void onNavigate(AjaxRequestTarget target, SchedulerViewType view)
			{
				Scheduler.this.onNavigate(target, view);
			}

			@Override
			public void onCreate(AjaxRequestTarget target, SchedulerEvent event)
			{
				Scheduler.this.onCreate(target, event);
			}

			@Override
			public void onUpdate(AjaxRequestTarget target, SchedulerEvent event)
			{
				Scheduler.this.onUpdate(target, event);
			}

			@Override
			public void onDelete(AjaxRequestTarget target, SchedulerEvent event)
			{
				Scheduler.this.onDelete(target, event);
			}
		};
	}

	// Factory methods //

	/**
	 * Gets a new {@link IJQueryTemplate} to customize the event rendering
	 * 
	 * @return null by default
	 */
	// TODO add ISchedulerTemplate? (#getTextProperties seems to be useless, to be double checked)
	protected IJQueryTemplate newTemplate()
	{
		return null;
	}

	/**
	 * Gets a new {@link SchedulerModelBehavior}
	 *
	 * @param model the {@link SchedulerModel}
	 * @return the {@link SchedulerModelBehavior}
	 */
	protected SchedulerModelBehavior newSchedulerModelBehavior(final SchedulerModel model)
	{
		return new SchedulerModelBehavior(model);
	}
}
