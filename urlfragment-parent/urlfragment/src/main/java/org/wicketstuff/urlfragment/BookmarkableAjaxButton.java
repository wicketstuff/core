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
 * You can set or remove parameters using {@link #setFragmentParameter(String, Object)} or
 * {@link #removeFragmentParameter(String)} during your handling of the submit and error event.
 * </p>
 * 
 * @author Martin Knopf
 */
public abstract class BookmarkableAjaxButton extends AjaxButton implements IBookmarkableComponent
{
	private static final long serialVersionUID = 1L;
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
	protected void onSubmit(AjaxRequestTarget target)
	{
		this.target = target;
		this.onBookmarkableSubmit(target);
		this.target = null;
	}

	@Override
	protected void onError(AjaxRequestTarget target)
	{
		this.target = target;
		this.onBookmarkableError(target);
		this.target = null;
	}

	/**
	 * Override to handle the submit event. You can use {@link #urlFragment()} inside this method.
	 * 
	 * @param target {@link AjaxRequestTarget} can be used to perform update
	 */
	protected abstract void onBookmarkableSubmit(AjaxRequestTarget target);

	/**
	 * Override to handle the error event. You can use {@link #urlFragment()} inside this method.
	 * 
	 * @param target {@link AjaxRequestTarget} can be used to perform update
	 */
	protected abstract void onBookmarkableError(AjaxRequestTarget target);

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
		return new UrlFragment(this.target);
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.render(UrlParametersReceivingBehavior.getJS(BookmarkableAjaxButton.class));
	}

}
