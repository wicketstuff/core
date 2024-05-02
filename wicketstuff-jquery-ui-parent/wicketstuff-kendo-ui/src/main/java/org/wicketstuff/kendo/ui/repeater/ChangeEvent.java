package org.wicketstuff.kendo.ui.repeater;

import org.wicketstuff.jquery.core.JQueryEvent;
import org.wicketstuff.jquery.core.utils.RequestCycleUtils;

import com.github.openjson.JSONArray;

/**
 * Provides an event object that will be broadcasted by the {@code OnChangeAjaxBehavior} callback
 * 
 * @author Sebastien Briquet - sebfz1
 */
public class ChangeEvent extends JQueryEvent
{
	private final JSONArray items;

	public ChangeEvent()
	{
		String input = RequestCycleUtils.getQueryParameterValue("items").toString();

		this.items = new JSONArray(input);
	}

	public JSONArray getItems()
	{
		return this.items;
	}
}
