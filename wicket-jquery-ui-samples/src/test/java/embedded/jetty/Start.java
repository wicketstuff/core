package embedded.jetty;

import org.apache.wicket.util.time.Duration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

public class Start {
	/**
	 * The context path is hard-coded in TemplatePage.html
	 */
	private static final String CONTEXT_PATH = "/wicket-jquery-ui";
	private static final int PORT = 8080;
	private static final int TIMEOUT = (int) Duration.ONE_HOUR.getMilliseconds();

	public static void main(String[] args) throws Exception {
		System.setProperty("wicket.configuration", "development");

		Server server = new Server();
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(PORT);
		connector.setIdleTimeout(TIMEOUT);
		server.addConnector(connector);

		Resource keystore = Resource.newClassPathResource("/keystore");

		if (keystore != null && keystore.exists()) {
//			SslContextFactory factory = new SslContextFactory();
//			SslConnectionFactory factory = new SslConnectionFactory();
//			factory.

//			factory.setKeyStoreResource(keystore);
//			factory.setKeyStorePassword("wicket");
//			factory.setTrustStorePath("/keystore");
//			factory.setKeyManagerPassword("wicket");
//
//			SslSocketConnector sslConnector = new SslSocketConnector(factory);
//			sslConnector.setMaxIdleTime(TIMEOUT);
//			sslConnector.setPort(8443);
//			sslConnector.setAcceptors(4);
//
//			server.addConnector(sslConnector);			
		}

		WebAppContext context = new WebAppContext();
		context.setServer(server);
		context.setContextPath(CONTEXT_PATH);
		context.setWar("src/main/webapp");

		server.setHandler(context);

		try {
			System.out.println(String.format(">>> http://localhost:%d%s", PORT, CONTEXT_PATH));
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}
}
