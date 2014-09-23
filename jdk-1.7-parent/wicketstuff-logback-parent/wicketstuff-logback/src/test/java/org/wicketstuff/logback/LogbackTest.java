package org.wicketstuff.logback;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.util.crypt.StringUtils;
import org.apache.wicket.util.tester.WicketTester;
import org.eclipse.jetty.testing.ServletTester;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * Tests for {@link LogbackConfigListener}.
 * 
 * @author akiraly
 */
@RunWith(Parameterized.class)
public class LogbackTest
{
	private ServletTester servletTester;
	private WicketTester wicketTester;

	private String contextPath;
	private String logbackContextPath;

	@Parameters
	public static List<Object[]> parameters()
	{
		return Arrays.asList(new Object[][] { { "", LogbackConfigListener.CONTEXT_PATH_ROOT_VAL },
				{ "/", LogbackConfigListener.CONTEXT_PATH_ROOT_VAL }, { "/l", "l" } });
	}

	public LogbackTest(String contextPath, String logbackContextPath)
	{
		super();
		this.contextPath = contextPath;
		this.logbackContextPath = logbackContextPath;
	}

	@Before
	public void before() throws Exception
	{
		servletTester = new ServletTester();
		servletTester.getContext().setInitParameter(LogbackConfigListener.CONFIG_LOCATION_PARAM,
			LogbackConfigListener.LOCATION_PREFIX_CLASSPATH + "logback-custom-config.xml");
		servletTester.getContext().setInitParameter(
			LogbackConfigListener.CONFIG_CONTEXT_PATH_KEY_PARAM, "contextPath");
		servletTester.setContextPath(contextPath);
		servletTester.addEventListener(new LogbackConfigListener());

		servletTester.start();

		wicketTester = new WicketTester(new MockApplication(), servletTester.getContext()
			.getServletContext());
	}

	@Test
	public void test()
	{
		// this is a logback Logger not an slf4j one
		Logger logger = (Logger)LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);

		// if the cast or the assert fails the custom configuration of logback
		// failed, LogbackConfigListener bug
		ByteArrayAppender<ILoggingEvent> appender = (ByteArrayAppender<ILoggingEvent>)logger.getAppender("arrayAppender-" +
			logbackContextPath);
		Assert.assertNotNull(appender);

		appender.getOutputStream().reset();

		// warn log gets appended to appender
		logger.warn("");

		// now get it as string.
		String log = StringUtils.newStringUtf8(appender.getOutputStream().toByteArray());
		logger.info(log);

		// String is based on MockHttpServletRequest constants.
		// If this fails WicketWebPatternEncoder bugged or constants changed.
		Assert.assertEquals(
			"post http://localhost/context/?null null null 127.0.0.1:80 127.0.0.1:80 null Mozilla/5.0 (Windows; U; Windows NT 5.0; en-US; rv:1.7) Gecko/20040707 Firefox/0.9.2",
			log);
	}

	@After
	public void after() throws Exception
	{
		wicketTester.destroy();
		servletTester.stop();
	}

}
