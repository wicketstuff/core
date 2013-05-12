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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.IRequestParameters;

/**
 * This is a {@link WebPage} which will receive the URL query and fragment parameters asynchronously
 * after the initial request.
 * <p>
 * After the initial request this page executes an AJAX call (through a behavior) which invokes
 * {@link #onParameterArrival(IRequestParameters, AjaxRequestTarget)}. This is a typical AJAX event
 * handling method with the difference that you have the URL query and fragment parameters, your
 * site was requested with, available in it. This method serves as your entry point for components
 * depending on URL fragment parameters.
 * </p>
 * <p>
 * <strong>Be aware the this page will be rendered on the initial request in order to make the AJAX
 * call. This means, that, before the fragment parameters arrive, your implementation has to have
 * initial state (models) and content and cannot be invisible.</strong>
 * </p>
 * 
 * @author Martin Knopf
 * 
 */
public abstract class AsyncUrlFragmentAwarePage extends WebPage
{

	private transient AjaxRequestTarget target = null;
	protected UrlParametersReceivingBehavior urlParametersReceivingBehavior;

	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		urlParametersReceivingBehavior = new UrlParametersReceivingBehavior(getOptions())
		{
			@Override
			protected void onParameterArrival(IRequestParameters requestParameters,
				AjaxRequestTarget target)
			{
				AsyncUrlFragmentAwarePage.this.target = target;
				AsyncUrlFragmentAwarePage.this.onParameterArrival(requestParameters, target);
				AsyncUrlFragmentAwarePage.this.target = null;
			}
		};
		add(urlParametersReceivingBehavior);
	}

	/**
	 * Returns a map of options used for initializing the JavaScript library for reading and writing
	 * the URL fragment.
	 * <p>
	 * Possible options are:
	 * <ul>
	 * <li>
	 * fragmentIdentifierSuffix: String after the '#' (standard is '!')</li>
	 * <li>
	 * keyValueDelimiter: a String used to connect fragment parameters keys and values (standard is
	 * '&')</li>
	 * </ul>
	 * </p>
	 * 
	 * @return
	 */
	protected Map<String, String> getOptions()
	{
		return new HashMap<String, String>();
	}

	/**
	 * This is where you can grab the URL query and fragment parameters, your site was requested
	 * with.
	 * 
	 * @param requestParameters
	 * @param target
	 */
	protected abstract void onParameterArrival(IRequestParameters requestParameters,
		AjaxRequestTarget target);

	/**
	 * Sets the given URL fragment parameter by adding a JavaScript to the current
	 * {@link AjaxRequestTarget}.
	 * <p>
	 * The parameter will be overwritten if it already exists.
	 * </p>
	 * 
	 * @param parameterName
	 *            the name of your URL fragment parameter to set
	 * @param parameterValue
	 *            the value of your URL fragment parameter to set
	 */
	protected void setFragmentParameter(String parameterName, Object parameterValue)
	{
		if (this.target != null && parameterName != "" && parameterValue != "")
		{
			UrlParametersReceivingBehavior.setFragmentParameter(this.target, parameterName,
				parameterValue.toString());
		}
	}

	/**
	 * Sets the given URL fragment parameter by adding a JavaScript to the current
	 * {@link AjaxRequestTarget}.
	 * <p>
	 * The parameter will be created if it doesn't exists yet.
	 * </p>
	 * <p>
	 * The value will be appended with the specified delimiter if the parameter already exists.
	 * </p>
	 * 
	 * @param parameterName
	 *            the name of your URL fragment parameter to set
	 * @param parameterValue
	 *            the value of your URL fragment parameter to set
	 * @param delimiter
	 *            the delimiter the given value will be appended with if the given parameter already
	 *            exists
	 */
	protected void addFragmentParameter(String parameterName, Object parameterValue,
		String delimiter)
	{
		if (this.target != null && parameterName != "" && parameterValue != "")
		{
			UrlParametersReceivingBehavior.addFragmentParameter(this.target, parameterName,
				parameterValue.toString(), delimiter);
		}
	}

	/**
	 * Removes the given URL fragment parameter by adding a JavaScript to the current
	 * {@link AjaxRequestTarget}.
	 * 
	 * @param parameterName
	 *            the name of your URL fragment parameter to set
	 */
	protected void removeFragmentParameter(String parameterName)
	{
		if (this.target != null && parameterName != "")
		{
			UrlParametersReceivingBehavior.removeFragmentParameter(this.target, parameterName);
		}
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.render(UrlParametersReceivingBehavior.getJS(AsyncUrlFragmentAwarePage.class));
	}
}
