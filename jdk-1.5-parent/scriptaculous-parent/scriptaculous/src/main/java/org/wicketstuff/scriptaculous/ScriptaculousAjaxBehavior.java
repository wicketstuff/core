/*
 * $Id: ScriptaculousAjaxBehavior.java 2017 2007-04-29 14:59:34 +0000 (Sun, 29 Apr 2007) wireframe6464 $
 * $Revision: 2017 $
 * $Date: 2007-04-29 14:59:34 +0000 (Sun, 29 Apr 2007) $
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

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.wicketstuff.prototype.PrototypeResourceReference;

/**
 * Handles event requests using 'script.aculo.us'.
 * <p>
 * This class is mainly here to automatically add the javascript files you need.
 * As header contributions are done once per class, you can have multiple
 * instances/ subclasses without having duplicate header contributions.
 * </p>
 *
 * @see <a href="http://script.aculo.us/">script.aculo.us</a>
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public abstract class ScriptaculousAjaxBehavior extends AbstractDefaultAjaxBehavior {

	public static ScriptaculousAjaxBehavior newJavascriptBindingBehavior() {
		return new ScriptaculousAjaxBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void respond(AjaxRequestTarget target) {
				// do nothing
			}
		};
	}

	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		response.renderJavascriptReference(PrototypeResourceReference.INSTANCE);
		
		addJavascriptReference(response, "builder.js");
		addJavascriptReference(response, "effects.js");
		addJavascriptReference(response, "dragdrop.js");
		addJavascriptReference(response, "controls.js");
	}

	private void addJavascriptReference(IHeaderResponse response, String resource) {
		response.renderJavascriptReference(new JavascriptResourceReference(ScriptaculousAjaxBehavior.class, resource));
	}
}
