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
package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.wicketstuff.pageserializer.kryo2.inspecting.InspectingKryoSerializer;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.AnalyzingSerializationListener;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ComponentIdAsLabel;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ImmutableTree;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ObjectId;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.TreeProcessors;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.filter.TypeFilter;
import org.wicketstuff.pageserializer.kryo2.pages.ListViewPage;

public class SimilarNodeTreeTransformatorTest
{	
	@Test
	public void dontChangeAnything()
	{
		ImmutableTree source = new ImmutableTree(new ObjectId(1), Root.class, "root", 10,
			noChildren());

		ISerializedObjectTree result = SimilarNodeTreeTransformator.transformTree(source);

		ImmutableTree expected = new ImmutableTree(new ObjectId(1), Root.class, "root", 10,
			noChildren());

		Trees.assertEqualsTree(expected, result);
	}

	@Test
	public void compressSubNodes()
	{
		ImmutableTree source = new ImmutableTree(new ObjectId(1), Root.class, "root", 10,
			sameChildren5Times());

		ISerializedObjectTree result = SimilarNodeTreeTransformator.transformTree(source);

		List<ISerializedObjectTree> oneSubNode = new ArrayList<ISerializedObjectTree>();
		List<ISerializedObjectTree> threeSubNodes = new ArrayList<ISerializedObjectTree>();
		threeSubNodes.add(new ImmutableTree(null, A.class, "a", 15, noChildren()));
		threeSubNodes.add(new ImmutableTree(null, B.class, "b", 20, noChildren()));
		threeSubNodes.add(new ImmutableTree(null, C.class, "c", 25, noChildren()));
		oneSubNode.add(new ImmutableTree(null, Has3.class, "has3-0|has3-1|has3-2|has3-3|has3-4",
			50, threeSubNodes));
		ImmutableTree expected = new ImmutableTree(new ObjectId(1), Root.class, "root", 10,
			oneSubNode);

		Trees.assertEqualsTree(expected, result);
	}

	@Test
	public void compressListView()
	{
		WicketTester tester = new WicketTester();
		tester.startPage(ListViewPage.class, new PageParameters());
		Assert.assertNotNull(tester.getLastRenderedPage());

		IReportOutput reportOutput=new LoggerReportOutput();

		InspectingKryoSerializer kryo = new InspectingKryoSerializer(Bytes.megabytes(1),
			new AnalyzingSerializationListener(new ComponentIdAsLabel(), TreeProcessors.listOf(
				new TreeSizeReport(reportOutput), new TreeTransformator(new SimilarNodeTreeTransformator(
					new TreeSizeReport(reportOutput)), TreeTransformator.strip(new TypeFilter(Class.class))))));
		kryo.serialize(tester.getLastRenderedPage());
	}
	
	@Test
	public void testCompression() throws IOException {
		IReportOutput reportOutput=new LoggerReportOutput();

		ISerializedObjectTree source = Trees.fromResource(getClass(), "sample1");
		Assert.assertEquals("asSample", source.label());
		ISerializedObjectTree result = SimilarNodeTreeTransformator.transformTree(source);
		new TreeSizeReport(reportOutput).process(result);
		ISerializedObjectTree match = Trees.fromResource(getClass(), "sample1-match");
		new TreeSizeReport(reportOutput).process(match);

		Trees.assertEqualsTree(match, result);
	}
	
	@Test
	public void secondNodeHasMoreChilds() throws IOException {
		IReportOutput reportOutput=new LoggerReportOutput();
		
		ISerializedObjectTree source = Trees.fromResource(getClass(), "nodeChildSize");
		Assert.assertEquals("asSample", source.label());
		new TreeSizeReport(reportOutput).process(source);
		ISerializedObjectTree result = SimilarNodeTreeTransformator.transformTree(source);
		new TreeSizeReport(reportOutput).process(result);
		ISerializedObjectTree match = Trees.fromResource(getClass(), "nodeChildSize-match");
		new TreeSizeReport(reportOutput).process(match);
		
		Trees.assertEqualsTree(match, result);
	}
	
	@Test
	public void testTreeType() throws IOException {
		ISerializedObjectTree sourceA = Trees.fromResource(getClass(), "treeType-A");
		ISerializedObjectTree sourceB = Trees.fromResource(getClass(), "treeType-B");
		TreeType treeTypeA=new TreeType(sourceA);
		TreeType treeTypeB=new TreeType(sourceB);
		Assert.assertEquals(treeTypeA, treeTypeB);
	}

	private List<? extends ISerializedObjectTree> sameChildren5Times()
	{
		List<ISerializedObjectTree> ret = new ArrayList<ISerializedObjectTree>();
		for (int i = 0; i < 5; i++)
		{
			ret.add(new ImmutableTree(new ObjectId(10 + i), Has3.class, "has3-" + i, 10,
				differentChildren(i)));
		}
		return ret;
	}

	private List<? extends ISerializedObjectTree> differentChildren(int idx)
	{
		List<ISerializedObjectTree> ret = new ArrayList<ISerializedObjectTree>();
		ret.add(new ImmutableTree(new ObjectId(100 + idx + 1), A.class, "a", 3, noChildren()));
		ret.add(new ImmutableTree(new ObjectId(100 + idx + 2), B.class, "b", 4, noChildren()));
		ret.add(new ImmutableTree(new ObjectId(100 + idx + 3), C.class, "c", 5, noChildren()));
		return ret;
	}

	private static List<? extends ISerializedObjectTree> noChildren()
	{
		return new ArrayList<ISerializedObjectTree>();
	}

	static class Root
	{

	}

	static class Has3
	{

	}

	static class A
	{

	}

	static class B
	{

	}

	static class C
	{

	}
	
	static class D
	{

	}
}
