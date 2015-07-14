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
package org.wicketstuff.annotation.packages;

import static org.hamcrest.CoreMatchers.is;

import org.apache.wicket.Page;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.request.component.IRequestablePage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.annotation.mount.MountPath;
import org.wicketstuff.annotation.scan.AnnotatedMountList;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;
import org.wicketstuff.annotation.scan.AnnotationTest;

/**
 * @author Doug Donohoe
 * @author Ronald Tetsuo Miura
 */
public class AnnotationPackagesTest extends Assert
{
	@MountPath(value = "baseMountPath")
	private static class BaseMountPathPage extends Page
	{
		private static final long serialVersionUID = 1L;
	}
	
	@MountPath(value = "extendedBaseMountPath")
	private static class ExtendedBaseMountPathPage extends BaseMountPathPage
	{
		private static final long serialVersionUID = 1L;
	}

	private class TestMountedMapper extends MountedMapper
	{

		public TestMountedMapper(String mountPath, Class<? extends IRequestablePage> pageClass)
		{
			super(mountPath, pageClass);
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

	private TestAnnotatedMountScanner testScanner = new TestAnnotatedMountScanner();

	@Before
	public void before() throws Exception
	{
		testScanner = new TestAnnotatedMountScanner();
	}

	@Test
	public void packageScanHierarchical()
	{
		AnnotatedMountList list = testScanner.scanPackages(AnnotationPackagesTest.class.getPackage()
				.getName());
		assertThat("Should have gotten 2 items", list.size(), is(2));
	}
	
	@Test
	public void testOnlyScanAndPackages()
	{
		AnnotatedMountList list = testScanner.scanPackages(AnnotationPackagesTest.class.getPackage()
				// Note that has a dot that forces to use this package.
			.getName(),AnnotationTest.class.getPackage().getName() + "."); 
		assertThat("Should have gotten 8 items", list.size(), is(8));
	}
	
	@Test
	public void testScanAndScanPackages()
	{
		AnnotatedMountList list = testScanner.scanPackages(AnnotationPackagesTest.class.getPackage()
				// Without a dot so scan 'scan' and 'scanpackages' packages.
			.getName(),AnnotationTest.class.getPackage().getName()); 
		assertThat("Should have gotten 9 items", list.size(), is(9));
	}
}
