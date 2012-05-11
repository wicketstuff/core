package com.googlecode.wicket.jquery.ui.kendo.combobox;

import java.util.List;

public interface IComboBoxTemplate
{
	/**
	 * <b>Note: </b> a "text" property cannot be in the list. It is a reserved property which is mapped to the text of the {@link ComboBox}.<br/>
	 * If you need the textual representation is not the result of the bean's toString() method, you should use a {@link IComboBoxRenderer} (like {@link ComboBoxRenderer})  
	 * @return
	 * TODO: to rewrite
	 */
	List<String> getProperties();
	
	/**
	 * Get the html template to be used to customize the {@link ComboBox} display.
	 * @return the html template
	 * @see http://demos.kendoui.com/web/combobox/template.html
	 */
	String getHtml();
}
