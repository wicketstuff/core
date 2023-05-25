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

import java.io.File;
import java.util.UUID;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.lang.Bytes;
import org.wicketstuff.pageserializer.common.analyze.AnalyzingSerializationListener;
import org.wicketstuff.pageserializer.common.analyze.ComponentIdAsLabel;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTreeProcessor;
import org.wicketstuff.pageserializer.common.analyze.NativeTypesAsLabel;
import org.wicketstuff.pageserializer.common.analyze.TreeProcessors;
import org.wicketstuff.pageserializer.common.analyze.report.RenderTreeProcessor;
import org.wicketstuff.pageserializer.common.analyze.report.SimilarNodeTreeTransformator;
import org.wicketstuff.pageserializer.common.analyze.report.SortedTreeSizeReport;
import org.wicketstuff.pageserializer.common.analyze.report.TreeTransformator;
import org.wicketstuff.pageserializer.common.analyze.report.TypeSizeReport;
import org.wicketstuff.pageserializer.common.analyze.report.d3js.D3DataFileRenderer;
import org.wicketstuff.pageserializer.common.analyze.report.filter.TypeFilter;
import org.wicketstuff.pageserializer.common.analyze.report.io.DirectoryBasedReportOutput;
import org.wicketstuff.pageserializer.common.analyze.report.io.Keys;
import org.wicketstuff.pageserializer.common.listener.ISerializationListener;
import org.wicketstuff.pageserializer.common.listener.SerializationListeners;
import org.wicketstuff.pageserializer.kryo2.inspecting.InspectingKryoSerializer;
import org.wicketstuff.pageserializer.kryo2.inspecting.validation.DefaultJavaSerializationValidator;


public class ApplicationWithDirectoryBasedOutput extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return SamplePage.class;
	}

	@Override
	protected void init() {
		super.init();

			File outputDirectory = tempDirectory("kryo2-reports");
			System.out.println("Reports in: "+outputDirectory);

			DirectoryBasedReportOutput output = new DirectoryBasedReportOutput(outputDirectory);

			// output of report of type sizes, sorted tree report (by size), aggregated tree
			ISerializedObjectTreeProcessor typeAndSortedTreeAndCollapsedSortedTreeProcessors = TreeProcessors.listOf(
				new TypeSizeReport(output.with(Keys.withNameAndFileExtension("typesize", "txt"))),
				new SortedTreeSizeReport(output.with(Keys.withNameAndFileExtension("treesize", "txt"))),
				new SimilarNodeTreeTransformator(new SortedTreeSizeReport(output.with(Keys.withNameAndFileExtension("sorted-treesize", "txt")))),
				new RenderTreeProcessor(output.with(Keys.withNameAndFileExtension("d3js-chart", "html")),new D3DataFileRenderer()));

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

	private File tempDirectory(String prefix) {
		File baseDir = new File(System.getProperty("java.io.tmpdir"));
		String baseName = prefix + "-" + UUID.randomUUID().toString();

		File tempDir = new File(baseDir, baseName);
		if (tempDir.mkdir()) {
			return tempDir;
		}

		throw new RuntimeException("Could not create tempdir " + baseName + " in " + baseDir);
	}
}
