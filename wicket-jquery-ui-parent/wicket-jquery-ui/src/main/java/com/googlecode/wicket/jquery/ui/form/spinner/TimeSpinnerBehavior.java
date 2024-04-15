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
package com.googlecode.wicket.jquery.ui.form.spinner;

import org.apache.wicket.resource.JQueryPluginResourceReference;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a jQuery time spinner {@link JQueryBehavior}
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.22.0
 * @since 7.1.2
 */
public class TimeSpinnerBehavior extends SpinnerBehavior
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "timespinner";

	public TimeSpinnerBehavior(String selector, ISpinnerListener listener, Options options)
	{
		super(selector, METHOD, listener, options);

		// jQuery Globalize should have been set //
		this.add(new JQueryPluginResourceReference(TimeSpinnerBehavior.class, "timespinner.js"));
	}
}
