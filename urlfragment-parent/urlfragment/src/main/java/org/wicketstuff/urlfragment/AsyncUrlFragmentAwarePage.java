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
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.mapper.parameter.PageParameters;

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
public abstract class AsyncUrlFragmentAwarePage extends WebPage implements IBookmarkableComponent
{
	private static final long serialVersionUID = 1L;
	private transient AjaxRequestTarget target = null;
	protected UrlParametersReceivingBehavior urlParametersReceivingBehavior;

	public AsyncUrlFragmentAwarePage()
	{
		super();
	}

	public AsyncUrlFragmentAwarePage(IModel<?> model)
	{
		super(model);
	}

	public AsyncUrlFragmentAwarePage(PageParameters parameters)
	{
		super(parameters);
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		urlParametersReceivingBehavior = new UrlParametersReceivingBehavior(getOptions())
		{
			private static final long serialVersionUID = 1L;

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
	 * </p>
	 * <ul>
	 * <li>
	 * fragmentIdentifierSuffix: String after the '#' (standard is '!')</li>
	 * <li>
	 * keyValueDelimiter: a String used to connect fragment parameters keys and values (standard is
	 * '&amp;')</li>
	 * </ul>
	 * 
	 * @return options {@link Map} created
	 */
	protected Map<String, String> getOptions()
	{
		return new HashMap<String, String>();
	}

	/**
	 * This is where you can grab the URL query and fragment parameters, your site was requested
	 * with. You can use {@link #urlFragment()} inside this method.
	 * 
	 * @param requestParameters parameters set on this page
	 * @param target {@link AjaxRequestTarget} can be used for page updating
	 */
	protected abstract void onParameterArrival(IRequestParameters requestParameters,
		AjaxRequestTarget target);

	@Override
	@Deprecated
	public void setFragmentParameter(String parameterName, Object parameterValue)
	{
		if (this.target != null && parameterName != "" && parameterValue != "")
		{
			urlFragment().putParameter(parameterName, parameterValue);
		}
	}

	@Override
	@Deprecated
	public void addFragmentParameter(String parameterName, Object parameterValue, String delimiter)
	{
		if (this.target != null && parameterName != "" && parameterValue != "")
		{
			urlFragment().putParameter(parameterName, parameterValue, delimiter);
		}
	}

	@Override
	@Deprecated
	public void removeFragmentParameter(String parameterName)
	{
		if (this.target != null && parameterName != "")
		{
			urlFragment().removeParameter(parameterName);
		}
	}

	/**
	 * Returns a {@link UrlFragment} connected to the current {@link AjaxRequestTarget}. Use the
	 * {@link UrlFragment} to update the URL fragment in the browser after the current AJAX event.
	 * 
	 * @return created {@link UrlFragment} for chaining
	 */
	protected UrlFragment urlFragment()
	{
		return new UrlFragment(target);
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.render(UrlParametersReceivingBehavior.getJS(AsyncUrlFragmentAwarePage.class));
	}
}
