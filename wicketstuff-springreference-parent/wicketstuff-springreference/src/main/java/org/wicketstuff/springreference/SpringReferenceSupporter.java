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

import jakarta.servlet.ServletContext;

import org.apache.wicket.Application;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.lang.Args;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <p>
 * Used by {@link SpringReference} and {@link AbstractSpringDependencies} to do the actual spring
 * bean lookups. This must be registered in the init() method of your wicket {@link Application} or
 * {@link WebApplication} otherwise {@link SpringReference} and {@link AbstractSpringDependencies}
 * will not work.
 * </p>
 * <p>
 * Example:
 *
 * <pre>
 * <code>
 *  public class App extends WebApplication {
 * 	&#64;Override
 * 	public Class&lt;? extends Page&gt; getHomePage() {
 * 		return HomePage.class;
 * 	}
 *
 * 	&#64;Override
 * 	protected void init() {
 * 		super.init();
 *
 * 		getMarkupSettings().setDefaultMarkupEncoding(CharEncoding.UTF_8);
 *
 * 		<b>SpringReferenceSupporter.register(this);</b> // &lt;--
 * 	}
 * }
 * </code>
 * </pre>
 *
 * </p>
 *
 * @author akiraly
 *
 */
public class SpringReferenceSupporter extends AbstractSpringReferenceSupporter
{

	private static MetaDataKey<SpringReferenceSupporter> LOCATOR_KEY = new MetaDataKey<SpringReferenceSupporter>()
	{
		private static final long serialVersionUID = 5075847072788088007L;
	};

	private final ApplicationContext applicationContext;

	/**
	 * Constructor.
	 *
	 * @param applicationContext
	 *            where the spring bean will be searched for, not null
	 */
	public SpringReferenceSupporter(ApplicationContext applicationContext)
	{
		Args.notNull(applicationContext, "applicationContext");

		this.applicationContext = applicationContext;
	}

	/**
	 * Creates and registers an instance of this class with the wicket web application. Uses
	 * {@link WebApplicationContextUtils#getRequiredWebApplicationContext(ServletContext)} to get
	 * spring web application context. If more fine grained registration is needed use
	 * {@link #register(Application, SpringReferenceSupporter)}. If you want to use
	 * {@link SpringReference} you have to use one of the register methods in your wicket
	 * applications init().
	 *
	 * @param application
	 *            wicket web application, not null
	 */
	public static void register(WebApplication application)
	{
		Args.notNull(application, "application");

		ServletContext servletContext = application.getServletContext();
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		register(application, new SpringReferenceSupporter(applicationContext));
	}

	/**
	 * Registers the passed in supporter with the wicket application. This is the more sophisticated
	 * variant of registration. Most users could go with {@link #register(WebApplication)}.
	 *
	 * @param application
	 *            wicket application, not null
	 * @param supporter
	 *            spring reference supporter. Null value means removal.
	 */
	public static void register(Application application, SpringReferenceSupporter supporter)
	{
		Args.notNull(application, "application");

		application.setMetaData(LOCATOR_KEY, supporter);
	}

	/**
	 * @return instance registered with the current threads wicket application
	 */
	protected static SpringReferenceSupporter get()
	{
		return Application.get().getMetaData(LOCATOR_KEY);
	}

	@Override
	protected ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}
}
