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

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.progressbar.spring.AsynchronousExecutor;
import org.wicketstuff.progressbar.spring.ITaskService;
import org.wicketstuff.progressbar.spring.TaskService;

/**
 * Wicket application for progess bar examples. For illustration purposes, the task service is
 * stored in the application, this should certainly be done with a spring context.
 * 
 * 
 * @author Christopher Hlubek (hlubek)
 * 
 */
public class ExampleApplication extends WebApplication
{

	private final ITaskService taskService;

	/**
	 *
	 */
	public ExampleApplication()
	{
		taskService = new TaskService(new AsynchronousExecutor());
	}

	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return SimpleProgressExamplePage.class;
	}

	public synchronized ITaskService getTaskService()
	{
		return taskService;
	}

}
