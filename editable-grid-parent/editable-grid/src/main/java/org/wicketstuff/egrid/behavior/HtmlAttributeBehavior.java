package org.wicketstuff.egrid.behavior;

import java.util.Map.Entry;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.value.IValueMap;
import org.wicketstuff.egrid.attribute.Attribute;
import org.wicketstuff.egrid.attribute.HTMLAttribute;

public class HtmlAttributeBehavior extends Behavior
{

	private static final long serialVersionUID = 1L;

	private final Attribute htmlAttribute;

	public HtmlAttributeBehavior(Attribute htmlAttribute)
	{
		if (htmlAttribute == null)
		{
			throw new IllegalArgumentException("Argument [attribute] cannot be null");
		}
		this.htmlAttribute = htmlAttribute;

	}


	@Override
	public void onComponentTag(final Component component, final ComponentTag tag)
	{

		if (isEnabled(component))
		{
			for (Entry<String, String> entry : htmlAttribute.attributeEntries())
			{
				addHTMLAttribute(entry.getKey(), entry.getValue(), tag.getAttributes());
			}
		}
	}

	private void addHTMLAttribute(String key, final String value, final IValueMap attributes)
	{

		if (HTMLAttribute.CLASS.equals(key))
		{

			if (attributes.containsKey(HTMLAttribute.CLASS))
			{
				key = newClassValue(attributes.get(HTMLAttribute.CLASS).toString(), key);
			}
		}

		attributes.put(key, key);
	}

	private String newClassValue(final String currentValue, final String appendValue)
	{
		StringBuffer value = new StringBuffer();

		if (!Strings.isEmpty(currentValue))
		{
			value.append(currentValue).append(" ");
		}
		value.append(appendValue);

		return value.toString();
	}

	public Attribute getAttribute()
	{
		return htmlAttribute;
	}
}
