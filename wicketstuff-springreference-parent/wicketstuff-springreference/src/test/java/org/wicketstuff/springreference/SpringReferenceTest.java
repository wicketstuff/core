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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.wicket.Application;
import org.apache.wicket.ThreadContext;
import org.apache.wicket.mock.MockApplication;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Tests for {@link SpringReference} and {@link SpringReferenceSupporter}.
 *
 * @author akiraly
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class SpringReferenceTest
{

	@Autowired
	private ApplicationContext applicationContext;

	@BeforeEach
	public void before()
	{
		Application application = new MockApplication();
		ThreadContext.setApplication(application);

		SpringReferenceSupporter.register(application, new SpringReferenceSupporter(
			applicationContext));
	}

	@AfterEach
	public void after()
	{
		ThreadContext.detach();
	}

	@Test
	public void classOnlyTest()
	{
		SpringReference<ClassService> reference = SpringReference.of(ClassService.class);

		testReference(reference, 1);
	}

	@Test
	public void nameAndClassTest()
	{
		SpringReference<PrimaryService> reference = SpringReference.of(PrimaryService.class,
			"named");

		testReference(reference, 2);
	}

	@Test
	public void primaryTest()
	{
		SpringReference<PrimaryService> reference = SpringReference.of(PrimaryService.class);

		testReference(reference, 3);
	}

	@Test
	public void autowireTest()
	{
		SpringReference<AutowireService> reference = SpringReference.of(AutowireService.class);

		testReference(reference, 6);
	}

	@Test
	public void constructorTest()
	{
		SpringReference<ConstructorService> reference = SpringReference.of(ConstructorService.class);

		testReference(reference, 4);
	}

	@Test
	public void subClass1Test()
	{
		SpringReference<AService> reference = SpringReference.of(AService.class, "named");

		testReference(reference, 2);
	}

	@Test
	public void subClass2Test()
	{
		SpringReference<AService> reference = SpringReference.of(AService.class);

		testReference(reference, 3);
	}

	@Test
	public void lazyTest()
	{
		SpringReference<AService> reference = SpringReference.of(AService.class,
			"not-existing-bean-id");

		try
		{
			testReference(reference, -1);
			// no exception?
			throw new IllegalStateException("Not exsisting spring bean found.");
		}
		catch (NoSuchBeanDefinitionException e)
		{
			// expected, all ok.
		}
	}

	@Test
	public void cloneEqualityTest()
	{
		SpringReference<ConstructorService> refClazzOnly = SpringReference.of(ConstructorService.class);
		SpringReference<ConstructorService> refNamed = SpringReference.of(ConstructorService.class,
			"constructor");

		SpringReference<ConstructorService> refClazzOnly2 = refClazzOnly.clone();
		SpringReference<ConstructorService> refNamed2 = refNamed.clone();

		assertNotSame(refClazzOnly, refClazzOnly2);
		assertNotSame(refNamed, refNamed2);

		refClazzOnly.get();

		assertThat(refClazzOnly, CoreMatchers.not(CoreMatchers.equalTo(refNamed)));

		refNamed.get();

		assertEquals(refClazzOnly.hashCode(), refClazzOnly2.hashCode());
		assertEquals(refNamed.hashCode(), refNamed2.hashCode());

		assertEquals(refClazzOnly, refClazzOnly2);
		assertEquals(refNamed, refNamed2);
	}

	protected <T extends AService> void testReference(SpringReference<T> reference,
		int expectedTreasure)
	{
		assertNotNull(reference);
		T service = reference.get();
		assertNotNull(service);

		reference = writeRead(reference);

		assertNotNull(reference);
		service = reference.get();
		assertNotNull(service);

		assertEquals(expectedTreasure, service.doStuff());
	}

	/**
	 * Check serialization support.
	 */
	protected <T> SpringReference<T> writeRead(SpringReference<T> reference)
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream os = null;
			try
			{
				os = new ObjectOutputStream(baos);
				os.writeObject(reference);
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
				@SuppressWarnings("unchecked")
				SpringReference<T> result = (SpringReference<T>)is.readObject();
				assertNotSame(reference, result);
				return result;
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
}
