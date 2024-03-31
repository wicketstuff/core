/*
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.openlayers.event;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.wicketstuff.openlayers.IOpenLayersMap;

public abstract class MoveEndListenerBehavior extends AbstractDefaultAjaxBehavior
{
	private static final long serialVersionUID = -3369444402534250143L;

	@Override
	public void renderHead(Component c, IHeaderResponse response)
	{
		super.renderHead(c, response);
		response.render(OnDomReadyHeaderItem.forScript(getJSaddListener()));
	}

	@Override
	protected void onBind()
	{
		if (!(getComponent() instanceof IOpenLayersMap))
		{
			throw new IllegalArgumentException("must be bound to Openlayers map interface");
		}
	}

	protected final IOpenLayersMap getOpenLayersMap()
	{
		return (IOpenLayersMap)getComponent();
	}

	/**
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected final void respond(AjaxRequestTarget target)
	{
		getOpenLayersMap().update(target);
		onMoveEnd(target);
	}

	public String getJSaddListener()
	{
		return getOpenLayersMap().getJSinvoke("addMoveEndListener('" + getCallbackUrl() + "')");
	}

	protected abstract void onMoveEnd(AjaxRequestTarget target);
}
