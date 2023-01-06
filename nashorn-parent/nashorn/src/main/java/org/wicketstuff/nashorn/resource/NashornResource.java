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

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.script.Bindings;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.NullWriter;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.PartWriterCallback;

import org.openjdk.nashorn.api.scripting.ClassFilter;

/**
 * A nashorn resource to execute java script on server side.<br>
 * <br>
 * Please ensure to use the {@link NashornSecurityManager} to restrict class access.
 * 
 * @author Tobias Soloschenko
 *
 */
@SuppressWarnings({ "restriction" })
public class NashornResource extends AbstractResource
{

	private static final long serialVersionUID = 1L;

	private ScheduledExecutorService scheduledExecutorService;

	private long delay;

	private TimeUnit delayUnit;

	private long wait;

	private TimeUnit waitUnit;

	private long maxScriptMemorySize;

	/**
	 * Creates a new nashorn resource
	 * 
	 * @param scheduledExecutorService
	 *            the scheduled executor service to run scripts
	 * @param delay
	 *            the delay until a script execution is going to be terminated
	 * @param delayUnit
	 *            the unit until a script execution is going to be terminated
	 * @param wait
	 *            how long to w8 until the next memory check occurs
	 * @param waitUnit
	 *            the unit until the next memory check occurs
	 * @param maxScriptMemorySize
	 *            the memory usage the script process should use - else it will be aborted
	 */
	public NashornResource(ScheduledExecutorService scheduledExecutorService, long delay,
		TimeUnit delayUnit, long wait, TimeUnit waitUnit, long maxScriptMemorySize)
	{
		this.scheduledExecutorService = scheduledExecutorService;
		this.delay = delay;
		this.delayUnit = delayUnit;
		this.wait = wait;
		this.waitUnit = waitUnit;
		this.maxScriptMemorySize = maxScriptMemorySize;
	}

	/**
	 * Executes the java script code received from the client and returns the response
	 */
	@Override
	protected ResourceResponse newResourceResponse(Attributes attributes)
	{
		RequestCycle cycle = RequestCycle.get();
		Long startbyte = cycle.getMetaData(CONTENT_RANGE_STARTBYTE);
		Long endbyte = cycle.getMetaData(CONTENT_RANGE_ENDBYTE);
		HttpServletRequest httpServletRequest = (HttpServletRequest)attributes.getRequest()
			.getContainerRequest();
		try (InputStream inputStream = httpServletRequest.getInputStream())
		{
			String script = IOUtils.toString(inputStream);
			if (script.contains("nashornResourceReferenceScriptExecutionThread"))
			{
				throw new IllegalAccessException(
					"It is not allowed to gain access to the nashorn thread itself! (nashornResourceReferenceScriptExecutionThread)");
			}
			String safeScript = ensureSafetyScript(script, attributes);
			NashornScriptCallable nashornScriptCallable = new NashornScriptCallable(safeScript,
				attributes, getClassFilter(), getWriter(), getErrorWriter())
			{
				@Override
				protected void setup(Attributes attributes, Bindings bindings)
				{
					NashornResource.this.setup(attributes, bindings);
				}
			};
			Object scriptResult = executeScript(nashornScriptCallable, true);
			ResourceResponse resourceResponse = new ResourceResponse();
			resourceResponse.setContentType("text/plain");
			resourceResponse.setWriteCallback(new PartWriterCallback(
				IOUtils.toInputStream(scriptResult != null ? scriptResult.toString() : ""),
				Long.valueOf(scriptResult != null ? scriptResult.toString().length() : 0),
				startbyte, endbyte));
			return resourceResponse;
		}
		catch (Exception e)
		{
			ResourceResponse errorResourceResponse = processError(e);
			if (errorResourceResponse == null)
			{
				throw new WicketRuntimeException(
					"Error while reading / executing the script the script", e);
			}
			else
			{
				return errorResourceResponse;
			}

		}
	}

	/**
	 * Ensure that the given script is going to be safe. Safe because of endless loops for example.
	 * 
	 * @param script
	 *            the script to be make safe
	 * @param attributes
	 *            the attributes
	 * @return the safe script
	 * @throws Exception
	 *             if an error occured while making the script safe
	 */
	private String ensureSafetyScript(String script, Attributes attributes) throws Exception
	{
		ClassFilter classFilter = new ClassFilter()
		{
			@Override
			public boolean exposeToScripts(String arg0)
			{
				return true;
			}
		};
		NashornScriptCallable nashornScriptCallable = new NashornScriptCallable(
			getScriptByName(NashornResource.class.getSimpleName() + ".js"), attributes, classFilter,
			getWriter(), getErrorWriter())
		{
			@Override
			protected void setup(Attributes attributes, Bindings bindings)
			{
				bindings.put("script", script);
				bindings.put("debug", isDebug());
				bindings.put("debug_log_prefix", NashornResource.class.getSimpleName() + " - ");
			}
		};
		return executeScript(nashornScriptCallable, false).toString();
	}


	/**
	 * Gets a script by name - the scope is always the class NashornResource
	 * 
	 * @param name
	 *            the name of the script
	 * @return the script
	 * @throws IOException
	 *             if the script fail to load
	 */
	private String getScriptByName(String name) throws IOException
	{
		String script = "";

		try (InputStream scriptInputStream = NashornResource.class.getResourceAsStream(name))
		{
			script = IOUtils.toString(scriptInputStream);
		}
		return script;
	}

	/**
	 * Executes a given script callable and the corresponding script
	 * 
	 * @param executeScript
	 *            the script callable to execute
	 * @param watch
	 *            if the script execution should be watched
	 * @return the script result
	 * @throws Exception
	 */
	private Object executeScript(NashornScriptCallable executeScript, boolean watch)
		throws Exception
	{
		Future<Object> scriptTask = scheduledExecutorService.submit(executeScript);
		if (watch && waitUnit != null)
		{
			scheduledExecutorService.execute(new NashornMemoryWatcher(executeScript, scriptTask,
				wait, waitUnit, maxScriptMemorySize, isDebug(), getErrorWriter()));
		}
		scheduledExecutorService.schedule(() -> {
			scriptTask.cancel(true);
		} , this.delay, this.delayUnit);
		return scriptTask.get();
	}

	/**
	 * Customize the error response sent to the client
	 * 
	 * @param e
	 *            the exception occurred
	 * @return the error response
	 */
	protected ResourceResponse processError(Exception e)
	{
		return null;
	}

	/**
	 * Setup the bindings and make information available to the scripting context
	 * 
	 * @param attributes
	 *            the attributes of the request
	 * @param bindings
	 *            the bindings to add java objects to
	 */
	protected void setup(Attributes attributes, Bindings bindings)
	{
		// NOOP
	}

	/**
	 * Gets the class filter to apply to the scripting engine
	 * 
	 * @return the class filter to apply to the scripting engine
	 */
	protected ClassFilter getClassFilter()
	{
		// default is to allow nothing!
		return new ClassFilter()
		{
			@Override
			public boolean exposeToScripts(String name)
			{
				return false;
			}
		};
	}

	/**
	 * Gets the writer to which print outputs are going to be written to
	 * 
	 * the default is to use {@link NullWriter}
	 * 
	 * @return the writer for output
	 */
	protected Writer getWriter()
	{
		return new NullWriter();
	}

	/**
	 * Gets the writer to which error messages are going to be written to
	 * 
	 * the default is to use {@link NullWriter}
	 * 
	 * @return the error writer
	 */
	protected Writer getErrorWriter()
	{
		return new NullWriter();
	}

	/**
	 * If debug is enabled
	 * 
	 * @return if debug is enabled
	 */
	protected boolean isDebug()
	{
		return false;
	}
}