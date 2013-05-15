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

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Encapsulates the JavaScripts used to edit the URL fragment in the browser.
 * 
 * @author Martin Knopf
 * 
 */
public class UrlFragment
{
	private final AjaxRequestTarget target;

	public UrlFragment(AjaxRequestTarget target)
	{
		this.target = target;
	}

	public UrlFragment setParameter(String parameterName, Object parameterValue)
	{
		addJsToTarget(String.format(
			"try{if(window.UrlUtil){window.UrlUtil.setFragmentParameter('%s','%s');}}catch(e){}",
			parameterName, parameterValue.toString()));
		return this;
	}

	public UrlFragment addParameter(String parameterName, Object parameterValue, String delimiter)
	{
		addJsToTarget(String.format(
			"try{if(window.UrlUtil){window.UrlUtil.addFragmentParameter('%s','%s','%s');}}catch(e){}",
			parameterName, parameterValue.toString(), delimiter));
		return this;
	}

	public UrlFragment removeParameter(String parameterName)
	{
		addJsToTarget(String.format(
			"try{if(window.UrlUtil){window.UrlUtil.removeFragmentParameter('%s');}}catch(e){}",
			parameterName));
		return this;
	}

	public UrlFragment set(Object urlFragment)
	{
		addJsToTarget(String.format(
			"try{if(window.UrlUtil){window.UrlUtil.setFragment('%s');}}catch(e){}",
			urlFragment.toString()));
		return this;
	}

	private void addJsToTarget(String js)
	{
		if (this.target != null)
		{
			this.target.prependJavaScript(js);
		}
	}

}
