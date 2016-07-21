/*
 *  Copyright 2007 dwayne.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.wicketstuff.jquery.datepicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.wicketstuff.jquery.Options;

/**
 * see http://kelvinluck.com/assets/jquery/datePicker/v2/demo/documentation.html
 * 
 * @author dwayne (David Bernard)
 */
public class DatePickerOptions extends Options
{

	private static final long serialVersionUID = 1L;

	/* public static enum ShowHeader {NONE, SHORT, LONG,}; */
	public static enum VPos
	{
		TOP, BOTTOM
	};
	public static enum HPos
	{
		LEFT, RIGHT
	}

	/*
	 * Set true to override the javascript localized messages shown in the datepicker by dynamically
	 * getting the fieldnames from a SimpleDateFormat object set to the current locale of the
	 * component.
	 */
	public boolean dynamicLocalizedMessages = false;

	/**
	 * month (Number): The month to render when the date picker is opened (NOTE that months are zero
	 * based). Default is today's month.
	 */
	public DatePickerOptions month(int v)
	{
		set("month", v);
		return this;
	}

	/** year (Number): The year to render when the date picker is opened. Default is today's year. */
	public DatePickerOptions year(int v)
	{
		set("year", v);
		return this;
	}

	/** startDate (String): The first date date can be selected. */
	public DatePickerOptions startDate(String v)
	{
		set("startDate", v);
		return this;
	}

	/** endDate (String): The last date that can be selected. */
	public DatePickerOptions endDate(String v)
	{
		set("endDate", v);
		return this;
	}

	/**
	 * createButton (Boolean): Whether to create a .dp-choose-date anchor directly after the matched
	 * element which when clicked will trigger the showing of the date picker. Default is true.
	 */
	public DatePickerOptions createButton(boolean v)
	{
		set("createButton", v);
		return this;
	}

	/**
	 * showYearNavigation (Boolean): Whether to display buttons which allow the user to navigate
	 * through the months a year at a time. Default is true.
	 */
	public DatePickerOptions showYearNavigation(boolean v)
	{
		set("showYearNavigation", v);
		return this;
	}

	/**
	 * closeOnSelect (Boolean): Whether to close the date picker when a date is selected. Default is
	 * true.
	 */
	public DatePickerOptions closeOnSelect(boolean v)
	{
		set("closeOnSelect", v);
		return this;
	}

	/**
	 * displayClose (Boolean): Whether to create a "Close" button within the date picker popup.
	 * Default is false.
	 */
	public DatePickerOptions displayClose(boolean v)
	{
		set("displayClose", v);
		return this;
	}

	/**
	 * selectMultiple (Boolean): Whether a user should be able to select multiple dates with this
	 * date picker. Default is false.
	 */
	public DatePickerOptions selectMultiple(boolean v)
	{
		set("selectMultiple", v);
		return this;
	}

	/**
	 * clickInput (Boolean): If the matched element is an input type="text" and this option is true
	 * then clicking on the input will cause the date picker to appear.
	 */
	public DatePickerOptions clickInput(boolean v)
	{
		set("clickInput", v);
		return this;
	}

	/**
	 * verticalPosition (Number): The vertical alignment of the popped up date picker to the matched
	 * element. Default is TOP.
	 */
	public DatePickerOptions verticalPosition(VPos v)
	{
		set("verticalPosition", v.ordinal());
		return this;
	}

	/**
	 * horizontalPosition (Number): The horizontal alignment of the popped up date picker to the
	 * matched element. One of $.dpConst.POS_LEFT and $.dpConst.POS_RIGHT.
	 */
	public DatePickerOptions horizontalPosition(HPos v)
	{
		set("horizontalPosition", v.ordinal());
		return this;
	}

	/**
	 * verticalOffset (Number): The number of pixels offset from the defined verticalPosition of
	 * this date picker that it should pop up in. Default in 0.
	 */
	public DatePickerOptions verticalOffset(int v)
	{
		set("verticalOffset", v);
		return this;
	}

	/**
	 * horizontalOffset (Number): The number of pixels offset from the defined horizontalPosition of
	 * this date picker that it should pop up in. Default in 0.
	 */
	public DatePickerOptions horizontalOffset(int v)
	{
		set("horizontalOffset", v);
		return this;
	}

	/**
	 * hoverClass (String): The class to attach to each cell when you hover over it (to allow you to
	 * use hover effects in IE6 which doesn't support the :hover pseudo-class on elements other than
	 * links). Default is dp-hover. Pass false if you don't want a hover class.
	 */
	public DatePickerOptions hoverClass(String v)
	{
		set("hoverClass", v);
		return this;
	}

	/** allowing (or not) selection of Date in paste (default : false) */
	public DatePickerOptions allowDateInPast(final boolean allowInPast, final String datePattern)
	{

		final Date startDate;
		if (allowInPast)
		{
			startDate = new Date(0);
		}
		else
		{
			startDate = new Date();
		}

		final DateFormat dateFormat = new SimpleDateFormat(datePattern);

		return startDate(dateFormat.format(startDate));
	}

	public DatePickerOptions dynamicLocalizedMessages(boolean v)
	{
		dynamicLocalizedMessages = v;
		return this;
	}
}
