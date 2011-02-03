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
package org.wicketstuff;

import winstone.Launcher;

import java.util.HashMap;

/**
 * Seperate startup class for people that want to run the examples directly.
 */
public class RunWebApp {

    /**
     * Main function, starts the jetty server.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        try {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("webroot", "src/main/webapp"); // or any other command line
            // args, eg port
            Launcher.initLogger(params);
            new Launcher(params); // spawns threads (not daemon), so your application doesn't block
        } catch (Exception exc) {
            exc.printStackTrace();
            System.exit(100);
        }
    }
}
