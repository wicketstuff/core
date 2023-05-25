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
package org.wicketstuff.pageserializer.kryo2.examples;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.lang.Bytes;
import org.wicketstuff.pageserializer.common.analyze.AnalyzingSerializationListener;
import org.wicketstuff.pageserializer.common.analyze.ComponentIdAsLabel;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTreeProcessor;
import org.wicketstuff.pageserializer.common.analyze.NativeTypesAsLabel;
import org.wicketstuff.pageserializer.common.analyze.TreeProcessors;
import org.wicketstuff.pageserializer.common.analyze.report.IReportOutput;
import org.wicketstuff.pageserializer.common.analyze.report.LoggerReportOutput;
import org.wicketstuff.pageserializer.common.analyze.report.SimilarNodeTreeTransformator;
import org.wicketstuff.pageserializer.common.analyze.report.SortedTreeSizeReport;
import org.wicketstuff.pageserializer.common.analyze.report.TreeTransformator;
import org.wicketstuff.pageserializer.common.analyze.report.TypeSizeReport;
import org.wicketstuff.pageserializer.common.analyze.report.filter.TypeFilter;
import org.wicketstuff.pageserializer.common.listener.ISerializationListener;
import org.wicketstuff.pageserializer.common.listener.SerializationListeners;
import org.wicketstuff.pageserializer.kryo2.inspecting.InspectingKryoSerializer;
import org.wicketstuff.pageserializer.kryo2.inspecting.validation.DefaultJavaSerializationValidator;

/**
 * Application object for your web application. If you want to run this application without
 * deploying, run the Start class.
 *
 * @see org.wicketstuff.pageserializer.kryo.mycompany.Start#main(String[])
 */
public class Application extends WebApplication
{
	@Override
	public Class<SamplePage> getHomePage()
	{
		return SamplePage.class;
	}

	@Override
	public void init()
	{
		super.init();

		IReportOutput reportOutput=new LoggerReportOutput();

		// output of report of type sizes, sorted tree report (by size), aggregated tree
		ISerializedObjectTreeProcessor typeAndSortedTreeAndCollapsedSortedTreeProcessors = TreeProcessors.listOf(
			new TypeSizeReport(reportOutput), new SortedTreeSizeReport(reportOutput), new SimilarNodeTreeTransformator(
				new SortedTreeSizeReport(reportOutput)));

		// strips class object writes from tree
		TreeTransformator treeProcessors = new TreeTransformator(
			typeAndSortedTreeAndCollapsedSortedTreeProcessors,
			TreeTransformator.strip(new TypeFilter(Class.class)));

		// serialization listener notified on every written object
		ISerializationListener serializationListener = SerializationListeners.listOf(
			new DefaultJavaSerializationValidator(), new AnalyzingSerializationListener(
				new NativeTypesAsLabel(new ComponentIdAsLabel()), treeProcessors));


		InspectingKryoSerializer serializer = new InspectingKryoSerializer(Bytes.megabytes(1L),
			serializationListener);

		getFrameworkSettings().setSerializer(serializer);
	}
}
