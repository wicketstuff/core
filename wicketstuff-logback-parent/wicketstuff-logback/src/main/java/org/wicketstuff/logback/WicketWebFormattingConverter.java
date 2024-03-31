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

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * <p>
 * A custom logback conversion word converter. By registering this converter (through
 * <code>&lt;conversionRule&gt;</code> element in logback config) and using the "%web" conversion word
 * in the logback encoder pattern wicket apps can produce web information (method, url, session id,
 * ...) in their log messages. If there is no request information available the placeholder is
 * replaced with an empty string in the final message.
 * </p>
 *
 * <p>
 * Example logback configuration:
 *
 * <pre>
 * <code>
 * {@literal
 * <conversionRule conversionWord="web"
 *     converterClass="org.wicketstuff.logback.WicketWebFormattingConverter" />
 *
 * <appender name="consoleAppender" class="ch.qos.logback.core.ConsoleAppender">
 *     <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
 *         <charset>UTF-8</charset>
 *         <pattern>%d|%p|%t|%c{36}|%r|<b>%web</b>%n\t%caller{1}\t%m%n%xEx</pattern>
 *     </encoder>
 *     <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
 *         <level>debug</level>
 *     </filter>
 * </appender>
 * }
 * </code>
 * </pre>
 *
 * The above will result in log messages like this:
 *
 * <pre>
 * <code>
 * 2011-02-21 14:18:26,281|INFO|"http-nio-8080"-exec-2|o.w.logback.examples.HomePage|28066|GET http://localhost:8080/wicketstuff-logback-examples/?null null null 127.0.0.1:59363 127.0.0.1:8080 null Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.13 (KHTML, like Gecko) Chrome/9.0.597.98 Safari/534.13
 * 	Caller+0	 at org.wicketstuff.logback.examples.HomePage.<init>(HomePage.java:19)
 * 	Logging is good - said the lumberjack.
 * </code>
 * </pre>
 *
 * The full message format is available in the javadoc of {@link AbstractWebFormattingConverter}.
 *
 * </p>
 * <p>
 * There are similar solutions using {@link Filter}-s, MDC and NDC to solve the same task (like
 * logback's own MDCInsertingServletFilter or spring's AbstractRequestLoggingFilter and its
 * subclasses). The difference from those is performance (and the amount of information provided).
 * Those filters are always collecting information for every request however usually only a small
 * portion of requests result in actual logging. This solution only gets invoked when the logging
 * event is indeed producing a log message. It is on the "other side of the fence".
 * </p>
 *
 * <p>
 * Implementation of {@link AbstractWebFormattingConverter} that uses wicket to locate the current
 * {@link HttpServletRequest} object in {@link #getRequest()} method. Registerable through
 * <code>&lt;conversionRule&gt;</code> element in logback config.
 * </p>
 *
 * @author akiraly
 */
public class WicketWebFormattingConverter extends AbstractWebFormattingConverter
{
	/**
	 * This is the word that can be used in the layout pattern: "web".
	 */
	public static final String CONVERSION_WORD = "web";

	@Override
	protected HttpServletRequest getRequest()
	{
		RequestCycle cycle = RequestCycle.get();
		if (cycle == null) {
			return null;
		}

		Request request = cycle.getRequest();

		if (request == null) {
			return null;
		}

		Object containerRequest = request.getContainerRequest();

		if (!(containerRequest instanceof HttpServletRequest)) {
			return null;
		}

		return (HttpServletRequest)containerRequest;
	}
}
