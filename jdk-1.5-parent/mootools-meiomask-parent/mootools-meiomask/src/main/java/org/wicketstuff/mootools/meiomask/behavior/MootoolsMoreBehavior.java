/*
 * Copyright 2011 inaiat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.mootools.meiomask.behavior;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;

/**
 *
 * @author inaiat
 */
public class MootoolsMoreBehavior extends MootoolsCoreBehavior {

	private static final long serialVersionUID = -523620975856861332L;
	// create a reference to the base mootools javascript file.
    // we use JavascriptResourceReference so that the included file will have its comments stripped and gzipped.
    private static final ResourceReference MOOTOOLS_MORE_JS = new JavascriptResourceReference(MootoolsMoreBehavior.class,
            "res/mootools-more-1.3.0.1.js");

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.renderJavascriptReference(MOOTOOLS_MORE_JS);
    }
    
}
