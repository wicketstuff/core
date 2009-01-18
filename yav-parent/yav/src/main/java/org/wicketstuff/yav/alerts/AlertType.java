package org.wicketstuff.yav.alerts;

import java.io.Serializable;

/**
 * @author Zenika
 */
public class AlertType implements Serializable {

	private static final long serialVersionUID = 2225814307024815433L;
	
	public static final AlertType CLASSIC = new AlertType("'classic'");
	public static final AlertType INLINE = new AlertType("'inline'");
	public static final AlertType INNER_HTML = new AlertType("'innerHtml'");
	public static final AlertType JS_VAR = new AlertType("'jsVar'");
	
	private final String type;
	
	private AlertType(String type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return type;
	}
}