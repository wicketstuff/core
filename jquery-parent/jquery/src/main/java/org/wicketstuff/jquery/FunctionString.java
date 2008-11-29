package org.wicketstuff.jquery;

import java.io.Serializable;

/**
 * 
 * @author Edvin Syse <edvin@sysedata.no>
 *
 */
public class FunctionString implements Serializable {
	private String value;

	public FunctionString(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override public String toString() {
		return this.value;
	}

}
