package org.wicketstuff.push.cometd;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.push.IChannelTarget;

/**
 * This merthod is a wrapper of {@link AjaxRequestTarget} on
 * {@link IChannelTarget} used in {@link CometdBehavior}.
 * 
 * @author Vincent Demay
 */
public class CometdTarget implements IChannelTarget {

	private final AjaxRequestTarget target;

	public CometdTarget(final AjaxRequestTarget target) {
		super();
		this.target = target;
	}

	public void addComponent(final Component component) {
		target.addComponent(component);
	}

	public void addComponent(final Component component, final String markupId) {
		target.addComponent(component, markupId);
	}

	public void appendJavascript(final String javascript) {
		target.appendJavascript(javascript);
	}

	public void focusComponent(final Component component) {
		target.focusComponent(component);
	}

	public void prependJavascript(final String javascript) {
		target.prependJavascript(javascript);
	}
}
