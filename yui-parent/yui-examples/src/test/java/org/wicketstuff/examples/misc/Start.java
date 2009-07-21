package org.wicketstuff.examples.misc;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * Starts the YUI examples with jetty.
 * <p>
 * The application is available on http://localhost:8080/yui.
 * 
 * @author Erik van Oosten
 */
public class Start {

	public static void main(String[] args) throws Exception {
		System.setProperty("wicket.configuration", "development");

		Server server = new Server();
		SocketConnector connector = new SocketConnector();
		connector.setPort(8080);
		server.setConnectors(new Connector[] { connector });

		WebAppContext webContext = new WebAppContext();
		webContext.setServer(server);
		webContext.setContextPath("/yui");
		webContext.setWar("src/main/webapp");
		server.addHandler(webContext);

		try {
			System.out.println(">>> STARTING EMBEDDED JETTY SERVER");
			server.start();

			while (System.in.available() == 0) {
				Thread.sleep(5000);
			}

			server.stop();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}

}
