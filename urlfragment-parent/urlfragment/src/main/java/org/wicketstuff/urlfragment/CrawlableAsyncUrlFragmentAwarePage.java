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

import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * WORK IN PROGRESS
 * 
 * @author Martin Knopf
 * 
 */
public abstract class CrawlableAsyncUrlFragmentAwarePage extends AsyncUrlFragmentAwarePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		PageParameters parameters = new PageParameters(this.getPageParameters());
		if (!parameters.get("_escaped_fragment_").isEmpty() &&
			!parameters.get("_escaped_fragment_").isNull())
		{
			String escapedFragment = parameters.get("_escaped_fragment_").toString();
			String[] escapedParameters = escapedFragment.split("%26");
			for (String escapedParameter : escapedParameters)
			{
				String key = escapedParameter.split("x")[0];
				String value = escapedParameter.split("x")[1];
				parameters.set(key, value);
			}
			this.onEscapedFragmentIncome(parameters);
			this.remove(this.urlParametersReceivingBehavior);
		}
		else
		{
			this.onInitialRequest(parameters);
		}
	}

	protected abstract void onInitialRequest(PageParameters parameters);

	/**
	 * This is the callback for when your page was called synchronously with the
	 * '_escaped_fragment_' URL query parameter. All escaped URL fragment parameters were put in the
	 * given {@link PageParameters}.
	 * 
	 * @param parameters {@link PageParameters} received
	 */
	protected abstract void onEscapedFragmentIncome(PageParameters parameters);
}
