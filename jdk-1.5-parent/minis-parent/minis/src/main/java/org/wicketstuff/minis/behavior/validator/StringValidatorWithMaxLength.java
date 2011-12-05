package org.wicketstuff.minis.behavior.validator;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.validation.validator.StringValidator;

/**
 * Similar to {@link StringValidator}-s but these add maxlength attribute to the html tag. If you
 * want to see this functionality in Apache Wicket vote on
 * https://issues.apache.org/jira/browse/WICKET-1310
 * 
 * @author akiraly
 */
public class StringValidatorWithMaxLength
{

	/**
	 * "maxlength" standard html input attribute name
	 */
	public static final String MAX_LENGTH = "maxlength";

	/**
	 * Adds a maxlength attribute to the tag.
	 * 
	 * @param component
	 *            owning wicket component
	 * @param tag
	 *            tag to add to
	 * @param maxLength
	 *            attribute value
	 */
	public static void addMaxLengthToTag(Component component, ComponentTag tag, int maxLength)
	{
		tag.put(MAX_LENGTH, maxLength);
	}

	/**
	 * Works like {@link StringValidator#exactLength(int)} but the returned validator is also adding
	 * maxlength attribute to the html tag.
	 * 
	 * @param length
	 *            the required length of the string
	 * 
	 * @return the requested <code>StringValidator</code>
	 */
	public static StringValidator exactLength(final int length)
	{
		return new StringValidator(length, length)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onComponentTag(Component component, ComponentTag tag)
			{
				super.onComponentTag(component, tag);

				addMaxLengthToTag(component, tag, length);
			}
		};
	}

	/**
	 * Works like {@link StringValidator#lengthBetween(int, int)} but the returned validator is also
	 * adding maxlength attribute to the html tag.
	 * 
	 * @param minimum
	 *            the minimum length of the string
	 * @param maximum
	 *            the maximum length of the string
	 * 
	 * @return the requested <code>StringValidator</code>
	 */
	public static StringValidator lengthBetween(int minimum, int maximum)
	{
		return new StringValidator(minimum, maximum)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onComponentTag(Component component, ComponentTag tag)
			{
				super.onComponentTag(component, tag);

				addMaxLengthToTag(component, tag, getMaximum());
			}
		};
	}

	/**
	 * Works like {@link StringValidator#maximumLength(int)} but the returned validator is also
	 * adding maxlength attribute to the html tag.
	 * 
	 * @param maximum
	 *            the maximum length of the string
	 * 
	 * @return the requested <code>StringValidator</code>
	 */
	public static StringValidator maximumLength(int maximum)
	{
		return new StringValidator(null, maximum)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onComponentTag(Component component, ComponentTag tag)
			{
				super.onComponentTag(component, tag);

				addMaxLengthToTag(component, tag, getMaximum());
			}
		};
	}
}
