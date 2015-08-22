package org.wicketstuff.foundation.util;

import java.util.Arrays;
import java.util.StringTokenizer;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.StringValue;

/**
 * 
 * Helper class for HTML attributes.
 *
 */
public class Attribute {

	private static String DATA_OPTIONS_SEPARATOR = ";";
	
	/**
	 * Set class attribute value (overwrite)
	 * @param tag
	 * @param className
	 * @return
	 */
	public static ComponentTag setClass(ComponentTag tag, String className) {
		Args.notNull(tag, "tag");
		Args.notNull(className, "className");
		tag.put("class", className);
		return tag;
	}
	
	/**
	 * Append new value to class attribute
	 * @param tag
	 * @param className
	 * @return
	 */
	public static ComponentTag addClass(ComponentTag tag, String className) {
		Args.notNull(tag, "tag");
		Args.notNull(className, "className");
		if (!hasClass(tag, className)) {
			String value = tag.getAttribute("class");
			if (value == null) {
				tag.put("class", className);
			} else {
				tag.put("class", value + " " + className);
			}
		}
		return tag;
	}

	/**
	 * Remove single value from class attribute
	 * @param tag
	 * @param className
	 * @return
	 */
	public static ComponentTag removeClass(ComponentTag tag, String className) {
		Args.notNull(tag, "tag");
		Args.notNull(className, "className");
		if (hasClass(tag, className)) {
			tag.put("class", removeToken(tag.getAttribute("class"), className, " "));
		}
		return tag;
	}
	
	/**
	 * Check if class attribute has value
	 * @param tag
	 * @param className
	 * @return
	 */
	public static boolean hasClass(ComponentTag tag, String className) {
		Args.notNull(tag, "tag");
		String value = tag.getAttribute("class");
		if (value == null) {
			return false;
		}
		String[] existing = value.split(" ");
		return Arrays.asList(existing).contains(className);
	}

	/**
	 * Adds attribute without value
	 * @param tag
	 * @param attribute
	 * @return
	 */
	public static ComponentTag addAttribute(ComponentTag tag, String attribute) {
		Args.notNull(tag, "tag");
		Args.notNull(attribute, "attribute");
		String v = null;
		tag.put(attribute, StringValue.valueOf(v));
		return tag;
	}

	/**
	 * Adds attribute with given value
	 * @param tag
	 * @param attribute
	 * @param value
	 * @return
	 */
	public static ComponentTag addAttribute(ComponentTag tag, String attribute, String value) {
		Args.notNull(tag, "tag");
		Args.notNull(attribute, "attribute");
		Args.notNull(value, "value");
		if (!hasAttribute(tag, attribute)) {
			tag.put(attribute, value);
		} else {
			String previous = getAttribute(tag, attribute);
			tag.put(attribute, previous + " " + value);
		}
		return tag;
	}

	/**
	 * Adds attribute with given value
	 * @param tag
	 * @param attribute
	 * @param value
	 * @return
	 */
	public static ComponentTag addAttribute(ComponentTag tag, String attribute, boolean value) {
		Args.notNull(tag, "tag");
		Args.notNull(attribute, "attribute");
		Args.notNull(value, "value");
		tag.put(attribute, value);
		return tag;
	}

	/**
	 * Adds attribute with given value
	 * @param tag
	 * @param attribute
	 * @param value
	 * @return
	 */
	public static ComponentTag addAttribute(ComponentTag tag, String attribute, int value) {
		Args.notNull(tag, "tag");
		Args.notNull(attribute, "attribute");
		Args.notNull(value, "value");
		tag.put(attribute, value);
		return tag;
	}
	
	/**
	 * Removes attribute
	 * @param tag
	 * @param attribute
	 * @return
	 */
	public static ComponentTag removeAttribute(ComponentTag tag, String attribute) {
		Args.notNull(tag, "tag");
		Args.notNull(attribute, "attribute");
		tag.remove(attribute);
		return tag;
	}
	
	/**
	 * Test if tag contains attribute
	 * @param tag
	 * @param attribute
	 * @return
	 */
	public static boolean hasAttribute(ComponentTag tag, String attribute) {
		Args.notNull(tag, "tag");
		Args.notNull(attribute, "attribute");
		return tag.toString().contains(attribute);
	}

	/**
	 * Gets attribute value
	 * @param tag
	 * @param attribute
	 * @return
	 */
	public static String getAttribute(ComponentTag tag, String attribute) {
		Args.notNull(tag, "tag");
		Args.notNull(attribute, "attribute");
		return tag.getAttribute(attribute);
	}
	
	/**
	 * Sets data-options attribute value
	 * @param tag
	 * @param value
	 * @return
	 */
	public static ComponentTag setDataOptions(ComponentTag tag, String value) {
		Args.notNull(tag, "tag");
		Args.notNull(value, "value");
		tag.put("data-options", value + DATA_OPTIONS_SEPARATOR);
		return tag;
	}
	
	/**
	 * Adds to data-options attribute value
	 * @param tag
	 * @param value
	 * @return
	 */
	public static ComponentTag addDataOptions(ComponentTag tag, String value) {
		Args.notNull(tag, "tag");
		Args.notNull(value, "value");
		if (!hasDataOptions(tag, value)) {
			String current = tag.getAttribute("data-options");
			if (current == null) {
				tag.put("data-options", value + DATA_OPTIONS_SEPARATOR);
			} else {
				tag.put("data-options", current + value + DATA_OPTIONS_SEPARATOR);
			}
		}
		return tag;
	}

	/**
	 * Remove data-options value
	 * @param tag
	 * @param value
	 * @return
	 */
	public static ComponentTag removeDataOptions(ComponentTag tag, String value) {
		Args.notNull(tag, "tag");
		Args.notNull(value, "value");
		if (hasDataOptions(tag, value)) {
			tag.put("data-options", removeToken(tag.getAttribute("data-options"), value, DATA_OPTIONS_SEPARATOR));
		}
		return tag;
	}
	
	/**
	 * Tests whether data-options contains value
	 * @param tag
	 * @param value
	 * @return
	 */
	public static boolean hasDataOptions(ComponentTag tag, String value) {
		Args.notNull(tag, "tag");
		Args.notNull(value, "value");
		String current = tag.getAttribute("data-options");
		if (current == null) {
			return false;
		}
		String[] existing = current.split(";");
		return Arrays.asList(existing).contains(value);
	}	
	
	/**
	 * Removes token from attribute.
	 * @param attribute - Attribute value.
	 * @param remove - Token to remove.
	 * @param separator - Token separator.
	 * @return new attribute value.
	 */
	public static String removeToken(String attribute, String remove, String separator) {
		if (attribute == null || remove == null || separator == null) {
			return attribute;
		}
		StringTokenizer tokenizer = new StringTokenizer(attribute, separator);
		StringBuilder sb = new StringBuilder();
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (!token.isEmpty() && !token.equals(remove)) {
				if (sb.length() <= 0) {
					sb.append(token);
				} else {
					sb.append(separator + token);
				}
			}
		}
		return sb.toString();
	}
}
