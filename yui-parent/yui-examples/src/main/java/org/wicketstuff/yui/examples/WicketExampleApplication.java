/*
 * $Id: WicketExampleApplication.java 4180 2006-02-08 13:58:37Z joco01 $
 * $Revision: 4180 $ $Date: 2006-02-08 21:58:37 +0800 (Wed, 08 Feb 2006) $
 * 
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
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

import org.apache.wicket.protocol.http.WebApplication;

/**
 * Wicket Application class.
 */
public abstract class WicketExampleApplication extends WebApplication {
	/**
	 * Constructor.
	 */
	public WicketExampleApplication() {
	}

	/**
	 * @see wicket.protocol.http.WebApplication#init()
	 */
	protected void init() {
		getMarkupSettings().setStripWicketTags(true);
	}
}
