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
package org.wicketstuff.datatable_autocomplete.panel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.datatable_autocomplete.util.StringUtils;


/**
 * @author mocleiri
 * 
 *         Originally the intent was to use this to allow multiple fields to
 *         drive search for results.
 * 
 *         However it has been modified so that it will work with just a single
 *         field.
 * 
 *         The idea is that changes to the search field will be encoded onto the
 *         url which will then be processed accordingly without having to submit
 *         anything.
 * 
 * 
 */
public class AJAXAutoCompleteBehavior extends
		AjaxEventBehavior {

	/**
	 * @param event
	 */
	public AJAXAutoCompleteBehavior(String event) {

		super(event);
		
	
	}

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3314379544248117565L;

	private static final Logger	log					= LoggerFactory
															.getLogger(AJAXAutoCompleteBehavior.class);


	// controls how the dependencies are appended to the behaviour callback URL and then parsed
	// on the server side within the behaviour processing action.
	private AutoCompleteDependencyProcessor	dependencyProcessor;

	
	/**
		 * 
		 */
	public AJAXAutoCompleteBehavior(String event,
			AutoCompleteDependencyProcessor processor) {

		super(event);
		this.dependencyProcessor = processor;


	}
	
	

	
	/**
	 * @param dependencyProcessor the dependencyProcessor to set
	 */
	protected void setDependencyProcessor(
			AutoCompleteDependencyProcessor dependencyProcessor) {
	
		this.dependencyProcessor = dependencyProcessor;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax
	 * .AjaxRequestTarget)
	 */
	@Override
	protected final void onEvent(AjaxRequestTarget target) {

		/*
		 * First we apply any of the parameters to the data provider
		 */
		Request request = RequestCycle.get().getRequest();

		
		// delegate the action to the processor.
		dependencyProcessor.onAjaxUpdate(request, target);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#getFailureScript()
	 */
	@Override
	protected CharSequence getFailureScript() {

		return "Wicket.Log.info ('on failure script');";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#getSuccessScript()
	 */
	@Override
	protected CharSequence getSuccessScript() {

		return "Wicket.Log.info ('on success script');";
	}

	/*
	 * (non-Javadoc)address=
	 * 
	 * @see
	 * org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#getCallbackScript(
	 * boolean)
	 */
	@Override
	protected final CharSequence getCallbackScript(boolean onlyTargetActivePage) {

		/*
		 * Encode the callback script appending to the url the current client side value of the component values.
		 */
		CharSequence baseUrl = super.getCallbackUrl();

		CharSequence callbackScript = baseUrl;

		List<String> parameterList = new ArrayList<String>();

		for (String parameter : dependencyProcessor
				.getQueryParameterToComponentMap().keySet()) {

			Component c = dependencyProcessor.getQueryParameterToComponentMap()
					.get(parameter);

			String adjustedUrl = "&" + parameter + "='+Wicket.$('"
					+ c.getMarkupId() + "').value";

			parameterList.add(adjustedUrl);

		}

		
		
		callbackScript = StringUtils.join (new StringBuffer(callbackScript), parameterList, "+'").toString();

		String script = "wicketAjaxGet('" + callbackScript + ");";

		if (dependencyProcessor.getThrottingDuration() != null)
			return AbstractDefaultAjaxBehavior.throttleScript(script,
					getComponent().getMarkupId(), dependencyProcessor.getThrottingDuration());
		else
			return script;

	}
	
	protected final void onComponentTag(final ComponentTag tag)
	{

		// call to super intentionally ommitted.
		
		// only add the event handler when the component is enabled.
		Component myComponent = getComponent();
		if (myComponent.isEnabled() && myComponent.isEnableAllowed())
		{
			
			List<String>eventScripts = new LinkedList<String>();
			
			eventScripts.add(getEventHandler().toString());
			
			addAdditionalJavaScript (eventScripts);
			
			tag.put(super.getEvent(), StringUtils.join(eventScripts, ";"));
		}
	}




	/**
	 * Allows subclasses to adjust the ordering of the scripts and to add in additional scripts.
	 * 
	 * @param eventScripts
	 */
	protected void addAdditionalJavaScript(List<String> eventScripts) {

		
	}

}
