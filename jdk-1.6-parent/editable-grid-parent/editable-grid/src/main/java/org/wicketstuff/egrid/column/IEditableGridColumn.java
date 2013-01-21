package org.wicketstuff.egrid.column;

/**
 * 
 * @author Nadeem Mohammad
 *
 */
public interface IEditableGridColumn<T>
{
	EditableCellPanel<T> getEditableCellPanel(String componentId);
}
