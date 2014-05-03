package embedded.jetty;

import org.apache.wicket.util.time.Duration;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

public class Start
{
	/**
	 * The context path is hard-coded in TemplatePage.html
	 */
	private static final String CONTEXT_PATH = "/wicket-jquery-ui";
	private static final int PORT = 8080;
	private static final long TIMEOUT = Duration.ONE_HOUR.getMilliseconds();

	public static void main(String[] args) throws Exception
	{
		System.setProperty("wicket.configuration", "development");

		Server server = new Server();

		HttpConfiguration http_config = new HttpConfiguration();
		http_config.setSecureScheme("https");
		http_config.setSecurePort(8443);
		http_config.setOutputBufferSize(32768);

		ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(http_config));
		http.setPort(PORT);
		http.setIdleTimeout(TIMEOUT);

		server.addConnector(http);

		Resource keystore = Resource.newClassPathResource("/keystore");
		if (keystore != null && keystore.exists())
		{
			// if a keystore for a SSL certificate is available, start a SSL
			// connector on port 8443.
			// By default, the quickstart comes with a Apache Wicket Quickstart
			// Certificate that expires about half way september 2021. Do not
			// use this certificate anywhere important as the passwords are
			// available in the source.

			SslContextFactory sslContextFactory = new SslContextFactory();
			sslContextFactory.setKeyStoreResource(keystore);
			sslContextFactory.setKeyStorePassword("wicket");
			sslContextFactory.setKeyManagerPassword("wicket");

			HttpConfiguration https_config = new HttpConfiguration(http_config);
			https_config.addCustomizer(new SecureRequestCustomizer());

			ServerConnector https = new ServerConnector(server, new SslConnectionFactory(sslContextFactory, "http/1.1"), new HttpConnectionFactory(https_config));
			https.setPort(8443);
			https.setIdleTimeout(500000);

			server.addConnector(https);
			System.out.println("SSL access to the examples has been enabled on port 8443");
			System.out.println("You can access the application using SSL on https://localhost:8443");
			System.out.println();
		}

		WebAppContext context = new WebAppContext();
		context.setServer(server);
		context.setContextPath(CONTEXT_PATH);
		context.setWar("src/main/webapp");

		server.setHandler(context);

		try
		{
			System.out.println(String.format(">>> http://localhost:%d%s", PORT, CONTEXT_PATH));
			server.start();
			server.join();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(100);
		}
	}
}
