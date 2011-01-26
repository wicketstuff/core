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
package org.wicketstuff.dojo11;

import java.util.HashSet;
import java.util.Iterator;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * <p>
 * This behavior has to be extended to write a new Behavior (Handler) using
 * Dojo. Adding the required dojo libs (see <tt>dojo.require(...)</tt>) can
 * be achieved using the <code>setRequire()</code> method and adding the
 * required libs to the <tt>libs</tt> variable.
 * </p>
 * 
 * <p>
 * This behavior also takes care to add needed require on a ajaxRequestTarget
 * </p>
 * 
 * <p>
 * Since 1.3.0-beta<br/>
 * This method is not adapted to be used with widget, see {link AbstractDojoWidgetBehavior}.
 * This behavior only deals with dojoRequire statements and is not in charge of
 * the widget dojo parsing
 * </p>
 * 
 * @author vdemay
 */
public abstract class AbstractRequireDojoBehavior extends AbstractDefaultDojoBehavior {
	
	/**
	 * A class to deals with require
	 * @author vdemay
	 * 
	 */
	public class RequireDojoLibs extends HashSet<String> {

	}
	
	private RequireDojoLibs libs = new RequireDojoLibs();

	/**
	 * TODO : is there a way to put all dojo.require at the same place on the rendered page??????
	 * @see org.wicketstuff.dojo11.AbstractDefaultDojoBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		StringBuffer require = getRequire();

		//render dojo.require
		response.renderJavascript(require, AbstractRequireDojoBehavior.class.getName());
	}
	
	/**
	 * Method to generate list of the <code>dojo.require</code>
	 * Also called in {@link DojoTargetRefresherManager} 
	 * @return the stringBuffer containing the <code>dojo.require</code> list
	 */
	public final StringBuffer getRequire(){
		setRequire(libs); // will be implemented by childs
		StringBuffer require = new StringBuffer();

		Iterator<String> ite = libs.iterator();
		while (ite.hasNext()) {
			require.append("dojo.require(\"");
			require.append((String) ite.next());
			require.append("\");\n");
		}

		require.append("\n");
		
		return require;
	}

	/**
	 * allow subclass to register new Dojo require libs
	 * 
	 * @param libs
	 */
	public abstract void setRequire(RequireDojoLibs libs);

	/**
	 * this method is used to interpret dojoWidgets rendered via XMLHTTPRequest
	 * FIXME : in TargetRefresherMPanager differency AbstractRequire and DojoWidgetBehavior
	 */
	protected void onComponentRendered() {
		
		// if a Dojo Widget is rerender needs to run some javascript to refresh
		// it. TargetRefresherManager contains top level dojo widgets
		if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget) {
			AjaxRequestTarget target = (AjaxRequestTarget) RequestCycle.get().getRequestTarget();
			//and register listener
			DojoTargetRefresherManager mgr = DojoTargetRefresherManager.get();
			mgr.addComponent(getComponent());
			target.addListener(mgr);
			onComponentReRendered(target);
		}
	}
	

	/**
	 * Add Javascript scripts when a dojo component is Rerender in a
	 * {@link AjaxRequestTarget}
	 * 
	 * @param ajaxTarget
	 */
	public void onComponentReRendered(AjaxRequestTarget ajaxTarget) {

	}

}
