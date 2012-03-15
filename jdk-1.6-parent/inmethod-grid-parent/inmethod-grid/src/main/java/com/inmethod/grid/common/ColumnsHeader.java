<<<<<<< HEAD
package com.inmethod.grid.common;

import java.util.Collection;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;

import com.inmethod.grid.IGridColumn;

/**
 * Panel that provides the markup for {@link ColumnsHeaderRepeater}.
 * 
 * @param <M>
 *            grid model object type
 * @param <I>
 *            row/item model object type
 * 
 * @see ColumnsHeaderRepeater
 * @author Matej Knopp
 */
public abstract class ColumnsHeader<M, I> extends Panel
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param id
	 */
	public ColumnsHeader(String id)
	{
		super(id);

		setRenderBodyOnly(true);

		add(new ColumnsHeaderRepeater<M, I>("header")
		{

			private static final long serialVersionUID = 1L;

			@Override
			Collection<IGridColumn<M, I>> getActiveColumns()
			{
				return ColumnsHeader.this.getActiveColumns();
			}

			@Override
			int getColumnWidth(IGridColumn<M, I> column)
			{
				return ColumnsHeader.this.getColumnWidth(column);
			}

			@Override
			protected void sortStateChanged(AjaxRequestTarget target)
			{
				ColumnsHeader.this.sortStateChanged(target);
			}

		});
	}

	abstract protected void sortStateChanged(AjaxRequestTarget target);

	abstract Collection<IGridColumn<M, I>> getActiveColumns();

	abstract int getColumnWidth(IGridColumn<M, I> column);

}
=======
package com.inmethod.grid.common;

import java.util.Collection;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;

import com.inmethod.grid.IGridColumn;

/**
 * Panel that provides the markup for {@link ColumnsHeaderRepeater}.
 * 
 * @param <M>
 *            grid model object type
 * @param <I>
 *            row/item model object type
 * 
 * @see ColumnsHeaderRepeater
 * @author Matej Knopp
 */
public abstract class ColumnsHeader<M, I> extends Panel
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param id
	 */
	public ColumnsHeader(String id)
	{
		super(id);

		setRenderBodyOnly(true);

		add(new ColumnsHeaderRepeater<M, I>("header")
		{

			private static final long serialVersionUID = 1L;

			@Override
			Collection<IGridColumn<M, I>> getActiveColumns()
			{
				return ColumnsHeader.this.getActiveColumns();
			}

			@Override
			int getColumnWidth(IGridColumn<M, I> column)
			{
				return ColumnsHeader.this.getColumnWidth(column);
			}

			@Override
			protected void sortStateChanged(AjaxRequestTarget target)
			{
				ColumnsHeader.this.sortStateChanged(target);
			}

		});
	}

	abstract protected void sortStateChanged(AjaxRequestTarget target);

	abstract Collection<IGridColumn<M, I>> getActiveColumns();

	abstract int getColumnWidth(IGridColumn<M, I> column);

}
>>>>>>> [InMethod Grid] Cherry Picked Initial Raystorm-InMethod-Grid Cherry Pick into Master
