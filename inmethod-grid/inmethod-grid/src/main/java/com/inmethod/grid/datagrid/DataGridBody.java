package com.inmethod.grid.datagrid;

import java.util.Collection;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.IGridSortState;
import com.inmethod.grid.IDataSource.IQuery;
import com.inmethod.grid.common.AbstractGridRow;
import com.inmethod.grid.common.AbstractPageableView;
import com.inmethod.grid.common.AttachPrelightBehavior;

/**
 * Contains data grid rows.
 * 
 * @author Matej Knopp
 */
public abstract class DataGridBody extends Panel implements IPageable {

	/**
	 * Constructor
	 * 
	 * @param id
	 *            component id
	 */
	public DataGridBody(String id) {
		super(id);
		setRenderBodyOnly(true);

		add(new Data("row"));
	}

	protected abstract IDataSource getDataSource();

	protected abstract int getRowsPerPage();

	protected abstract IGridSortState getSortState();

	protected abstract Collection<IGridColumn> getActiveColumns();

	protected abstract boolean isItemSelected(IModel itemModel);

	protected abstract void rowPopulated(WebMarkupContainer rowItem);

	private Data getData() {
		return (Data) get("row");
	}

	int getTotalRowCount() {
		return getData().getTotalRowCount();
	}

	int getCurrentPageItemCount() {
		return getData().getCurrentPageItemCount();
	}

	/**
	 * {@inheritDoc}
	 */
	public int getCurrentPage() {
		return getData().getCurrentPage();
	}

	/**
	 * {@inheritDoc}
	 */
	public int getPageCount() {
		return getData().getPageCount();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCurrentPage(int page) {
		getData().setCurrentPage(page);
	}

	class Data extends AbstractPageableView {

		private static final long serialVersionUID = 1L;

		private Data(String id) {
			super(id);
			setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
		}

		@Override
		protected IDataSource getDataSource() {
			return DataGridBody.this.getDataSource();
		}

		@Override
		protected int getRowsPerPage() {
			return DataGridBody.this.getRowsPerPage();
		}

		@Override
		protected IGridSortState getSortState() {
			return DataGridBody.this.getSortState();
		}

		@Override
		protected IQuery wrapQuery(final IQuery original) {
			return new DataGrid.IGridQuery() {
				public int getCount() {
					return original.getCount();
				}

				public int getFrom() {
					return original.getFrom();
				}

				public IGridSortState getSortState() {
					return original.getSortState();
				}

				public int getTotalCount() {
					return original.getTotalCount();
				}

				public DataGrid getDataGrid() {
					return (DataGrid) DataGridBody.this.findParent(DataGrid.class);
				}
			};
		}

		@Override
		protected void populateItem(final Item item) {
			item.add(new AbstractGridRow("item", item.getDefaultModel()) {
				private static final long serialVersionUID = 1L;

				@Override
				protected Collection<IGridColumn> getActiveColumns() {
					return DataGridBody.this.getActiveColumns();
				}

				@Override
				protected int getRowNumber() {
					return item.getIndex();
				}
			});
			item.add(new AttachPrelightBehavior());
			rowPopulated(item);
		}

		protected class RowItem extends Item {

			private static final long serialVersionUID = 1L;

			protected RowItem(String id, int index, IModel model) {
				super(id, index, model);
			}
			
			@Override
			protected void onComponentTag(ComponentTag tag) {

				super.onComponentTag(tag);
			
				CharSequence klass = tag.getString("class");
				if (klass == null) {
					klass = "";
				}
				if (klass.length() > 0)
					klass = klass + " ";

				if (getIndex() % 2 == 0) {
					klass = klass + "imxt-even";
				} else {
					klass = klass + "imxt-odd";
				}

				klass = klass + " imxt-want-prelight imxt-grid-row";

				if (isItemSelected(getDefaultModel())) {
					klass = klass + " imxt-selected";
				}

				tag.put("class", klass);
			}
		};
		
		@Override
		protected Item newItem(String id, final int index, final IModel model) {
			Item item = new RowItem(id, index, model);
			item.setOutputMarkupId(true);
			return item;
		}
	};

}
