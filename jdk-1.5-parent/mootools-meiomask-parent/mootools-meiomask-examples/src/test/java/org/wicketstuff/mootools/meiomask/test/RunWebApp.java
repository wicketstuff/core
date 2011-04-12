package org.wicketstuff.mootools.meiomask.test;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public class RunWebApp
{

	public static void main(String[] args) throws Exception
	{
		Server server = new Server();
		SocketConnector connector = new SocketConnector();
		connector.setPort(8080);
		server.setConnectors(new Connector[] { connector });

		WebAppContext context = new WebAppContext();
		context.setServer(server);
		context.setContextPath("/");
		context.setWar("src/main/webapp");

		server.setHandler(context);
		try
		{
			server.start();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
