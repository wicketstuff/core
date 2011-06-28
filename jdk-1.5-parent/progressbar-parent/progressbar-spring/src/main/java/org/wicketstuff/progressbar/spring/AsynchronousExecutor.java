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

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Simple wrapper around java.util.concurrent Executors.
 * 
 * 
 * @author Christopher Hlubek (hlubek)
 * 
 */
public class AsynchronousExecutor implements Executor
{

	private final ExecutorService executor;

	public AsynchronousExecutor()
	{
		executor = Executors.newCachedThreadPool();
	}

	public void execute(Runnable command)
	{
		executor.execute(command);
	}

	public void join() throws InterruptedException
	{
		executor.awaitTermination(1, TimeUnit.SECONDS);
	}

}
