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

import java.lang.ref.Reference;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.io.IClusterable;

/**
 * <p>
 * This class together with {@link SpringReferenceSupporter} is an alternative to wicket-spring's
 * <code>&#64;SpringBean</code> and <code>SpringComponentInjector</code> to integrate spring with
 * wicket in a web application.
 * </p>
 * <p>
 * Inspired by the concept of {@link Reference} classes and by the implementation of
 * <code>&#64;SpringBean</code> this class was made to overcome the shortcomings of dynamic proxying
 * classes (need for no-private no-arg constructor, final methods not working in some cases, method
 * annotations lost). If you used <code>&#64;SpringBean</code> and ever saw mysterious stack traces
 * like:
 * 
 * <pre>
 * <code>
 * Caused by: java.lang.IllegalArgumentException: Protected method: fooMethod()V
 * 	at net.sf.cglib.proxy.MethodProxy.invoke(MethodProxy.java:196)
 * 	at org.apache.wicket.proxy.LazyInitProxyFactory$CGLibInterceptor.intercept(LazyInitProxyFactory.java:319)
 * </code>
 * </pre>
 * 
 * or
 * 
 * <pre>
 * <code>
 * Caused by: java.lang.IllegalArgumentException: No visible constructors in class FooClass
 * 	at net.sf.cglib.proxy.Enhancer.filterConstructors(Enhancer.java:531)
 * 	at net.sf.cglib.proxy.Enhancer.generateClass(Enhancer.java:448)
 * 	at net.sf.cglib.core.DefaultGeneratorStrategy.generate(DefaultGeneratorStrategy.java:25)
 * 	at net.sf.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:216)
 * 	at net.sf.cglib.proxy.Enhancer.createHelper(Enhancer.java:377)
 * 	at net.sf.cglib.proxy.Enhancer.create(Enhancer.java:285)
 * </code>
 * </pre>
 * 
 * This class is the solution.
 * </p>
 * <p>
 * Because this class does not need an <code>IComponentInstantiationListener</code>, does not use
 * dynamic proxies and looks up spring beans lazily, it should be slightly faster. It also supports
 * serializing as <code>&#64;SpringBean</code> does.
 * </p>
 * <p>
 * <b>Instances of this class use the {@link SpringReferenceSupporter} for bean lookup, so it must
 * be registered in your wicket {@link WebApplication} init() method (
 * <code>SpringReferenceSupporter.register(this);</code>). Otherwise you will get
 * {@link NullPointerException} when calling {@link #get()}. See {@link SpringReferenceSupporter}
 * for more information.</b>
 * </p>
 * <p>
 * Declaration (you can declare it in your wicket components, models or your custom classes as
 * well):
 * 
 * <pre>
 * <code>
 * private final SpringReference&lt;AuthenticationManager&gt; authenticationManagerRef = SpringReference.of(AuthenticationManager.class, "authenticationManager");
 * private final SpringReference&lt;AbstractRememberMeServices&gt; rememberMeServicesRef = SpringReference.of(AbstractRememberMeServices.class);
 * </code>
 * </pre>
 * 
 * Access:
 * 
 * <pre>
 * <code>
 * authenticationManagerRef.get().authenticate(token);
 * AbstractRememberMeServices rememberMeServices = rememberMeServicesRef.get();
 * </code>
 * </pre>
 * 
 * </p>
 * 
 * @param <T>
 *            type of the wrapped spring bean
 * 
 * @author akiraly
 */
public class SpringReference<T> extends AbstractSpringReference<T> implements IClusterable
{
	private static final long serialVersionUID = -1798985501215878818L;

	/**
	 * Constructor.
	 * 
	 * @param clazz
	 *            class of the wrapped spring bean, not null
	 * @param name
	 *            beanName of the wrapped spring bean, can be null
	 */
	public SpringReference(Class<T> clazz, String name)
	{
		super(clazz, name);
	}

	/**
	 * Creator method for easy usage. Use this if you only want to specify the type of the spring
	 * bean.
	 * 
	 * @param <T>
	 *            type of the wrapped spring bean
	 * @param clazz
	 *            class of the wrapped spring bean, not null
	 * @return new SpringReference instance wrapping the spring bean
	 */
	public static <T> SpringReference<T> of(Class<T> clazz)
	{
		return of(clazz, null);
	}

	/**
	 * Creator method for easy usage. Use this if need to specify a spring bean by name too.
	 * 
	 * @param <T>
	 *            type of the wrapped spring bean
	 * @param clazz
	 *            class of the wrapped spring bean, not null
	 * @param name
	 *            name of the wrapped spring bean, can be null
	 * @return new SpringReference instance wrapping the spring bean
	 */
	public static <T> SpringReference<T> of(Class<T> clazz, String name)
	{
		return new SpringReference<T>(clazz, name);
	}

	@Override
	protected SpringReferenceSupporter getSupporter()
	{
		return SpringReferenceSupporter.get();
	}

	@Override
	public SpringReference<T> clone()
	{
		return (SpringReference<T>)super.clone();
	}
}
