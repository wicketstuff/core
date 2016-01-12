package org.wicketstuff.objectautocomplete;

import java.io.Serializable;

import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.request.Response;

/**
 * @author roland
 * @since Sep 26, 2008
 */
public class AbstractObjectAutoCompleteRenderer<O> implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String idProperty = "id";

	/**
	 * Get the verified id value for an object
	 * 
	 * @param object
	 *            for which to fetch the id
	 * @return the id
	 * @throws IllegalStateException
	 *             if the id value could not be extracted
	 */
	final protected String getIdValueForObject(O object)
	{
		String idValue = getIdValue(object);
		if (idValue == null)
		{
			throw new IllegalStateException(
				"A call to idValue(Object) returned an illegal value: null for object: " +
					object.toString());
		}
		return idValue;
	}

	/**
	 * Get id of the object to render. By default this is extracted by reflection using the
	 * {@link #idProperty}, but can be overwritten by a subclass
	 * 
	 * @param object
	 *            object from which to extract the id
	 * @return the id value
	 * @throws IllegalArgumentException
	 *             if no id could be extracted
	 */
	protected String getIdValue(O object)
	{
		Object returnValue = PropertyResolver.getValue(idProperty, object);
		if (returnValue == null)
		{
			throw new IllegalArgumentException("Id property " + idProperty +
				" could not be extracted from object " + object);
		}
		return returnValue.toString();
	}

	/**
	 * Get and check proper text value
	 * 
	 * @param object
	 *            object for which to get the text value
	 * @return return the verified text value
	 * @throws IllegalStateException
	 *             if the text value could not be extracted
	 */
	protected final String getTextValueForObject(O object)
	{
		String textValue = getTextValue(object);

		textValue = textValue.replaceAll("\\\"", "&quot;");
		if (textValue == null)
		{
			throw new IllegalStateException(
				"A call to textValue(Object) returned an illegal value: null for object: " +
					object.toString());
		}
		return textValue;
	}

	/**
	 * Retrieves the text value that will be set on the textbox if this assist is selected Can be
	 * overwritten, by default it returns the object's toString()
	 * 
	 * @param pObject
	 *            assist choice object
	 * @return the text value that will be set on the textbox if this assist is selected
	 */
	protected String getTextValue(O pObject)
	{
		return pObject.toString();
	}

	/**
	 * Set the property for extracting the id of an object
	 * 
	 * @param pIdProperty
	 *            property name of the object id
	 */
	public void setIdProperty(String pIdProperty)
	{
		this.idProperty = pIdProperty;
	}

	protected void renderObject(O object, Response response, String criteria)
	{
		response.write("<li idvalue=\"" + getIdValueForObject(object) + "\" textvalue=\"" +
			getTextValueForObject(object) + "\">");
		renderChoice(object, response, criteria);
		response.write("</li>");
	}

	/**
	 * Render the visual portion of the assist. Usually the html representing the assist choice
	 * object is written out to the response use
	 *
	 * @param object
	 *            current assist choice
	 * @param response
	 * @param criteria
	 */
	protected void renderChoice(O object, Response response, String criteria)
	{
		response.write(getTextValueForObject(object));
	}
}
