package com.googlecode.wicket.kendo.ui.repeater;

import com.github.openjson.JSONArray;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;

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
