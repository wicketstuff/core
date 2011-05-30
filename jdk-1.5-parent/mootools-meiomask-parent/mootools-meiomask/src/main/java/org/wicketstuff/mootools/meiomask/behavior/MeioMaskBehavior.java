/*
 *  Copyright 2010 inaiat.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.wicketstuff.mootools.meiomask.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.Response;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.util.string.JavascriptUtils;
import org.wicketstuff.mootools.meiomask.MaskType;

/**
 *
 * @author inaiat
 */
public class MeioMaskBehavior extends MootoolsMoreBehavior {

	private static final long serialVersionUID = 5849853913862285246L;

	private static final ResourceReference MEIO_MASK = new JavascriptResourceReference(MeioMaskBehavior.class,
            "res/meio-mask-min-2.0.1.js");
    private final MaskType maskType;
    private final String options;

    public MeioMaskBehavior(MaskType type) {
        this(type, null);
    }

    public MeioMaskBehavior(MaskType type, String options) {
        this.maskType = type;
        this.options = options;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.renderJavascriptReference(MEIO_MASK);
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        super.onComponentTag(component, tag);
        tag.put("data-meiomask", maskType.getMaskName());

        if (options != null) {
            tag.put("data-meiomask-options", options);
        }
    }

    @Override
    public void onRendered(Component component) {
        super.onRendered(component);
        Response response = component.getResponse();
        response.write(JavascriptUtils.SCRIPT_OPEN_TAG);
        response.write("$('");
        response.write(component.getMarkupId());
        response.write("').meiomask($('");
        response.write(component.getMarkupId());
        response.write("').get('data-meiomask'), JSON.decode($('");
        response.write(component.getMarkupId());
        response.write("').get('data-meiomask-options')));");
        response.write(JavascriptUtils.SCRIPT_CLOSE_TAG);
    }
}
