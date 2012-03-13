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
package org.wicketstuff.annotation.scan;

import junit.framework.TestCase;

import org.apache.wicket.Page;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Doug Donohoe
 * @author Ronald Tetsuo Miura
 */
public class AnnotationTest extends TestCase
{
	@MountPath
	private static class DefaultMountPathPage extends Page
	{
		private static final long serialVersionUID = 1L;
	}

	@MountPath(value = "mountPath")
	private static class ExplicitMountPathPage extends Page
	{
		private static final long serialVersionUID = 1L;
	}

	@MountPath(value = "primary", alt = { "alt1", "alt2" })
	private static class AlternativePathsPage extends Page
	{
		private static final long serialVersionUID = 1L;
	}

	private class TestMountedMapper extends MountedMapper
	{
		public final String mountPath;

		public TestMountedMapper(String mountPath, Class<? extends IRequestablePage> pageClass)
		{
			super(mountPath, pageClass);
			this.mountPath = mountPath;
		}
	}

	private class TestAnnotatedMountScanner extends AnnotatedMountScanner
	{
		@Override
		public IRequestMapper getRequestMapper(String mountPath,
			Class<? extends IRequestablePage> pageClass)
		{
			return new TestMountedMapper(mountPath, pageClass);
		}
	}

	private class CustomAnnotatedMountScanner extends TestAnnotatedMountScanner
	{
		@Override
		public String getDefaultMountPath(Class<? extends IRequestablePage> pageClass)
		{
			return pageClass.getSimpleName().toLowerCase();
		}
	}

	private TestAnnotatedMountScanner testScanner = new TestAnnotatedMountScanner();
	private final CustomAnnotatedMountScanner customScanner = new CustomAnnotatedMountScanner();

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		testScanner = new TestAnnotatedMountScanner();
	}

	public void testDefaultMountPath()
	{
		AnnotatedMountList list = testScanner.scanClass(DefaultMountPathPage.class);
		assertEquals(1, list.size());
		assertEquals("DefaultMountPathPage", ((TestMountedMapper)list.get(0)).mountPath);
	}

	public void testExplicitMountPath()
	{
		AnnotatedMountList list = testScanner.scanClass(ExplicitMountPathPage.class);
		assertEquals(1, list.size());
		assertEquals("mountPath", ((TestMountedMapper)list.get(0)).mountPath);
	}

	public void testAlternativePaths()
	{
		AnnotatedMountList list = testScanner.scanClass(ExplicitMountPathPage.class);
		list = testScanner.scanClass(AlternativePathsPage.class);
		assertEquals(3, list.size());
		assertEquals("primary", ((TestMountedMapper)list.get(0)).mountPath);
		assertEquals("alt1", ((TestMountedMapper)list.get(1)).mountPath);
		assertEquals("alt2", ((TestMountedMapper)list.get(2)).mountPath);
	}

	public void testPackageScan()
	{
		final int expectedCount = 5;
		AnnotatedMountList list = testScanner.scanPackage(AnnotationTest.class.getPackage()
			.getName());
		assertEquals("Should have gotten " + expectedCount + " items", expectedCount, list.size());
	}

	public void testCustomDefaultPath()
	{
		AnnotatedMountList list = customScanner.scanClass(DefaultMountPathPage.class);
		assertEquals(1, list.size());
		assertEquals("defaultmountpathpage", ((TestMountedMapper)list.get(0)).mountPath);
	}

}