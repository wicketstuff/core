package org.wicketstuff.dojo11.push.cometd;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo11.push.IChannelTarget;

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
	
	/**
	 * Construct.
	 * @param target
	 */
	public CometdTarget(AjaxRequestTarget target) {
		super();
		this.target = target;
	}

	/**
	 * @see org.wicketstuff.dojo11.push.IChannelTarget#addComponent(org.apache.wicket.Component)
	 */
	public void addComponent(Component component) {
		target.addComponent(component);
	}
	/**
	 * @see org.wicketstuff.dojo11.push.IChannelTarget#addComponent(org.apache.wicket.Component, java.lang.String)
	 */
	public void addComponent(Component component, String markupId) {
		target.addComponent(component, markupId);
	}
	
	/**
	 * @see org.wicketstuff.dojo11.push.IChannelTarget#appendJavascript(java.lang.String)
	 */
	public void appendJavascript(String javascript) {
		target.appendJavascript(javascript);
	}

	/**
	 * @see org.wicketstuff.dojo11.push.IChannelTarget#focusComponent(org.apache.wicket.Component)
	 */
	public void focusComponent(Component component) {
		target.focusComponent(component);
	}

	/**
	 * @see org.wicketstuff.dojo11.push.IChannelTarget#prependJavascript(java.lang.String)
	 */
	public void prependJavascript(String javascript) {
		target.prependJavascript(javascript);
	}

	/**
	 * @return the wrapped {@link AjaxRequestTarget}
	 */
	public AjaxRequestTarget getTarget()
	{
		return target;
	}

	
}
