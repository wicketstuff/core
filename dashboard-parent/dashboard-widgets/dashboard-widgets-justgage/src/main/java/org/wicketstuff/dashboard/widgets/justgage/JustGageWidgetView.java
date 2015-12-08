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
package org.wicketstuff.dashboard.widgets.justgage;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.dashboard.Widget;
import org.wicketstuff.dashboard.web.WidgetView;
import org.wicketstuff.dashboard.widgets.justgage.option.JavaScriptOptionsRenderer;

/**
 * @author Decebal Suiu
 */
public class JustGageWidgetView extends WidgetView {

	private static final long serialVersionUID = 1L;

	private ResourceReference raphaelReference = new PackageResourceReference(
			JustGageWidgetView.class, "res/raphael.2.1.0.min.js");
	private ResourceReference justgageReference = new PackageResourceReference(
			JustGageWidgetView.class, "res/justgage.1.0.1.min.js");

	private String gaugeId;
	
	public JustGageWidgetView(String id, Model<Widget> model) {
		super(id, model);
		
		gaugeId = "gauge" + getSession().nextSequenceValue();
		add(new WebMarkupContainer("gauge").setMarkupId(gaugeId));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		
		response.render(JavaScriptHeaderItem.forReference(raphaelReference));
		response.render(JavaScriptHeaderItem.forReference(justgageReference));
		
		response.render(OnDomReadyHeaderItem.forScript(getJustGageJavaScript()));
	}
	
	private CharSequence getJustGageJavaScript() {
		/*
		var g = new JustGage({
		    id: "gauge", 
		    value: 67, 
		    min: 0,
		    max: 100,
		    title: "Visitors"
		  }); 
		*/
		
		JustGageWidget widget = (JustGageWidget) getModelObject();		
		JustGage justGage = widget.getJustGage();
		justGage.setId(gaugeId);
		
		StringBuilder function = new StringBuilder();
		function.append("var " + gaugeId + " = ");
		function.append("new JustGage({");
		JavaScriptOptionsRenderer.renderOptions(justGage, function);
		function.append("});");

//		System.out.println(function);
		
	 	return function;
	}
		
	/*
	private String getGaugeId() {
//		return "gauge-" + getModelObject().getId();
		return getGaugeVarname();
	}
	
	private String getGaugeVarname() {
		return "gauge" + getSession().nextSequenceValue();
	}
	*/
	
}
