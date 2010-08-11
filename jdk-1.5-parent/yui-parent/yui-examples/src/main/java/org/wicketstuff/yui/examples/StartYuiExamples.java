/*
 * $Id: StartYuiExamples.java 3400 2005-12-09 07:43:38Z ivaynberg $
 * $Revision: 3400 $
 * $Date: 2005-12-09 15:43:38 +0800 (Fri, 09 Dec 2005) $
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
package org.wicketstuff.yui.examples;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.management.MBeanContainer;

/**
 * Separate startup class for people that want to run the examples directly.
 * <p>
 * Application is available on the URL http://localhost:8080/wicket-contrib-yui-examples
 */
public class StartYuiExamples {

	/**
	 * Main function, starts the jetty server.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(8080);
		server.setConnectors(new Connector[] { connector });

		WebAppContext web = new WebAppContext();
		web.setContextPath("/wicket-contrib-yui-examples");
		web.setWar("src/main/webapp");
		server.addHandler(web);

		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
		server.getContainer().addEventListener(mBeanContainer);
		mBeanContainer.start();

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}

	/**
	 * Construct.
	 */
	StartYuiExamples() {
		super();
	}
}
