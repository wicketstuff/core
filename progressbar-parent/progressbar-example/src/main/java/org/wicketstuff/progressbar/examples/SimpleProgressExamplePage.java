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
package org.wicketstuff.progressbar.examples;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.wicketstuff.progressbar.ProgressBar;
import org.wicketstuff.progressbar.Progression;
import org.wicketstuff.progressbar.ProgressionModel;


/**
 * <p>
 * Simple example of an active progress bar without using a dedicated Spring task service.
 * </p>
 * 
 * <p>
 * The progress is stored directly in the page and the task is started in a new thread (this is not
 * suited for production!).
 * </p>
 * 
 * @author Christopher Hlubek (hlubek)
 * 
 */
public class SimpleProgressExamplePage extends PageSupport
{

	private static final long serialVersionUID = 1L;

	/**
	 * The current progress is stored here
	 */
	int progress = 0;

	int item = 0;

	public SimpleProgressExamplePage()
	{
		final Form<Void> form = new Form<Void>("form");
		final ProgressBar bar;
		form.add(bar = new ProgressBar("bar", new ProgressionModel()
		{
			private static final long serialVersionUID = 1L;

			// Get current progress from page field
			@Override
			protected Progression getProgression()
			{
				return new Progression(progress, "Item " + item);
			}
		})
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onFinished(AjaxRequestTarget target)
			{
				// Hide progress bar after finish
				setVisible(false);
				// Add some JavaScript after finish
				target.appendJavaScript("alert('Task done!')");

				// re-enable button
				Component button = form.get("submit");
				button.setEnabled(true);
				target.add(button);
			}
		});
		// Hide progress bar initially
		bar.setVisible(false);

		form.add(new IndicatingAjaxButton("submit", form)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				// Start the progress bar, will set visibility to true
				bar.start(target);
				// Thread holds reference to page :(
				new Thread()
				{
					@Override
					public void run()
					{
						for (int i = 0; i <= 50; i++)
						{
							try
							{
								Thread.sleep(400);
							}
							catch (InterruptedException e)
							{
							}
							item = i;
							progress = i * 2;
						}
						// The bar is stopped automatically, if progress is done
					}
				}.start();

				// disable button
				setEnabled(false);
			}

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				target.prependJavaScript("alert('Failed to update progress');");
			}

		});
		form.setOutputMarkupId(true);
		add(form);
	}
}
