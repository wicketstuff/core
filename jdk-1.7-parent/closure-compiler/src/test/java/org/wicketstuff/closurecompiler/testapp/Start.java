package org.wicketstuff.closurecompiler.testapp;

import java.util.EnumSet;
import java.util.logging.Handler;
import java.util.logging.LogManager;

import org.apache.wicket.Application;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.protocol.http.ContextParamWebApplicationFactory;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.util.lang.Bytes;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.servlet.DispatcherType;

public class Start
{
	private static final Logger log = LoggerFactory.getLogger(Start.class);
	// private static final RuntimeConfigurationType CONFIG = RuntimeConfigurationType.DEPLOYMENT;
	private static final RuntimeConfigurationType CONFIG = RuntimeConfigurationType.DEVELOPMENT;

	static
	{
		// redirect jcl to slf4j
		final java.util.logging.Logger rootLogger = LogManager.getLogManager().getLogger("");
		final Handler[] handlers = rootLogger.getHandlers();

		for (Handler hander : handlers)
		{
			rootLogger.removeHandler(hander);
		}

		SLF4JBridgeHandler.install();
	}

	public static void main(String[] args) throws Exception
	{

		final Server server = new Server();
		final SocketConnector connector = new SocketConnector();

		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		connector.setPort(8080);
		server.setConnectors(new Connector[] { connector });

		server.setHandler(createApp());

		server.start();
		log.info("server started");
	}

	private static ServletContextHandler createApp()
	{
		// servlet handler
		final ServletContextHandler root = new ServletContextHandler();
		root.setContextPath("/");
		root.setSessionHandler(new SessionHandler());
		root.setMaxFormContentSize((int)Bytes.megabytes(20).bytes());

		// servlet support
		root.addServlet(DefaultServlet.class, "/*");

		// wicket application
		final FilterHolder wicket = new FilterHolder(WicketFilter.class);
		wicket.setInitParameter(Application.CONFIGURATION, CONFIG.name());
		wicket.setInitParameter(ContextParamWebApplicationFactory.APP_CLASS_PARAM,
			ClosureCompilerApp.class.getName());
		wicket.setInitParameter(WicketFilter.FILTER_MAPPING_PARAM, "/*");
		root.addFilter(wicket, "/*", EnumSet.allOf(DispatcherType.class));

		return root;
	}
}
