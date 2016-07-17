/*
 * Copyright 2012 Decebal Suiu
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wicketstuff.dashboard.widgets.ofchart;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * http://cwiki.apache.org/WICKET/open-flash-chart-and-wicket.html
 */
public class SWFObject extends Behavior {

	private static final long serialVersionUID = 1L;

	private Map<String, String> parameters = new HashMap<String, String>();
	private Map<String, String> attributes = new HashMap<String, String>();

	private String version;
	private String flashUrl;
	private String width; // can be in percent (ex 100%)
	private String height; // can be in percent (ex 100%)
	private Component component;

	/**
	 * Construct.
	 * <p/>
	 * version can be a string in the format of
	 * 'majorVersion.minorVersion.revision'. An example would be: "6.0.65". Or
	 * you can just require the major version, such as "6".
	 * 
	 * @param flashUrl
	 *            The url of your swf file.
	 * @param width
	 *            width of swf
	 * @param height
	 *            height of movie
	 * @param version
	 *            Flash version to support
	 */
	public SWFObject(String flashUrl, String width, String height, String version) {
		if (flashUrl == null) {
			throw new IllegalArgumentException("Argument [flashUrl] cannot be null");
		}
		
		this.flashUrl = flashUrl;
		this.width = width;
		this.height = height;
		this.version = version;
	}

	public String getJavaScript() {
		final String id = component.getMarkupId();
		String parObj = buildDataObject(getParameters());
		String attObj = buildDataObject(getAttributes());

		// embedSWF: function(swfUrlStr, replaceElemIdStr, widthStr, heightStr, swfVersionStr, xiSwfUrlStr, flashvarsObj, parObj, attObj)
		String javascript = String.format("swfobject.embedSWF('%s','%s', '%s', '%s', '%s', '%s', %s, %s );",
				flashUrl, id, width, height, version, "expressInstall.swf", parObj, attObj);

		 // see http://old.nabble.com/Re%3A-Graphs%2C-Charts-and-Wicket-p21987222.html
		Optional<AjaxRequestTarget> target = RequestCycle.get().find(AjaxRequestTarget.class);
		if (target.isPresent()) {
			target.get().appendJavaScript(javascript);
		}
		
		return javascript;
	}

	@Override
	public void bind(Component component) {
		this.component = component;
		component.setOutputMarkupId(true);
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(SWFObject.class, "res/swfobject-2.2.js")));
		response.render(JavaScriptHeaderItem.forScript(getJavaScript(), null));
	}

	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
	}

	public void addParameter(String name, String value) {
		parameters.put(name, value);
	}

	public void addAttribute(String name, String value) {
		attributes.put(name, value);
	}

	protected Map<String, String> getParameters() {
		return parameters;
	}

	protected Map<String, String> getAttributes() {
		return attributes;
	}

	private String buildDataObject(Map<String, String> data) {
		String quote = "\"";
		if ((data != null) && !data.isEmpty()) {
			StringBuilder result = new StringBuilder();
            int size = getParameters().entrySet().size();
            int count = 0;
            for (Map.Entry<String, String> e : getParameters().entrySet()) {
                result.append("{");
                result.append(quote).append(e.getKey()).append(quote).
                       append(":").
                       append(quote).append(e.getValue()).append(quote);
                result.append("}");
                if (count < size-1) {
                    result.append(",");
                }
                count++;
            }            
			return result.toString();
		}
		
		return "{}";
	}

}