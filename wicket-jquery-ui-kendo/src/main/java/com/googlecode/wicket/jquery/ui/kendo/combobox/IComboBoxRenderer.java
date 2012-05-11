package com.googlecode.wicket.jquery.ui.kendo.combobox;

import org.apache.wicket.IClusterable;


public interface IComboBoxRenderer<T> extends IClusterable
{
	/**
	 * Gets the text to be displayed 
	 * @return
	 */
	String getText(T object);
	String getText(T object, String property);
	String getValue(T object, int index);
}
