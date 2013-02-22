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
package org.wicketstuff.objectautocomplete;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.IAutoCompleteRenderer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.CoreLibrariesContributor;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Behaviour for object auto completion using a slightly modified variant of {@see
 * org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteBehavior}
 *
 * An (hidden) element is required to store the object id which has been selected.
 *
 * The type parameter is the type of the object to be rendered (not it's id)
 *
 * @author roland
 * @since May 18, 2008
 */
public class ObjectAutoCompleteBehavior<O> extends AbstractAutoCompleteBehavior
{
	private static final long serialVersionUID = 1L;
	private static final ResourceReference OBJECTAUTOCOMPLETE_JS = new JavaScriptResourceReference(
		ObjectAutoCompleteBehavior.class, "wicketstuff-objectautocomplete.js");
	// Our version of 'wicket-autocomplete.js', with the patch from WICKET-1651
	private static final ResourceReference AUTOCOMPLETE_OBJECTIFIED_JS = new JavaScriptResourceReference(
		ObjectAutoCompleteBehavior.class, "wicketstuff-dropdown-list.js");

	// Element holding the object id as value
	private final Component objectElement;

	private final ObjectAutoCompleteCancelListener cancelListener;
	private final AutoCompletionChoicesProvider<O> choicesProvider;

	// one of this renderer must be set with the response renderer taking precedence
	private final IAutoCompleteRenderer<O> renderer;
	private final ObjectAutoCompleteResponseRenderer<O> responseRenderer;

	// =====================================================================================================
	// Specific configuration options:

	// tag name which indicates the possible choices (typically this is a "li")
	private final String choiceTagName;

	// alignment of menu
	private final ObjectAutoCompleteBuilder.Alignment alignment;

	// width of drop down
	private long width = 0;

	// delay for how long to wait for the update
	private final long delay;

	// whether search should be triggered on paste event
	private final boolean searchOnPaste;

	<I extends Serializable> ObjectAutoCompleteBehavior(Component pObjectElement,
		ObjectAutoCompleteBuilder<O, I> pBuilder)
	{
		renderer = pBuilder.autoCompleteRenderer;
		settings = new AutoCompleteSettings().setMaxHeightInPx(pBuilder.maxHeightInPx)
			.setPreselect(pBuilder.preselect)
			.setShowListOnEmptyInput(pBuilder.showListOnEmptyInput);
		choiceTagName = pBuilder.choiceTagName;
		width = pBuilder.width;
		alignment = pBuilder.alignement;

		objectElement = pObjectElement;
		responseRenderer = pBuilder.autoCompleteResponseRenderer;
		cancelListener = pBuilder.cancelListener;
		choicesProvider = pBuilder.choicesProvider;
		searchOnPaste = pBuilder.searchOnPaste;
		delay = pBuilder.delay;
	}

	/**
	 * Temporarily solution until patch from WICKET-1651 is applied. Note, that we avoid a call to
	 * super to avoid the initialization in the direct parent class, but we have to copy over all
	 * other code from the parent,
	 *
	 * @param response
	 *            response to write to
	 */
	@Override
	public void renderHead(Component c, IHeaderResponse response)
	{
		abstractDefaultAjaxBehaviour_renderHead(c, response);
		initHead(response);
	}

    @Override
    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
        super.updateAjaxAttributes(attributes);
        attributes.setWicketAjaxResponse(false);
        attributes.setDataType("text");
    }

    @Override
	protected void onRequest(final String input, RequestCycle requestCycle)
	{
		IRequestHandler target = new IRequestHandler()
		{
			public void respond(IRequestCycle requestCycle)
			{
				WebResponse response = (WebResponse)requestCycle.getResponse();

				// Determine encoding
				final String encoding = Application.get()
					.getRequestCycleSettings()
					.getResponseRequestEncoding();
				response.setContentType("text/xml; charset=" + encoding);

				// Make sure it is not cached by a
				response.disableCaching();

				Iterator<O> comps = getChoices(input);
				if (responseRenderer != null)
				{
					// there is a dedicated renderer configured
					responseRenderer.onRequest(comps, response, input);
				}
				else
				{
					renderer.renderHeader(response);
					int renderedObjects = 0;
					while (comps.hasNext())
					{
						final O comp = comps.next();
						renderer.render(comp, response, input);
						renderedObjects++;
					}
					renderer.renderFooter(response, renderedObjects);
				}
			}

			public void detach(IRequestCycle requestCycle)
			{
			}

		};
		requestCycle.scheduleRequestHandlerAfterCurrent(target);
	}

	// Copied over from AbstractDefaultAjaxBehaviour.renderHead() until patch
	// in WICKET-1651 gets applied
	private void abstractDefaultAjaxBehaviour_renderHead(Component component, IHeaderResponse response)
	{
		CoreLibrariesContributor.contributeAjax(component.getApplication(), response);
	}

	/**
	 * Initialize response with our own java script
	 *
	 * @param response
	 *            response to write to
	 */
	protected void initHead(IHeaderResponse response)
	{
		response.render(JavaScriptHeaderItem.forReference(AUTOCOMPLETE_OBJECTIFIED_JS));
		response.render(JavaScriptHeaderItem.forReference(OBJECTAUTOCOMPLETE_JS));
		final String id = getComponent().getMarkupId();
		String initJS = String.format("new Wicketstuff.ObjectAutoComplete('%s','%s','%s',%s);", id,
			objectElement.getMarkupId(), getCallbackUrl(), getSettings());
		response.render(OnDomReadyHeaderItem.forScript(initJS));
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		if (cancelListener != null)
		{
			final String keypress =
					"if (event) { var kc=Wicket.Event.keyCode(event); if (kc==27) {" +
						"Wicket.Ajax.get({'u': '" + getCallbackUrl() + "&cancel=true&force=true'});" +
						"return false;" +
					"} else if (kc==13) " +
							"return false; " +
					"else return true;}";
			tag.put("onkeypress", keypress);

//			final String onblur = "Wicket.Ajax.get({'u': '" + getCallbackUrl() + "&cancel=true' }); return false;";
//			tag.put("onblur", onblur);
		}
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		RequestCycle requestCycle = RequestCycle.get();
		boolean cancel = requestCycle.getRequest()
			.getRequestParameters()
			.getParameterValue("cancel")
			.toBoolean();
		boolean force = requestCycle.getRequest()
			.getRequestParameters()
			.getParameterValue("force")
			.toBoolean();

		if (cancelListener != null && cancel)
		{
			cancelListener.searchCanceled(target, force);
		}
		else
		{
			super.respond(target);
		}
	}

	protected Iterator<O> getChoices(String input)
	{
		return choicesProvider.getChoices(input);
	}

	// Create settings
	private CharSequence getSettings()
	{
		StringBuilder builder = new StringBuilder(constructSettingsJS());
		if (choiceTagName != null || alignment != null || width != 0)
		{
			// remove trailing "}"
			builder.setLength(builder.length() - 1);
			if (choiceTagName != null)
			{
				builder.append(",choiceTagName: '").append(choiceTagName.toUpperCase()).append("'");
			}
			if (alignment != null)
			{
				builder.append(",align: '")
					.append(
						alignment == ObjectAutoCompleteBuilder.Alignment.LEFT ? "left" : "right")
					.append("'");
			}
			if (width != 0)
			{
				builder.append(",width: ").append(width);
			}
			if (delay != 0)
			{
				builder.append(",delay: ").append(delay);
			}
			if (searchOnPaste)
			{
				builder.append(",searchOnPaste: true");
			}
			builder.append("}");
		}
		return builder;
	}
}
