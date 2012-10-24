package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.reportbuilder;

import java.io.Serializable;

/**
 * typed attributes
 * @author mosmann
 *
 */
public interface IAttributes
{
	/**
	 * returns attribute with type T or defaultValue if not set
	 * @param defaultValue 
	 * @return attribute
	 */
	<T extends Enum<T>> T get(T defaultValue);

	/**
	 * returns attribute for id with type T
	 * @param id attribute id
	 * @param defaultValue default value
	 * @return attribute
	 */
	<T extends Serializable> T get(TypedAttribute<T> id, T defaultValue);

}
