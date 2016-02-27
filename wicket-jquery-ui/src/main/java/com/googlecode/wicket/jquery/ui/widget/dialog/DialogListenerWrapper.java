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
package com.googlecode.wicket.jquery.ui.widget.dialog;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.util.lang.Args;

/**
 * Wrapper/Delegate class for {@link IDialogListener}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class DialogListenerWrapper implements IDialogListener
{
	private static final long serialVersionUID = 1L;

	private final IDialogListener listener;
	
	public DialogListenerWrapper(IDialogListener listener)
	{
		this.listener = Args.notNull(listener, "listener");
	}

	@Override
	public boolean isDefaultCloseEventEnabled()
	{
		return this.listener.isDefaultCloseEventEnabled();
	}

	@Override
	public boolean isEscapeCloseEventEnabled()
	{
		return this.listener.isEscapeCloseEventEnabled();
	}

	@Override
	public void onClick(AjaxRequestTarget target, DialogButton button)
	{
		this.listener.onClick(target, button);
	}

	@Override
	public void onClose(IPartialPageRequestHandler handler, DialogButton button)
	{
		this.listener.onClose(handler, button);
	}
}
