package org.wicketstuff.jwicket.ui.menu;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.jwicket.JQueryResourceReference;
import org.wicketstuff.jwicket.JsMap;
import org.wicketstuff.jwicket.SpecialKeys;
import org.wicketstuff.jwicket.ui.AbstractJqueryUiEmbeddedBehavior;


/**
 * You can add an instance of this class to a Wicket {@link Component} to make it
 * a resizable {@link Component}.
 * An instance of this class can be added to one and only one
 * {@link Component}. Another {@link Component} that should have exactly the
 * same behavior needs it's own instance.
 */
public class Menu extends AbstractJqueryUiEmbeddedBehavior {

	private static final long serialVersionUID = 1L;

	public static final JQueryResourceReference uiMenuJs = new JQueryResourceReference(Menu.class, "menu.js");

	private JsMap options = new JsMap();


	public Menu() {
		super(	SpecialKeys.specialKeysJs,
				AbstractJqueryUiEmbeddedBehavior.jQueryUiWidgetJs,
				uiMenuJs,
				
				AbstractJqueryUiEmbeddedBehavior.jQueryUiCustomCss
		);
	}

    /**
	 * Handles the event processing during resizing.
	 */
	@Override
	protected void respond(final AjaxRequestTarget target) {
		/***********
		Component component = getComponent();
		Request request;
		if (component != null && (request = component.getRequest()) != null) {
			EventType eventType = EventType.stringToType(request.getParameter(EventType.IDENTIFIER));
			if (eventType == EventType.ON_SELECT) {
				String selectedDate = request.getParameter("date");
				SpecialKeys specialKeys = new SpecialKeys(request);
				onSelect(target, selectedDate, specialKeys);
				Locale locale = Session.get().getLocale();
				DateFormat df;
				if (locale != null)
					df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
				else
					df = DateFormat.getDateInstance(DateFormat.MEDIUM);

				try {
					onSelect(target, df.parse(selectedDate), specialKeys);
				} catch (Exception e) {
					onSelect(target, (java.util.Date)null, specialKeys);
				}

				try {
					java.sql.Date date = new java.sql.Date(df.parse(selectedDate).getTime());
					onSelect(target, date, specialKeys);
				} catch (Exception e) {
					onSelect(target, (java.sql.Date)null, specialKeys);
				}
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
		*/
	}



	@Override
	protected JsBuilder getJsBuilder() {
		/*****
		if (onSelectNotificationWanted)
			options.put(EventType.ON_SELECT.eventName,
				new JsFunction("function(dateText,inst) { wicketAjaxGet('" +
								this.getCallbackUrl() +
								"&date='+dateText" +
								"+'&" + EventType.IDENTIFIER + "=" + EventType.ON_SELECT +
								"&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
								");}"));
		else
			options.remove(EventType.ON_SELECT.getEventName());


		if (onCloseNotificationWanted)
			options.put(EventType.ON_CLOSE.eventName,
				new JsFunction("function(dateText,inst) { wicketAjaxGet('" +
								this.getCallbackUrl() +
								"&date='+dateText" +
								"+'&" + EventType.IDENTIFIER + "=" + EventType.ON_CLOSE +
								"&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
								");}"));
		else
			options.remove(EventType.ON_CLOSE.getEventName());


		if (onChangeMonthYearNotificationWanted)
			options.put(EventType.ON_CHANGE_MONTH_YEAR.eventName,
				new JsFunction("function(year,month,inst) { wicketAjaxGet('" +
								this.getCallbackUrl() +
								"&year='+year" +
								"+'&month='+month" +
								"+'&" + EventType.IDENTIFIER + "=" + EventType.ON_CHANGE_MONTH_YEAR +
								"&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
								");}"));
		else
			options.remove(EventType.ON_CHANGE_MONTH_YEAR.getEventName());


		if (onBeforeShowDayNotificationWanted)
			options.put(EventType.BEFORE_SHOW_DAY.eventName,
				new JsFunction("function(date) { wicketAjaxGet('" +
								this.getCallbackUrl() +
								"&date='+date" +
								"+'&" + EventType.IDENTIFIER + "=" + EventType.BEFORE_SHOW_DAY +
								"&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
								");return new Array(true, '','');}"));
		else
			options.remove(EventType.BEFORE_SHOW_DAY.getEventName());
*/

		JsBuilder builder = new JsBuilder();
		

		builder.append("jQuery('#sisIsSeUl').menu(");
//		builder.append("jQuery('#menu1').menu(");
		builder.append("{");
//		builder.append(options.toString(rawOptions));
		
		
builder.append(
		
		
//		"mode: 'static'," +
		"type: 'drilldown'," +
		//selectCategories: true,
		"selected: function(event, ui) {"+
		"	alert('Selected item@1');" +
		"	alert('Selected item@2 ', ui.item);" +
		"}" +
//		"browse: function(event, ui) {"
		//console.log('Browsing item ', ui.item);

		
		""
		);
		
		
		
		builder.append("}");
		builder.append(")");

		return builder;
	}

}
