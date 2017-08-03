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
package org.wicketstuff.progressbar.examples;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.wicketstuff.progressbar.ProgressBar;
import org.wicketstuff.progressbar.spring.ITaskService;
import org.wicketstuff.progressbar.spring.Task;
import org.wicketstuff.progressbar.spring.TaskProgressionModel;


/**
 * <p>
 * Example of an active progress bar using the tasks service.
 * </p>
 * 
 * @author Christopher Hlubek (hlubek)
 * 
 */
public class TaskServiceProgressExamplePage extends PageSupport
{

	private static final long serialVersionUID = 1L;

	private static class DummyTask extends Task
	{
		private final int iterations;

		public DummyTask(int iterations)
		{
			this.iterations = iterations;
		}

		@Override
		protected void run()
		{
			for (int i = 0; i < iterations; i++)
			{
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
				}
				updateProgress(i, iterations);
				if (isCancelled())
					return;
			}
		}
	}

	public TaskServiceProgressExamplePage()
	{
		final Form<Void> form = new Form<Void>("form");
		final ProgressBar bar;
		final TaskProgressionModel progressionModel = new TaskProgressionModel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected ITaskService getTaskService()
			{
				return getExampleApplication().getTaskService();
			}
		};
		form.add(bar = new ProgressBar("bar", progressionModel)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onFinished(AjaxRequestTarget target)
			{
				ITaskService taskService = getExampleApplication().getTaskService();
				// finish the task!
				taskService.finish(progressionModel.getTaskId());
				// Hide progress bar after finish
				setVisible(false);
				// Add some JavaScript after finish
				target.appendJavaScript("alert('Task done and finished!')");

				// re-enable button
				Component button = form.get("submit");
				button.setEnabled(true);
				target.add(button);
			}
		});
		// Hide progress bar initially
		bar.setVisible(false);

		form.add(new IndicatingAjaxButton("submit", form)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				ITaskService taskService = getExampleApplication().getTaskService();
				// Schedule and start a new task
				Long taskId = taskService.scheduleAndStart(new DummyTask(60));
				// Set taskId for model
				progressionModel.setTaskId(taskId);
				// Start the progress bar, will set visibility to true
				bar.start(target);

				// disable button
				setEnabled(false);
			}

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				target.prependJavaScript("alert('Failed to schedule task.');");
			}

		});
		form.setOutputMarkupId(true);
		add(form);
	}
}
