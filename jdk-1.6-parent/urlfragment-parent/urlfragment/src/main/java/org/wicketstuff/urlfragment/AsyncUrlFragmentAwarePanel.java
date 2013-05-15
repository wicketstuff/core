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
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;

/**
 * This is a {@link Panel} which will receive the URL query and fragment parameters asynchronously
 * after the initial request.
 * <p>
 * After the initial request this panel executes an AJAX call (through a behavior) which invokes
 * {@link #onParameterArrival(IRequestParameters, AjaxRequestTarget)}. This is a typical AJAX event
 * handling method with the difference that you have the URL query and fragment parameters, your
 * site was requested with, available in it. This method serves as your entry point for components
 * depending on URL fragment parameters.
 * </p>
 * <p>
 * <strong>Be aware the this panel will be rendered on the initial request in order to make the AJAX
 * call. This means, that, before the fragment parameters arrive, your implementation has to have
 * initial state (models) and content and cannot be invisible.</strong>
 * </p>
 * 
 * @author Martin Knopf
 * 
 */
public abstract class AsyncUrlFragmentAwarePanel extends Panel implements IBookmarkableComponent
{

	private transient AjaxRequestTarget target = null;

	public AsyncUrlFragmentAwarePanel(String id)
	{
		super(id);
		this.initializeFragmentBehavior();
	}

	public AsyncUrlFragmentAwarePanel(String id, IModel<?> model)
	{
		super(id, model);
		this.initializeFragmentBehavior();
	}

	private void initializeFragmentBehavior()
	{
		this.setOutputMarkupId(true);
		add(new UrlParametersReceivingBehavior(getOptions())
		{

			@Override
			protected void onParameterArrival(IRequestParameters requestParameters,
				AjaxRequestTarget target)
			{
				AsyncUrlFragmentAwarePanel.this.target = target;
				AsyncUrlFragmentAwarePanel.this.onParameterIncome(requestParameters, target);
				AsyncUrlFragmentAwarePanel.this.target = null;
				target.add(AsyncUrlFragmentAwarePanel.this);
			}

		});
	}

	/**
	 * Returns a map of options used for initializing the JavaScript library for reading and writing
	 * the URL fragment.
	 * <p>
	 * Possible options are:
	 * <ul>
	 * <li>
	 * 'fragmentIdentifierSuffix': String after the '#' (standard is '!')</li>
	 * <li>
	 * 'keyValueDelimiter': a String used to connect fragment parameters keys and values (standard
	 * is '&')</li>
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
	 * with. You can use {@link #urlFragment()} inside this method.
	 * 
	 * @param requestParameters
	 * @param target
	 */
	protected abstract void onParameterIncome(IRequestParameters requestParameters,
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

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.render(UrlParametersReceivingBehavior.getJS(AsyncUrlFragmentAwarePanel.class));
	}

	/**
	 * Returns a {@link UrlFragment} connected to the current {@link AjaxRequestTarget}. Use the
	 * {@link UrlFragment} to update the URL fragment in the browser after the current AJAX event.
	 * 
	 * @return
	 */
	protected UrlFragment urlFragment()
	{
		return new UrlFragment(target);
	}

}
