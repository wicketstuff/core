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

import org.wicketstuff.progressbar.Progression;
import org.wicketstuff.progressbar.ProgressionModel;

/**
 * Use this as a model for a ProgressBar, if tasks are submitted to the task service. The current
 * progress is fetched from the task service.
 * 
 * After scheduling a task, set the taskId.
 * 
 * @author Christopher Hlubek (hlubek)
 * 
 */
public class TaskProgressionModel extends ProgressionModel
{

	private static final long serialVersionUID = 1L;

	private final ITaskService taskService;

	private Long taskId;

	public TaskProgressionModel(ITaskService taskService)
	{
		this.taskService = taskService;
	}

	public TaskProgressionModel()
	{
		this(null);
	}

	@Override
	protected Progression getProgression()
	{
		return getTaskService().getProgression(taskId);
	}

	public Long getTaskId()
	{
		return taskId;
	}

	public void setTaskId(Long taskId)
	{
		this.taskId = taskId;
	}

	protected ITaskService getTaskService()
	{
		return taskService;
	}

}
