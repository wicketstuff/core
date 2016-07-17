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

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;

/**
 * Encapsulates the JavaScripts used to edit the URL fragment in the browser.
 * 
 * @author Martin Knopf
 * 
 */
public class UrlFragment
{
	private final IPartialPageRequestHandler handler;

	public UrlFragment(IPartialPageRequestHandler handler)
	{
		this.handler = handler;
	}

	/**
	 * Sets the value of window.location.hash to the given String.
	 * 
	 * @param urlFragment JS function to handle location hash
	 * @return this for chaining
	 */
	public UrlFragment set(Object urlFragment)
	{
		addJsToTarget(String.format(
			"try{if(window.UrlUtil){window.UrlUtil.setFragment('%s');}}catch(e){}",
			urlFragment.toString()));
		return this;
	}

	/**
	 * Sets the value of window.location.hash to the given key-value-pair.
	 * 
	 * @param parameterName the name to be set
	 * @param parameterValue the value of the parameter being set
	 * @return this for chaining
	 */
	public UrlFragment set(String parameterName, Object parameterValue)
	{
		addJsToTarget(String.format(
			"try{if(window.UrlUtil){window.UrlUtil.setFragment('%s','%s');}}catch(e){}",
			parameterName, parameterValue.toString()));
		return this;
	}

	/**
	 * Puts the given key-value-pair into window.location.hash.
	 * 
	 * @param parameterName the name to be set
	 * @param parameterValue the value of the parameter being set
	 * @return this for chaining
	 */
	public UrlFragment putParameter(String parameterName, Object parameterValue)
	{
		addJsToTarget(String.format(
			"try{if(window.UrlUtil){window.UrlUtil.putFragmentParameter('%s','%s');}}catch(e){}",
			parameterName, parameterValue.toString()));
		return this;
	}

	/**
	 * Puts the given key-value-pair into window.location.hash. If the key already exists the value
	 * will be appended to the existing value with the given delimiter.
	 * 
	 * @param parameterName the name to be set
	 * @param parameterValue the value of the parameter being set
	 * @param valueDelimiter parameter value delimiter to set multiple values
	 * @return this for chaining
	 */
	public UrlFragment putParameter(String parameterName, Object parameterValue,
		String valueDelimiter)
	{
		addJsToTarget(String.format(
			"try{if(window.UrlUtil){window.UrlUtil.putFragmentParameter('%s','%s','%s');}}catch(e){}",
			parameterName, parameterValue.toString(), valueDelimiter));
		return this;
	}

	/**
	 * Removes the key-value-pair from window.location.hash that is identified by the given
	 * parameter name.
	 * 
	 * @param parameterName the name to be removed
	 * @return this for chaining
	 */
	public UrlFragment removeParameter(String parameterName)
	{
		addJsToTarget(String.format(
			"try{if(window.UrlUtil){window.UrlUtil.removeFragmentParameter('%s');}}catch(e){}",
			parameterName));
		return this;
	}

	private void addJsToTarget(String js)
	{
		if (this.handler != null)
		{
			this.handler.prependJavaScript(js);
		}
	}

}
