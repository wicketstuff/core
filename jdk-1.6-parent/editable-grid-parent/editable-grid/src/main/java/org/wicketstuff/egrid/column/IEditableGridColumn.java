package org.wicketstuff.egrid.column;


public interface IEditableGridColumn<T>
{
	EditableCellPanel<T> getEditableCellPanel(String componentId);
}
