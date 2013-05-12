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
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONStringer;
import org.apache.wicket.ajax.json.JSONWriter;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * Read and write URL fragment parameters. This {@link Behavior} will execute an AJAX call to itself
 * with the URL query and fragment parameters. For examples, see
 * {@link #UrlFragmentBehavior(String[])} and {@link #UrlFragmentBehavior(String[], Component...)}.
 * <p>
 * This class also provides the necessary JavaScript to set and remove URL fragment parameters if
 * you so wish. You can prepend or append the scripts to your AJAX events. For examples, see
 * {@link #setFragmentParameter(String, String)} and
 * {@link #removeFragmentParameter(String, String)}.
 * </p>
 * 
 * @author Martin Knopf
 * 
 */
public abstract class UrlParametersReceivingBehavior extends AbstractDefaultAjaxBehavior
{

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
			.append("window.UrlUtil = newUrlUtil(")
			.append(optionsJsonString())
			.append(");")
			.append("UrlUtil.setWicketAjaxCall(function(){" + getCallbackFunctionBody() + "});")
			.append("$(window).bind('hashchange',window.UrlUtil.back);")
			.append("window.UrlUtil.sendUrlParameters();")
			.append("}catch(e){}");
		response.render(new OnDomReadyHeaderItem(sb.toString()));
		response.render(getJS(getClass()));
	}

	private String optionsJsonString()
	{
		String optionsJsonString = "";
		try
		{
			JSONWriter writer = new JSONStringer().object();
			for (String key : options.keySet())
			{
				writer.key(key).value(options.get(key));
			}
			writer.endObject();
			optionsJsonString = writer.toString();
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

		if (this.components != null)
			addComponentsToBeRendered(target);
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

	/**
	 * Prepends a JavaScript to the given {@link AjaxRequestTarget} that will put the given
	 * parameter into the URL fragment. The parameter will be overwritten if it already exists in
	 * the URL fragment. The script keeps the order of already existing parameters.
	 * <p>
	 * Be aware that the returned JavaScript depends on the JavaScript coming from
	 * {@link #getJS(Class)}.
	 * </p>
	 * 
	 * @param target
	 *            the you want to prepend the JavaScript to
	 * @param parameterName
	 *            the name of your URL fragment parameter to set
	 * @param parameterValue
	 *            the value of your URL fragment parameter to set
	 * @return
	 */
	public static void setFragmentParameter(AjaxRequestTarget target, String parameterName,
		Object parameterValue)
	{
		target.prependJavaScript(String.format(
			"try{if(window.UrlUtil){window.UrlUtil.setFragmentParameter('%s','%s');}}catch(e){}",
			parameterName, parameterValue.toString()));
	}

	/**
	 * Prepends a JavaScript to the given {@link AjaxRequestTarget} that will put the given
	 * parameter into the URL fragment if it doesn't exist there. The value will be appended
	 * delimited by the specified delimiter if the parameter already exists in the URL fragment. The
	 * script keeps the order of already existing parameters.
	 * <p>
	 * Be aware that the returned JavaScript depends on the JavaScript coming from
	 * {@link #getJS(Class)}.
	 * </p>
	 * 
	 * @param target
	 *            the you want to prepend the JavaScript to
	 * @param parameterName
	 *            the name of your URL fragment parameter to set
	 * @param parameterValue
	 *            the value of your URL fragment parameter to set
	 * @param delimiter
	 *            the delimiter the given value will be appended with if the given parameter already
	 *            exists
	 * @return
	 */
	public static void addFragmentParameter(AjaxRequestTarget target, String parameterName,
		Object parameterValue, String delimiter)
	{
		target.prependJavaScript(String.format(
			"try{if(window.UrlUtil){window.UrlUtil.addFragmentParameter('%s','%s','%s');}}catch(e){}",
			parameterName, parameterValue.toString(), delimiter));
	}

	/**
	 * Prepends a JavaScript to the given {@link AjaxRequestTarget} that will remove the given
	 * parameter from the URL fragment. The script keeps the order of already existing parameters.
	 * <p>
	 * Be aware that the returned JavaScript depends on the JavaScript coming from
	 * {@link #getJS(Class)}.
	 * </p>
	 * 
	 * @param target
	 *            the you want to prepend the JavaScript to
	 * @param parameterName
	 *            the name of your URL fragment parameter to remove
	 * @return
	 */
	public static void removeFragmentParameter(AjaxRequestTarget target, String parameterName)
	{
		target.prependJavaScript(String.format(
			"try{if(window.UrlUtil){window.UrlUtil.removeFragmentParameter('%s');}}catch(e){}",
			parameterName));
	}

	/**
	 * Returns the {@link HeaderItem} representing the JavaScript library used to read and write URL
	 * fragment parameters.
	 * 
	 * @param scope
	 *            the scope of the {@link PackageResourceReference}
	 * @return
	 */
	protected static HeaderItem getJS(Class<?> scope)
	{
		PackageResourceReference ref = new PackageResourceReference(scope, "urlfragment.js");
		return JavaScriptHeaderItem.forReference(ref, ref.getName());
	}
}
