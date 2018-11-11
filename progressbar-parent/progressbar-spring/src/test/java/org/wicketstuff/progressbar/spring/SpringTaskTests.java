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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.Executor;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wicketstuff.progressbar.ProgressBar;
import org.wicketstuff.progressbar.Progression;
import org.wicketstuff.progressbar.ProgressionModel;
import org.wicketstuff.progressbar.spring.Task.Message;

public class SpringTaskTests
{

	private WicketTester tester;

	private TaskService taskService;

	@BeforeEach
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

		@Override
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
		assertEquals(0, task.getProgress(), "Task progress initially 0");
		Long taskId = taskService.schedule(task);
		assertEquals(task, taskService.getTask(taskId), "Task can be found by ID");
		taskService.start(taskId);
		assertEquals(2, data[0], "Task was executed");
		assertEquals(100, task.getProgress(), "Task progress is 100");
		assertTrue(task.isDone(), "Task is done after finish");
		assertNotNull(taskService.getTask(taskId), "Task NOT removed from service after done");
		taskService.finish(taskId);
		assertNull(taskService.getTask(taskId), "Task removed from service with finish");

		data[0] = 0;
		task.reset();
		assertEquals(0, task.getProgress(), "Task progress is 0 after reset");
		// cancel should stop task at 50%
		task.cancel();
		Long newTaskId = taskService.scheduleAndStart(task);
		assertFalse(taskId.equals(newTaskId), "Unique id generated");

		assertEquals(1, data[0], "Cancelled after 50%");
		assertTrue(task.isCancelled(), "Task was cancelled");
		assertEquals(50, task.getProgress(), "Progress is 50%");
		// done if cancelled?
		assertTrue(task.isDone(), "Task is done");
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
			@Override
			public void run()
			{
				data[0] = true;
			}
		});
		executor.join();
		assertTrue(data[0]);
	}
}
