/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.springreference;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.wicket.Application;
import org.apache.wicket.ThreadContext;
import org.apache.wicket.mock.MockApplication;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests for {@link AbstractSpringDependencies}.
 * 
 * @author akiraly
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AbstractSpringDependenciesTest
{

	@Autowired
	private ApplicationContext applicationContext;

	static class Deps extends AbstractSpringDependencies
	{
		private static final long serialVersionUID = 1L;

		@Autowired
		transient ClassService classService;

		@Autowired
		@Qualifier("qualified")
		transient PrimaryService qualifiedPrimaryService;

		@Autowired
		transient PrimaryService primaryService;

		@Autowired
		transient AutowireService autowireService;

		@Autowired
		transient ConstructorService constructorService;

		@Autowired
		@Qualifier("qualified")
		transient AService qualifiedSubClassService;

		@Autowired
		transient AService subClassService;

		@Autowired(required = false)
		@Qualifier("not-existing-qualifier")
		transient AService notExistingService;
	}

	private Deps deps;

	@Before
	public void before()
	{
		Application application = new MockApplication();
		ThreadContext.setApplication(application);

		SpringReferenceSupporter.register(application, new SpringReferenceSupporter(
			applicationContext));

		deps = new Deps();
	}

	@After
	public void after()
	{
		ThreadContext.detach();
	}

	@Test
	public void classOnlyTest()
	{
		testService(deps, new IGetter<ClassService>()
		{
			public ClassService get(Deps deps)
			{
				return deps.classService;
			}
		}, 1);
	}

	@Test
	public void qualifierAndClassTest()
	{
		testService(deps, new IGetter<PrimaryService>()
		{
			public PrimaryService get(Deps deps)
			{
				return deps.qualifiedPrimaryService;
			}
		}, 2);
	}

	@Test
	public void primaryTest()
	{
		testService(deps, new IGetter<PrimaryService>()
		{
			public PrimaryService get(Deps deps)
			{
				return deps.primaryService;
			}
		}, 3);
	}

	@Test
	public void autowireTest()
	{
		testService(deps, new IGetter<AutowireService>()
		{
			public AutowireService get(Deps deps)
			{
				return deps.autowireService;
			}
		}, 6);
	}

	@Test
	public void constructorTest()
	{
		testService(deps, new IGetter<ConstructorService>()
		{
			public ConstructorService get(Deps deps)
			{
				return deps.constructorService;
			}
		}, 4);
	}

	@Test
	public void subClass1Test()
	{
		testService(deps, new IGetter<AService>()
		{
			public AService get(Deps deps)
			{
				return deps.qualifiedSubClassService;
			}
		}, 2);
	}

	@Test
	public void subClass2Test()
	{
		testService(deps, new IGetter<AService>()
		{
			public AService get(Deps deps)
			{
				return deps.subClassService;
			}
		}, 3);
	}

	protected <T extends AService> void testService(Deps deps, IGetter<T> getter,
		int expectedTreasure)
	{
		Assert.assertNotNull(deps);

		T service1 = getter.get(deps);
		Assert.assertNotNull(service1);
		Assert.assertEquals(expectedTreasure, service1.doStuff());

		Deps deps2 = writeRead(deps);
		Assert.assertNotNull(deps2);
		Assert.assertNotSame(deps, deps2);

		T service2 = getter.get(deps2);
		Assert.assertSame(service1, service2);

		Deps deps3 = (Deps)deps.clone();
		Assert.assertNotNull(deps3);
		Assert.assertNotSame(deps, deps3);

		T service3 = getter.get(deps3);
		Assert.assertSame(service1, service3);
	}

	/**
	 * Check serialization support.
	 */
	protected Deps writeRead(Deps deps)
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream os = null;
			try
			{
				os = new ObjectOutputStream(baos);
				os.writeObject(deps);
			}
			finally
			{
				if (os != null)
					os.close();
				baos.close();
			}

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream is = null;
			try
			{
				is = new ObjectInputStream(bais);
				return (Deps)is.readObject();
			}
			finally
			{
				if (is != null)
					is.close();
				bais.close();
			}
		}
		catch (ClassNotFoundException e)
		{
			throw new IllegalStateException("(De)Serialization failed.", e);
		}
		catch (IOException e)
		{
			throw new IllegalStateException("(De)Serialization failed.", e);
		}
	}

	private interface IGetter<T extends AService>
	{
		T get(Deps deps);
	}
}
