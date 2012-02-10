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

import java.util.List;

import org.wicketstuff.progressbar.Progression;
import org.wicketstuff.progressbar.spring.Task.Message;

/**
 * 
 * 
 * 
 * The task should always be referenced with the taskId in the Wicket page, so there is no public
 * getTask(...) method!
 * 
 * @author Christopher Hlubek (hlubek)
 * 
 */
public interface ITaskService
{

	/**
	 * Schedules the task, but doesn't start!
	 * 
	 * @param task
	 *            The task to schedule
	 * @return Generated ID of the task
	 */
	Long schedule(Task task);

	/**
	 * Start the task with given taskId
	 * 
	 * @param taskId
	 */
	void start(Long taskId);

	/**
	 * Cancel the task with given taskId. The task itself is responsible for stopping, so this
	 * should be considered as some type of "soft interrupt".
	 * 
	 * @param taskId
	 */
	void cancel(Long taskId);

	/**
	 * Cleanup the task with the given taskId and remove it from the task service.
	 * 
	 * Should always be called to free the task and prevent memory leaks!
	 * 
	 * Since this not really safe to do from a web page AJAX callback, you should always register
	 * the TaskService as a SESSION bean.
	 * 
	 * @param taskId
	 */
	void finish(Long taskId);

	/**
	 * Schedule and start a task.
	 * 
	 * Equal to:
	 * 
	 * <pre>
	 * Long taskId = taskService.schedule(task);
	 * taskService.start(taskId);
	 * </pre>
	 * 
	 * @param task
	 * @return taskId for this Task
	 */
	Long scheduleAndStart(Task task);

	/**
	 * Get the current progression of a task.
	 * 
	 * @param taskId
	 * @return the current progression of a task.
	 */
	Progression getProgression(Long taskId);

	List<Message> getMessages(Long taskId);

}