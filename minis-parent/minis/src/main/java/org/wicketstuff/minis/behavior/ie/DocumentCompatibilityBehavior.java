package org.wicketstuff.minis.behavior.ie;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.http.WebResponse;

/**
 * Force a compatibility mode on <em>Internet Explorer</em>.
 * <br>
 * Adds a {@code X-UA-Compatibility}-header to the response which takes precedence over
 * browser settings, "intranet" exclusions, {@code <meta>}-tags and any <em>quirk</em>-modus
 * detection.
 * 
 * @see #ieEdge()       
 *   
 * @author svenmeier
 */
public class DocumentCompatibilityBehavior extends Behavior {
	
	private static final String X_UA_COMPATIBLE = "X-UA-Compatible";
	
	private final String content;

	/**
	 * Default using latest <em>standard</em> compatibility.
	 */
	public DocumentCompatibilityBehavior() {
		this.content = "IE=Edge";
	}
	
	public DocumentCompatibilityBehavior(final String content) {
		this.content = content;
	}

	@Override
	public void beforeRender(Component component) {
		
		Response response = component.getResponse();
		if (response instanceof WebResponse) {
			((WebResponse)response).addHeader(X_UA_COMPATIBLE, content);
		}
	}
	
	public static DocumentCompatibilityBehavior ieEdge() {
		return new DocumentCompatibilityBehavior();
	}
	
	public static DocumentCompatibilityBehavior ie11() {
		return new DocumentCompatibilityBehavior("IE=11");
	}
	
	public static DocumentCompatibilityBehavior ie10() {
		return new DocumentCompatibilityBehavior("IE=10");
	}
	
	public static DocumentCompatibilityBehavior ie9() {
		return new DocumentCompatibilityBehavior("IE=9");
	}
	
	public static DocumentCompatibilityBehavior ie8() {
		return new DocumentCompatibilityBehavior("IE=8");
	}
	
	public static DocumentCompatibilityBehavior ieEmulate11() {
		return new DocumentCompatibilityBehavior("IE=Emulate11");
	}
	
	public static DocumentCompatibilityBehavior ieEmulate10() {
		return new DocumentCompatibilityBehavior("IE=Emulate10");
	}
	
	public static DocumentCompatibilityBehavior ieEmulate9() {
		return new DocumentCompatibilityBehavior("IE=Emulate9");
	}
	
	public static DocumentCompatibilityBehavior ieEmulate8() {
		return new DocumentCompatibilityBehavior("IE=Emulate8");
	}
}