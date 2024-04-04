/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.wicket.jquery.ui;

import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;
import org.apache.wicket.csp.CSPDirective;
import org.apache.wicket.csp.CSPDirectiveSrcValue;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Provides the {@link IInitializer} for jQuery UI
 *
 * @author Sebastien Briquet - sebfz1
 */
public class Initializer implements IInitializer
{
	@Override
	public void init(Application application)
	{
		if (application instanceof WebApplication)
		{
			WebApplication webApplication = (WebApplication) application;
			webApplication.getAjaxRequestTargetListeners().add(new JQueryDestroyListener());
			webApplication.getCspSettings().blocking() // lf
					.add(CSPDirective.STYLE_SRC, CSPDirectiveSrcValue.UNSAFE_INLINE) // lf
					.add(CSPDirective.SCRIPT_SRC, CSPDirectiveSrcValue.UNSAFE_EVAL);
		}
	}

	@Override
	public void destroy(Application application)
	{
		// noop
	}

	@Override
	public String toString()
	{
		return "Wicket jQuery UI initializer";
	}
}
