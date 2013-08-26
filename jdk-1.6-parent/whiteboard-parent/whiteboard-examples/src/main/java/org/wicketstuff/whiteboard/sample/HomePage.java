/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.whiteboard.sample;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.whiteboard.Whiteboard;
import org.wicketstuff.whiteboard.WhiteboardBehavior;

import java.io.*;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(WhiteboardBehavior.class);

	public HomePage(final PageParameters parameters) {
		super(parameters);
		String content = "";
		File savedWhiteboard = new File("Whiteboard_2013_08_12_01_28_13.json");

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(savedWhiteboard));

			String line = reader.readLine();
			while (line != null) {
				content += line;
				line = reader.readLine();
			}
		} catch (Exception e) {
			log.error("Unexpected error: ", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					// noop
				}
			}
			Whiteboard whiteboard = new Whiteboard("whiteboardContainer", content);
			this.add(whiteboard);
		}

	}

}
