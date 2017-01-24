package org.wicketstuff.minis.behavior.safari;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * Safari 5 loads pages persisted on back button, regardless of all {@code cache-control} headers.
 * <br>
 * When added to a page, this behavior will prevent this via a small JavaScript snippet which forces
 * a reload of the page.
 * 
 * @see http://stackoverflow.com/questions/5297122/preventing-cache-on-back-button-in-safari-5
 * 
 * @author svenmeier
 */
public class PersistedCacheBehavior extends Behavior {

	public static final ResourceReference REF = new JavaScriptResourceReference(PersistedCacheBehavior.class, "script.js");

	private PersistedCacheBehavior() {
	}
	
	@Override
	public void bind(Component component) {
		if ((component instanceof Page) == false) {
			throw new IllegalArgumentException("must be bound to a page");
		}
	}
	
	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		response.render(JavaScriptHeaderItem.forReference(REF));
	}
	
	public static PersistedCacheBehavior prevent() {
		return new PersistedCacheBehavior(); 
	}
}
