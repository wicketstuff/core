package run.gmap3;

import org.apache.wicket.util.time.Duration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

public class Start {

	public static void main(String[] args) throws Exception {

		int timeout = (int) Duration.ONE_HOUR.getMilliseconds();

		Server server = new Server();
		ServerConnector connector = new ServerConnector( server );

		// Set some timeout options to make debugging easier.
		connector.setIdleTimeout( timeout );
		connector.setSoLingerTime( -1 );
		connector.setPort( 8080 );
		server.addConnector( connector );

		Resource keystore = Resource.newClassPathResource( "/keystore" );
		if( keystore != null && keystore.exists() ){
			// if a keystore for a SSL certificate is available, start a SSL connector on port 8443.
			// By default, the quickstart comes with a Apache Wicket Quickstart certificate that expires about half way september 2021.
			// Do not use this certificate anywhere important as the passwords are available in the source.

			SslContextFactory factory = new SslContextFactory();
			factory.setKeyStoreResource( keystore );
			factory.setKeyStorePassword( "wicket" );
			factory.setTrustStoreResource( keystore );
			factory.setKeyManagerPassword( "wicket" );
			ServerConnector sslConnector = new ServerConnector( server, factory );
			sslConnector.setIdleTimeout( timeout );
			sslConnector.setPort( 8443 );
			server.addConnector( sslConnector );

			System.out.println( "SSL access to the quickstart has been enabled on port 8443" );
			System.out.println( "You can access the application using SSL on https://localhost:8443" );
			System.out.println();
		}

		System.setProperty( "java.naming.factory.url.pkgs", "org.eclipse.jetty.jndi" );
		System.setProperty( "java.naming.factory.initial", "org.eclipse.jetty.jndi.InitialContextFactory" );

		WebAppContext webAppContext = new WebAppContext();

		webAppContext.setServer( server );
		webAppContext.setContextPath( "/" );
		webAppContext.setWar( "src/main/webapp" );

		// START JMX SERVER
		// MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		// MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
		// server.getContainer().addEventListener(mBeanContainer);
		// mBeanContainer.start();

		server.setHandler( webAppContext );

		try{
			System.out.println( ">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP" );
			server.start();
			System.in.read();
			System.out.println( ">>> STOPPING EMBEDDED JETTY SERVER" );
			server.stop();
			server.join();
		}catch(Exception e){
			e.printStackTrace();
			System.exit( 1 );
		}
	}
}