package org.wicketstuff.foundation.util;

import org.apache.wicket.util.lang.Args;

/**
 * String related utilities.
 * @author ilkka
 *
 */
public class StringUtil {

	/**
	 * Transforms enum value to CSS class name.
	 * @param name - Enum value.
	 * @return transformed string.
	 */
	public static String EnumNameToCssClassName(String name) {
		Args.notNull(name, "name");
		return name.replace("_", "-").toLowerCase();
	}
}
