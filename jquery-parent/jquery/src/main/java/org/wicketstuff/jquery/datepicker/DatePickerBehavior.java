/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.jquery.datepicker;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.DateConverter;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.jquery.JQueryBehavior;
import org.wicketstuff.minis.behavior.CompositeBehavior;

/**
 * Add support of the <a
 * href="http://kelvinluck.com/assets/jquery/datePicker/v2/demo/">datePicker</a>.
 * 
 * @author David Bernard (dwayne)
 * @created 2007-09-08
 */
@SuppressWarnings(value = "serial")
public class DatePickerBehavior extends JQueryBehavior
{

	public static final ResourceReference DATE_JS = new PackageResourceReference(
		DatePickerBehavior.class, "date.js");
	public static final ResourceReference JQUERY_DATEPICKER_JS = new PackageResourceReference(
		DatePickerBehavior.class, "jquery.datePicker.js");
	public static final ResourceReference DATEPICKER_CSS = new PackageResourceReference(
		DatePickerBehavior.class, "datePicker.css");
	private final DatePickerOptions options_;
	private String format_;
	private boolean includeJquery = true;

	public boolean isIncludeJquery()
	{
		return includeJquery;
	}

	public void setIncludeJquery(boolean includeJquery)
	{
		this.includeJquery = includeJquery;
	}

	public DatePickerBehavior()
	{
		this(null);
	}

	public DatePickerBehavior(DatePickerOptions options)
	{
		if (options == null)
		{
			options = new DatePickerOptions();
		}
		options_ = options;
	}

// private void convertDateInOptions(IConverter cnv, String key, Locale locale) {
// Date date = (Date)options_.get(key);
// if (date != null) {
// options_.set(key, cnv.convertToString(date, locale));
// }
// }
	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		if (includeJquery)
			super.renderHead(component, response);
		response.render(CssHeaderItem.forReference(DATEPICKER_CSS));
		response.render(JavaScriptHeaderItem.forReference(DATE_JS));
		try
		{
			WebClientInfo info = WebSession.get().getClientInfo();
			if (info.getUserAgent().contains("MSIE"))
			{
				response.render(JavaScriptHeaderItem.forReference(JQUERY_BGIFRAME_JS));
			}
		}
		catch (ClassCastException exc)
		{
			logger().info("can't find info about client", exc);
		}

		// if (ie) {
		// response.renderJavascriptReference(JQUERY_BGIFRAME_JS);
		// }
		response.render(JavaScriptHeaderItem.forReference(JQUERY_DATEPICKER_JS));

		/* Support localized messages in the datepicker clientside */
		if (options_.dynamicLocalizedMessages)
		{
			Map<String, StringBuilder> lm = new HashMap<String, StringBuilder>();
			SimpleDateFormat sdf = new SimpleDateFormat("", getComponent().getLocale());
			lm.put("dayNames", new StringBuilder("Date.dayNames = ["));
			lm.put("abbrDayNames", new StringBuilder("Date.abbrDayNames = ["));
			lm.put("monthNames", new StringBuilder("Date.monthNames = ["));
			lm.put("abbrMonthNames", new StringBuilder("Date.abbrMonthNames = ["));

			for (int i = 1; i < 8; i++)
			{
				lm.get("dayNames").append(
					" '" + Strings.capitalize(sdf.getDateFormatSymbols().getWeekdays()[i]));
				lm.get("abbrDayNames").append(
					" '" + Strings.capitalize(sdf.getDateFormatSymbols().getShortWeekdays()[i]));

				if (i < 7)
				{
					lm.get("dayNames").append("',");
					lm.get("abbrDayNames").append("',");
				}
			}

			for (int i = 0; i < 12; i++)
			{
				lm.get("monthNames").append(
					" '" + Strings.capitalize(sdf.getDateFormatSymbols().getMonths()[i]));
				lm.get("abbrMonthNames").append(
					" '" + Strings.capitalize(sdf.getDateFormatSymbols().getShortMonths()[i]));

				if (i < 11)
				{
					lm.get("monthNames").append("',");
					lm.get("abbrMonthNames").append("',");
				}
			}

			String locMess = lm.get("dayNames").toString() + "' ];\n" + lm.get("abbrDayNames") +
				"' ];\n" + lm.get("monthNames") + "' ];\n" + lm.get("abbrMonthNames") + "' ];\n";

			response.render(JavaScriptHeaderItem.forScript(locMess, "localization_override" +
				getComponent().getMarkupId()));
		}
	}

	@Override
	protected CharSequence getOnReadyScript()
	{
		String selector = ".date-pick";
		Component component = getComponent();
		if (!(component instanceof Page))
		{
			selector = "#" + component.getMarkupId();
		}
		return String.format("Date.format = '%s';$('%s').datePicker(%s)", format_, selector,
			options_.toString(false));
	}

	@Override
	protected void onBind()
	{
		super.onBind();
		Component component = getComponent();
		if (component instanceof TextField)
		{
			component.setOutputMarkupId(true);

			if (component instanceof ITextFormatProvider)
			{
				format_ = ((ITextFormatProvider)component).getTextFormat().toLowerCase();
			}
			else
			{
				TextField<?> tf = (TextField<?>)component;
				IConverter<?> cnv = tf.getConverter(tf.getType());
				if (cnv != null && DateConverter.class.isAssignableFrom(cnv.getClass()))
				{
					SimpleDateFormat sdf = (SimpleDateFormat)((DateConverter)cnv).getDateFormat(component.getLocale());
					format_ = sdf.toPattern().toLowerCase();
				}

// convertDateInOptions(cnv, "startDate", component.getLocale());
// convertDateInOptions(cnv, "endDate", component.getLocale());
			}

			component.add(getDatePickerStyle());
		}
		else
		{
			throw new RuntimeException(
				"DatePicketBehavior is intended to be attached to a TextField component!");
		}
	}

	public Behavior getDatePickerStyle()
	{
		return new CompositeBehavior(AttributeModifier.append("class", "date-pick"),
			AttributeModifier.replace("size", String.valueOf(format_.length())),
			AttributeModifier.replace("maxlength", String.valueOf(format_.length())),
			AttributeModifier.replace("title", format_));
	}
}
