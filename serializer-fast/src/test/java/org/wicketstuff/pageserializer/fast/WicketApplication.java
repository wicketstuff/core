/**
 * Copyright (C)
 * 	2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 * 	2012 Michael Mosmann <michael@mosmann.de>
 *
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
package org.wicketstuff.pageserializer.fast;

import java.io.File;
import java.util.UUID;

import org.apache.wicket.Component;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.pageserializer.common.analyze.AnalyzingSerializationListener;
import org.wicketstuff.pageserializer.common.analyze.IObjectLabelizer;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTreeProcessor;
import org.wicketstuff.pageserializer.common.analyze.TreeProcessors;
import org.wicketstuff.pageserializer.common.analyze.report.Level;
import org.wicketstuff.pageserializer.common.analyze.report.RenderTreeProcessor;
import org.wicketstuff.pageserializer.common.analyze.report.SimilarNodeTreeTransformator;
import org.wicketstuff.pageserializer.common.analyze.report.SortedTreeSizeReport;
import org.wicketstuff.pageserializer.common.analyze.report.TreeTransformator;
import org.wicketstuff.pageserializer.common.analyze.report.TypeSizeReport;
import org.wicketstuff.pageserializer.common.analyze.report.d3js.D3DataFileRenderer;
import org.wicketstuff.pageserializer.common.analyze.report.filter.ITreeFilter;
import org.wicketstuff.pageserializer.common.analyze.report.io.DirectoryBasedReportOutput;
import org.wicketstuff.pageserializer.common.analyze.report.io.Keys;
import org.wicketstuff.pageserializer.common.listener.ISerializationListener;
import org.wicketstuff.pageserializer.common.listener.SerializationListeners;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see org.wicketstuff.pageserializer.fast.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
	@Override
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	public void init()
	{
		super.init();

		IObjectLabelizer labelizer = new IObjectLabelizer()
		{

			@Override
			public String labelFor(Object object)
			{
				if (object instanceof Component)
				{
					return ((Component)object).getId();
				}
				return null;
			}
		};

		DirectoryBasedReportOutput reportOutput = new DirectoryBasedReportOutput(
				tempDirectory("reports"));

		ISerializedObjectTreeProcessor treeProcessor = TreeProcessors.listOf(
				new TypeSizeReport(reportOutput.with(Keys.withNameAndFileExtension(
						"TypeSizeReport", "txt"))),
				new SortedTreeSizeReport(reportOutput.with(Keys.withNameAndFileExtension(
						"SortedTreeSizeReport", "txt"))),
				new RenderTreeProcessor(reportOutput.with(Keys.withNameAndFileExtension(
						"d3js-chart", "html")), new D3DataFileRenderer()),
				new SimilarNodeTreeTransformator(new SortedTreeSizeReport(reportOutput.with(Keys
						.withNameAndFileExtension("StrippedSortedTreeSizeReport", "txt")))));
		ITreeFilter filter = new ITreeFilter()
		{
			@Override
			public boolean accept(ISerializedObjectTree source, Level current)
			{
				return source.type() != Class.class;
			}
		};
		ISerializedObjectTreeProcessor cleanedTreeProcessor = new TreeTransformator(treeProcessor,
				TreeTransformator.strip(filter));
		ISerializationListener listener = SerializationListeners
				.listOf(new AnalyzingSerializationListener(labelizer, cleanedTreeProcessor));

		getFrameworkSettings().setSerializer(new FastWicketSerializer().setListener(listener));
	}

	private File tempDirectory(String prefix)
	{
		File baseDir = new File(System.getProperty("java.io.tmpdir"));
		String baseName = prefix + "-" + UUID.randomUUID().toString();

		File tempDir = new File(baseDir, baseName);
		if (tempDir.mkdir())
		{
			return tempDir;
		}

		throw new RuntimeException("Could not create tempdir " + baseName + " in " + baseDir);
	}
}
