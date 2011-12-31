/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributed by United Services Automotive Association (USAA)
 */
package org.wicketstuff.webflow;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.wicketstuff.webflow.components.WebFlowLink;
import org.wicketstuff.webflow.extras.ProgressBarLinksPanel;
import org.wicketstuff.webflow.session.PageFlowSession;


/**
 * <p>Page2 class.</p>
 *
 * @author Clint Checketts, Florian Braun, Doug Hall
 * @version $Id: $
 */
public class Page2 extends WebPage {

	/**
	 * <p>Constructor for Page2.</p>
	 */
	public Page2() {
		add(new ProgressBarLinksPanel("progress", Model.ofList(PageFlowSession.get().getFlowState().getProgressBarLinks())));
		add(new WebFlowLink<Void>("previous"));
		add(new WebFlowLink<Void>("next"));
	}
}
