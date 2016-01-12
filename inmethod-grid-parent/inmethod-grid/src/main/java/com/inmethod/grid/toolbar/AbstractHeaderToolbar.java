package com.inmethod.grid.toolbar;

import org.apache.wicket.model.IModel;

import com.inmethod.grid.common.AbstractGrid;

/**
 * Toolbars extending this class are displayed in table header right below the row displaying column
 * names. The toolbar must produce markup with structure such as
 * 
 * <pre>
 *   &lt;th&gt;column 1 data&lt;/th&gt;
 *   &lt;th&gt;column 2 data&lt;/th&gt;
 *   ...
 *   &lt;th&gt;column n data&lt;/th&gt;
 * </pre>
 * 
 * as it's put directly inside a table row.
 * 
 * @param <M>
 *            grid model object type
 * @param <I>
 *            row/item model object type
 * 
 * @author Matej Knopp
 */
public abstract class AbstractHeaderToolbar<M, I, S> extends AbstractToolbar<M, I, S>
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param dataGrid
	 * @param model
	 */
	public AbstractHeaderToolbar(AbstractGrid<M, I, S> dataGrid, IModel<String> model)
	{
		super(dataGrid, model);
	}

}
