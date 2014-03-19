package org.wicketstuff.yui.helper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class YuiAttribute implements Serializable
{
	private static final long serialVersionUID = 1L;

	Map<String, YuiProperty> propertyMap = new HashMap<String, YuiProperty>();

	public YuiAttribute()
	{
	}

	public void add(String element, YuiProperty attribute)
	{
		if (isValid(element, attribute))
		{
			propertyMap.put(element, attribute);
		}
	}

	public String getJsScript()
	{

		int count = 0;
		StringBuilder jsScript = new StringBuilder();
		for (String aKey : propertyMap.keySet())
		{
			if (count == 0)
			{
				jsScript.append(aKey).append(": {");
				YuiProperty aYuiProperty = propertyMap.get(aKey);
				jsScript.append(aYuiProperty.getJsScript()).append(" }");
			}
			else
			{
				jsScript.append(", ").append(aKey).append(": {");
				YuiProperty aYuiProperty = propertyMap.get(aKey);
				jsScript.append(aYuiProperty.getJsScript()).append(" }");
			}
			count++;
		}
		return jsScript.toString();
	}

	public Map getPropertyMap()
	{
		return propertyMap;
	}

	public String getReverseJsScript()
	{
		int count = 0;
		String jsScript = "";
		Set keySet = propertyMap.keySet();
		Iterator iter = keySet.iterator();
		while (iter.hasNext())
		{
			if (count == 0)
			{
				String aKey = (String)iter.next();
				jsScript = jsScript + aKey + ": {";
				YuiProperty aYuiProperty = propertyMap.get(aKey);
				jsScript = jsScript + aYuiProperty.getReverseJsScript() + " }";
			}
			else
			{
				String aKey = (String)iter.next();
				jsScript = jsScript + ", " + aKey + ": {";
				YuiProperty aYuiProperty = propertyMap.get(aKey);
				jsScript = jsScript + aYuiProperty.getReverseJsScript() + " }";
			}
			count++;
		}
		return jsScript;
	}

	public void setPropertyMap(Map<String, YuiProperty> propertyMap)
	{
		this.propertyMap = propertyMap;
	}

	/**
	 * validate that is valid key e.g. : borderWidth, lineHeight ....
	 * 
	 * @param element
	 * @param attribute
	 * @return
	 */
	private boolean isValid(String element, YuiProperty attribute)
	{
		// TODO Auto-generated method stub
		return true;
	}

}
