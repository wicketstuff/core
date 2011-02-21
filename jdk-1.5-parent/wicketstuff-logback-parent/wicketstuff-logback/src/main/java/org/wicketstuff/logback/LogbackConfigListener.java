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
package org.wicketstuff.logback;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.OptionHelper;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * <p>
 * {@link ServletContextListener} that can be used in web applications to define
 * the location of the logback configuration.
 * </p>
 * <p>
 * Should be the first listener to configure logback before using it. Location
 * is defined in the <code>logbackConfigLocation</code> context param. Snippet
 * from web.xml:
 * 
 * <pre>
 * <code>
 * 	&lt;listener>
 * 		&lt;listener-class>org.wicketstuff.logback.LogbackConfigListener&lt;/listener-class>
 * 	&lt;/listener>
 * 
 * 	&lt;context-param>
 * 		&lt;param-name>logbackConfigLocation&lt;/param-name>
 * 		&lt;param-value>/WEB-INF/log-sc.xml&lt;/param-value>
 * 	&lt;/context-param>
 * </code>
 * </pre>
 * 
 * Placeholders (ex: ${user.home}) in <code>logbackConfigLocation</code> are
 * supported. Location examples:<br />
 * <code>/WEB-INF/log-sc.xml</code> (starts with '/') -> loaded from servlet
 * context<br />
 * <code>classpath:foo/log-cp.xml</code> (starts with "classpath:") -> loaded
 * from classpath<br />
 * <code>file:/D:/log-absfile.xml</code> (is a valid url) -> loaded as url<br />
 * <code>D:/log-absfile.xml</code> (is an absolute file path) -> loaded as
 * absolute file<br />
 * <code>log-relfile.xml</code> (is a relative file path) -> loaded as file
 * relative to the servlet container working directory
 * </p>
 * <p>
 * This class does not depend on wicket so it can be used in non-wicket based
 * logback using web applications too.
 * </p>
 * 
 * @author akiraly
 */
public class LogbackConfigListener implements ServletContextListener {

	/**
	 * Context param name.
	 */
	public static final String CONFIG_LOCATION_PARAM = "logbackConfigLocation";

	/**
	 * Prefix for classpath urls.
	 */
	public static final String LOCATION_PREFIX_CLASSPATH = "classpath:";

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		ILoggerFactory ilc = LoggerFactory.getILoggerFactory();

		if (!(ilc instanceof LoggerContext)) {
			sc.log("Can not configure logback. " + LoggerFactory.class
					+ " is using " + ilc + " which is not an instance of "
					+ LoggerContext.class);
			return;
		}

		LoggerContext lc = (LoggerContext) ilc;

		String location = sc.getInitParameter(CONFIG_LOCATION_PARAM);

		if (location != null)
			location = OptionHelper.substVars(location, lc);

		if (location == null) {
			sc.log("Can not configure logback. Location is null."
					+ " Maybe context param \"" + CONFIG_LOCATION_PARAM
					+ "\" is not set or is not correct.");
			return;
		}

		URL url = toUrl(sc, location);

		if (url == null) {
			sc.log("Can not configure logback. Could not find logback"
					+ " config neither as servlet context-, nor as"
					+ " classpath-, nor as url-, nor as file system"
					+ " resource. Config location = \"" + location + "\".");
			return;
		}

		sc.log("Configuring logback. Config location = \"" + location
				+ "\", full url = \"" + url + "\".");

		configure(sc, url, lc);
	}

	protected void configure(ServletContext sc, URL location, LoggerContext lc) {
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(lc);
		lc.stop();
		try {
			configurator.doConfigure(location);
		} catch (JoranException e) {
			sc.log("Failed to configure logback.", e);
		}
		StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
	}

	protected URL toUrl(ServletContext sc, String location) {
		URL url = null;

		if (location.startsWith("/"))
			try {
				url = sc.getResource(location);
			} catch (MalformedURLException e1) {
				// NO-OP
			}

		if (url == null && location.startsWith(LOCATION_PREFIX_CLASSPATH))
			url = Thread
					.currentThread()
					.getContextClassLoader()
					.getResource(
							location.substring(LOCATION_PREFIX_CLASSPATH
									.length()));

		if (url == null)
			try {
				url = new URL(location);
			} catch (MalformedURLException e) {
				// NO-OP
			}

		if (url == null) {
			File file = new File(location);
			if (!file.isAbsolute())
				file = file.getAbsoluteFile();
			if (file.isFile())
				try {
					url = file.toURI().normalize().toURL();
				} catch (MalformedURLException e) {
					// NO-OP
				}
		}

		return url;
	}

	public void contextDestroyed(ServletContextEvent sce) {
		ILoggerFactory ilc = LoggerFactory.getILoggerFactory();

		if (!(ilc instanceof LoggerContext))
			return;

		LoggerContext lc = (LoggerContext) ilc;
		lc.stop();
	}
}
