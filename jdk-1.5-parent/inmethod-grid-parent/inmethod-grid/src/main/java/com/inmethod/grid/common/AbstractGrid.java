package com.inmethod.grid.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.tree.TreeModel;

import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.WicketAjaxReference;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WicketEventReference;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.CompressedResourceReference;
import org.apache.wicket.request.resource.JavascriptResourceReference;
import org.apache.wicket.util.string.JavascriptUtils;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.IGridSortState;
import com.inmethod.grid.SizeUnit;
import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.grid.toolbar.AbstractHeaderToolbar;
import com.inmethod.grid.toolbar.AbstractToolbar;
import com.inmethod.grid.treegrid.TreeGrid;

/**
 * Provides common functionality for {@link DataGrid} and {@link TreeGrid}.
 * 
 * @author Matej Knopp
 */
public abstract class AbstractGrid extends Panel implements IHeaderContributor {

	/**
	 * Creates new {@link AbstractGrid} instance
	 * 
	 * @param id
	 * @param model
	 * @param columns
	 */
	public AbstractGrid(String id, IModel model, List<IGridColumn> columns) {
		super(id, model);

		setVersioned(false);

		columnsSanityCheck(columns);

		setOutputMarkupId(true);

		this.columns = columns;

		add(new Header("header"));

		Form form = new Form("form");
		add(form);

		WebMarkupContainer bodyContainer = new WebMarkupContainer("bodyContainer") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				if (getContentHeight() > 0 && getContentHeightSizeUnit() != null) {
					tag.put("style", "height:" + getContentHeight() + getContentHeightSizeUnit().getValue());
				}
			}
		};
		form.add(bodyContainer);
		bodyContainer.setOutputMarkupId(true);

		bodyContainer.add(new Label("firstRow", new EmptyRowModel()).setEscapeModelStrings(false));

		add(topToolbarContainer = new RepeatingView("topToolbarContainer"));
		add(bottomToolbarContainer = new RepeatingView("bottomToolbarContainer"));
		add(headerToolbarContainer = new RepeatingView("headerToolbarContainer"));

		// renders the initialization javascript right after the grid itself
		add(new AbstractBehavior() {
			private static final long serialVersionUID = 1L;

			@Override
			public void onRendered(Component component) {
				renderInitializationJavascript(getResponse());
			}
		});

		columnState = new ColumnsState(columns);

		add(submitColumnStateBehavior);
	}

	public Form getForm() {
		return (Form) get("form");
	};

	/**
	 * Checks whether the column is a valid grid column
	 * 
	 * @param column
	 */
	protected void columnSanityCheck(IGridColumn column) {
		String id = column.getId();
		if (Strings.isEmpty(id)) {
			throw new IllegalStateException("Column id must be a non-empty string.");
		}
		for (int i = 0; i < id.length(); ++i) {
			char c = id.charAt(i);
			if (!Character.isLetterOrDigit(c) && c != '.' && c != '-' && c != '_') {
				throw new IllegalStateException("Column id contains invalid character(s).");
			}
		}
		if (column.isResizable() && column.getSizeUnit() != SizeUnit.PX) {
			throw new IllegalStateException("Resizable columns size must be in the PX unit.");
		}
	}

	/**
	 * Checks whether the columns have proper values.
	 * 
	 * @param columns
	 */
	private void columnsSanityCheck(List<IGridColumn> columns) {
		for (int i = 0; i < columns.size(); ++i) {
			IGridColumn column = columns.get(i);
			columnSanityCheck(column);
		}
		for (int i = 0; i < columns.size(); ++i) {
			IGridColumn column = columns.get(i);

			for (int j = 0; j < columns.size(); ++j) {
				if (i != j) {
					IGridColumn otherColumn = columns.get(j);
					if (column.getId().equals(otherColumn.getId())) {
						throw new IllegalStateException("Each column must have unique id");
					}
				}
			}
		}
	}

	/**
	 * Invoked when client change the column state (e.g. resize or reorder a
	 * column).
	 * 
	 * @see #getColumnState()
	 */
	public void onColumnStateChanged() {

	}

	SubmitColumnStateBehavior submitColumnStateBehavior = new SubmitColumnStateBehavior();

	/**
	 * Ajax behavior that submits column state to server
	 * 
	 * @author Matej Knopp
	 */
	private class SubmitColumnStateBehavior extends AbstractDefaultAjaxBehavior {

		private static final long serialVersionUID = 1L;

		@Override
		protected void respond(AjaxRequestTarget target) {
			// get the columnState request parameter (set by javascript)
			String state = getRequest().getRequestParameters().getParameterValue("columnState").toString();

			// apply it to current state
			columnState.updateColumnsState(state);
			
			onColumnStateChanged();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public CharSequence getCallbackScript() {
			// this assumes presence of columnState variable in the surrounding
			// javacript context
			return generateCallbackScript("wicketAjaxGet('" + getCallbackUrl() + "&columnState=' + columnState");
		}

	};

	/**
	 * Manages order, visibility and sizes of columns.
	 */
	private ColumnsState columnState;

	/**
	 * Returns the column state.
	 * 
	 * @see ColumnsState
	 * @return state of columns
	 */
	public ColumnsState getColumnState() {
		return columnState;
	}

	/**
	 * Sets a new column state. The state must not be null and must match
	 * current set of columns, i.e. for every column in grid there must be entry
	 * in the given state.
	 * 
	 * @see ColumnsState
	 * @param columnState
	 *            new column state
	 */
	public void setColumnState(ColumnsState columnState) {
		if (columnState == null) {
			throw new IllegalArgumentException("ColumnStateManager may not be null.");
		}
		if (!columnState.matches(columns)) {
			throw new IllegalArgumentException("ColumnStateManager doesn't match current columns.");
		}
		this.columnState = columnState;
	}
	
	/* (non-Javadoc)
	 * @see org.apache.wicket.Component#onInitialize()
	 */
	@Override
	protected void onInitialize() {
		super.onInitialize();

		for (IGridColumn column : columns) {
			column.setGrid(this);
		}
	}

	@Override
	protected void onBeforeRender() {
		setFlag(FLAG_RENDERING, true);

		super.onBeforeRender();
	}

	@Override
	protected void onAfterRender() {
		super.onAfterRender();

		setFlag(FLAG_RENDERING, false);
	}

	boolean isRendering() {
		return getFlag(FLAG_RENDERING);
	}

	private RepeatingView topToolbarContainer;
	private RepeatingView bottomToolbarContainer;
	private RepeatingView headerToolbarContainer;

	/**
	 * Adds toolbar to specified container
	 * 
	 * @param toolbar
	 * @param container
	 */
	private void addToolbar(AbstractToolbar toolbar, RepeatingView container) {
		if (toolbar == null) {
			throw new IllegalArgumentException("argument 'toolbar' cannot be null");
		}

		// create a container item for the toolbar (required by repeating view)
		WebMarkupContainer item = new WebMarkupContainer(container.newChildId());
		item.setRenderBodyOnly(true);
		item.add(toolbar);

		container.add(item);
	}

	/**
	 * Adds a toolbar to the top section (above the grid header).
	 * 
	 * @see AbstractToolbar
	 * @param toolbar
	 *            toolbar instance
	 */
	public void addTopToolbar(AbstractToolbar toolbar) {
		addToolbar(toolbar, topToolbarContainer);
	}

	/**
	 * Adds a toolbar to the bottom section (below the actual data).
	 * 
	 * @see AbstractToolbar
	 * @param toolbar
	 *            toolbar instance
	 */
	public void addBottomToolbar(AbstractToolbar toolbar) {
		addToolbar(toolbar, bottomToolbarContainer);
	}

	/**
	 * Ads a toolbar to the header section (below the grid header, above the
	 * actual data).
	 * 
	 * @see AbstractToolbar
	 * @param toolbar
	 *            toolbar instance
	 */
	public void addHeaderToolbar(AbstractHeaderToolbar toolbar) {
		addToolbar(toolbar, headerToolbarContainer);
	}

	/**
	 * INTERNAL
	 * <p>
	 * Id of toolbar item (inside toolbar repeaters).
	 */
	static public final String INTERNAL_TOOLBAR_ITEM_ID = "item";

	/**
	 * Model for the markup of empty row (first data row that has zero height).
	 * The empty row is needed for maintaining layout of the other rows.
	 * 
	 * @author Matej Knopp
	 */
	private class EmptyRowModel extends Model {
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Serializable getObject() {
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < getActiveColumns().size(); ++i) {
				result.append("<td></td>");
			}
			return result.toString();
		}
	};

	/**
	 * Component that represents the grid header.
	 * 
	 * @see ColumnsHeader
	 * @author Matej Knopp
	 */
	private class Header extends ColumnsHeader {

		private static final long serialVersionUID = 1L;

		private Header(String id) {
			super(id);
		}

		@Override
		Collection<IGridColumn> getActiveColumns() {
			return AbstractGrid.this.getActiveColumns();
		}

		@Override
		int getColumnWidth(IGridColumn column) {
			int width = getColumnState().getColumnWidth(column.getId());
			if (width != -1 && column.getSizeUnit() == SizeUnit.PX)
				return width;
			else
				return column.getInitialSize();
		}

		@Override
		protected void sortStateChanged(AjaxRequestTarget target) {
			AbstractGrid.this.onSortStateChanged(target);
		}

	};

	/**
	 * Invoked when sort state of this grid has changed (e.g. user clicked a
	 * sortable column header). By default refreshes the grid.
	 * 
	 * @param target
	 */
	protected void onSortStateChanged(AjaxRequestTarget target) {
		target.addComponent(this);
	}

	/**
	 * Renders the javascript required to initialize the client state for this
	 * grid instance. Called after every grid render.
	 * 
	 * @param response
	 */
	private void renderInitializationJavascript(Response response) {
		JavascriptUtils.writeOpenTag(response);
		response.write("(function() {\n");

		// initialize the columns
		response.write("var columns = [\n");
		Collection<IGridColumn> columns = getActiveColumns();
		int i = 0;
		for (IGridColumn column : columns) {
			++i;
			response.write("  {");
			response.write(" minSize: " + column.getMinSize());
			response.write(", maxSize: " + column.getMaxSize());
			response.write(", id: \"" + column.getId() + "\"");
			response.write(", resizable: " + column.isResizable());
			response.write(", reorderable: " + column.isReorderable());
			response.write("  }");
			if (i != columns.size()) {
				response.write(",");
			}
			response.write("\n");
		}
		;
		response.write("];\n");

		// method that calls the proper listener when column state is changed
		response.write("var submitStateCallback = function(columnState) { ");
		response.write(submitColumnStateBehavior.getCallbackScript());
		response.write(" }\n");

		// initialization
		response.write("InMethod.XTableManager.instance.register(\"" + getMarkupId()
				+ "\", columns, submitStateCallback);\n");
		response.write("})();\n");
		JavascriptUtils.writeCloseTag(response);
	};

	/**
	 * Returns collection of currently visible columns.
	 * 
	 * @return collection of currently visible columns
	 */
	public Collection<IGridColumn> getActiveColumns() {
		return getColumnState().getVisibleColumns(columns);
	}

	/**
	 * Returns the list of all columns in this grid.
	 * 
	 * @return list of columns
	 */
	public List<IGridColumn> getAllColumns() {
		return Collections.unmodifiableList(columns);
	}

	// contains all columns
	private final List<IGridColumn> columns;

	private GridSortState sortState = null;

	/**
	 * Returns the sort state of this grid.
	 * 
	 * @see IGridSortState
	 * @see GridSortState
	 * 
	 * @return sort state
	 */
	public GridSortState getSortState() {
		if (sortState == null) {
			sortState = new GridSortState();
		}
		return sortState;
	};

	/**
	 * Constant for the Vista theme (default).
	 */
	public static final String THEME_VISTA = "imxt-vista";

	private String theme = THEME_VISTA;

	/**
	 * Sets the grid theme. Grid theme is used as CSS style class for the grid.
	 * The theme itself consist of a proper style definition in stylesheet. For
	 * more information on custom theme creation see the custom theme example.
	 * 
	 * @param theme
	 *            theme identifier
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * Returns the theme identifier
	 * 
	 * @return theme identifier
	 * @see #setTheme(String)
	 */
	public String getTheme() {
		return theme;
	}

	// set during rendering of whole componen
	private static int FLAG_RENDERING = FLAG_RESERVED8;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);

		CharSequence klass = tag.getString("class");
		if (klass == null) {
			klass = "";
		}
		if (klass.length() > 0) {
			klass = klass + " ";
		}

		klass = klass + "imxt-grid " + getTheme();

		klass = klass + " imxt-selectable";

		tag.put("class", klass);

		if (tag.getName().equals("table")) {
			tag.setName("div");
		}
	}

	private static final JavascriptResourceReference JS_YAHOO = new JavascriptResourceReference(AbstractGrid.class,
			"res/yahoo.js");
	private static final JavascriptResourceReference JS_EVENT = new JavascriptResourceReference(AbstractGrid.class,
			"res/event.js");
	private static final JavascriptResourceReference JS_DOM = new JavascriptResourceReference(AbstractGrid.class,
			"res/dom.js");
	private static final JavascriptResourceReference JS_SCRIPT = new JavascriptResourceReference(AbstractGrid.class,
			"res/script.js");
	private static final CompressedResourceReference CSS = new CompressedResourceReference(AbstractGrid.class,
			"res/style.css");

	/**
	 * {@inheritDoc}
	 */
	public void renderHead(IHeaderResponse response) {
		response.renderJavascriptReference(WicketEventReference.INSTANCE);
		response.renderJavascriptReference(WicketAjaxReference.INSTANCE);
		response.renderJavascriptReference(JS_YAHOO);
		response.renderJavascriptReference(JS_EVENT);
		response.renderJavascriptReference(JS_DOM);
		response.renderJavascriptReference(JS_SCRIPT);
		response.renderCSSReference(CSS);
	}

	/**
	 * Alters the selection state of item specified by the item model.
	 * 
	 * @param itemModel
	 *            item model
	 * @param selected
	 *            <code>true</code> if the item should be selected,
	 *            <code>false</code> otherwise.
	 */
	public abstract void selectItem(IModel itemModel, boolean selected);

	/**
	 * Marks all currently displayed items as selected. For {@link DataGrid}
	 * this selects all items on current page, for {@link TreeGrid} this selects
	 * all currently visible nodes.
	 */
	public abstract void selectAllVisibleItems();

	/**
	 * Deselects all items. This method marks all items (not just visible items)
	 * as no selected.
	 */
	public abstract void resetSelectedItems();

	/**
	 * Queries whether the item specified by itemModel is currently selected.
	 * 
	 * @param itemModel
	 *            item model
	 * @return <code>true</code> if the item is selected, <code>false</code>
	 *         otherwise
	 */
	public abstract boolean isItemSelected(IModel itemModel);

	/**
	 * Returns the collection of models of all currently selected items.
	 * 
	 * @return collection of models of currently selected items
	 */
	public abstract Collection<IModel> getSelectedItems();

	/**
	 * Sets whether user will be able to select more than one item.
	 * 
	 * @param value
	 *            <code>true</code> if the user will be able to select more
	 *            than one item at a time, <code>false</code> otherwise
	 *            (single selection mode).
	 */
	public abstract void setAllowSelectMultiple(boolean value);

	/**
	 * Returns whether user will be able to select more than one item at a time.
	 * 
	 * @return <code>true</code> if multiple items can be selected at a time,
	 *         <code>false</code> otherwise.
	 */
	public abstract boolean isAllowSelectMultiple();

	/**
	 * During an Ajax request this method updates the changed grid rows.
	 * <p>
	 * The definition of "changed" varies in {@link DataGrid} and
	 * {@link TreeGrid}.
	 * <ul>
	 * <li>In both grids the items for which the selection state changed are
	 * considered changed.
	 * <li>In {@link TreeGrid} the changes to {@link TreeModel} itself are
	 * being tracked (assuming the proper listeners are fired) and all rows that
	 * need to be updated are also considered changed.
	 * <li>In {@link DataGrid} the items that need to be updated can be marked
	 * by {@link DataGrid#markItemDirty(IModel)} or
	 * {@link DataGrid#markAllItemsDirty()}. The grid itself doesn't track
	 * changes to specific items (apart from the selection state).
	 * </ul>
	 */
	public abstract void update();

	/**
	 * Invoked when an item selection state has been changed.
	 * 
	 * @param item
	 *            item model
	 * @param newValue
	 *            <code>true</code> if the item became selected,
	 *            <code>false</code> otherwise.
	 */
	protected void onItemSelectionChanged(IModel item, boolean newValue) {

	}

	private String lastClickedColumn = null;

	public IGridColumn getLastClickedColumn() {
		for (IGridColumn column : columns) {
			if (column.getId().equals(lastClickedColumn)) {
				return column;
			}
		}
		return null;
	}

	public void cleanLastClickedColumn() {
		lastClickedColumn = null;
	};

	protected boolean disableRowClickNotifications() {
		return false;
	}

	/**
	 * Called after a grid row has been populated. This method allows adding
	 * behaviors to grid rows.
	 * 
	 * @param rowComponent
	 */
	protected void onRowPopulated(final WebMarkupContainer rowComponent) {

		if (disableRowClickNotifications())
			return;

		rowComponent.add(new AjaxFormSubmitBehavior(getForm(), "onclick") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target) {

			}

			@Override
			protected void onError(AjaxRequestTarget target) {

			}

			@Override
			protected void onEvent(AjaxRequestTarget target) {

				// preserve the entered values in form components
				Form<?> form = getForm();
				form.visitFormComponentsPostOrder(new IVisitor<FormComponent<?>, Void>() {

					public void component(FormComponent<?> formComponent,
							IVisit<Void> visit) {
						if (formComponent.isVisibleInHierarchy()) {
							formComponent.inputChanged();
						}
					}
				});

				String column = getRequest().getRequestParameters().getParameterValue("column").toString();

				lastClickedColumn = column;

				IModel model = rowComponent.getDefaultModel();

				IGridColumn lastClickedColumn = getLastClickedColumn();
				if (lastClickedColumn != null) {					
					if (onCellClicked(target, model, lastClickedColumn) == true) {
						return;
					}
					if (lastClickedColumn.cellClicked(model) == true) {
						return;
					}					
				}

				onRowClicked(target, model);
			}

			@Override
			public CharSequence getCallbackUrl() {
				return super.getCallbackUrl() + "&column='+col+'";
			}

			@Override
			protected IAjaxCallDecorator getAjaxCallDecorator() {
				return new AjaxCallDecorator() {

					private static final long serialVersionUID = 1L;

					@Override
					public CharSequence decorateScript(Component c, CharSequence script) {
						return super.decorateScript(c, "if (InMethod.XTable.canSelectRow(event)) { "
								+ "var col=(this.imxtClickedColumn || ''); this.imxtClickedColumn='';" + script
								+ " }");
					}
				};
			}
		});

	}
	
	protected boolean onCellClicked(AjaxRequestTarget target, IModel rowModel, IGridColumn column) 	{
		return false;
	}

	protected void onRowClicked(AjaxRequestTarget target, IModel rowModel) {
		if (isClickRowToSelect()) {
			boolean selected = isItemSelected(rowModel);

			if (selected == false || isClickRowToDeselect()) {
				selectItem(rowModel, !selected);
				update();
			}
		}
	}

	private boolean clickRowToSelect = false;

	/**
	 * Sets whether a click on grid row should select/deselect the row.
	 * 
	 * @see #setClickRowToDeselect(boolean)
	 * @param clickRowToSelect
	 *            <code>true</code> if the row selection state should be
	 *            changed upon a mouse click, <code>false</code> otherwise.
	 * @return <code>this</code> (useful for method chaining)
	 */
	public AbstractGrid setClickRowToSelect(boolean clickRowToSelect) {
		this.clickRowToSelect = clickRowToSelect;
		return this;
	}

	/**
	 * Returns whether a click on grid row should select/deselect the row.
	 * 
	 * @return <code>true</code> if the row click should alter the row
	 *         selection state, <code>false</code> otherwise.
	 */
	public boolean isClickRowToSelect() {
		return clickRowToSelect;
	}

	private boolean clickRowToDeselect = true;

	/**
	 * Sets whether a click on selected grid row should deselect it. This only
	 * applies when {@link #setClickRowToSelect(boolean)} is set to
	 * <code>true</code>.
	 * 
	 * @param clickRowToDeselect
	 *            whether clicking a selected row should deselect it
	 */
	public void setClickRowToDeselect(boolean clickRowToDeselect) {
		this.clickRowToDeselect = clickRowToDeselect;
	}

	/**
	 * Returns whether clicking a selected row deselects it.
	 * 
	 * @return <code>true</code> if clicking a selected row deselects it,
	 *         <code>false</code> otherwise.
	 */
	public boolean isClickRowToDeselect() {
		return clickRowToDeselect;
	}

	private int contentHeight = 0;

	private SizeUnit contentHeightSizeUnit;

	/**
	 * Sets the height of grid content. Content is the part of grid displaying
	 * the actual data (rows), i.e. it doesn't cover the header part and
	 * toolbars. When the actual content height is bigger than specified height,
	 * a vertical scrollbar is displayed.
	 * 
	 * @param contentHeight
	 *            desired height of the content or <code>null</code> is the
	 *            height should be determined by the actual height (no scrollbar
	 *            displayed, defalt value)
	 * @param contentSizeUnit
	 *            size unit for the <code>contentHeight</code>
	 */
	public void setContentHeight(Integer contentHeight, SizeUnit contentSizeUnit) {
		if (contentHeight != null) {
			this.contentHeight = contentHeight;
		} else {
			this.contentHeight = 0;
		}
		this.contentHeightSizeUnit = contentSizeUnit;
	}

	/**
	 * Returns the content height.
	 * 
	 * @return content height or 0 if the content height should be determined by
	 *         the actual content.
	 * @see #setContentHeight(Integer, SizeUnit)
	 */
	public int getContentHeight() {
		return contentHeight;
	}

	/**
	 * Returns the size unit for content height.
	 * 
	 * @return size unit
	 */
	public SizeUnit getContentHeightSizeUnit() {
		return contentHeightSizeUnit;
	}

	/**
	 * Returns the row in DataTable that contains the child component
	 * 
	 * @param child
	 * @return
	 */
	public abstract WebMarkupContainer findParentRow(Component child);

	/**
	 * Returns the row component for specified item.
	 * 
	 * @param rowModel
	 * @return
	 */
	protected abstract WebMarkupContainer findRowComponent(IModel rowModel);

	/**
	 * Marks the item from the given model as dirty. Dirty items are updated
	 * during Ajax requests when {@link AbstractGrid#update()} method is called.
	 * 
	 * @param itemModel
	 *            model used to access the item
	 */
	public abstract void markItemDirty(IModel model);

	private boolean selectToEdit = false;

	/**
	 * Determines whether selected items should also be editable. This should be
	 * set to <code>false</code> when the grid is both selectable and editable
	 * (independently).
	 * 
	 * @param selectToEdit
	 *            whether selected rows should be editable
	 */
	public void setSelectToEdit(boolean selectToEdit) {
		this.selectToEdit = selectToEdit;
	}

	/**
	 * Returns whether selected rows are also editable.
	 * 
	 * @return
	 */
	public boolean isSelectToEdit() {
		return selectToEdit;
	}

	private final static MetaDataKey<Boolean> EDITING = new MetaDataKey<Boolean>() {
		private static final long serialVersionUID = 1L;
	};

	/**
	 * Sets the edit mode of the row. If selectToEdit is true, this is same as
	 * calling {@link #selectItem(IModel, boolean)}.
	 * 
	 * @see #setSelectToEdit(boolean)
	 * 
	 * @param rowModel
	 *            row model
	 * @param edit
	 *            <code>true</code> if the row should be in editable mode,
	 *            <code>false</code> otherwise.
	 */
	public void setItemEdit(IModel rowModel, boolean edit) {
		if (isSelectToEdit()) {
			selectItem(rowModel, edit);
		} else {
			WebMarkupContainer rowComponent = findRowComponent(rowModel);
			if (rowComponent != null) {
				boolean editing = Boolean.TRUE.equals(rowComponent.getMetaData(EDITING));
				if (editing != edit) {
					rowComponent.setMetaData(EDITING, edit);
					markItemDirty(rowModel);
				}
			}
		}
	}

	/**
	 * Returns whether the row is in editable mode.
	 * 
	 * @param rowModel
	 * @return <code>true</code> if the row is in editable mode,
	 *         <code>false</code> otherwise.
	 */
	public boolean isItemEdited(IModel rowModel) {
		if (isSelectToEdit()) {
			return isItemSelected(rowModel);
		} else {
			WebMarkupContainer rowComponent = findRowComponent(rowModel);
			if (rowComponent != null) {
				return Boolean.TRUE.equals(rowComponent.getMetaData(EDITING));
			} else {
				return false;
			}
		}
	}
}
