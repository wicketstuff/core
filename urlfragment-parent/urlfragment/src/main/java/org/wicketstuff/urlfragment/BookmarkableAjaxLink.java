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
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.IModel;

/**
 * This is an {@link AjaxLink} which will set an URL fragment parameter in the browser.
 * <p>
 * You can provide a default parameter to the constructors so it will be set automatically during
 * event handling. You can also set or remove parameters by yourself using
 * {@link #setFragmentParameter(String, Object)} or {@link #removeFragmentParameter(String)}.
 * </p>
 * 
 * @author Martin Knopf
 * 
 * @param <T> the type of Model object
 */
public abstract class BookmarkableAjaxLink<T> extends AjaxLink<T> implements IBookmarkableComponent
{
	private static final long serialVersionUID = 1L;

	private transient AjaxRequestTarget target = null;

	protected String defaultarameterName = "";
	protected String defaultParamterValue = "";

	/**
	 * Constructor.
	 * 
	 * @param id id of the link being created
	 */
	public BookmarkableAjaxLink(String id)
	{
		super(id);
	}

	/**
	 * Constructor.
	 * 
	 * @param id id of the link being created
	 * @param model {@link IModel} to be set as model object
	 */
	public BookmarkableAjaxLink(String id, IModel<T> model)
	{
		super(id, model);
	}

	/**
	 * Constructor. Sets given URL fragment parameter in the browser during the click event. Be
	 * aware that the given parameter name and value will not be set if they are empty.
	 * 
	 * @param id id of the link being created
	 * @param parameterName the name of the default parameter for this link
	 * @param parameterValue the value of the default parameter for this link
	 */
	public BookmarkableAjaxLink(String id, String parameterName, String parameterValue)
	{
		super(id);
		this.defaultarameterName = parameterName;
		this.defaultParamterValue = parameterValue;
	}

	/**
	 * Constructor. Sets given default URL fragment parameter during the click event. Be aware that
	 * the given parameter name and value will not be set if either one is empty and that the
	 * parameter will be overwritten if it already exists.
	 * 
	 * @param id id of the link being created
	 * @param model {@link IModel} to be set as model object
	 * @param parameterName the name of the default parameter for this link
	 * @param parameterValue the value of the default parameter for this link
	 */
	public BookmarkableAjaxLink(String id, IModel<T> model, String parameterName,
		String parameterValue)
	{
		super(id, model);
		this.defaultarameterName = parameterName;
		this.defaultParamterValue = parameterValue;
	}

	@Override
	public void onClick(AjaxRequestTarget target)
	{
		if (this.defaultarameterName != "" && this.defaultParamterValue != "")
		{
			urlFragment().putParameter(defaultarameterName, defaultParamterValue);
		}
		this.target = target;
		this.onBookmarkableClick(target);
		this.target = null;
	}

	/**
	 * Handles the click event. Your default URL fragment parameter was already set automatically if
	 * it wasn't empty. You can use {@link #urlFragment()} inside this method.
	 * 
	 * @param target {@link AjaxRequestTarget} can be used to perform update
	 */
	public abstract void onBookmarkableClick(AjaxRequestTarget target);

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
		response.render(UrlParametersReceivingBehavior.getJS(BookmarkableAjaxLink.class));
	}

}
