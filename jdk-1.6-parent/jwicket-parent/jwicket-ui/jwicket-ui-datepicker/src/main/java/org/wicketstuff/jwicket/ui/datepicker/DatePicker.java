package org.wicketstuff.jwicket.ui.datepicker;


import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.jwicket.*;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.*;


/**
 * You can add an instance of this class to a Wicket {@link Component} to make it
 * a resizable {@link Component}.
 * An instance of this class can be added to one and only one
 * {@link Component}. Another {@link Component} that should have exactly the
 * same behavior needs it's own instance.
 */
public class DatePicker extends JQueryDurableAjaxBehavior implements IStyleResolver {

    private static final long serialVersionUID = 1L;

    public static final JQueryResourceReference uiDatepickerJs = new JQueryResourceReference(DatePicker.class, "jquery.ui.datepicker.js");
    public static final JQueryResourceReference uiDatepickerJs_de = new JQueryResourceReference(DatePicker.class, "jquery.ui.datepicker-de.js");

    public static final JQueryResourceReference datePickerDefaultShowDayState = new JQueryResourceReference(DatePicker.class, "datePickerDefaultShowDayState.js");


    protected JsMap options = new JsMap();

    public DatePicker() {
        super(new JQueryResourceReference(DatePicker.class, "jquery.ui.core.js"), SpecialKeys.specialKeysJs, datePickerDefaultShowDayState,
                uiDatepickerJs
        );
        initDatePicker();

    }

    public DatePicker(final ResourceReference icon) {
        this();
        setButtonImage(icon);
    }

    public DatePicker(final CharSequence icon) {
        this();
        setButtonImage(icon.toString());
    }


    private void initDatePicker() {
        addCssResources(getCssResources());


        Locale locale = Session.get().getLocale();
        if (locale != null) {
            addUserProvidedResourceReferences(new JQueryResourceReference(DatePicker.class, "jquery.ui.datepicker-" + locale.getLanguage() + ".js"));
        }

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
            EventType eventType = EventType.stringToType(request.getRequestParameters().getParameterValue(EventType.IDENTIFIER).toString());
            if (eventType == EventType.ON_SELECT) {
                String selectedDate = request.getRequestParameters().getParameterValue("date").toString();
                SpecialKeys specialKeys = new SpecialKeys(request);
                onSelect(target, selectedDate, specialKeys);
                Locale locale = Session.get().getLocale();
                DateFormat df;
                if (locale != null) {
                    df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
                } else {
                    df = DateFormat.getDateInstance(DateFormat.SHORT);
                }

                Date parsedDate = null;
                java.sql.Date parsedSqlDate = null;
                try {
                    parsedDate = df.parse(selectedDate);
                    parsedSqlDate = new java.sql.Date(parsedDate.getTime());
                } catch (Exception e) {
                    throw new WicketRuntimeException("Error converting '" + selectedDate + "' to a Date object.", e);
                }
                onSelect(target, parsedDate, specialKeys);
                onSelect(target, parsedSqlDate, specialKeys);

                if (component instanceof FormComponent<?>) {
                    ((FormComponent<?>) component).inputChanged();
                }
            } else if (eventType == EventType.ON_CLOSE) {
                onClose(target, request.getRequestParameters().getParameterValue("date").toString(), new SpecialKeys(request));
            } else if (eventType == EventType.ON_CHANGE_MONTH_YEAR) {
                onChangeMonthYear(target, request.getRequestParameters().getParameterValue("year").toString(), request.getRequestParameters().getParameterValue("month").toString(), new SpecialKeys(request));
            } else if (eventType == EventType.BEFORE_SHOW) {
                onBeforeShow(target);
            }
        }
    }


    /**
     * Sets the 'buttonImageOnly' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DatePicker setButtonImageOnly(final boolean value) {
        if (!value) {
            this.options.remove("buttonImageOnly");
        } else {
            this.options.put("buttonImageOnly", value);
        }
        return this;
    }

    public DatePicker setButtonImageOnly(final AjaxRequestTarget target, final boolean value) {
        setButtonImageOnly(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','buttonImageOnly'," + value + ");");
        return this;
    }


    /**
     * Sets the 'autoSize' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DatePicker setAutoSize(final boolean value) {
        if (!value) {
            this.options.remove("autoSize");
        } else {
            this.options.put("autoSize", value);
        }
        return this;
    }

    public DatePicker setAutoSize(final AjaxRequestTarget target, final boolean value) {
        setAutoSize(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','autoSize'," + value + ");");
        return this;
    }


    /**
     * Sets the 'buttonText' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value The new buttonText or {@code null} to remove this option.
     * @return this object
     */
    public DatePicker setButtonText(final String value) {
        if (value == null) {
            this.options.remove("buttonText");
        } else {
            this.options.put("buttonText", value);
        }
        return this;
    }

    public DatePicker setButtonText(final AjaxRequestTarget target, final String value) {
        setButtonText(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','buttonText','" + value + "');");
        return this;
    }


    /**
     * Sets a button image that is displayed near the {@link org.apache.wicket.markup.html.form.TextField} for the visual date
     * representation.
     */
    public DatePicker setButtonImage(final String value) {
        if (value == null || value.trim().length() == 0) {
            this.options.remove("buttonImage");
            this.options.remove("showOn");
        } else {
            this.options.put("buttonImage", value);
            this.options.put("showOn", "button");
        }
        return this;
    }

    public DatePicker setButtonImage(final AjaxRequestTarget target, final String value) {
        setButtonImage(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','buttonImage','" + (value == null ? "" : value) + "','showOn','button');");
        return this;
    }


    /**
     * Sets a button image that is displayed near the {@link org.apache.wicket.markup.html.form.TextField} for the visual date
     * representation.
     */
    public DatePicker setButtonImage(final ResourceReference value) {
        if (value == null) {
            this.options.remove("buttonImage");
            this.options.remove("showOn");
        } else {
//            TODO_WICKET15

            this.options.put("buttonImage", "resources/" + value.getKey());
//            this.options.put("buttonImage", "resources/" + value.getSharedResourceKey());
            this.options.put("showOn", "button");
        }
        return this;
    }

    public DatePicker setButtonImage(final AjaxRequestTarget target, final ResourceReference value) {
        setButtonImage(value);
        //            TODO_WICKET15
//        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','buttonImage','" + (value == null ? "" : value.getSharedResourceKey()) + "','showOn','button');");
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','buttonImage','" + (value == null ? "" : value.getKey()) + "','showOn','button');");
        return this;
    }


    /**
     * Sets the 'changeMonth' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DatePicker setChangeMonth(final boolean value) {
        if (!value) {
            this.options.remove("changeMonth");
        } else {
            this.options.put("changeMonth", value);
        }
        return this;
    }

    public DatePicker setChangeMonth(final AjaxRequestTarget target, final boolean value) {
        setChangeMonth(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','changeMonth'," + value + ");");
        return this;
    }


    /**
     * Sets the 'changeYear' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DatePicker setChangeYear(final boolean value) {
        if (!value) {
            this.options.remove("changeYear");
        } else {
            this.options.put("changeYear", value);
        }
        return this;
    }

    public DatePicker setChangeYear(final AjaxRequestTarget target, final boolean value) {
        setChangeYear(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','changeYear'," + value + ");");
        return this;
    }


    /**
     * Sets the 'closeText' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DatePicker setCloseText(final String value) {
        if (value == null || value.trim().length() == 0) {
            this.options.remove("closeText");
        } else {
            this.options.put("closeText", value);
        }
        return this;
    }

    public DatePicker setCloseText(final AjaxRequestTarget target, final String value) {
        setCloseText(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','closeText','" + value + "');");
        return this;
    }


    /**
     * Sets the 'constraintInput' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DatePicker setConstraintInput(final boolean value) {
        if (!value) {
            this.options.remove("constraintInput");
        } else {
            this.options.put("constraintInput", value);
        }
        return this;
    }

    public DatePicker setConstraintInput(final AjaxRequestTarget target, final boolean value) {
        setConstraintInput(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','constraintInput'," + value + ");");
        return this;
    }


    /**
     * Sets the 'currentText' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value Text to be dsiplayes on button
     * @return this object
     */
    public DatePicker setCurrentText(final String value) {
        if (value == null || value.trim().length() == 0) {
            this.options.remove("currentText");
        } else {
            this.options.put("currentText", value);
        }
        return this;
    }

    public DatePicker setCurrentText(final AjaxRequestTarget target, final String value) {
        setCurrentText(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','currentText','" + value + "');");
        return this;
    }


    /**
     * Sets the 'dateFormat' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value the date format
     * @return this object
     */
    public DatePicker setDateFormat(final String value) {
        if (value == null || value.trim().length() == 0) {
            this.options.remove("dateFormat");
        } else {
            this.options.put("dateFormat", value);
        }
        return this;
    }

    public DatePicker setDateFormat(final AjaxRequestTarget target, final String value) {
        setDateFormat(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','dateFormat','" + value + "');");
        return this;
    }


    public DatePicker setDate(final AjaxRequestTarget target, final Date date) {
        String jsDate = getJavaScriptDateObject(date);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('setDate'," + jsDate + ");");
        return this;
    }

    public DatePicker setDefaultDate(final Date date) {
        return addToOrRemoveOfOptions("defaultDate", date);
    }

    private DateFormat getLocalDateFormat() {
        Locale locale = Session.get().getLocale();
        DateFormat df;
        if (locale != null) {
            df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        } else {
            df = DateFormat.getDateInstance(DateFormat.SHORT);
        }
        return df;
    }


    /**
     * Sets the 'duration' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value duration in ms
     * @return this object
     */
    public DatePicker setDuration(final String value) {
        if (value == null || value.trim().length() == 0) {
            this.options.remove("duration");
        } else {
            this.options.put("duration", value);
        }
        return this;
    }

    public DatePicker setDuration(final AjaxRequestTarget target, final String value) {
        setDuration(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','duration','" + value + "');");
        return this;
    }

    public DatePicker setDuration(final int value) {
        if (value <= 0) {
            this.options.remove("duration");
        } else {
            this.options.put("duration", value);
        }
        return this;
    }

    public DatePicker setDuration(final AjaxRequestTarget target, final int value) {
        setDuration(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','duration'," + value + ");");
        return this;
    }


    /**
     * Sets the 'duration' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value duration
     * @return this object
     */
    public DatePicker setDuration(final JQuerySpeed value) {
        if (value == null) {
            this.options.remove("duration");
        } else {
            this.options.put("duration", value.getSpeed());
        }
        return this;
    }

    public DatePicker setDuration(final AjaxRequestTarget target, final JQuerySpeed value) {
        setDuration(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','duration','" + value.getSpeed() + "');");
        return this;
    }

    /**
     * The range of years displayed in the year drop-down: either relative to today's year ("-nn:+nn"), relative to the currently selected year ("c-nn:c+nn"),
     * absolute ("nnnn:nnnn"), or combinations of these formats ("nnnn:-nn").
     * Note that this option only affects what appears in the drop-down, to restrict which dates may be selected use the minDate and/or maxDate options.
     */

    public DatePicker setYearRange(final String value) {
        return addToOrRemoveOfOptions("yearRange", value);
    }


    /**
     * Sets the 'maxDate' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value max date as String
     * @return this object
     */
    public DatePicker setMaxDate(final String value) {
        return addToOrRemoveOfOptions("maxDate", value);
    }

    private DatePicker addToOrRemoveOfOptions(String key, String value) {
        if (value == null) {
            this.options.remove(key);
        } else {
            this.options.put(key, value);
        }
        return this;
    }

    private DatePicker addToOrRemoveOfOptions(String key, Date value) {
        if (value == null) {
            this.options.remove(key);
        } else {
            String jsDate = getJavaScriptDateObject(value);
            this.options.put(key, new JsScript(jsDate));
        }
        return this;
    }

    public DatePicker setMaxDate(final AjaxRequestTarget target, final String value) {
        setMaxDate(value);
        addOptionToDatepicker(target, "maxDate", value);
        return this;
    }

    private void addOptionToDatepicker(AjaxRequestTarget target, String key, String value) {
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','" + key + "','" + value + "');");
    }


    /**
     * Sets the 'maxDate' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value max date as String
     * @return this object
     */
    public DatePicker setMaxDate(final Date value) {
        return addToOrRemoveOfOptions("maxDate", value);
    }

    public DatePicker setMaxDate(final AjaxRequestTarget target, final Date value) {
        setMaxDate(value);
        if (value == null) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','maxDate',null);");
        } else {
            String jsDate = getJavaScriptDateObject(value);
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','maxDate'," + jsDate + ");");
        }
        return this;
    }

    private String getJavaScriptDateObject(Date value) {
        // to avoid problems with different date formats, we use a JAvaScript Date object instead

        Locale locale = Session.get().getLocale();
        Calendar cal;
        if (locale != null) {
            cal = Calendar.getInstance(locale);
        } else {
            cal = Calendar.getInstance();
        }
        cal.setTime(value);

        return "new Date(" + cal.get(Calendar.YEAR) + "," + cal.get(Calendar.MONTH) + "," + cal.get(Calendar.DAY_OF_MONTH) + ")";
    }

    public DatePicker setMaxDate(final java.sql.Date value) {
        if (value == null) {
            this.options.remove("maxDate");
        } else {
            // to avoid problems with different date formats, we use a JAvaScript Date object instead
            Locale locale = Session.get().getLocale();
            Calendar cal;
            if (locale != null) {
                cal = Calendar.getInstance(locale);
            } else {
                cal = Calendar.getInstance();
            }
            cal.setTimeInMillis(value.getTime());

            String jsDate = "new Date(" + cal.get(Calendar.YEAR) + "," + cal.get(Calendar.MONTH) + "," + cal.get(Calendar.DAY_OF_MONTH) + ")";
            this.options.put("maxDate", new JsScript(jsDate));
        }
        return this;
    }

    public DatePicker setMaxDate(final AjaxRequestTarget target, final java.sql.Date value) {
        setMaxDate(value);
        if (value == null) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','maxDate',null);");
        } else {
            // to avoid problems with different date formats, we use a JAvaScript Date object instead
            Locale locale = Session.get().getLocale();
            Calendar cal;
            if (locale != null) {
                cal = Calendar.getInstance(locale);
            } else {
                cal = Calendar.getInstance();
            }
            cal.setTimeInMillis(value.getTime());

            String jsDate = "new Date(" + cal.get(Calendar.YEAR) + "," + cal.get(Calendar.MONTH) + "," + cal.get(Calendar.DAY_OF_MONTH) + ")";
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','maxDate'," + jsDate + ");");
        }
        return this;
    }


    /**
     * Sets the 'minDate' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value min date as String
     * @return this object
     */
    public DatePicker setMinDate(final String value) {
        if (value == null) {
            this.options.remove("minDate");
        } else {
            this.options.put("minDate", value);
        }
        return this;
    }

    public DatePicker setMinDate(final AjaxRequestTarget target, final String value) {
        setMinDate(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','minDate','" + value + "');");
        return this;
    }


    /**
     * Sets the 'minDate' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value min date as String
     * @return this object
     */
    public DatePicker setMinDate(final Date value) {
        if (value == null) {
            this.options.remove("minDate");
        } else {
            // to avoid problems with different date formats, we use a JAvaScript Date object instead
            String jsDate = getJavaScriptDateObject(value);

            this.options.put("minDate", new JsScript(jsDate));
        }
        return this;
    }

    public DatePicker setMinDate(final AjaxRequestTarget target, final Date value) {
        setMinDate(value);
        if (value == null) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','minDate',null);");
        } else {
            String jsDate = getJavaScriptDateObject(value);

            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','minDate'," + jsDate + ");");
        }
        return this;
    }

    public DatePicker setMinDate(final java.sql.Date value) {
        if (value == null) {
            this.options.remove("minDate");
        } else {
            // to avoid problems with different date formats, we use a JAvaScript Date object instead
            Locale locale = Session.get().getLocale();
            Calendar cal;
            if (locale != null) {
                cal = Calendar.getInstance(locale);
            } else {
                cal = Calendar.getInstance();
            }
            cal.setTimeInMillis(value.getTime());

            String jsDate = "new Date(" + cal.get(Calendar.YEAR) + "," + cal.get(Calendar.MONTH) + "," + cal.get(Calendar.DAY_OF_MONTH) + ")";

            this.options.put("minDate", new JsScript(jsDate));
        }
        return this;
    }

    public DatePicker setMinDate(final AjaxRequestTarget target, final java.sql.Date value) {
        setMinDate(value);
        if (value == null) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','minDate',null);");
        } else {
            // to avoid problems with different date formats, we use a JAvaScript Date object instead
            Locale locale = Session.get().getLocale();
            Calendar cal;
            if (locale != null) {
                cal = Calendar.getInstance(locale);
            } else {
                cal = Calendar.getInstance();
            }
            cal.setTimeInMillis(value.getTime());

            String jsDate = "new Date(" + cal.get(Calendar.YEAR) + "," + cal.get(Calendar.MONTH) + "," + cal.get(Calendar.DAY_OF_MONTH) + ")";

            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','minDate'," + jsDate + ");");
        }
        return this;
    }


    /**
     * Sets the 'numberOfMonths' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value the number of months to show at one
     * @return this object
     */
    public DatePicker setNumberOfMonths(final int value) {
        if (value == 0) {
            this.options.remove("numberOfMonths");
        } else {
            this.options.put("numberOfMonths", value);
        }
        return this;
    }

    public DatePicker setNumberOfMonths(final AjaxRequestTarget target, final int value) {
        setNumberOfMonths(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','numberOfMonths'," + value + ");");
        return this;
    }


    /**
     * Sets the 'numberOfMonths' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     */
    public DatePicker setNumberOfMonths(final int rows, final int columns) {
        if (rows == 0 || columns == 0) {
            this.options.remove("numberOfMonths");
        } else {
            this.options.put("numberOfMonths", rows, columns);
        }
        return this;
    }

    public DatePicker setNumberOfMonths(final AjaxRequestTarget target, final int rows, final int columns) {
        setNumberOfMonths(rows, columns);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() +
                "').datepicker('option','numberOfMonths',[" + rows + "," + columns + "]);");
        return this;
    }


    /**
     * Sets the 'selectOtherMonths' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DatePicker setSelectOtherMonths(final boolean value) {
        if (!value) {
            this.options.remove("selectOtherMonths");
        } else {
            this.options.put("selectOtherMonths", value);
        }
        return this;
    }

    public DatePicker setSelectOtherMonths(final AjaxRequestTarget target, final boolean value) {
        setSelectOtherMonths(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','selectOtherMonths'," + value + ");");
        return this;
    }


    /**
     * Sets the 'showAnim' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DatePicker setShowAnim(final ShowAnim value) {
        if (value == null) {
            this.options.remove("showAnim");
        } else {
            this.options.put("showAnim", value.getAnimName());
        }
        return this;
    }

    public DatePicker setShowAnim(final AjaxRequestTarget target, final ShowAnim value) {
        setShowAnim(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','showAnim'," + value.getAnimName() + ");");
        return this;
    }


    /**
     * Sets the 'showButtonPanel' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DatePicker setShowButtonPanel(final boolean value) {
        if (!value) {
            this.options.remove("showButtonPanel");
        } else {
            this.options.put("showButtonPanel", value);
        }
        return this;
    }

    public DatePicker setShowButtonPanel(final AjaxRequestTarget target, final boolean value) {
        setShowButtonPanel(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','showButtonPanel'," + value + ");");
        return this;
    }


    /**
     * Sets the 'showCurrentAtPos' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DatePicker setShowCurrentAtPos(final int value) {
        if (value == 0) {
            this.options.remove("showCurrentAtPos");
        } else {
            this.options.put("showCurrentAtPos", value);
        }
        return this;
    }

    public DatePicker setShowCurrentAtPos(final AjaxRequestTarget target, final int value) {
        setShowCurrentAtPos(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','showCurrentAtPos'," + value + ");");
        return this;
    }


    /**
     * Sets the 'showMonthAfterYear' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DatePicker setShowMonthAfterYear(final boolean value) {
        if (!value) {
            this.options.remove("showMonthAfterYear");
        } else {
            this.options.put("showMonthAfterYear", value);
        }
        return this;
    }

    public DatePicker setShowMonthAfterYear(final AjaxRequestTarget target, final boolean value) {
        setShowMonthAfterYear(value);
        target.appendJavaScript(getJQueryObjectOfComponent(getComponent()) + ".datepicker('option','showMonthAfterYear'," + value + ");");
        return this;
    }

    protected String getJQueryObjectOfComponent(Component component) {
        return "jQuery('#" + component.getMarkupId() + "')";
    }

    /**
     * Sets the 'showOn' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DatePicker setShowOn(final ShowOnTrigger value) {
        if (value == null) {
            this.options.remove("showOn");
        } else {
            this.options.put("showOn", value);
        }
        return this;
    }

    public DatePicker setShowOn(final AjaxRequestTarget target, final ShowOnTrigger value) {
        setShowOn(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','showOn','" + value.getTriggerName() + "');");
        return this;
    }


    /**
     * Sets the 'showOtherMonths' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DatePicker setShowOtherMonths(final boolean value) {
        if (!value) {
            this.options.remove("showOtherMonths");
        } else {
            this.options.put("showOtherMonths", value);
        }
        return this;
    }

    public DatePicker setShowOtherMonths(final AjaxRequestTarget target, final boolean value) {
        setShowOtherMonths(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','showOtherMonths'," + value + ");");
        return this;
    }


    /**
     * Sets the 'showWeek' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DatePicker setShowWeek(final boolean value) {
        if (!value) {
            this.options.remove("showWeek");
        } else {
            this.options.put("showWeek", value);
        }
        return this;
    }

    public DatePicker setShowWeek(final AjaxRequestTarget target, final boolean value) {
        setShowWeek(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','showWeek'," + value + ");");
        return this;
    }


    /**
     * Sets the 'stepMonths' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param value how many months to move in one step
     * @return this object
     */
    public DatePicker setStepMonths(final int value) {
        if (value <= 0) {
            this.options.remove("stepMonths");
        } else {
            this.options.put("stepMonths", value);
        }
        return this;
    }

    public DatePicker setStepMonths(final AjaxRequestTarget target, final int value) {
        setStepMonths(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker('option','stepMonths'," + value + ");");
        return this;
    }

    /**
     * Sets the 'monthNamesShort' property for this DatePicker. Please consult the
     * jQuery documentation for a detailed description of this property.
     *
     * @param monthNamesShort The list of abbreviated month names, as used in the month header on each datepicker and as requested via the dateFormat option.
     *                        Ex.  new String[]{ "Jan", "Feb", "Mar", "Apr", "Maj", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"}
     * @return this object
     */

    public DatePicker setMonthNamesShort(String[] monthNamesShort){
        if (monthNamesShort == null) {
            this.options.remove("monthNamesShort");
        } else {
            this.options.put("monthNamesShort", monthNamesShort);
        }
        return this;
    }


    private String cssClass = null;

    /**
     * If you want en individual style for a DatePicker you may set this style with this method.
     * The DatePicker the gets sourrounded with a &lt;div class="..."&gt;.
     * Normally a DatePicker element looks like
     * <pre>
     * &lt;div id="ui-datepicker-div" class="ui-datepicker ui-widget ui-widget-content ....&gt;...&lt;/div&gt;
     * </pre>
     * If you set a cssClass the DatePicket looks like
     * <pre>
     * &lt;div class="cssClass"&gt;&lt;div id="ui-datepicker-div" class="ui-datepicker ui-widget ui-widget-content ....&gt;...&lt;/div&gt;&lt;/div&gt;
     * </pre>
     *
     * @param cssClass your custom css class
     */
    public void setCssClass(final String cssClass) {
        this.cssClass = cssClass;
    }


    private boolean onSelectNotificationWanted = false;

    /**
     * If set to {@code true}, the callback-Method {@link #onSelect(AjaxRequestTarget, String, SpecialKeys) }
     * is called when a date was picked.
     * See the jquery-ui documentation for more information
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DatePicker setWantOnSelectNotification(final boolean value) {
        this.onSelectNotificationWanted = value;
        return this;
    }


    private boolean onCloseNotificationWanted = false;

    /**
     * If set to {@code true}, the callback-Method {@link #onClose(AjaxRequestTarget, String, SpecialKeys) }
     * is called when a date was picked.
     * See the jquery-ui documentation for more information
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DatePicker setWantOnCloseNotification(final boolean value) {
        this.onCloseNotificationWanted = value;
        return this;
    }


    private boolean onChangeMonthYearNotificationWanted = false;

    /**
     * If set to {@code true}, the callback-Method {@link #onChangeMonthYear(AjaxRequestTarget, String, String, SpecialKeys) }
     * is called when a date was picked.
     * See the jquery-ui documentation for more information
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DatePicker setWantOnChangeMonthYearNotification(final boolean value) {
        this.onChangeMonthYearNotificationWanted = value;
        return this;
    }


    private boolean onBeforeShowNotificationWanted = false;

    /**
     * If set to {@code true}, the callback-Method {@link #onBeforeShow(org.apache.wicket.ajax.AjaxRequestTarget)}
     * is called before the DatePicker gets displayed.
     * <p/>
     * See the jquery-ui documentation for detailed information
     *
     * @return this object
     */
    public DatePicker setWantOnBeforeShowNotificationWanted(final boolean value) {
        this.onBeforeShowNotificationWanted = value;
        return this;
    }


    /*
     private boolean destroyBeforeRedraw = true;
     public void setDestroyBeforeRedraw(final boolean value) {
         this.destroyBeforeRedraw = value;
     }
     */

    @Override
    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
        super.updateAjaxAttributes(attributes);

    }

    @Override
    protected JsBuilder getJsBuilder() {
        if (this.onBeforeShowNotificationWanted) {
            this.options.put(EventType.BEFORE_SHOW.eventName,
                    new JsFunction("function(dateText,inst) { Wicket.Ajax.get({ 'u':'" +
                            this.getCallbackUrl() +
                            "', 'dep': [ function() {return {'" + EventType.IDENTIFIER + "':'" + EventType.BEFORE_SHOW +
                            "', 'keys':jQuery.jWicketSpecialKeysGetPressed()}}]" +
                            "});}"));
        } else {
            this.options.remove(EventType.BEFORE_SHOW.getEventName());
        }


        if (this.onSelectNotificationWanted) {
            this.options.put(EventType.ON_SELECT.eventName,
                    new JsFunction("function(dateText,inst) { Wicket.Ajax." + configureOnSelectGetOrPost() +"({ 'u':'" +
                            this.getCallbackUrl() +
                            "', 'dep': [ function() {return {'date': dateText ,'" + EventType.IDENTIFIER + "':'" + EventType.ON_SELECT +
                            "', 'keys':jQuery.jWicketSpecialKeysGetPressed()}}]" +
                            "});}"));

        } else {
            this.options.remove(EventType.ON_SELECT.getEventName());
        }


        if (this.onCloseNotificationWanted) {
            this.options.put(EventType.ON_CLOSE.eventName,
                    new JsFunction("function(dateText,inst) { Wicket.Ajax.get({ 'u':'" +
                            this.getCallbackUrl() +
                            "', 'dep': [ function() {return {'date': dateText ,'" + EventType.IDENTIFIER + "':'" + EventType.ON_CLOSE +
                            "', 'keys':jQuery.jWicketSpecialKeysGetPressed()}}]" +
                            "});}"));
        } else {
            this.options.remove(EventType.ON_CLOSE.getEventName());
        }


        if (this.onChangeMonthYearNotificationWanted) {
            this.options.put(EventType.ON_CHANGE_MONTH_YEAR.eventName,

                    onChangeMonthJsFunction());
        } else {
            this.options.remove(EventType.ON_CHANGE_MONTH_YEAR.getEventName());
        }


        if (hasToAddBeforeShowDayToOptions()) {
            this.options.put(EventType.BEFORE_SHOW_DAY.eventName,
                    new JsFunction("function(date) {" +
                            "return " + getCheckFunctionName() + "(date);" +
                            "}"));
        } else {
            this.options.remove(EventType.BEFORE_SHOW_DAY.getEventName());
        }


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
        if (isAlreadyRendered()) {
            builder.append("jQuery('#" + getComponent().getMarkupId() + "+button').remove();");
        }

        /* Normal processing */
        builder.append("jQuery('#" + getComponent().getMarkupId() + "').datepicker(");
        builder.append("{");
        builder.append(this.options.toString(this.rawOptions));
        builder.append("}");
        builder.append(")");


        if (this.cssClass != null) {
            builder.append(";jQuery('#ui-datepicker-div').wrap('<div class=\"");
            builder.append(this.cssClass);
            builder.append("\" />')");
        }

        return builder;
    }

    protected JsFunction onChangeMonthJsFunction() {
        return new JsFunction("function(year,month,inst) { Wicket.Ajax.get({ 'u':'" +
                this.getCallbackUrl() +
                "', 'dep': [ function() {return {" +
                "'year': year" +
                ", 'month': month" +
                ",'" + EventType.IDENTIFIER + "': '" + EventType.ON_CHANGE_MONTH_YEAR +
                "', 'keys': jQuery.jWicketSpecialKeysGetPressed()}}]" +
                "});}");
    }


    protected enum AjaxCall implements Serializable{
        GET("get"),
        POST("post");

        private final String axajCall;

         AjaxCall(String ajaxCall){
           this.axajCall = ajaxCall;
        }

        public String getAxajCall() {
            return this.axajCall;
        }
    }
    protected String configureOnSelectGetOrPost() {
        return AjaxCall.GET.getAxajCall();
    }

    protected boolean hasToAddBeforeShowDayToOptions() {
        return this.showDayStates != null && this.showDayStates.size() > 0;
    }



    protected void onBeforeShow(final AjaxRequestTarget target) {
    }

    /**
     * If you have set {@link #setWantOnSelectNotification(boolean)} to {@code true}
     * this method is called after the user picked a date in the datepicker.
     *
     * @param target      the AjaxRequestTarget of the resize operation.
     * @param pickedDate  The selected date as {@code String}
     * @param specialKeys the special keys that were pressed when the event occurs
     */
    protected void onSelect(final AjaxRequestTarget target, final String pickedDate, final SpecialKeys specialKeys) {
    }

    /**
     * If you have set {@link #setWantOnSelectNotification(boolean)} to {@code true}
     * this method is called after the user picked a date in the datepicker. This method is called after
     * {@link #onSelect(AjaxRequestTarget, String, SpecialKeys) was called only if the picked date can be parsed into
     * a Java Date object.
     *
     * @param target      the AjaxRequestTarget of the resize operation.
     * @param pickedDate  The selected date as {@link java.util.Date}
     * @param specialKeys the special keys that were pressed when the event occurs
     */
    protected void onSelect(final AjaxRequestTarget target, final java.util.Date pickedDate, final SpecialKeys specialKeys) {
    }

    /**
     * If you have set {@link #setWantOnSelectNotification(boolean)} to {@code true}
     * this method is called after the user picked a date in the datepicker. This method is called after
     * {@link #onSelect(AjaxRequestTarget, String, SpecialKeys) was called only if the picked date can be parsed into
     * a Java Date object.
     *
     * @param target      the AjaxRequestTarget of the resize operation.
     * @param pickedDate  The selected date as {@link java.util.Date}
     * @param specialKeys the special keys that were pressed when the event occurs
     */
    protected void onSelect(final AjaxRequestTarget target, final java.sql.Date pickedDate, final SpecialKeys specialKeys) {
    }


    /**
     * If you have set {@link #setWantOnCloseNotification(boolean)} to {@code true}
     * this method is called after the datepicker was closed, regardless of picking a date.
     *
     * @param target      the AjaxRequestTarget of the resize operation.
     * @param specialKeys the special keys that were pressed when the event occurs
     */
    protected void onClose(final AjaxRequestTarget target, final String pickedDate, final SpecialKeys specialKeys) {
    }


    /**
     * If you have set {@link #setWantOnChangeMonthYearNotification(boolean)} to {@code true}
     * this method is called after another moth or year is displayed, regardless of picking a date.
     *
     * @param target      the AjaxRequestTarget of the resize operation.
     * @param specialKeys the special keys that were pressed when the event occurs
     */
    protected void onChangeMonthYear(final AjaxRequestTarget target, final String year, final String month, final SpecialKeys specialKeys) {
    }


    private Collection<ShowDay> showDayStates = null;

    /**
     * Add one more special treatment for a day. If you don't provide a {@link ShowDay} object for a day it is
     * selectable by default.
     * Use this method if you want a special treatment (selectable, CSS class oder tooltip text)
     * for a day.
     * You may call this method as often as you like. The state is added to the existing list of states.
     * Do not provide different states for one day. The resulting behavior is not defined.
     *
     * @param state as specific treatment for one day.
     */
    public void addShowDayState(final ShowDay state) {
        if (this.showDayStates == null) {
            this.showDayStates = new ArrayList<ShowDay>(1);
        }
        this.showDayStates.add(state);
        this.dayCheckerRendered = false;
    }


    /**
     * Set the special treatments for multiple days at one. This removes all former definitons and
     * replaces them with the new {@code states}.
     *
     * @param states A {@code Collection} of states.
     */
    public void setShowDayStates(final Collection<ShowDay> states) {
        this.showDayStates = states;
        this.dayCheckerRendered = false;
    }


    /**
     * This Class controls the visibility of a single day rectangle in the DatePicker popup and the
     * ability to select this day and an optional text popup for a single day.
     */
    public static class ShowDay implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * If the day corresponding to parameter {@code day} should be selectable, the parameter {@code selectable} should be set to
         * {@code true}, if not it should be set to {@code false}.
         *
         * @param date       the day
         * @param selectable {@code true} if the day should be selectable and {@code false} if the day sould net be selectable.
         */
        public ShowDay(final Date date, final boolean selectable) {
            this(date, selectable, "", "");
        }


        /**
         * If the day corresponding to parameter {@code day} should be selectable, the parameter {@code selectable} should be set to
         * {@code true}, if not it should be set to {@code false}. The CSS class of the day can be controlled
         * with parameter {@code cssClass}.
         *
         * @param date       the day
         * @param selectable {@code true} if the day should be selectable and {@code false} if the day sould net be selectable.
         * @param cssClass   regardless from {@code selectable} state of the day this CSS class is assigned to the day rectangle
         */
        public ShowDay(final Date date, final boolean selectable, final String cssClass) {
            this(date, selectable, cssClass, "");
        }


        /**
         * If the day corresponding to parameter {@code day} should be selectable, the parameter {@code selectable} should be set to
         * {@code true}, if not it should be set to {@code false}. The CSS class of the day can be controlled
         * with parameter {@code cssClass}.
         *
         * @param date       the day
         * @param selectable {@code true} if the day should be selectable and {@code false} if the day sould net be selectable.
         * @param cssClass   regardless from {@code selectable} state of the day this CSS class is assigned to the day rectangle
         * @param tooltip    a tooltip text to be shon wehn the mouse is placed over the given day
         */
        public ShowDay(final Date date, final boolean selectable, final String cssClass, final String tooltip) {
            setDate(date);
            setSelectable(selectable);
            setCssClass(cssClass);
            setTooltip(tooltip);
        }


        private Date date;

        public Date getDate() {
            return this.date;
        }

        public void setDate(final Date value) {
            this.date = value;
        }


        private boolean selectable;

        public boolean isSelectable() {
            return this.selectable;
        }

        public void setSelectable(final boolean value) {
            this.selectable = value;
        }


        private String cssClass;

        public String getCssClass() {
            return this.cssClass;
        }

        public void setCssClass(final String value) {
            this.cssClass = value;
        }


        private String tooltip;

        public String getTooltip() {
            return this.tooltip;
        }

        public void setTooltip(final String value) {
            this.tooltip = value;
        }


        @Override
        public String toString() {
            return "ShowDay: selectable=" + isSelectable() + ", CSS class=" + getCssClass() + ", tooltip=" + getTooltip();
        }
    }


    private boolean dayCheckerRendered = false;

    @Override
    public void renderHead(Component component, final IHeaderResponse response) {
        super.renderHead(component, response);
        if (hasToAddBeforeShowDayToOptions() && !this.dayCheckerRendered) {
            //draggablesAcceptedByDroppable.renderJsDropAcceptFunction(response);
            this.dayCheckerRendered = true;

            StringBuilder sb = getJsShowDatesVariable();

            sb.append("var ");
            sb.append(getCheckFunctionName());
            sb.append(" = function(date) {\n");
            sb.append("   var hash = '';\n");
            // Day for hash
            sb.append("   var intVal = date.getDate();\n");
            sb.append("   if (intVal < 10)\n");
            sb.append("      hash += '0';\n");
            sb.append("   hash += intVal;\n");
            // Month for Hash
            sb.append("   intVal = date.getMonth()+1;\n");
            sb.append("   if (intVal < 10)\n");
            sb.append("      hash += '0';\n");
            sb.append("   hash += intVal;\n");
            // Year
            sb.append("   intVal = date.getFullYear();\n");
            sb.append("   if (intVal < 1000)\n");
            sb.append("      hash += '0';\n");
            sb.append("   if (intVal < 100)\n");
            sb.append("      hash += '0';\n");
            sb.append("   if (intVal < 10)\n");
            sb.append("      hash += '0';\n");
            sb.append("   hash += intVal;\n");
            // retrieve the day from array
            sb.append("   var found = ");
            sb.append(getCheckFunctionName());
            sb.append("days[hash];\n");
            sb.append("   if (found != null)\n");
            sb.append("      return found;\n");
            sb.append("   else\n");
            sb.append("      return datePickerDefaultShowDayState;\n");
            sb.append("};");

            response.render(JavaScriptHeaderItem.forScript(sb.toString(), getCheckFunctionName() + "ID"));
        }
    }

    protected StringBuilder getJsShowDatesVariable() {
        StringBuilder sb = new StringBuilder();
            sb.append("window.").append(getCheckFunctionName()).append("days = {");
        if (this.showDayStates != null && this.showDayStates.size() > 0) {
            Calendar cal = Calendar.getInstance();
            boolean first = true;
            for (ShowDay day : this.showDayStates) {
                if (!first) {
                    sb.append(",");
                } else {
                    first = false;
                }
                cal.setTime(day.getDate());

                // Hash code for day
                sb.append("'");
                // Day
                int numVal = cal.get(Calendar.DAY_OF_MONTH);
                if (numVal < 10) {
                    sb.append("0");
                }
                sb.append(numVal);
                // Month
                numVal = cal.get(Calendar.MONTH) + 1;
                if (numVal < 10) {
                    sb.append("0");
                }
                sb.append(numVal);
                // Year
                numVal = cal.get(Calendar.YEAR);
                if (numVal < 1000) {
                    sb.append("0");
                }
                if (numVal < 100) {
                    sb.append("0");
                }
                if (numVal < 10) {
                    sb.append("0");
                }
                sb.append(numVal);
                sb.append("':new Array(");
                sb.append(day.isSelectable() ? "true" : "false");
                sb.append(",'");
                sb.append(day.getCssClass());
                sb.append("','");
                sb.append(day.getTooltip());
                sb.append("')");
            }
        }
        sb.append("};\n");
        return sb;
    }


    private String getCheckFunctionName() {
        return "jWicketCheckBeforeShowDayFor" + getComponent().getMarkupId();
    }


    /**
     * Disable the datepicker
     *
     * @param target An AjaxRequestTarget
     */
    public void disable(final AjaxRequestTarget target) {
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker( 'disable' );");
        target.add(getComponent());
    }

    public void refresh(final AjaxRequestTarget target) {
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker( 'refresh' );");
    }

    /**
     * Enable the datepicker
     *
     * @param target An AjaxRequestTarget
     */
    public void enable(final AjaxRequestTarget target) {
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').datepicker( 'enable' );");
        target.appendJavaScript(getJsBuilder().toString());
    }


    private enum EventType implements Serializable {

        UNKNOWN("*"),
        BEFORE_SHOW("beforeShow"),
        BEFORE_SHOW_DAY("beforeShowDay"),
        ON_CHANGE_MONTH_YEAR("onChangeMonthYear"),
        ON_CLOSE("onClose"),
        ON_SELECT("onSelect");

        public static final String IDENTIFIER = "wicketDatepickerEvent";

        private final String eventName;

        private EventType(final String eventName) {
            this.eventName = eventName;
        }

        public String getEventName() {
            return this.eventName;
        }

        public static EventType stringToType(final String s) {
            for (EventType t : EventType.values()) {
                if (t.getEventName().equals(s)) {
                    return t;
                }
            }
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

    //    @Override
    public JQueryCssResourceReference[] getCssResources() {
        return new JQueryCssResourceReference[]{
                new JQueryCssResourceReference(DatePicker.class, "css/jquery-ui.css"),
                new JQueryCssResourceReference(DatePicker.class, "css/jquery.ui.base.css"),
                new JQueryCssResourceReference(DatePicker.class, "css/jquery.ui.theme.css")
        };
    }

}
