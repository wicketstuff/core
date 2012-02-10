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

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * <p>
 * Abstract base class for spring bean finding. Used by {@link AbstractSpringReference}.
 * </p>
 * <p>
 * This class is intended to be used in a singleton/service way because it maintains caches to speed
 * up lookups. Subclasses must implement the getApplicationContext() method used to locate the
 * spring context. This class does not depend on wicket or spring-web. So in theory subclasses can
 * be used in non-wicket, non-web spring applications too.
 * </p>
 * 
 * @author akiraly
 */
public abstract class AbstractSpringReferenceSupporter
{
	private final Map<String, WeakReference<?>> singletonCache = new ConcurrentHashMap<String, WeakReference<?>>();

	private final Map<String, String> beanNameCache = new ConcurrentHashMap<String, String>();

	/**
	 * Looks up the spring bean from a spring {@link ApplicationContext}. The bean is set in the
	 * reference. If the name was not given for the reference this method will fill that too. Throws
	 * a {@link RuntimeException} if the bean could not be found.
	 * 
	 * @param <T>
	 *            type of the wrapped spring bean
	 * @param ref
	 *            reference where the instance will be set
	 * @return loaded spring bean or throws a {@link RuntimeException} if loading failed.
	 */
	@SuppressWarnings("unchecked")
	public <T> T findAndSetInstance(AbstractSpringReference<T> ref)
	{
		T instance;
		WeakReference<T> instanceRef;
		String clazzName = ref.getClazz().getName();

		// check cache with clazz name if clazzBasedOnlyLookup
		if (ref.isClazzBasedOnlyLookup())
		{
			instanceRef = (WeakReference<T>)singletonCache.get(clazzName);

			if (instanceRef != null && (instance = instanceRef.get()) != null)
			{
				ref.setInstanceRef(instanceRef);
				return instance;
			}
		}

		// find out bean name if not given
		String name = ref.getName();
		if (name == null)
		{
			name = findBeanName(ref);
			ref.setName(name);
		}

		// check cache with clazz and bean name
		String singletonCacheKey = clazzName + " " + name;
		instanceRef = (WeakReference<T>)singletonCache.get(singletonCacheKey);
		boolean singleton = true;
		if (instanceRef == null || (instance = instanceRef.get()) == null)
		{
			ApplicationContext applicationContext = getApplicationContext();
			instance = applicationContext.getBean(name, ref.getClazz());
			singleton = applicationContext.isSingleton(name);
			// if singleton put into cache with clazz and bean name key
			if (singleton)
			{
				instanceRef = new WeakReference<T>(instance);
				singletonCache.put(singletonCacheKey, instanceRef);
			}
		}

		// if singleton and originally name was not given (eg. clazz based only
		// lookup) put into cache with clazz name
		if (ref.isClazzBasedOnlyLookup() && singleton)
			singletonCache.put(clazzName, instanceRef);

		ref.setInstanceRef(instanceRef);

		return instance;
	}

	/**
	 * Finds out the exact name for a spring bean. Used when only the class was given by the user.
	 * If there is not exactly one candidate (even after taking primary beans into account) an
	 * exception is thrown.
	 * 
	 * @param <T>
	 *            type of the wrapped spring bean
	 * @param ref
	 *            reference to find name for
	 * @return name of the spring bean or {@link IllegalStateException} if there is not exactly one
	 *         candidate.
	 */
	protected <T> String findBeanName(AbstractSpringReference<T> ref)
	{
		Class<?> clazz = ref.getClazz();
		String clazzName = clazz.getName();

		// check name cache
		String name = beanNameCache.get(clazzName);
		if (name != null)
			return name;

		ApplicationContext applicationContext = getApplicationContext();

		// get candidates
		String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext,
			clazz);

		// too many/too few candidates, lets check for primary beans
		if (names.length != 1)
		{
			if (applicationContext instanceof ConfigurableApplicationContext)
			{
				ConfigurableListableBeanFactory fact = ((ConfigurableApplicationContext)applicationContext).getBeanFactory();
				List<String> autowireCandidates = new LinkedList<String>();
				List<String> primaries = new LinkedList<String>();

				for (String n : names)
				{
					BeanDefinition beanDefinition = getBeanDefinition(fact, n);
					if (beanDefinition != null && beanDefinition.isAutowireCandidate())
					{
						autowireCandidates.add(n);
						if (beanDefinition.isPrimary())
							primaries.add(n);
					}
				}

				if (!primaries.isEmpty())
					names = primaries.toArray(new String[primaries.size()]);
				else if (!autowireCandidates.isEmpty())
					names = autowireCandidates.toArray(new String[autowireCandidates.size()]);
			}

			// still too many/too few candidates
			if (names.length != 1)
				throw new IllegalStateException(
					"Zero or more than one spring bean candidates. Type: " + clazz +
						", candidates: " + Arrays.toString(names));
		}

		name = names[0];
		beanNameCache.put(clazzName, name);

		return name;
	}

	/**
	 * Tries to get the {@link BeanDefinition} of a spring bean.
	 * 
	 * @param fact
	 *            spring bean factory
	 * @param name
	 *            spring bean name to find definition for
	 * @return bean definition, can be null
	 */
	protected BeanDefinition getBeanDefinition(ConfigurableListableBeanFactory fact, String name)
	{
		if (fact.containsBeanDefinition(name))
			return fact.getBeanDefinition(name);

		BeanFactory parent = fact.getParentBeanFactory();
		if (parent instanceof ConfigurableListableBeanFactory)
			return getBeanDefinition((ConfigurableListableBeanFactory)parent, name);

		return null;
	}

	/**
	 * @return context where the spring bean is searched for, not null
	 */
	protected abstract ApplicationContext getApplicationContext();

	/**
	 * Clears the internal cache.
	 */
	public void clearCache()
	{
		beanNameCache.clear();
		singletonCache.clear();
	}
}
