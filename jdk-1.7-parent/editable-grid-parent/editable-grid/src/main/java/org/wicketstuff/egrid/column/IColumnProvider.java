package org.wicketstuff.egrid.column;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
/**
 * 
 * @author Nadeem Mohammad
 *
 */
public interface IColumnProvider<T, S>
{	
	List<? extends IColumn<T, S>> getColumns();
}
