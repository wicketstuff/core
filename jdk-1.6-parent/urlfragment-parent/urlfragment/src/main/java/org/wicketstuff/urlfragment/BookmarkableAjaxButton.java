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
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * This is an {@link AjaxButton} with the ability to set and remove URL fragment parameters.
 * <p>
 * You can set or remove parameters using {@link #setFragmentParameter(String, String)} or
 * {@link #removeFragmentParameter(String)} during your handling of the submit and error event.
 * </p>
 * 
 * @author Martin Knopf
 */
public abstract class BookmarkableAjaxButton extends AjaxButton
{

	private transient AjaxRequestTarget target = null;

	public BookmarkableAjaxButton(String id)
	{
		super(id);
	}

	public BookmarkableAjaxButton(String id, IModel<String> model)
	{
		super(id, model);
	}

	public BookmarkableAjaxButton(String id, Form<?> form)
	{
		super(id, form);
	}

	public BookmarkableAjaxButton(String id, IModel<String> model, Form<?> form)
	{
		super(id, model, form);
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form)
	{
		this.target = target;
		this.onBookmarkableSubmit(target, form);
		this.target = null;
	}

	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form)
	{
		this.target = target;
		this.onBookmarkableError(target, form);
		this.target = null;
	}

	/**
	 * Override to handle the submit event. You can use
	 * <ul>
	 * <li>{@link #setFragmentParameter(String, String)},</li>
	 * <li>{@link #addFragmentParameter(String, String, String)} and</li>
	 * <li>{@link #removeFragmentParameter(String)}.</li>
	 * </ul>
	 * 
	 * @param target
	 * @param form
	 */
	protected abstract void onBookmarkableSubmit(AjaxRequestTarget target, Form<?> form);

	/**
	 * Override to handle the error event. You can use
	 * <ul>
	 * <li>{@link #setFragmentParameter(String, String)},</li>
	 * <li>{@link #addFragmentParameter(String, String, String)} and</li>
	 * <li>{@link #removeFragmentParameter(String)}.</li>
	 * </ul>
	 * 
	 * @param target
	 * @param form
	 */
	protected abstract void onBookmarkableError(AjaxRequestTarget target, Form<?> form);

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
		response.render(UrlParametersReceivingBehavior.getJS(BookmarkableAjaxButton.class));
	}

}
