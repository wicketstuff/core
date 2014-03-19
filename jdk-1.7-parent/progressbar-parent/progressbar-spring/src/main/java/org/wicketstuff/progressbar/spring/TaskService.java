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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.progressbar.Progression;
import org.wicketstuff.progressbar.spring.Task.Message;


/**
 * <p>
 * Should be registered as SESSION scoped bean to prevent memory leaks with unfinished tasks.
 * </p>
 * 
 * TODO automatic cleanup of tasks
 * 
 * @author Christopher Hlubek (hlubek)
 * 
 */
public class TaskService implements ITaskService
{

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

	private final Executor executor;

	public TaskService(Executor executor)
	{
		this.executor = executor;
	}

	private Map<Long, Task> tasks = new HashMap<Long, Task>();

	private long nextTaskId = 0;

	public synchronized Long schedule(Task task)
	{
		Long taskId = nextTaskId();
		LOGGER.debug("Scheduling task with ID: " + taskId);
		tasks.put(taskId, task);
		return taskId;
	}

	public void start(Long taskId)
	{
		Task task = getTask(taskId);
		if (task != null)
		{
			LOGGER.debug("Starting task with ID: " + taskId);
			start(task);
		}
		else
		{
			LOGGER.warn("Task ID " + taskId + " not found");
		}
	}

	public void cancel(Long taskId)
	{
		Task task = getTask(taskId);
		if (task != null)
		{
			task.cancel();
		}
	}

	private void start(Task task)
	{
		executor.execute(task.getRunnable());
	}

	Task getTask(Long taskId)
	{
		return tasks.get(taskId);
	}


	private synchronized long nextTaskId()
	{
		return nextTaskId++;
	}

	public Long scheduleAndStart(Task task)
	{
		Long taskId = schedule(task);
		start(task);
		return taskId;
	}

	public void finish(Long taskId)
	{
		tasks.remove(taskId);
	}

	public Progression getProgression(Long taskId)
	{
		Task task = getTask(taskId);
		// HACK Need real finished setting in Progression
		// FIXME we really don't know if task is null!
		return (task == null || task.isDone()) ? new Progression(100) : new Progression(
			task.getProgress());
	}

	public List<Message> getMessages(Long taskId)
	{
		Task task = getTask(taskId);
		// FIXME clone messages to prevent direct reference to task!
		return task.getMessages();
	}

}
