package org.wicketstuff.push.cometd;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.push.IChannelTarget;

/**
 * This merthod is a wrapper of {@link AjaxRequestTarget} on {@link IChannelTarget}
 * used in {@link CometdBehavior}
 * 
 * 
 * @author Vincent Demay
 *
 */
public class CometdTarget implements IChannelTarget{

	private AjaxRequestTarget target;
	
	public CometdTarget(AjaxRequestTarget target) {
		super();
		this.target = target;
	}

	public void addComponent(Component component) {
		target.addComponent(component);
	}

	public void addComponent(Component component, String markupId) {
		target.addComponent(component, markupId);
	}

	public void appendJavascript(String javascript) {
		target.appendJavascript(javascript);
	}

	public void focusComponent(Component component) {
		target.focusComponent(component);
	}

	public void prependJavascript(String javascript) {
		target.prependJavascript(javascript);
	}

}
