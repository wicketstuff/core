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
package org.wicketstuff.nashorn.resource;

import java.io.IOException;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.sun.management.ThreadMXBean;

/**
 * The memory watcher is used to detect memory leaks. If the check interval is to low a high cpu
 * impact is the consequence. if it is to high a program might swallow to much memory and a oom
 * exception occurs.
 * 
 * @author Tobias Soloschenko
 */
@SuppressWarnings({ "restriction" })
public class NashornMemoryWatcher implements Runnable
{

	private ThreadMXBean threadMXBean;

	private boolean threadAllocatedMemorySupported;

	private NashornScriptCallable nashornScriptCallable;

	private Future<Object> scriptTask;

	private long wait;

	private TimeUnit unit;

	private long maxMemoryUsage;

	private boolean debug;

	private Writer errorWriter;

	/**
	 * Creates a new memory watcher
	 * 
	 * @param nashornScriptCallable
	 *            the script callable to be watched
	 * @param scriptTask
	 *            the script task to get the thread of
	 * @param wait
	 *            how long to wait until the next check
	 * @param unit
	 *            unit until the next check
	 * @param maxMemoryUsage
	 *            how much memory should be used as a maximum
	 * @param debug
	 *            if debug is enabled
	 * @param errorWriter
	 *            the error writer to output some information
	 */
	public NashornMemoryWatcher(final NashornScriptCallable nashornScriptCallable,
		final Future<Object> scriptTask, long wait, TimeUnit unit, long maxMemoryUsage,
		boolean debug, Writer errorWriter)
	{
		this.threadMXBean = (ThreadMXBean)ManagementFactory.getThreadMXBean();
		this.threadMXBean
			.setThreadAllocatedMemoryEnabled(this.threadAllocatedMemorySupported = this.threadMXBean
				.isThreadAllocatedMemorySupported());
		this.nashornScriptCallable = nashornScriptCallable;
		this.scriptTask = scriptTask;
		this.wait = wait;
		this.unit = unit;
		this.maxMemoryUsage = maxMemoryUsage;
		this.debug = debug;
		this.errorWriter = errorWriter;
	}

	@Override
	public void run()
	{
		if (threadAllocatedMemorySupported)
		{
			while (!scriptTask.isDone())
			{
				long threadId = nashornScriptCallable.getThreadId();
				if (threadId >= 0)
				{
					long threadAllocatedBytes = threadMXBean.getThreadAllocatedBytes(threadId);
					if (threadAllocatedBytes > maxMemoryUsage)
					{
						if (debug)
						{
							try
							{
								errorWriter
									.write("The script process with the thread id: " + threadId +
										" has been aborted due to memory abuse!\nSafe Script: " +
										nashornScriptCallable.getScript() + "\n");
								errorWriter.flush();
							}
							catch (IOException e)
							{
								// NOOP
							}
						}
						scriptTask.cancel(true);

					}
				}
				try
				{
					unit.sleep(wait);
				}
				catch (InterruptedException e)
				{
					// NOOP
				}
			}
		}
		else
		{
			throw new IllegalStateException(
				"The measurement of allocated memory is not enabled / supported! This may cause scripts to abuse memory consumption! To deactivate the watch feature don't apply the corresponding parameters at the " +
					NashornResourceReference.class.getName());
		}
	}
}

