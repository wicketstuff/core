/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.datastores.common.app;

import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public class Start {
    public static void main(String[] args) {
	    Server server = new Server();

	    HttpConfiguration http_config = new HttpConfiguration();
	    http_config.setSecureScheme("https");
	    http_config.setSecurePort(8443);
	    http_config.setOutputBufferSize(32768);

	    ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(http_config));
	    http.setPort(8080);
	    http.setIdleTimeout(1000 * 60 * 60);

	    server.addConnector(http);

	    WebAppContext bb = new WebAppContext();
	    bb.setServer(server);
	    bb.setContextPath("/");
	    bb.setWar("src/test/webapp");

        server.setHandler(bb);

        try {
            System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
            server.start();
            System.in.read();
            System.out.println(">>> STOPPING EMBEDDED JETTY SERVER");
            server.stop();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
