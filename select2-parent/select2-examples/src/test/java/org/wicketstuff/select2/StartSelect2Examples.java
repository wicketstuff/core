package org.wicketstuff.select2;

import org.apache.wicket.util.file.File;
import org.apache.wicket.util.time.Duration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public class StartSelect2Examples
{
	public static void main(String[] args) throws Exception
	{
		int timeout = (int)Duration.ONE_HOUR.getMilliseconds();

		Server server = new Server();
		ServerConnector http = new ServerConnector(server);

		// Set some timeout options to make debugging easier.
		http.setIdleTimeout(timeout);
		http.setSoLingerTime(-1);
		http.setPort(8080);
		server.addConnector(http);

		WebAppContext bb = new WebAppContext();
		bb.setContextPath("/");
		bb.setWar(new File("src/main/webapp").getAbsolutePath());

		server.setHandler(bb);

		try
		{
			System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
			server.start();
			System.in.read();
			System.out.println(">>> STOPPING EMBEDDED JETTY SERVER");
			server.stop();
			server.join();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
}
