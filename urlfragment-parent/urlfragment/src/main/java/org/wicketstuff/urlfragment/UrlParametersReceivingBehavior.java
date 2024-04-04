/* Copyright (c) 2013 Martin Knopf
 *
 * Licensed under the MIT license;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.urlfragment;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.PackageResourceReference;

import com.github.openjson.JSONException;
import com.github.openjson.JSONObject;

/**
 * Reads URL fragment parameters. This {@link Behavior} will execute an AJAX call to itself with the
 * URL query and fragment parameters.
 *
 * @author Martin Knopf
 *
 */
public abstract class UrlParametersReceivingBehavior extends AbstractDefaultAjaxBehavior
{
	private static final long serialVersionUID = 1L;
	protected static final JavaScriptHeaderItem JS_REF
			= JavaScriptHeaderItem.forReference(new PackageResourceReference(UrlParametersReceivingBehavior.class, "urlfragment.js"));

	private final Component[] components;
	private final Map<String, String> options;

	/**
	 * Constructor that takes an array of {@link Component}s which will be added to the
	 * {@link AjaxRequestTarget} for you when the URL parameters come in.
	 *
	 * @param components
	 *            the components you wish to add to the request target when the income of the URL
	 *            fragment parameters is handled
	 */
	public UrlParametersReceivingBehavior(Component... components)
	{
		this(new HashMap<String, String>(), components);
	}

	/**
	 * Constructor that takes an array of {@link Component}s which will be added to the
	 * {@link AjaxRequestTarget} for you when the URL parameters come in.
	 *
	 * @param options
	 *            JS options to customize the behavior
	 * @param components
	 *            the components you wish to add to the request target when the income of the URL
	 *            fragment parameters is handled
	 */
	public UrlParametersReceivingBehavior(Map<String, String> options, Component... components)
	{
		super();
		this.options = options;
		this.components = components;
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		StringBuilder sb = new StringBuilder().append("try{")
			.append("if (window.UrlUtil === undefined) {window.UrlUtil = newUrlUtil(")
			.append(optionsJsonString())
			.append(");")
			.append("UrlUtil.setWicketAjaxCall(function(){" + getCallbackFunctionBody() + "});")
			.append("$(window).bind('hashchange',window.UrlUtil.back);")
			.append("window.UrlUtil.sendUrlParameters();}")
			.append("}catch(e){}");
		response.render(new OnDomReadyHeaderItem(sb.toString()));
		response.render(JS_REF);
	}

	private String optionsJsonString()
	{
		String optionsJsonString = "";
		try
		{
			optionsJsonString = new JSONObject(options).toString();
		}
		catch (JSONException e)
		{
		}
		return optionsJsonString;
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		onParameterArrival(RequestCycle.get().getRequest().getRequestParameters(), target);

		if (this.components != null) {
			addComponentsToBeRendered(target);
		}
	}

	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
	{
		super.updateAjaxAttributes(attributes);
		attributes.getDynamicExtraParameters().add("return window.UrlUtil.joinQueryAndFragment();");
	}

	private void addComponentsToBeRendered(AjaxRequestTarget target)
	{
		target.add(this.components);
	}

	/**
	 * Hook for evaluating the request parameters when they come in.
	 *
	 * @param requestParameters
	 *            use this to obtain the values of your URL fragment parameters
	 * @param target
	 *            use this like in any other ajax callback
	 */
	protected abstract void onParameterArrival(IRequestParameters requestParameters,
		AjaxRequestTarget target);
}
