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
package org.wicketstuff.dojo11.dojofx;


import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.dojo11.templates.DojoPackagedTextTemplate;
import org.wicketstuff.dojo11.util.JavaScriptEvent;

/**
 * This classacts as an AjaxHandler an can be added to components. It adds a
 * dojo.lfx.html wiper to the class which reactes to a target component's ONCLICK
 * method.
 * 
 * TODO: We wanted to make a generic wiper that could also react to
 * ONMOUSEOVER/ONMOUSEOUT but the current dojoimplementation made our version
 * very unstable. If you want to help with this, or hear the problems we ran
 * into please give a yell on the mailing list. TODO: dojo.lfx.html currently
 * only supports top-down wiping, so down-top wiping and horizontal wiping is
 * currently not supported. We have however, requested this on the Dojo
 * animation wishlist. TODO: streamlining javascript handling: see
 * renderHeadContribution(HtmlHeaderContainer container). TODO: Due to problems
 * with unique javascript function/variable naming (can't use componentpath for
 * vars and function names) wiper can theoretically not work (yet untested...)
 * with ListItems. The only thing unique about ListItems is their component
 * path....
 * 
 * @author Marco van de Haar
 * @author Ruud Booltink
 * 
 */
public class FxToggler extends AbstractAnimationBehavior
{
	/**
	 * 
	 */
	private static final String FUNCTION_CONNECT_FX_TOGGLER = "connectFXToggler";
	/**
	 * 
	 */
	private static final String FX_WIPER_HIDDEN_CLASS = "FXWiperHidden";
	/**
	 * 
	 */
	private static final String FX_WIPER_SHOWN_CLASS = "FXWiperShown";
	private static final String JAVASCRIPT;
	private static final String JAVASCRIPT_ID;

	private final ToggleEvents _events;
	private final ToggleAnimations _animations;
	private final Component _trigger;
	private boolean _startShown;

	static {
		DojoPackagedTextTemplate template = new DojoPackagedTextTemplate(FxToggler.class, FxToggler.class.getSimpleName()+".js");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("wiperHiddenClass", FX_WIPER_HIDDEN_CLASS);
		map.put("wiperShownClass", FX_WIPER_SHOWN_CLASS);
		map.put("connectFXTogglerFunction", FUNCTION_CONNECT_FX_TOGGLER);
		JAVASCRIPT = template.asString(map);
		JAVASCRIPT_ID = template.getStaticKey();
	}
	
	/**
	 * Constructor with custom startDisplay
	 * 
	 * @param events
	 * @param animations
	 * @param trigger
	 *            Component which'ONCLICK triggers teh wiping effect
	 * @param startDisplay
	 *            whether the wiping component starts wiped in or wiped
	 *            out(wiped in means vissible)
	 */
	public FxToggler(ToggleEvents events, ToggleAnimations animations, Component trigger, boolean startDisplay)
	{	
		_events = events;
		_animations = animations;
		_trigger = trigger;
		_startShown = startDisplay;
		_trigger.setOutputMarkupId(true);
	}

	/**
	 * @see org.wicketstuff.dojo11.AbstractRequireDojoBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		if (getComponent().isVisibleInHierarchy()) {
			response.renderJavascript(JAVASCRIPT, JAVASCRIPT_ID);
			response.renderOnDomReadyJavascript(FUNCTION_CONNECT_FX_TOGGLER+"('"+getTrigger().getMarkupId()+"', '"+
					getComponent().getMarkupId()+"', '"+getEvents().getShow()+"', '"+getEvents().getHide()+"', "+getStartShown()+", "+
					getAnimations().getShowFunction(getComponent())+", "+getAnimations().getHideFunction(getComponent())+")");
		}
	}

	/**
	 * @return events
	 */
	public ToggleEvents getEvents()
	{
		return _events;
	}

	/**
	 * @return animation functions
	 */
	public ToggleAnimations getAnimations()
	{
		return _animations;
	}
	
	/**
	 * @return trigger component
	 */
	public Component getTrigger() {
		return _trigger;
	}
	
	/**
	 * @return display component at beginning
	 */
	public boolean getStartShown() {
		return _startShown;
	}
	
	/**
	 * @param shown
	 */
	public void setStartShown(boolean shown)
	{
		_startShown = shown;
	}

	/*
	 * @see wicket.AjaxHandler#getBodyOnloadContribution()
	 */
	protected String getBodyOnloadContribution()
	{
		// You might want to add dojo.event.connect calls here to bind
		// animations to functions.
		// * currently however, this is done by Attributemodifiers in onBind();

		// return "init();";
		return null;
	}

	/**
	 * method to remove colons from string s.
	 * @param s string to parse
	 * @return string wichtout colons
	 */
	public String removeColon(String s) {
		  StringTokenizer st = new StringTokenizer(s,":",false);
		  String t="";
		  while (st.hasMoreElements()) t += st.nextElement();
		  return t;
	  }
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see wicket.AjaxHandler#onBind()
	 */
	protected void onBind()
	{
		Component component = getComponent();
		component.setOutputMarkupId(true);
		
		getTrigger().add(new AttributeAppender("class", true, new Model((_startShown ? FX_WIPER_SHOWN_CLASS : FX_WIPER_HIDDEN_CLASS)), " "));
				
		ToggleEvents e = getEvents();
		
		super.onBind();
	}

	
	
	/**
	 * @see org.wicketstuff.dojo11.AbstractRequireDojoBehavior#setRequire(org.wicketstuff.dojo11.AbstractRequireDojoBehavior.RequireDojoLibs)
	 */
	@Override
	public void setRequire(RequireDojoLibs libs)
	{
		getAnimations().getHideAnim().setRequire(libs);
		getAnimations().getShowAnim().setRequire(libs);
	}

	/**
	 * @param target
	 */
	public void hide(final AjaxRequestTarget target) {
		target.prependJavascript(getHideScript());
	}

	/**
	 * @return JavaScript snippet to hide component
	 */
	public String getHideScript()
	{
		return "dojo.publish('/"+getComponent().getMarkupId()+"/toggle/hide',[]);";
	}
	
	/**
	 * @param target
	 */
	public void show(final AjaxRequestTarget target) {
		target.prependJavascript(getShowScript());
	}

	/**
	 * @return JavaScript snippet to show component
	 */
	public String getShowScript()
	{
		return "dojo.publish('/"+getComponent().getMarkupId()+"/toggle/show',[]);";
	}

	/**
	 * @return JavaScript snippet to show component
	 */
	public String getToggleScript()
	{
		return "dojo.publish('/"+getComponent().getMarkupId()+"/toggle/toggle',[]);";
	}
	
	/**
	 * @param event hide triggering event
	 * @return behavior to hide toggled component on event
	 */
	public IBehavior newHideBehavior(JavaScriptEvent event) {
		return new AttributeAppender(event.getName(), true, new PropertyModel(this, "hideScript"),";");
	}
	
	/**
	 * @param event show triggering event
	 * @return behavior to show toggled component on event
	 */
	public IBehavior newShowBehavior(JavaScriptEvent event) {
		return new AttributeAppender(event.getName(), true, new PropertyModel(this, "showScript"),";");
	}
	
	/**
	 * @param event toggle triggering event
	 * @return behavior to toggle toggled component on event
	 */
	public IBehavior newToggleBehavior(JavaScriptEvent event) {
		return new AttributeAppender(event.getName(), true, new PropertyModel(this, "toggleScript"),";");
	}
	
	/**
	 * @see org.wicketstuff.dojo11.dojofx.AbstractAnimationBehavior#getAnimation()
	 */
	@Override
	public Animation getAnimation() {
		return getStartShown() ? getAnimations().getHideAnim() : getAnimations().getShowAnim();
	}

}
