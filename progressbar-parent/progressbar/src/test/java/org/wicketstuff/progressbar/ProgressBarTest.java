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
package org.wicketstuff.progressbar;

import static org.junit.Assert.assertTrue;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

public class ProgressBarTest
{

	private static class DummyTask
	{
		private int progress;

		void proceed(int percent)
		{
			progress = percent;
		}

		public int getProgress()
		{
			return progress;
		}
	}

	// TODO add failing to progress

	// TODO add cancel to progress

	// TODO enable messaging of current / total numbers

	/**
	 * <p>
	 * Test that the progress bar is correctly rendered and updated with the progression model.
	 * </p>
	 * 
	 * <p>
	 * This test doesn't take the AJAX self update into account.
	 * </p>
	 */
	@Test
	public void testProgressBar()
	{
		WicketTester wt = new WicketTester();
		final DummyTask testProgressive = new DummyTask();
		Panel progressBar = new ProgressBar("panelId", new ProgressionModel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected Progression getProgression()
			{
				return new Progression(testProgressive.getProgress());
			}

		});
		wt.startComponentInPage(progressBar, null);
		wt.assertLabel("panelId:label", "0%");
		testProgressive.proceed(50);
		wt.assertLabel("panelId:label", "50%");
		assertTrue(progressBar.getOutputMarkupId());
	}

	@Test
	public void testProgressBarMessage()
	{
		WicketTester wt = new WicketTester();
		final DummyTask testProgressive = new DummyTask();
		wt.startComponentInPage(new ProgressBar("panelId", new ProgressionModel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected Progression getProgression()
			{
				return new Progression(testProgressive.getProgress(), "Going for " +
					testProgressive.getProgress());
			}

		}), null);
		wt.assertLabel("panelId:message", "Going for 0");
		testProgressive.proceed(75);
		wt.assertLabel("panelId:message", "Going for 75");
	}

}
