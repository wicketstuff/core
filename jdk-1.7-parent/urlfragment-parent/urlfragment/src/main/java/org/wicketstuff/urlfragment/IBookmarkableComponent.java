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

public interface IBookmarkableComponent
{

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
	@Deprecated
	void setFragmentParameter(String parameterName, Object parameterValue);

	/**
	 * <p>
	 * <strong> Use the following instead:
	 * {@code urlFragment().addParameter(parameterName, parameterValue, delimiter);} </strong>
	 * </p>
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
	@Deprecated
	void addFragmentParameter(String parameterName, Object parameterValue, String delimiter);

	/**
	 * <p>
	 * <strong> Use the following instead: {@code urlFragment().removeParameter(parameterName);}
	 * </strong>
	 * </p>
	 * Removes the given URL fragment parameter by adding a JavaScript to the current
	 * {@link AjaxRequestTarget}.
	 * 
	 * @param parameterName
	 *            the name of your URL fragment parameter to set
	 */
	@Deprecated
	void removeFragmentParameter(String parameterName);

}
