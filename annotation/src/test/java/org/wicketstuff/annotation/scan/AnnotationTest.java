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

import org.apache.wicket.Page;
import org.apache.wicket.core.request.mapper.HomePageMapper;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.request.component.IRequestablePage;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Doug Donohoe
 * @author Ronald Tetsuo Miura
 */
public class AnnotationTest extends Assert
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

	@MountPath(value = "/")
	private static class HomePathsPage extends Page
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

	@Before
	public void before() throws Exception
	{
		testScanner = new TestAnnotatedMountScanner();
	}

    @Test
	public void defaultMountPath()
	{
		AnnotatedMountList list = testScanner.scanClass(DefaultMountPathPage.class);
		assertEquals(1, list.size());
		assertEquals("DefaultMountPathPage", ((TestMountedMapper)list.get(0)).mountPath);
	}

    @Test
	public void explicitMountPath()
	{
		AnnotatedMountList list = testScanner.scanClass(ExplicitMountPathPage.class);
		assertEquals(1, list.size());
		assertEquals("mountPath", ((TestMountedMapper)list.get(0)).mountPath);
	}

    @Test
	public void alternativePaths()
	{
        AnnotatedMountList list = testScanner.scanClass(AlternativePathsPage.class);
		assertEquals(3, list.size());
		assertEquals("alt1", ((TestMountedMapper)list.get(0)).mountPath);
		assertEquals("alt2", ((TestMountedMapper)list.get(1)).mountPath);
		assertEquals("primary", ((TestMountedMapper)list.get(2)).mountPath);
	}

    @Test
	public void packageScan()
	{
		AnnotatedMountList list = testScanner.scanPackage(AnnotationTest.class.getPackage()
			.getName());
		assertThat("Should have gotten 6 items", list.size(), is(6));
	}

    @Test
	public void customDefaultPath()
	{
		AnnotatedMountList list = customScanner.scanClass(DefaultMountPathPage.class);
		assertEquals(1, list.size());
		assertEquals("defaultmountpathpage", ((TestMountedMapper)list.get(0)).mountPath);
	}

	@Test
	public void homePath()
	{
		AnnotatedMountScanner scanner = new AnnotatedMountScanner();
		AnnotatedMountList list = scanner.scanClass(HomePathsPage.class);
		assertThat(list.size(), is(1));
		assertThat(list.get(0), CoreMatchers.instanceOf(HomePageMapper.class));
	}

}
