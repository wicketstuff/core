package org.wicketstuff.scriptaculous;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.behavior.AbstractAjaxBehavior;


/**
 * Helper class for programatically constructing javascript.
 * 
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public class JavascriptBuilder
{
	private StringBuffer buffer = new StringBuffer();

	public void addLine(String line)
	{
		buffer.append(line).append("\n");
	}

	public String buildScriptTagString()
	{
		return "\n<script type=\"text/javascript\">\n" + toJavascript() + "</script>\n";
	}

	public String toJavascript()
	{
		return buffer.toString();
	}

	public String formatAsJavascriptHash(Map options)
	{
		if (options.isEmpty())
		{
			return "{}";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		for (Iterator iter = options.keySet().iterator(); iter.hasNext();)
		{
			String key = (String)iter.next();
			Object value = options.get(key);

			buffer.append("\n");
			buffer.append("  ").append(key).append(": ");
			buffer.append(formatJavascriptValue(value));

			if (iter.hasNext())
			{
				buffer.append(", ");
			}
		}
		buffer.append("\n");
		buffer.append("}");
		return buffer.toString();
	}

	private String formatJavascriptValue(Object value)
	{
		if (value instanceof CharSequence)
		{
			return "'" + (CharSequence)value + "'";
		}
		if (value instanceof Map)
		{
			return formatAsJavascriptHash((Map)value);
		}
		if (value instanceof Boolean)
		{
			return ((Boolean)value).toString();
		}
		if (value instanceof JavascriptFunction)
		{
			return ((JavascriptFunction)value).getFunction();
		}
		return value.toString();
	}

	public void addOptions(Map options)
	{
		addLine(formatAsJavascriptHash(options));
	}

	public static class JavascriptFunction implements Serializable
	{
		private static final long serialVersionUID = 1L;
		private String function;

		public JavascriptFunction(String function)
		{
			this.function = function;
		}

		public String getFunction()
		{
			return function;
		}
	}
	
	/**
	 * Convenience {@link JavascriptFunction} for performing a wicket ajax call to an {@link AjaxEventBehavior}
	 */
	public static class AjaxCallbackJavascriptFunction extends JavascriptFunction {
		private static final long serialVersionUID = 1L;
		public AjaxCallbackJavascriptFunction(AbstractAjaxBehavior behavior) {
			super("function() { wicketAjaxGet('" + behavior.getCallbackUrl() + "'); }");
		}
	}
}
