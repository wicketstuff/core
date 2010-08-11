/*
 * $Id: Start.java 889 2006-08-16 11:01:16 -0700 (Wed, 16 Aug 2006) ivaynberg $
 * $Revision: 889 $ $Date: 2006-08-16 11:01:16 -0700 (Wed, 16 Aug 2006) $
 *
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.phonebook;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * Seperate startup class for people that want to run the examples directly.
 */
public class StartPhonebook {
	/**
	 * Main function, starts the jetty server.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server();
		SocketConnector connector = new SocketConnector();
		connector.setPort(8080);
		server.setConnectors(new Connector[] { connector });

		WebAppContext context = new WebAppContext();
		context.setServer(server);
		context.setContextPath("/phonebook");
		context.setWar("src/main/webapp");

		server.addHandler(context);
		try {
			server.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
