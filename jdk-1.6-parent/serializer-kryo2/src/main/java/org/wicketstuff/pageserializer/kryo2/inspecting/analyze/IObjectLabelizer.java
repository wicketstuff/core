package org.wicketstuff.pageserializer.kryo2.inspecting.analyze;

/**
 * creates an label for an object instance if object is of type WebMarkupContainer it could return
 * the id of the component
 * 
 * @author mosmann
 * 
 */
public interface IObjectLabelizer
{
	/**
	 * label for an object
	 * @param object source
	 * @return a label or null if you dont know a good one
	 */
	String labelFor(Object object);
}
