package org.wicketstuff.jwicket.ui.dragdrop;


import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * If you have a droppable element you sometimes want this droppable only to accept
 * a well defined set of draggable elements to be dropped onto this element.
 * For this reason you can give each draggable a name and tell a dropable the name of
 * all the draggables that you want it to accept. Multiple draggable  can share the
 * same name.
 * 
 * This class collects all the names of draggable elements for one or more droppable elements
 * 
 *
 */
public class DraggablesAcceptedByDroppable implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String DRAG_NAME_IDENTIFIER = "dragClass";

	private static final String functionPrefix = "wicketJQeryDropAccessTester";
	private static int nextId = 0;

	private final String id;

	private List<String> acceptedNames = new ArrayList<String>();


	/**
	 * Create a {@code DraggablesAcceptedByDroppable} object
	 *
	 * @param names An Array of names of droppable objects.
	 * Duplicate names are eliminated null values are ignored.
	 */
	public DraggablesAcceptedByDroppable(final String... names) {
		id = functionPrefix + String.valueOf(nextId++);
		for (String name : names)
			if (name != null && !acceptedNames.contains(name))
				acceptedNames.add(name);
	}


	/**
	 * Add a name to the current names
	 * @param name The name of a droppable object.
	 * Duplicate types are eliminated null values are ignored.
	 */
	public void addName(final String name) {
		if (name != null && !acceptedNames.contains(name))
			acceptedNames.add(name);
	}

	/**
	 * Remove a name.
	 * @param name The name to be removed
	 */
	public void removeName(final String name) {
		if (name != null)
			acceptedNames.remove(name);
	}
	
	/**
	 * How many names are currently accepted
	 * @return The numer of currently accepted names
	 */
	public int size() {
		return acceptedNames.size();
	}


	/** Each collection of names has a unique id. It is used e.g. to
	 * construct a unique JavaScript function name
	 * 
	 * @return Tne id of this set od names
	 */
	public String getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object other) {
		return super.equals(other);
	}


	private String getTypesAsJsSet() {
		if (acceptedNames.size() > 10) {
			StringBuilder sb = new StringBuilder("[");
			boolean first = true;
			for (String s : acceptedNames) {
				if (!first)
					sb.append(",");
				else
					first = false;
				sb.append("'");
				sb.append(s);
				sb.append("'");
			}
			sb.append("]");
			return sb.toString();
		}
		else {
			String jsSet = "[";
			boolean first = true;
			for (String s : acceptedNames) {
				if (!first)
					jsSet += ",";
				else
					first = false;
				jsSet += "'";
				jsSet += s;
				jsSet += "'";
			}
			jsSet += "]";
			return jsSet.toString();
		}
	}


	/**
	 * A Javascript function is used to check the names of accepted droppables against the
	 * name of a certain draggable.
	 *
	 * @return The name of the JavaScript function
	 */
	public String getJsAcceptCheckerFunctionName() {
		return id;
	}

	
	/**
	 * Create a JavaScript function that checks the names against the
	 * name of the function's argument. If the name of the function's argument
	 * matches on name in the list of names the function returns true.
	 * 
	 * This method renders the JavaScript function for name checking into the page header.
	 *
	 * The JavaScript function has the form
	 * <pre>
	 * var someFunctionName = function(candidate) {
	 *     var accepted = ['droppableA','droppableB'];
	 *     for (var i = 0; i < accepted.length; i++)
	 *         if (accepted[i] == jQuery(candidate).attr('dragClass')
	 *             return true;
	 *     return false;
	 * }
	 * </pre>
	 * @param response
	 */
	public void renderJsDropAcceptFunction(final IHeaderResponse response) {
		if (acceptedNames != null && acceptedNames.size() > 0) {
			response.render(JavaScriptContentHeaderItem.forScript(
                    "var " + getJsAcceptCheckerFunctionName() + " = function(candidate) {\n" +
                            "	var accepted = " + getTypesAsJsSet() + ";\n" +
                            "	var candidateString = jQuery(candidate).attr('" + DRAG_NAME_IDENTIFIER + "');\n" +
                            "	for (var i = 0; i < accepted.length; i++)\n" +
                            "		if (accepted[i] == candidateString)\n" +
                            "			return true;" +
                            "	return false;" +
                            "};"
                    ,
                    id
            ));
		}
		else {
			response.render(JavaScriptContentHeaderItem.forScript(
				"var " + getJsAcceptCheckerFunctionName() + " = function(candidate) {\n" +
		    	"	return false;" +
				"};"
		    	,
				id
			));
		}

	}

}
