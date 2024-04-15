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
package com.googlecode.wicket.kendo.ui.console;

import java.util.List;

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;
import org.apache.wicket.feedback.IFeedback;
import org.apache.wicket.feedback.IFeedbackMessageFilter;

/**
 * Provides a Kendo UI feedback console widget<br>
 * <br>
 * <b>Note about the capacity:</b> the capacity allows to define a maximum number of messages.<br>
 * Elder messages will be automatically removed from the model object on insertion.<br>
 * However, this is only reflected when the component is (re-)rendered ({@link #onBeforeRender()} has to be invoked)
 *
 * @author Sebastien Briquet - sebfz1
 */
public class FeedbackConsole extends AbstractConsole implements IFeedback
{
	private static final long serialVersionUID = 1L;

	private FeedbackMessagesModel feedbackMessagesModel;

	/**
	 * Constructor with a default capacity ({@link ConsoleMessages#CAPACITY})
	 *
	 * @param id the markup id
	 */
	public FeedbackConsole(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param capacity the max capacity
	 */
	public FeedbackConsole(String id, int capacity)
	{
		super(id, capacity);
	}

	// Methods //

	/**
	 * Logs a {@link FeedbackMessage}
	 *
	 * @param message the {@link FeedbackMessage}
	 */
	protected void log(FeedbackMessage message)
	{
		this.log(message.getMessage(), this.isError(message));

		message.markRendered();
	}

	/**
	 * Logs a list of {@link FeedbackMessage}
	 *
	 * @param messages the list of {@link FeedbackMessage}
	 */
	protected final void log(List<FeedbackMessage> messages)
	{
		for (FeedbackMessage message : messages)
		{
			this.log(message);
		}
	}

	/**
	 * Logs a {@link FeedbackMessage}
	 *
	 * @param message the {@link FeedbackMessage}
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	protected void log(IPartialPageRequestHandler handler, FeedbackMessage message)
	{
		this.log(handler, message.getMessage(), this.isError(message));

		message.markRendered();
	}

	/**
	 * Logs a list of {@link FeedbackMessage}
	 *
	 * @param messages the list of {@link FeedbackMessage}
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	protected final void log(IPartialPageRequestHandler handler, List<FeedbackMessage> messages)
	{
		for (FeedbackMessage message : messages)
		{
			this.log(handler, message);
		}
	}

	/**
	 * Indicates whether the message is an error message
	 *
	 * @param message the {@link FeedbackMessage}
	 * @return true or false
	 */
	protected boolean isError(FeedbackMessage message)
	{
		return message.getLevel() >= FeedbackMessage.ERROR;
	}

	/**
	 * Refreshes the console with the current feedback messages
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void refresh(IPartialPageRequestHandler handler)
	{
		this.log(handler, this.feedbackMessagesModel.getObject());
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.feedbackMessagesModel = this.newFeedbackMessagesModel();
	}

	@Override
	protected void onBeforeRender()
	{
		super.onBeforeRender();

		this.log(this.feedbackMessagesModel.getObject()); // let throw a NPE. #newFeedbackMessagesModel is called just before in the request cycle
	}

	@Override
	protected void onDetach()
	{
		super.onDetach();

		this.feedbackMessagesModel.detach();
	}

	// Factories //

	/**
	 * Gets a new instance of the FeedbackMessagesModel to use.<br>
	 * This method can be overridden to provide a {@link IFeedbackMessageFilter}
	 *
	 * @return a new {@code FeedbackMessagesModel}
	 */
	protected FeedbackMessagesModel newFeedbackMessagesModel()
	{
		return new FeedbackMessagesModel(this);
	}
}
