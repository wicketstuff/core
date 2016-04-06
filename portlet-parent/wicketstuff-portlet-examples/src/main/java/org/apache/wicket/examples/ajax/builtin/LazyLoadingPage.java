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
package org.apache.wicket.examples.ajax.builtin;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;
import org.apache.wicket.markup.html.basic.Label;

/**
 * @author jcompagner
 */
public class LazyLoadingPage extends BasePage
{
	/**
	 * Construct.
	 */
	public LazyLoadingPage()
	{
		add(new AjaxLazyLoadPanel("lazy")
		{

			@Override
			public Component getLazyLoadComponent(String id)
			{
				// sleep for 5 seconds to show the behavior
				try
				{
					Thread.sleep(5000);
				}
				catch (InterruptedException e)
				{
					throw new RuntimeException(e);
				}
				return new Label(id, "Lazy Loaded after 5 seconds");
			}

		});
	}
}
