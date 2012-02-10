/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.jquery.block;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.jquery.JQueryBehavior;

public abstract class BlockingAjaxLink<T> extends AjaxLink<T>
{
	private static final long serialVersionUID = 1L;

	public static final ResourceReference BLOCK_JS = new PackageResourceReference(
		BlockingAjaxLink.class, "jquery.blockUI.js");

	final BlockOptions options;

	public BlockingAjaxLink(final String id, BlockOptions options)
	{
		super(id, null);
		this.options = options;
	}

	public BlockingAjaxLink(final String id, String message)
	{
		this(id, new BlockOptions().setMessage(message));
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		response.renderJavaScriptReference(JQueryBehavior.JQUERY_JS);
		response.renderJavaScriptReference(BLOCK_JS);
	}

	/**
	 * Returns ajax call decorator that will be used to decorate the ajax call.
	 * 
	 * @return ajax call decorator
	 */
	@Override
	protected IAjaxCallDecorator getAjaxCallDecorator()
	{
		return new IAjaxCallDecorator()
		{
			private static final long serialVersionUID = 1L;

			public CharSequence decorateScript(Component component, CharSequence script)
			{
				StringBuilder js = new StringBuilder();
				CharSequence sel = getBlockElementsSelector();
				if (sel != null)
				{
					js.append("$('").append(sel).append("').block( ");
				}
				else
				{
					js.append("$.blockUI( ");
				}
				return js.append(options.toString()).append(" ); ").append(script);
			}

			public CharSequence decorateOnSuccessScript(Component component, CharSequence script)
			{
				return script;
			}

			public CharSequence decorateOnFailureScript(Component component, CharSequence script)
			{
				return script;
			}
		};
	}

	public CharSequence getBlockElementsSelector()
	{
		return null;
	}

	@Override
	final public void onClick(final AjaxRequestTarget target)
	{
		doClick(target);

		CharSequence sel = getBlockElementsSelector();
		if (sel != null)
		{
			target.appendJavaScript("$('" + sel + "').unblock(); ");
		}
		else
		{
			target.appendJavaScript("$.unblockUI(); ");
		}
	}

	public abstract void doClick(final AjaxRequestTarget target);
}
