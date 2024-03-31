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

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A Task is some kind of long running action that should be done in the background. The task can
 * update the progress based on the current position in the workload.
 * </p>
 * 
 * <p>
 * Basically what you have to do for your own Task is to override the run() method and frequently
 * call one of the updateProgress methods.
 * </p>
 * 
 * @author Christopher Hlubek (hlubek)
 * 
 */
public class Task
{

	/**
	 * <p>
	 * Message is a value object for messages during task runtime.
	 * </p>
	 * 
	 * <p>
	 * These messages could be transferred to the wicket user interface later.
	 * </p>
	 * 
	 */
	public static class Message
	{
		/**
		 * The severity of the message
		 */
		public static enum Severity
		{
			INFO, WARN, ERROR;
		}

		public final Message.Severity severity;

		public final String messageKey;

		public final Object[] arguments;

		/**
		 * @param severity
		 *            the severity of the message
		 * @param messageKey
		 *            key of a message resources
		 * @param arguments
		 *            these arguments will be used for resource messages
		 */
		public Message(Message.Severity severity, String messageKey, Object[] arguments)
		{
			this.severity = severity;
			this.messageKey = messageKey;
			this.arguments = arguments;
		}

		/**
		 * Message with no arguments
		 * 
		 * @param severity
		 * @param messageKey
		 */
		public Message(Message.Severity severity, String messageKey)
		{
			this(severity, messageKey, null);
		}
	}

	private Runnable runnable;

	private int progress = 0;

	private boolean done = false;

	private boolean cancel = false;

	private List<Message> messages = new ArrayList<Message>();

	public Task()
	{

	}

	public Task(Runnable runnable)
	{
		this.runnable = runnable;
	}

	final Runnable getRunnable()
	{
		return new Runnable()
		{
			public void run()
			{
				try
				{
					if (runnable != null)
					{
						runnable.run();
					}
					else
					{
						Task.this.run();
					}
				}
				catch (Throwable t)
				{
					// TODO catch Exception and mark task as errorneous
					throw new RuntimeException(t);
				}
				finish();
			}
		};
	}

	private void finish()
	{
		done = true;
		doFinish();
	}

	/**
	 * <p>
	 * Implement the actual calculation of the task here.
	 * </p>
	 * 
	 * 
	 * <p>
	 * If iterating and cancelling should be supported you should check for a canceled task in every
	 * iteration:
	 * </p>
	 * 
	 * <pre>
	 * if (isCancelled())
	 * {
	 * 	return;
	 * }
	 * </pre>
	 * 
	 */
	protected void run()
	{

	}

	/**
	 * Will be called after finishing the task
	 */
	protected void doFinish()
	{

	}

	public boolean isCancelled()
	{
		return cancel;
	}

	public void reset()
	{
		progress = 0;
		done = false;
		cancel = false;
	}

	public void cancel()
	{
		cancel = true;
	}

	/**
	 * Update the current progress with current / total values (e.g. 1st of 5).
	 * 
	 * This should be called from the run method for every iteration.
	 * 
	 * @param current
	 *            The current iteration (counted from zero!)
	 * @param total
	 *            Total iterations
	 */
	public void updateProgress(int current, int total)
	{
		progress = (int)Math.ceil(((current + 1) / (double)total) * 100.0);
	}

	/**
	 * Update progress with percentage value
	 * 
	 * @param progressPercent
	 *            progress in percent in [0, 100]
	 */
	public void updateProgress(int progressPercent)
	{
		progress = Math.max(progress, progressPercent);
	}

	public boolean isDone()
	{
		return done;
	}

	public int getProgress()
	{
		return progress;
	}

	/**
	 * Add an info message
	 * 
	 * @param messageKey
	 * @param arguments
	 */
	public void info(String messageKey, Object... arguments)
	{
		addMessage(messageKey, Message.Severity.INFO, arguments);
	}

	/**
	 * Add an warn message
	 * 
	 * @param messageKey
	 * @param arguments
	 */
	public void warn(String messageKey, Object... arguments)
	{
		addMessage(messageKey, Message.Severity.WARN, arguments);
	}

	/**
	 * Add an error message
	 * 
	 * @param messageKey
	 * @param arguments
	 */
	public void error(String messageKey, Object... arguments)
	{
		addMessage(messageKey, Message.Severity.ERROR, arguments);
	}


	private void addMessage(String messageKey, Message.Severity severity, Object... arguments)
	{
		messages.add(new Message(severity, messageKey, arguments.length == 0 ? null : arguments));
	}

	/**
	 * Get generated messages
	 * 
	 * @return generated messages
	 */
	public List<Message> getMessages()
	{
		return messages;
	}

}
