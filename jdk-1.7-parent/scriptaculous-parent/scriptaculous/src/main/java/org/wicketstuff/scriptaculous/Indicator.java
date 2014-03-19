/*
 * $Id: Indicator.java 1959 2007-04-19 04:11:08 +0000 (Thu, 19 Apr 2007) eelco12 $ $Revision: 1959 $
 * $Date: 2007-04-19 04:11:08 +0000 (Thu, 19 Apr 2007) $
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
package org.wicketstuff.scriptaculous;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Indicator panel.
 */
public class Indicator extends Panel {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Construct.
	 */
	public Indicator() {
		super("indicator");

		add(new Image("indicatorImage", "indicator.gif"));
		add(new Label("indicatorLabel", "Processing..."));
	}

}
