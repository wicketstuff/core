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
package org.wicketstuff.progressbar.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.Executor;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.progressbar.ProgressBar;
import org.wicketstuff.progressbar.Progression;
import org.wicketstuff.progressbar.ProgressionModel;
import org.wicketstuff.progressbar.spring.Task.Message;

public class SpringTaskTests
{

	private WicketTester tester;

	private TaskService taskService;

	@Before
	public void setUp() throws Exception
	{
		// setup dependencies
		setUpDependencies();

		// setup mock injection
		ApplicationContextMock ctx = new ApplicationContextMock();
		addDependenciesToContext(ctx);

		tester = new WicketTester();
		tester.getApplication()
			.getComponentInstantiationListeners()
			.add(new SpringComponentInjector(tester.getApplication(), ctx, true));

		// add string resource loader
		// TODO: make this work for 1.5
		// tester.getApplication().getResourceSettings().addStringResourceLoader(
		// new ComponentStringResourceLoader());
		// tester.getApplication().getResourceSettings().addStringResourceLoader(
		// new ClassStringResourceLoader(getClass()));
	}

	private static class SynchronousExecutor implements Executor
	{

		public void execute(Runnable command)
		{
			command.run();
		}

	}

	private void setUpDependencies()
	{
		// set up task service with a dummy executor
		taskService = new TaskService(new SynchronousExecutor());
	}

	private void addDependenciesToContext(ApplicationContextMock ctx)
	{
		ctx.putBean("taskService", taskService);
	}

	@Test
	public void testTaskExecution()
	{
		final int[] data = new int[] { 0 };
		Task task = new Task()
		{
			@Override
			public void run()
			{
				for (int i = 0; i < 2; i++)
				{
					data[0]++;
					updateProgress(i, 2);
					if (isCancelled())
					{
						return;
					}
				}
			}
		};
		assertEquals("Task progress initially 0", 0, task.getProgress());
		Long taskId = taskService.schedule(task);
		assertEquals("Task can be found by ID", task, taskService.getTask(taskId));
		taskService.start(taskId);
		assertEquals("Task was executed", 2, data[0]);
		assertEquals("Task progress is 100", 100, task.getProgress());
		assertTrue("Task is done after finish", task.isDone());
		assertNotNull("Task NOT removed from service after done", taskService.getTask(taskId));
		taskService.finish(taskId);
		assertNull("Task removed from service with finish", taskService.getTask(taskId));

		data[0] = 0;
		task.reset();
		assertEquals("Task progress is 0 after reset", 0, task.getProgress());
		// cancel should stop task at 50%
		task.cancel();
		Long newTaskId = taskService.scheduleAndStart(task);
		assertFalse("Unique id generated", taskId.equals(newTaskId));

		assertEquals("Cancelled after 50%", 1, data[0]);
		assertTrue("Task was cancelled", task.isCancelled());
		assertEquals("Progress is 50%", 50, task.getProgress());
		// done if cancelled?
		assertTrue("Task is done", task.isDone());
	}

	// TODO Task with error / exception

	@Test
	public void testTaskMessaging()
	{
		Task task = new Task()
		{
			@Override
			public void run()
			{
				error("error.message");
				warn("warn.message");
				info("info.message");
			}
		};
		Long taskId = taskService.scheduleAndStart(task);

		// wait some time

		task = taskService.getTask(taskId);
		List<Task.Message> messages = task.getMessages();
		assertEquals(3, messages.size());
		assertEquals(Message.Severity.ERROR, messages.get(0).severity);
		assertEquals("error.message", messages.get(0).messageKey);
		assertNull(messages.get(0).arguments);

		assertEquals(Message.Severity.WARN, messages.get(1).severity);
		assertEquals("warn.message", messages.get(1).messageKey);

		assertEquals(Message.Severity.INFO, messages.get(2).severity);
		assertEquals("info.message", messages.get(2).messageKey);
	}

	@Test
	public void testProgressBar()
	{

		final Integer[] data = new Integer[] { 0 };
		Task task = new Task()
		{
			@Override
			public void run()
			{
				for (int i = 0; i < 2; i++)
				{
					data[0]++;
					updateProgress(i, 2);
					if (isCancelled())
					{
						return;
					}
				}
			}
		};

		final Long taskId = taskService.schedule(task);

		tester.startComponentInPage(new ProgressBar("panelId", new ProgressionModel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected Progression getProgression()
			{
				return taskService.getProgression(taskId);
			}
		}), null);
		tester.assertLabel("panel:label", "0%");

		taskService.start(taskId);

		tester.assertLabel("panel:label", "100%");
	}

	@Test
	public void testThreadPoolExecutor() throws InterruptedException
	{
		final Boolean[] data = new Boolean[] { false };
		AsynchronousExecutor executor = new AsynchronousExecutor();
		executor.execute(new Runnable()
		{
			public void run()
			{
				data[0] = true;
			}
		});
		executor.join();
		assertTrue(data[0]);
	}
}
