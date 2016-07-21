package org.wicketstuff.nashorn.resource;

import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Start
{
	public static void main(String[] args) throws IOException
	{
		
		System.setProperty("java.security.policy", Start.class.getResource("nashorn.policy").toString());
		System.setSecurityManager(new NashornSecurityManager(false));
		
		Server server = new Server(8080);
		WebAppContext bb = new WebAppContext();
		bb.setServer(server);
		bb.setContextPath("/");
		bb.setWar("src/main/webapp");

		// START JMX SERVER
		// MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		// MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
		// server.getContainer().addEventListener(mBeanContainer);
		// mBeanContainer.start();

		server.setHandler(bb);

		try
		{
			System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
			server.start();
			while (System.in.available() == 0)
			{
				Thread.sleep(5000);
			}
			server.stop();
			server.join();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(100);
		}
	}
}
