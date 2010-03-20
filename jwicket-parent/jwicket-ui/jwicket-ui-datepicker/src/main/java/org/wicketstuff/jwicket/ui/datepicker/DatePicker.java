package org.wicketstuff.jwicket.ui.datepicker;


import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.jwicket.JQueryJavascriptResourceReference;
import org.wicketstuff.jwicket.JQuerySpeed;
import org.wicketstuff.jwicket.SpecialKeys;
import org.wicketstuff.jwicket.ui.AbstractJqueryUiEmbeddedBehavior;


/**
 * You can add an instance of this class to a Wicket {@link Component} to make it
 * a resizable {@link Component}.
 * An instance of this class can be added to one and only one
 * {@link Component}. Another {@link Component} that should have exactly the
 * same behavior needs it's own instance.
 */
public class DatePicker extends AbstractJqueryUiEmbeddedBehavior {

	private static final long serialVersionUID = 1L;
	private static final JQueryJavascriptResourceReference uiDatepicker = new JQueryJavascriptResourceReference(DatePicker.class, "jquery.ui.datepicker.js");

	private JsMap options = new JsMap();


	public DatePicker() {
		this(null);
	}

	public DatePicker(final ResourceReference icon) {
		super(	uiDatepicker,
				AbstractJqueryUiEmbeddedBehavior.jQueryUiBaseCss,
				AbstractJqueryUiEmbeddedBehavior.jQueryUiThemeCss
		);
		Locale locale = Session.get().getLocale();
		if (locale != null)
			addUserProvidedResourceReferences(new JQueryJavascriptResourceReference(DatePicker.class, "jquery.ui.datepicker-" + locale.getLanguage() + ".js"));
		
		if (icon != null)
			setButtonImage(icon);
		
		setRestoreAfterRedraw(true);
	}

	/**
	 * Handles the event processing during resizing.
	 */
	@Override
	protected void respond(final AjaxRequestTarget target) {
		Component component = getComponent();
		Request request;
		if (component != null && (request = component.getRequest()) != null) {
			EventType eventType = EventType.stringToType(request.getParameter(EventType.IDENTIFIER));

			if (eventType == EventType.ON_SELECT) {
				onSelect(target, request.getParameter("date"), new SpecialKeys(request));
			}
			else if (eventType == EventType.ON_CLOSE) {
				onClose(target, request.getParameter("date"), new SpecialKeys(request));
			}
			else if (eventType == EventType.ON_CHANGE_MONTH_YEAR) {
				onChangeMonthYear(target, request.getParameter("year"), request.getParameter("month"), new SpecialKeys(request));
			}
			else if (eventType == EventType.BEFORE_SHOW_DAY) {
				onBeforeShowDay(target, request.getParameter("date"));
			}
		}
	}


	/**	
	 * Sets the 'autoSize' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DatePicker setAutoSize(final boolean value) {
		if (!value)
			options.remove("autoSize");
		else
			options.put("autoSize", value);
		return this;
	}
	public DatePicker setAutoSize(final AjaxRequestTarget target, final boolean value) {
		setAutoSize(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','autoSize'," + value + ");");
		return this;
	}


	/**	Sets a button image that is displayed near the {@link TextField} for the visual date
	 *	representation.
	 */
	public DatePicker setButtonImage(final String value) {
		if (value == null || value.trim().length() == 0) {
			options.remove("buttonImage");
			options.remove("showOn");
		}
		else {
			options.put("buttonImage", value);
			options.put("showOn", "button");
		}
		return this;
	}
	public DatePicker setButtonImage(final AjaxRequestTarget target, final String value) {
		setButtonImage(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','buttonImage','" + ((value==null)?"":value) + "','showOn','button');");
		return this;
	}



	/**	Sets a button image that is displayed near the {@link TextField} for the visual date
	 *	representation.
	 */
	public DatePicker setButtonImage(final ResourceReference value) {
		if (value == null) {
			options.remove("buttonImage");
			options.remove("showOn");
		}
		else {
			options.put("buttonImage", "resources/" + value.getSharedResourceKey());
			options.put("showOn", "button");
		}
		return this;
	}
	public DatePicker setButtonImage(final AjaxRequestTarget target, final ResourceReference value) {
		setButtonImage(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','buttonImage','" + ((value==null)?"":value.getSharedResourceKey()) + "','showOn','button');");
		return this;
	}


	/**	
	 * Sets the 'changeMonth' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DatePicker setChangeMonth(final boolean value) {
		if (!value)
			options.remove("changeMonth");
		else
			options.put("changeMonth", value);
		return this;
	}
	public DatePicker setChangeMonth(final AjaxRequestTarget target, final boolean value) {
		setChangeMonth(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','changeMonth'," + value + ");");
		return this;
	}


	/**	
	 * Sets the 'changeYear' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DatePicker setChangeYear(final boolean value) {
		if (!value)
			options.remove("changeYear");
		else
			options.put("changeYear", value);
		return this;
	}
	public DatePicker setChangeYear(final AjaxRequestTarget target, final boolean value) {
		setChangeYear(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','changeYear'," + value + ");");
		return this;
	}


	/**	
	 * Sets the 'closeText' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DatePicker setCloseText(final String value) {
		if (value == null || value.trim().length() == 0)
			options.remove("closeText");
		else
			options.put("closeText", value);
		return this;
	}
	public DatePicker setCloseText(final AjaxRequestTarget target, final String value) {
		setCloseText(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','closeText','" + value + "');");
		return this;
	}


	/**	
	 * Sets the 'constraintInput' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DatePicker setConstraintInput(final boolean value) {
		if (!value)
			options.remove("constraintInput");
		else
			options.put("constraintInput", value);
		return this;
	}
	public DatePicker setConstraintInput(final AjaxRequestTarget target, final boolean value) {
		setConstraintInput(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','constraintInput'," + value + ");");
		return this;
	}


	/**	
	 * Sets the 'currentText' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value Text to be dsiplayes on button
	 * @return this object
	 */
	public DatePicker setCurrentText(final String value) {
		if (value == null || value.trim().length() == 0)
			options.remove("currentText");
		else
			options.put("currentText", value);
		return this;
	}
	public DatePicker setCurrentText(final AjaxRequestTarget target, final String value) {
		setCurrentText(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','currentText','" + value + "');");
		return this;
	}


	/**	
	 * Sets the 'dateFormat' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value the date format
	 * @return this object
	 */
	public DatePicker setDateFormat(final String value) {
		if (value == null || value.trim().length() == 0)
			options.remove("dateFormat");
		else
			options.put("dateFormat", value);
		return this;
	}
	public DatePicker setDateFormat(final AjaxRequestTarget target, final String value) {
		setDateFormat(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','dateFormat','" + value + "');");
		return this;
	}


	/**	
	 * Sets the 'duration' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value duration in ms
	 * @return this object
	 */
	public DatePicker setDuration(final String value) {
		if (value == null || value.trim().length() == 0)
			options.remove("duration");
		else
			options.put("duration", value);
		return this;
	}
	public DatePicker setDuration(final AjaxRequestTarget target, final String value) {
		setDuration(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','duration','" + value + "');");
		return this;
	}

	public DatePicker setDuration(final int value) {
		if (value <= 0)
			options.remove("duration");
		else
			options.put("duration", value);
		return this;
	}
	public DatePicker setDuration(final AjaxRequestTarget target, final int value) {
		setDuration(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','duration'," + value + ");");
		return this;
	}



	/**	
	 * Sets the 'duration' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value duration
	 * @return this object
	 */
	public DatePicker setDuration(final JQuerySpeed value) {
		if (value == null)
			options.remove("duration");
		else
			options.put("duration", value.getSpeed());
		return this;
	}
	public DatePicker setDuration(final AjaxRequestTarget target, final JQuerySpeed value) {
		setDuration(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','duration','" + value.getSpeed() + "');");
		return this;
	}


	/**	
	 * Sets the 'maxDate' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value max date as String
	 * @return this object
	 */
	public DatePicker setMaxDate(final String value) {
		if (value == null)
			options.remove("maxDate");
		else
			options.put("maxDate", value);
		return this;
	}
	public DatePicker setMaxDate(final AjaxRequestTarget target, final String value) {
		setMaxDate(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','maxDate','" + value + "');");
		return this;
	}


	/**	
	 * Sets the 'maxDate' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value max date as String
	 * @return this object
	 */
	public DatePicker setMaxDate(final Date value) {
		if (value == null)
			options.remove("maxDate");
		else {
			Locale locale = Session.get().getLocale();
			DateFormat df;
			if (locale != null)
				df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
			else
				df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			options.put("maxDate", df.format(value));
		}
		return this;
	}
	public DatePicker setMaxDate(final AjaxRequestTarget target, final Date value) {
		setMaxDate(value);
		if (value == null)
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','maxDate',null);");
		else {
			Locale locale = Session.get().getLocale();
			DateFormat df;
			if (locale != null)
				df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
			else
				df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','maxDate','" + df.format(value) + "');");
		}
		return this;
	}

	public DatePicker setMaxDate(final java.sql.Date value) {
		if (value == null)
			options.remove("maxDate");
		else {
			Locale locale = Session.get().getLocale();
			DateFormat df;
			if (locale != null)
				df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
			else
				df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			options.put("maxDate", df.format(value));
		}
		return this;
	}
	public DatePicker setMaxDate(final AjaxRequestTarget target, final java.sql.Date value) {
		setMaxDate(value);
		if (value == null)
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','maxDate',null);");
		else {
			Locale locale = Session.get().getLocale();
			DateFormat df;
			if (locale != null)
				df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
			else
				df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','maxDate','" + df.format(value) + "');");
		}
		return this;
	}


	/**	
	 * Sets the 'minDate' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value min date as String
	 * @return this object
	 */
	public DatePicker setMinDate(final String value) {
		if (value == null)
			options.remove("minDate");
		else
			options.put("minDate", value);
		return this;
	}
	public DatePicker setMinDate(final AjaxRequestTarget target, final String value) {
		setMinDate(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','minDate','" + value + "');");
		return this;
	}


	/**	
	 * Sets the 'minDate' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value min date as String
	 * @return this object
	 */
	public DatePicker setMinDate(final Date value) {
		if (value == null)
			options.remove("minDate");
		else {
			Locale locale = Session.get().getLocale();
			DateFormat df;
			if (locale != null)
				df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
			else
				df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			options.put("minDate", df.format(value));
		}
		return this;
	}
	public DatePicker setMinDate(final AjaxRequestTarget target, final Date value) {
		setMinDate(value);
		if (value == null)
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','minDate',null);");
		else {
			Locale locale = Session.get().getLocale();
			DateFormat df;
			if (locale != null)
				df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
			else
				df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','minDate','" + df.format(value) + "');");
		}
		return this;
	}

	public DatePicker setMinDate(final java.sql.Date value) {
		if (value == null)
			options.remove("minDate");
		else {
			Locale locale = Session.get().getLocale();
			DateFormat df;
			if (locale != null)
				df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
			else
				df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			options.put("minDate", df.format(value));
		}
		return this;
	}
	public DatePicker setMinDate(final AjaxRequestTarget target, final java.sql.Date value) {
		setMinDate(value);
		if (value == null)
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','minDate',null);");
		else {
			Locale locale = Session.get().getLocale();
			DateFormat df;
			if (locale != null)
				df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
			else
				df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','minDate','" + df.format(value) + "');");
		}
		return this;
	}


	/**	
	 * Sets the 'numberOfMonths' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value the number of months to show at one
	 * @return this object
	 */
	public DatePicker setNumberOfMonths(final int value) {
		if (value == 0)
			options.remove("numberOfMonths");
		else
			options.put("numberOfMonths", value);
		return this;
	}
	public DatePicker setNumberOfMonths(final AjaxRequestTarget target, final int value) {
		setNumberOfMonths(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','numberOfMonths'," + value + ");");
		return this;
	}


	/**	
	 * Sets the 'numberOfMonths' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value the number of months to show at one
	 * @return this object
	 */
	public DatePicker setNumberOfMonths(final int rows, final int columns) {
		if (rows == 0 || columns == 0)
			options.remove("numberOfMonths");
		else
			options.put("numberOfMonths", new Object[]{rows,columns});
		return this;
	}
	public DatePicker setNumberOfMonths(final AjaxRequestTarget target, final int rows, final int columns) {
		setNumberOfMonths(rows, columns);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() +
				"').datepicker('option','numberOfMonths',[" + rows + "," + columns + "]);");
		return this;
	}


	/**	
	 * Sets the 'selectOtherMonths' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DatePicker setSelectOtherMonths(final boolean value) {
		if (!value)
			options.remove("selectOtherMonths");
		else
			options.put("selectOtherMonths", value);
		return this;
	}
	public DatePicker setSelectOtherMonths(final AjaxRequestTarget target, final boolean value) {
		setSelectOtherMonths(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','selectOtherMonths'," + value + ");");
		return this;
	}


	/**	
	 * Sets the 'showAnim' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DatePicker setShowAnim(final ShowAnim value) {
		if (value == null)
			options.remove("showAnim");
		else
			options.put("showAnim", value.getAnimName());
		return this;
	}
	public DatePicker setShowAnim(final AjaxRequestTarget target, final ShowAnim value) {
		setShowAnim(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','showAnim'," + value.getAnimName() + ");");
		return this;
	}


	/**	
	 * Sets the 'showButtonPanel' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DatePicker setShowButtonPanel(final boolean value) {
		if (!value)
			options.remove("showButtonPanel");
		else
			options.put("showButtonPanel", value);
		return this;
	}
	public DatePicker setShowButtonPanel(final AjaxRequestTarget target, final boolean value) {
		setShowButtonPanel(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','showButtonPanel'," + value + ");");
		return this;
	}


	/**	
	 * Sets the 'showCurrentAtPos' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DatePicker setShowCurrentAtPos(final boolean value) {
		if (!value)
			options.remove("showCurrentAtPos");
		else
			options.put("showCurrentAtPos", value);
		return this;
	}
	public DatePicker setShowCurrentAtPos(final AjaxRequestTarget target, final boolean value) {
		setShowCurrentAtPos(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','showCurrentAtPos'," + value + ");");
		return this;
	}


	/**	
	 * Sets the 'showMonthAfterYear' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DatePicker setShowMonthAfterYear(final boolean value) {
		if (!value)
			options.remove("showMonthAfterYear");
		else
			options.put("showMonthAfterYear", value);
		return this;
	}
	public DatePicker setShowMonthAfterYear(final AjaxRequestTarget target, final boolean value) {
		setShowMonthAfterYear(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','showMonthAfterYear'," + value + ");");
		return this;
	}


	/**	
	 * Sets the 'showOn' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DatePicker setShowOn(final ShowOnTrigger value) {
		if (value == null)
			options.remove("showOn");
		else
			options.put("showOn", value);
		return this;
	}
	public DatePicker setShowOn(final AjaxRequestTarget target, final ShowOnTrigger value) {
		setShowOn(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','showOn','" + value.getTriggerName() + "');");
		return this;
	}


	/**	
	 * Sets the 'showOtherMonths' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DatePicker setShowOtherMonths(final boolean value) {
		if (!value)
			options.remove("showOtherMonths");
		else
			options.put("showOtherMonths", value);
		return this;
	}
	public DatePicker setShowOtherMonths(final AjaxRequestTarget target, final boolean value) {
		setShowOtherMonths(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','showOtherMonths'," + value + ");");
		return this;
	}


	/**	
	 * Sets the 'showWeek' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DatePicker setShowWeek(final boolean value) {
		if (!value)
			options.remove("showWeek");
		else
			options.put("showWeek", value);
		return this;
	}
	public DatePicker setShowWeek(final AjaxRequestTarget target, final boolean value) {
		setShowWeek(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','showWeek'," + value + ");");
		return this;
	}


	/**	
	 * Sets the 'stepMonths' property for this DatePicker. Please consult the
	 * jQuery documentation for a detailed description of this property.
	 * @param value how many months to move in one step
	 * @return this object
	 */
	public DatePicker setStepMonths(final int value) {
		if (value <= 0)
			options.remove("stepMonths");
		else
			options.put("stepMonths", value);
		return this;
	}
	public DatePicker setStepMonths(final AjaxRequestTarget target, final int value) {
		setStepMonths(value);
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','stepMonths'," + value + ");");
		return this;
	}
	
	
	
	
	
	
	

	

	
	
	
	



	
	
	
	


	private boolean onSelectNotificationWanted = false;
	/**
	 * If set to {@code true}, the callback-Method {@link #onSelect(AjaxRequestTarget, String, SpecialKeys) }
	 * is called when a date was picked.
	 * See the jquery-ui documentation for more information
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DatePicker setWantOnSelectNotification(final boolean value) {
		onSelectNotificationWanted = value;
		return this;
	}



	private boolean onCloseNotificationWanted = false;
	/**
	 * If set to {@code true}, the callback-Method {@link #onClose(AjaxRequestTarget, String, SpecialKeys) }
	 * is called when a date was picked.
	 * See the jquery-ui documentation for more information
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DatePicker setWantOnCloseNotification(final boolean value) {
		onCloseNotificationWanted = value;
		return this;
	}



	private boolean onChangeMonthYearNotificationWanted = false;
	/**
	 * If set to {@code true}, the callback-Method {@link #onChangeMonthYear(AjaxRequestTarget, String, String, SpecialKeys) }
	 * is called when a date was picked.
	 * See the jquery-ui documentation for more information
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DatePicker setWantOnChangeMonthYearNotification(final boolean value) {
		onChangeMonthYearNotificationWanted = value;
		return this;
	}



	private boolean onBeforeShowDayNotificationWanted = false;
	/**
	 * If set to {@code true}, the callback-Method {@link #onBeforeShowDayNotificationWanted }
	 * is called for every day that gets displayes in the datepicker popup.
	 *
	 * See the jquery-ui documentation for detailed information
	 * @param value {@code true} or {@code false}.
	 * @return this object
	 */
	public DatePicker setWantOnBeforeShowDayNotification(final boolean value) {
		onBeforeShowDayNotificationWanted = value;
		return this;
	}


	
	/*
	private boolean destroyBeforeRedraw = true;
	public void setDestroyBeforeRedraw(final boolean value) {
		this.destroyBeforeRedraw = value;
	}
	*/

	@Override
	protected JsBuilder getJsBuilder() {
		if (onSelectNotificationWanted)
			options.put(EventType.ON_SELECT.eventName,
				new JsFunction("function(dateText,inst) { wicketAjaxGet('" +
								this.getCallbackUrl() +
								"&date='+dateText" +
								"+'&" + EventType.IDENTIFIER + "=" + EventType.ON_SELECT + "'" +
								"+'&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
								");}"));
		else
			options.remove(EventType.ON_SELECT.getEventName());


		if (onCloseNotificationWanted)
			options.put(EventType.ON_CLOSE.eventName,
				new JsFunction("function(dateText,inst) { wicketAjaxGet('" +
								this.getCallbackUrl() +
								"&date='+dateText" +
								"+'&" + EventType.IDENTIFIER + "=" + EventType.ON_CLOSE + "'" +
								"+'&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
								");}"));
		else
			options.remove(EventType.ON_CLOSE.getEventName());


		if (onChangeMonthYearNotificationWanted)
			options.put(EventType.ON_CHANGE_MONTH_YEAR.eventName,
				new JsFunction("function(year,month,inst) { wicketAjaxGet('" +
								this.getCallbackUrl() +
								"&year='+year" +
								"+'&month='+month" +
								"+'&" + EventType.IDENTIFIER + "=" + EventType.ON_CHANGE_MONTH_YEAR + "'" +
								"+'&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
								");}"));
		else
			options.remove(EventType.ON_CHANGE_MONTH_YEAR.getEventName());


		if (onBeforeShowDayNotificationWanted)
			options.put(EventType.BEFORE_SHOW_DAY.eventName,
				new JsFunction("function(date) { wicketAjaxGet('" +
								this.getCallbackUrl() +
								"&date='+date" +
								"+'&" + EventType.IDENTIFIER + "=" + EventType.BEFORE_SHOW_DAY + "'" +
								"+'&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
								");return new Array(true, '','');}"));
		else
			options.remove(EventType.BEFORE_SHOW_DAY.getEventName());


		JsBuilder builder = new JsBuilder();
		
		
		/*	It's always the same Ajax pain.
		 *	- having a tag <input type="text"/>
		 *	- having a DatePicker attached to this input filed
		 *	- the DatePicker has a button displayed that triggers the DataPicker
		 *	- this results in <input type="text" id="someId"/><button type="button" class="ui-datepicker-trigger"><img src=".....calendar22x24.gif" alt="..." title="..."></button>
		 *	If we redraw the input field in an Ajax call, only the <input> field is redrawed and
		 *	jQuery places a second button after the input field. The original button is not removed or updated.
		 *
		 *	One soution would be to put a <div> around the <input> tag like
		 *		<div id="someId">
		 *			<input/><button generated button/>
		 *		</div>
		 *	and to redraw the surrounding div insteda of the input tag. But this is not a transparent
		 *	sulution becaus the user must always take care of this and can not use the DatePicker carelessly.
		 *
		 *	Another solution would be to try to remove the button that was generated by jQuery
		 *	before the DatePicker is re-applies to the input field with a new image.
		 *	So I try to do this by selecting "#idOfInput+button" to catch the button after
		 *	the input. This might not work under all circumstances and with all browsers but
		 *	it seems to work even with IE6.
		 *
		 *	The best solution would be to have a unique id for the button but this would mean to modify
		 *	jQuery's js files.
		 *
		 * 	I have created a ticket for this: http://dev.jqueryui.com/ticket/5384
		 *	Let's see what happens.
		 */
		if (isAlreadyRendered())
			builder.append("jQuery('#" + getComponent().getMarkupId() + "+button').remove();");

		/* Normal processing */
		builder.append("jQuery('#" + getComponent().getMarkupId() + "').datepicker(");
		builder.append("{");
		builder.append(options.toString(rawOptions));
		builder.append("}");
		builder.append(");");


System.out.println("---------------------------------------------------------------------");
System.out.println("---------------------------------------------------------------------");
System.out.println(builder.toString());
System.out.println("---------------------------------------------------------------------");
		
		return builder;
	}


	/**
	 * If you have set {@link #setWantOnSelectNotification(boolean)} to {@code true}
	 * this method is called after the user picked a date in the datepicker.
	 *
	 * @param target the AjaxRequestTarget of the resize operation.
	 * @param specialKeys the special keys that were pressed when the event occurs
	 */
	protected void onSelect(final AjaxRequestTarget target, final String pickedDate, final SpecialKeys specialKeys) {}


	/**
	 * If you have set {@link #setWantOnCloseNotification(boolean)} to {@code true}
	 * this method is called after the datepicker was closed, regardless of picking a date.
	 *
	 * @param target the AjaxRequestTarget of the resize operation.
	 * @param specialKeys the special keys that were pressed when the event occurs
	 */
	protected void onClose(final AjaxRequestTarget target, final String pickedDate, final SpecialKeys specialKeys) {}


	/**
	 * If you have set {@link #setWantOnChangeMonthYearNotification(boolean)} to {@code true}
	 * this method is called after another moth or year is displayed, regardless of picking a date.
	 *
	 * @param target the AjaxRequestTarget of the resize operation.
	 * @param specialKeys the special keys that were pressed when the event occurs
	 */
	protected void onChangeMonthYear(final AjaxRequestTarget target, final String year, final String month, final SpecialKeys specialKeys) {}


	/**
	 * If you have set {@link #setWantOnBeforeShowDayNotification(boolean)} to {@code true}
	 * this method is called for every day in the datepicker poup.
	 * 
	 * This doesn't make any sense at the moment.
	 *
	 * @param target the AjaxRequestTarget of the resize operation.
	 * @param specialKeys the special keys that were pressed when the event occurs
	 */
	@Deprecated
	protected String onBeforeShowDay(final AjaxRequestTarget target, final String date) {return "";	}



	
	
	
	
	
	/**
	 * Disable the datepicker
	 *
	 * @param target An AjaxRequestTarget
	 */
	public void disable(final AjaxRequestTarget target) {
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker( 'disable' );");
		target.addComponent(getComponent());
	}


	/**
	 * Enable the datepicker
	 *
	 * @param target An AjaxRequestTarget
	 */
	public void enable(final AjaxRequestTarget target) {
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').datepicker( 'enable' );");
		target.appendJavascript(getJsBuilder().toString());
	}



	
	private enum EventType implements Serializable {

		UNKNOWN("*"),
		BEFORE_SHOW("beforeShow"),
		BEFORE_SHOW_DAY("beforeShowDay"),
		ON_CHANGE_MONTH_YEAR("onChangeMonthYear"),
		ON_CLOSE("onClose"),
		ON_SELECT("onSelect")
		;

		public static final String IDENTIFIER="wicketDatepickerEvent";

		private final String eventName;
		
		private EventType(final String eventName) {
			this.eventName = eventName;
		}
		
		public String getEventName() {
			return this.eventName;
		}
		
		public static EventType stringToType(final String s) {
			for (EventType t : EventType.values())
				if (t.getEventName().equals(s))
					return t;
			return UNKNOWN;
		}
		
		public String toString() {
			return this.eventName;
		}
	}

	
	
	public enum ShowAnim implements Serializable {
		SHOW("show"),
		SLIDE_DOWN("slideSown"),
		FADE_IN("fadeIn");

		private final String animName;
		
		private ShowAnim(final String animName) {
			this.animName = animName;
		}
		
		public String getAnimName() {
			return this.animName;
		}
		
		public String toString() {
			return this.animName;
		}
	}



	public enum ShowOnTrigger implements Serializable {
		FOCUS("focus"),
		BUTTON("button"),
		BOTH("both");

		private final String triggerName;
		
		private ShowOnTrigger(final String triggerName) {
			this.triggerName = triggerName;
		}
		
		public String getTriggerName() {
			return this.triggerName;
		}
		
		public String toString() {
			return this.triggerName;
		}
	}
}
