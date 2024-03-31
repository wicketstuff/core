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

import java.io.Writer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.script.Bindings;

import org.apache.wicket.request.resource.AbstractResource.ResourceResponse;
import org.apache.commons.io.output.NullWriter;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.IResource.Attributes;

import org.openjdk.nashorn.api.scripting.ClassFilter;

import org.apache.wicket.request.resource.ResourceReference;

/**
 * Creates a nashorn resource reference to accept java script code from the client side.<br>
 * <br>
 * Please ensure to use the {@link NashornSecurityManager} to restrict class access.
 * 
 * @author Tobias Soloschenko
 *
 */
@SuppressWarnings("restriction")
public class NashornResourceReference extends ResourceReference
{

	private ScheduledExecutorService scheduledExecutorService = null;

	private long delay;

	private TimeUnit delayUnit;

	private long wait;

	private TimeUnit waitUnit;

	private long maxScriptMemorySize;

	private boolean debug;

	/**
	 * Creates a nashorn resource reference with the given name
	 * 
	 * @param name
	 *            the name of the nashorn resource reference
	 * @param coreSize
	 *            the core size of the script execution pool
	 * @param delay
	 *            the delay until a script execution is going to be terminated
	 * @param delayUnit
	 *            the unit until a script execution is going to be terminated
	 */
	public NashornResourceReference(String name, int coreSize, long delay, TimeUnit delayUnit)
	{
		super(name);
		scheduledExecutorService = Executors.newScheduledThreadPool(coreSize);
		this.delay = delay;
		this.delayUnit = delayUnit;
	}

	/**
	 * Creates a nashorn resource reference with the given name
	 * 
	 * @param name
	 *            the name of the nashorn resource reference
	 * @param coreSize
	 *            the core size of the script execution pool
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
	public NashornResourceReference(String name, int coreSize, long delay, TimeUnit delayUnit,
		long wait, TimeUnit waitUnit, long maxScriptMemorySize)
	{
		super(name);
		scheduledExecutorService = Executors.newScheduledThreadPool(coreSize);
		this.delay = delay;
		this.delayUnit = delayUnit;
		this.wait = wait;
		this.waitUnit = waitUnit;
		this.maxScriptMemorySize = maxScriptMemorySize;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public IResource getResource()
	{
		return new NashornResource(scheduledExecutorService, this.delay, this.delayUnit, this.wait,
			this.waitUnit, this.maxScriptMemorySize)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void setup(Attributes attributes, Bindings bindings)
			{
				NashornResourceReference.this.setup(attributes, bindings);
			}

			@Override
			protected ResourceResponse processError(Exception e)
			{
				return NashornResourceReference.this.processError(e);
			}

			@Override
			protected ClassFilter getClassFilter()
			{
				return NashornResourceReference.this.getClassFilter();
			}

			@Override
			protected boolean isDebug()
			{
				return NashornResourceReference.this.isDebug();
			}

			@Override
			protected Writer getWriter()
			{
				return NashornResourceReference.this.getWriter();
			}

			@Override
			protected Writer getErrorWriter()
			{
				return NashornResourceReference.this.getErrorWriter();
			}
		};
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
		return debug;
	}

	/**
	 * Gets the scheduled executor services
	 * 
	 * @return the scheduled executor service
	 */
	public ScheduledExecutorService getScheduledExecutorService()
	{
		return scheduledExecutorService;
	}
}