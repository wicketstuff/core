package com.inmethod.grid.common;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.tree.TreeModel;

import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.resource.CoreLibrariesContributor;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.AppendingStringBuffer;
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
 * @param <M>
 *            grid model object type
 * @param <I>
 *            row/item model object type
 * 
 * @author Matej Knopp
 */
public abstract class AbstractGrid<M, I, S> extends Panel
{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new {@link AbstractGrid} instance
	 * 
	 * @param id
	 * @param model
	 * @param columns
	 */
	public AbstractGrid(String id, IModel<M> model, List<IGridColumn<M, I, S>> columns)
	{
		super(id, model);

		setVersioned(false);

		columnsSanityCheck(columns);

		setOutputMarkupId(true);

		this.columns = columns;

		add(new Header("header"));

		Form<Void> form = new Form<Void>("form");
		add(form);

		WebMarkupContainer bodyContainer = new WebMarkupContainer("bodyContainer")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);
				if (getContentHeight() > 0 && getContentHeightSizeUnit() != null)
				{
					tag.put("style", "height:" + getContentHeight() +
						getContentHeightSizeUnit().getValue());
				}
			}
		};
		form.add(bodyContainer);
		bodyContainer.setOutputMarkupId(true);

		bodyContainer.add(new Label("firstRow", new EmptyRowModel())
                 .setEscapeModelStrings(false));

		add(topToolbarContainer = new RepeatingView("topToolbarContainer"));
		add(bottomToolbarContainer = new RepeatingView("bottomToolbarContainer"));
		add(headerToolbarContainer = new RepeatingView("headerToolbarContainer"));

		add(new Behavior()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void renderHead(Component component, IHeaderResponse response)
			{
				super.renderHead(component, response);
				// since javascript can be rendered at the tail
				// of HTML document, do not initialize data grid
				// component until "DOM ready" event.
				response.render(OnDomReadyHeaderItem.forScript(getInitializationJavascript()));
			}
		});

		columnState = new ColumnsState(columns);

		add(submitColumnStateBehavior);
	}

	@SuppressWarnings("unchecked")
	public Form<Void> getForm()
	{
		return (Form<Void>)get("form");
	}

	/**
	 * Checks whether the column is a valid grid column
	 * 
	 * @param column
	 */
	protected void columnSanityCheck(IGridColumn<M, I, S> column)
	{
		String id = column.getId();
		if (Strings.isEmpty(id))
		{
			throw new IllegalStateException("Column id must be a non-empty string.");
		}
		for (int i = 0; i < id.length(); ++i)
		{
			char c = id.charAt(i);
			if (!Character.isLetterOrDigit(c) && c != '.' && c != '-' && c != '_')
			{
				throw new IllegalStateException("Column id contains invalid character(s).");
			}
		}
		if (column.isResizable() && column.getSizeUnit() != SizeUnit.PX)
		{
			throw new IllegalStateException("Resizable columns size must be in the PX unit.");
		}
	}

	/**
	 * Checks whether the columns have proper values.
	 * 
	 * @param columns
	 */
	private void columnsSanityCheck(List<IGridColumn<M, I, S>> columns)
	{
		for (int i = 0; i < columns.size(); ++i)
		{
			IGridColumn<M, I, S> column = columns.get(i);
			columnSanityCheck(column);
		}
		for (int i = 0; i < columns.size(); ++i)
		{
			IGridColumn<M, I, S> column = columns.get(i);

			for (int j = 0; j < columns.size(); ++j)
			{
				if (i != j)
				{
					IGridColumn<M, I, S> otherColumn = columns.get(j);
					if (column.getId().equals(otherColumn.getId()))
					{
						throw new IllegalStateException("Each column must have unique id");
					}
				}
			}
		}
	}

	/**
	 * Invoked when client change the column state (e.g. resize or reorder a column).
	 * 
	 * @see #getColumnState()
	 */
	public void onColumnStateChanged()
	{

	}

	SubmitColumnStateBehavior submitColumnStateBehavior = new SubmitColumnStateBehavior();

	/**
	 * Ajax behavior that submits column state to server
	 * 
	 * @author Matej Knopp
	 */
	private class SubmitColumnStateBehavior extends AbstractDefaultAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected void respond(AjaxRequestTarget target)
		{
			// get the columnState request parameter (set by javascript)
			String state = getRequest().getRequestParameters()
				.getParameterValue("columnState").toString(null);
			if(!Strings.isEmpty(state)){
				// apply it to current state
				columnState.updateColumnsState(state);

				onColumnStateChanged();
			}
		}
    
    @Override
		protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
		{
			super.updateAjaxAttributes(attributes);

			CharSequence columnStateParameter = "return {'columnState': columnState}";
			attributes.getDynamicExtraParameters().add(columnStateParameter);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public CharSequence getCallbackScript()
		{
			return getCallbackFunction(CallbackParameter.context("columnState"));
		}
	}

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
	public ColumnsState getColumnState()
	{
		return columnState;
	}

	/**
	 * Sets a new column state. The state must not be null and must match current set of columns,
	 * i.e. for every column in grid there must be entry in the given state.
	 * 
	 * @see ColumnsState
	 * @param columnState
	 *            new column state
	 */
	public void setColumnState(ColumnsState columnState)
	{
		if (columnState == null)
		{
			throw new IllegalArgumentException("ColumnStateManager may not be null.");
		}
		if (!columnState.matches(columns))
		{
			throw new IllegalArgumentException("ColumnStateManager doesn't match current columns.");
		}
		this.columnState = columnState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.Component#onInitialize()
	 */
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		for (IGridColumn<M, I, S> column : columns)
		{
			column.setGrid(this);
		}
	}

	@Override
	protected void onBeforeRender()
	{
		setFlag(FLAG_RENDERING, true);

		super.onBeforeRender();
	}

  //TODO: w/ the OnInitialize -> OnBeforeRender can FLAG_RENDERING be removed?
	@Override
	protected void onAfterRender()
	{
		super.onAfterRender();

		setFlag(FLAG_RENDERING, false);
	}

	boolean isRendering()
	{
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
	private void addToolbar(AbstractToolbar<M, I, S> toolbar, RepeatingView container)
	{
    Args.notNull(toolbar, "toolbar");

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
	public void addTopToolbar(AbstractToolbar<M, I, S> toolbar)
	{
		addToolbar(toolbar, topToolbarContainer);
	}

	/**
	 * Adds a toolbar to the bottom section (below the actual data).
	 * 
	 * @see AbstractToolbar
	 * @param toolbar
	 *            toolbar instance
	 */
	public void addBottomToolbar(AbstractToolbar<M, I, S> toolbar)
	{
		addToolbar(toolbar, bottomToolbarContainer);
	}

	/**
	 * Ads a toolbar to the header section (below the grid header, above the actual data).
	 * 
	 * @see AbstractToolbar
	 * @param toolbar
	 *            toolbar instance
	 */
	public void addHeaderToolbar(AbstractHeaderToolbar<M, I, S> toolbar)
	{
		addToolbar(toolbar, headerToolbarContainer);
	}

	/**
	 * INTERNAL
	 * <p>
	 * Id of toolbar item (inside toolbar repeaters).
	 */
	static public final String INTERNAL_TOOLBAR_ITEM_ID = "item";

	/**
	 * Model for the markup of empty row (first data row that has zero height). The empty row is
	 * needed for maintaining layout of the other rows.
	 * 
	 * @author Matej Knopp
	 */
	private class EmptyRowModel extends Model<String>
	{
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getObject()
		{
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < getActiveColumns().size(); ++i)
			{
				result.append("<td></td>");
			}
			return result.toString();
		}
	}

	/**
	 * Component that represents the grid header.
	 * 
	 * @see ColumnsHeader
	 * @author Matej Knopp
	 */
	private class Header extends ColumnsHeader<M, I, S>
	{
		private static final long serialVersionUID = 1L;

		private Header(String id)
		{
			super(id);
		}

		@Override
		Collection<IGridColumn<M, I, S>> getActiveColumns()
		{
			return AbstractGrid.this.getActiveColumns();
		}

		@Override
		int getColumnWidth(IGridColumn<M, I, S> column)
		{
			int width = getColumnState().getColumnWidth(column.getId());
			if (width != -1 && column.getSizeUnit() == SizeUnit.PX)
      {	return width; }
			else
      { return column.getInitialSize(); }
		}

		@Override
		protected void sortStateChanged(AjaxRequestTarget target)
		{
			onSortStateChanged(target);
		}

	}

	/**
	 * Invoked when sort state of this grid has changed (e.g. user clicked a sortable column
	 * header). By default refreshes the grid.
	 * 
	 * @param target
	 */
	protected void onSortStateChanged(AjaxRequestTarget target)
	{
		target.add(this);
	}

	/**
	 * Generates the javascript required to initialize the client state for this grid instance.
	 * Called after every grid render.
	 * 
	 * @return generated javascript code
	 */
	private String getInitializationJavascript()
	{
		AppendingStringBuffer sb = new AppendingStringBuffer(128);
		sb.append("(function() {\n");

		// initialize the columns
		sb.append("var columns = [\n");
		Collection<IGridColumn<M, I, S>> columns = getActiveColumns();
		int i = 0;
		for (IGridColumn<M, I, S> column : columns)
		{
			++i;
			sb.append("  {");
			sb.append(" minSize: " + column.getMinSize());
			sb.append(", maxSize: " + column.getMaxSize());
			sb.append(", id: \"" + column.getId() + "\"");
			sb.append(", resizable: " + column.isResizable());
			sb.append(", reorderable: " + column.isReorderable());
			sb.append("  }");
			if (i != columns.size())
			{
				sb.append(",");
			}
			sb.append("\n");
		}

		sb.append("];\n");

		// method that calls the proper listener when column state is changed
		sb.append("var submitStateCallback = ");
		sb.append(submitColumnStateBehavior.getCallbackScript());
		sb.append("\n");

		// initialization
		sb.append("InMethod.XTableManager.instance.register(\"" + getMarkupId() +
			"\", columns, submitStateCallback);\n");
		sb.append("})();\n");

		return sb.toString();
	}

	/**
	 * Returns collection of currently visible columns.
	 * 
	 * @return collection of currently visible columns
	 */
	public Collection<IGridColumn<M, I, S>> getActiveColumns()
	{
		return getColumnState().getVisibleColumns(columns);
	}

	/**
	 * Returns the list of all columns in this grid.
	 * 
	 * @return list of columns
	 */
	public List<IGridColumn<M, I, S>> getAllColumns()
	{
		return Collections.unmodifiableList(columns);
	}

	// contains all columns
	private final List<IGridColumn<M, I, S>> columns;

	private GridSortState<S> sortState = null;

	/**
	 * Returns the sort state of this grid.
	 * 
	 * @see IGridSortState
	 * @see GridSortState
	 * 
	 * @return sort state
	 */
	public GridSortState<S> getSortState()
	{
		if (sortState == null)
		{
			sortState = new GridSortState<S>(this);
		}
		return sortState;
	}

	/**
	 * Constant for the Vista theme (default).
	 */
	public static final String THEME_VISTA = "imxt-vista";

	private String theme = THEME_VISTA;

	/**
	 * Sets the grid theme. Grid theme is used as CSS style class for the grid. The theme itself
	 * consist of a proper style definition in stylesheet. For more information on custom theme
	 * creation see the custom theme example.
	 * 
	 * @param theme
	 *            theme identifier
	 */
	public void setTheme(String theme)
	{
		this.theme = theme;
	}

	/**
	 * Returns the theme identifier
	 * 
	 * @return theme identifier
	 * @see #setTheme(String)
	 */
	public String getTheme()
	{
		return theme;
	}

	// set during rendering of whole componen
	private static int FLAG_RENDERING = FLAG_RESERVED8;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);

		CharSequence klass = tag.getAttribute("class");
		if (klass == null)
		{
			klass = "";
		}
		if (klass.length() > 0)
		{
			klass = klass + " ";
		}

		klass = klass + "imxt-grid " + getTheme();

		klass = klass + " imxt-selectable";

		tag.put("class", klass);

		if (tag.getName().equals("table"))
		{
			tag.setName("div");
		}
	}

	public static final JavaScriptResourceReference JS_YAHOO =
                           new JavaScriptResourceReference(AbstractGrid.class,
                                                           "res/yahoo.js");
	public static final JavaScriptResourceReference JS_EVENT =
                           new JavaScriptResourceReference(AbstractGrid.class,
                                                           "res/event.js");
	public static final JavaScriptResourceReference JS_DOM =
                           new JavaScriptResourceReference(AbstractGrid.class,
                                                           "res/dom.js");
	
	public static final JavaScriptResourceReference JS_SCRIPT =
            new JavaScriptResourceReference(AbstractGrid.class,
                                            "res/script.js");
	
	public static final JavaScriptResourceReference JS_SCRIPT_JQ =
                           new JavaScriptResourceReference(AbstractGrid.class,
                                                           "res/script-jq.js");
	public static final PackageResourceReference CSS =
                              new PackageResourceReference(AbstractGrid.class,
                                                           "res/style.css");
	
	
	/**
	 * Flag to set/un-set the use of YUI as backing JS library. Its default is false and jquery is 
	 * used as backing JS library.
	 */
	private boolean useYui = false;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		CoreLibrariesContributor.contributeAjax(getApplication(), response);
		if(isUseYui()) {
			response.render(JavaScriptHeaderItem.forReference(JS_YAHOO));
			response.render(JavaScriptHeaderItem.forReference(JS_EVENT));
			response.render(JavaScriptHeaderItem.forReference(JS_DOM));
			response.render(JavaScriptHeaderItem.forReference(JS_SCRIPT));
		} else {
			// jquery is already part of Wicket. So there is nothing more to include.
			response.render(JavaScriptHeaderItem.forReference(JS_SCRIPT_JQ));
		}
		response.render(CssHeaderItem.forReference(CSS));
	}

	/**
	 * Alters the selection state of item specified by the item model.
	 * 
	 * @param itemModel
	 *            item model
	 * @param selected
	 *            <code>true</code> if the item should be selected, <code>false</code> otherwise.
	 */
	public abstract void selectItem(IModel<I> itemModel, boolean selected);

	/**
	 * Marks all currently displayed items as selected. For {@link DataGrid} this selects all items
	 * on current page, for {@link TreeGrid} this selects all currently visible nodes.
	 */
	public abstract void selectAllVisibleItems();

	/**
	 * Deselects all items. This method marks all items (not just visible items) as no selected.
	 */
	public abstract void resetSelectedItems();

	/**
	 * Queries whether the item specified by itemModel is currently selected.
	 * 
	 * @param itemModel
	 *            item model
	 * @return <code>true</code> if the item is selected, <code>false</code> otherwise
	 */
	public abstract boolean isItemSelected(IModel<I> itemModel);

	/**
	 * Returns the collection of models of all currently selected items.
	 * 
	 * @return collection of models of currently selected items
	 */
	public abstract Collection<IModel<I>> getSelectedItems();

	/**
	 * Sets whether user will be able to select more than one item.
	 * 
	 * @param value
	 *            <code>true</code> if the user will be able to select more than one item at a time,
	 *            <code>false</code> otherwise (single selection mode).
	 */
	public abstract void setAllowSelectMultiple(boolean value);

	/**
	 * Returns whether user will be able to select more than one item at a time.
	 * 
	 * @return <code>true</code> if multiple items can be selected at a time, <code>false</code>
	 *         otherwise.
	 */
	public abstract boolean isAllowSelectMultiple();

	/**
	 * During an Ajax request this method updates the changed grid rows.
	 * <p>
	 * The definition of "changed" varies in {@link DataGrid} and {@link TreeGrid}.
	 * <ul>
	 * <li>In both grids the items for which the selection state changed are considered changed.
	 * <li>In {@link TreeGrid} the changes to {@link TreeModel} itself are being tracked (assuming
	 * the proper listeners are fired) and all rows that need to be updated are also considered
	 * changed.
	 * <li>In {@link DataGrid} the items that need to be updated can be marked by
	 * {@link DataGrid#markItemDirty(IModel)} or {@link DataGrid#markAllItemsDirty()}. The grid
	 * itself doesn't track changes to specific items (apart from the selection state).
	 * </ul>
	 */
	public abstract void update();

	/**
	 * Invoked when an item selection state has been changed.
	 * 
	 * @param item
	 *            item model
	 * @param newValue
	 *            <code>true</code> if the item became selected, <code>false</code> otherwise.
	 */
	protected void onItemSelectionChanged(IModel<I> item, boolean newValue)
	{

	}

	private String lastClickedColumn = null;

	public IGridColumn<M, I, S> getLastClickedColumn()
	{
		for (IGridColumn<M, I, S> column : columns)
		{
			if (column.getId().equals(lastClickedColumn))
			{
				return column;
			}
		}
		return null;
	}

	public void cleanLastClickedColumn()
	{
		lastClickedColumn = null;
	}

	protected boolean disableRowClickNotifications()
	{
		return false;
	}

	/**
	 * Called after a grid row has been populated. This method allows adding behaviors to grid rows.
	 * 
	 * @param rowComponent
	 */
	protected void onRowPopulated(final WebMarkupContainer rowComponent)
	{
		if (disableRowClickNotifications()) { return; }

		rowComponent.add(new AjaxFormSubmitBehavior(getForm(), "click")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{

			}

			@Override
			protected void onError(AjaxRequestTarget target)
			{

			}

			@SuppressWarnings("unchecked")
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{	// preserve the entered values in form components
				Form<?> form = super.getForm();
				form.visitFormComponentsPostOrder(new IVisitor<FormComponent<?>, Void>()
                                              {
                                                public void component(FormComponent<?> formComponent,
                                                                      IVisit<Void> visit)
                                                {
                                                  if (formComponent.isVisibleInHierarchy())
                                                  {
                                                    formComponent.inputChanged();
                                                  }
                                                }
                                              });

				String column = getRequest().getRequestParameters()
                                    .getParameterValue("column").toString();

				lastClickedColumn = column;

				IModel<I> model = (IModel<I>)rowComponent.getDefaultModel();

				IGridColumn<M, I, S> lastClickedColumn = getLastClickedColumn();
				if (lastClickedColumn != null)
				{
					if (onCellClicked(target, model, lastClickedColumn))
					{
						return;
					}
					if (lastClickedColumn.cellClicked(model))
					{
						return;
					}
				}

				onRowClicked(target, model);
			}
      
      @Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
			{
				super.updateAjaxAttributes(attributes);

				CharSequence columnParameter = "return {'column': Wicket.$(attrs.c).imxtClickedColumn}";
				attributes.getDynamicExtraParameters().add(columnParameter);

				CharSequence precondition = "return InMethod.XTable.canSelectRow(attrs.event);";
				AjaxCallListener ajaxCallListener = new AjaxCallListener();
				ajaxCallListener.onPrecondition(precondition);
				attributes.setAllowDefault(true);
				attributes.getAjaxCallListeners().add(ajaxCallListener);
			}
      
      @Override
			public CharSequence getCallbackScript()
			{
				return getCallbackFunction(CallbackParameter.context("col"));
			}
		});
	}

	protected boolean onCellClicked(AjaxRequestTarget target, IModel<I> rowModel,
		                              IGridColumn<M, I, S> column)
	{
		return false;
	}

	protected void onRowClicked(AjaxRequestTarget target, IModel<I> rowModel)
	{
		if (isClickRowToSelect())
		{
			boolean selected = isItemSelected(rowModel);

			if (selected == false || isClickRowToDeselect())
			{
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
	 *            <code>true</code> if the row selection state should be changed upon a mouse click,
	 *            <code>false</code> otherwise.
	 * @return <code>this</code> (useful for method chaining)
	 */
	public AbstractGrid<M, I, S> setClickRowToSelect(boolean clickRowToSelect)
	{
		this.clickRowToSelect = clickRowToSelect;
		return this;
	}

	/**
	 * Returns whether a click on grid row should select/deselect the row.
	 * 
	 * @return <code>true</code> if the row click should alter the row selection state,
	 *         <code>false</code> otherwise.
	 */
	public boolean isClickRowToSelect()
	{
		return clickRowToSelect;
	}

	private boolean clickRowToDeselect = true;

	/**
	 * Sets whether a click on selected grid row should deselect it. This only applies when
	 * {@link #setClickRowToSelect(boolean)} is set to <code>true</code>.
	 * 
	 * @param clickRowToDeselect
	 *            whether clicking a selected row should deselect it
	 */
	public void setClickRowToDeselect(boolean clickRowToDeselect)
	{
		this.clickRowToDeselect = clickRowToDeselect;
	}

	/**
	 * Returns whether clicking a selected row deselects it.
	 * 
	 * @return <code>true</code> if clicking a selected row deselects it, <code>false</code>
	 *         otherwise.
	 */
	public boolean isClickRowToDeselect()
	{
		return clickRowToDeselect;
	}

	private int contentHeight = 0;

	private SizeUnit contentHeightSizeUnit;

	/**
	 * Sets the height of grid content. Content is the part of grid displaying the actual data
	 * (rows), i.e. it doesn't cover the header part and toolbars. When the actual content height is
	 * bigger than specified height, a vertical scrollbar is displayed.
	 * 
	 * @param contentHeight
	 *            desired height of the content or <code>null</code> is the height should be
	 *            determined by the actual height (no scrollbar displayed, defalt value)
	 * @param contentSizeUnit
	 *            size unit for the <code>contentHeight</code>
	 */
	public void setContentHeight(Integer contentHeight, SizeUnit contentSizeUnit)
	{
		if (contentHeight != null)
		{
			this.contentHeight = contentHeight;
		}
		else
		{
			this.contentHeight = 0;
		}
		contentHeightSizeUnit = contentSizeUnit;
	}

	/**
	 * Returns the content height.
	 * 
	 * @return content height or 0 if the content height should be determined by the actual content.
	 * @see #setContentHeight(Integer, SizeUnit)
	 */
	public int getContentHeight()
	{
		return contentHeight;
	}

	/**
	 * Returns the size unit for content height.
	 * 
	 * @return size unit
	 */
	public SizeUnit getContentHeightSizeUnit()
	{
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
	protected abstract WebMarkupContainer findRowComponent(IModel<I> rowModel);

	/**
	 * Marks the item from the given model as dirty. Dirty items are updated during Ajax requests
	 * when {@link AbstractGrid#update()} method is called.
	 * 
	 * @param model
	 *            model used to access the item
	 */
	public abstract void markItemDirty(IModel<I> model);

	private boolean selectToEdit = false;

	/**
	 * Determines whether selected items should also be editable. This should be set to
	 * <code>false</code> when the grid is both selectable and editable (independently).
	 * 
	 * @param selectToEdit
	 *            whether selected rows should be editable
	 */
	public void setSelectToEdit(boolean selectToEdit)
	{
		this.selectToEdit = selectToEdit;
	}

	/**
	 * Returns whether selected rows are also editable.
	 * 
	 * @return
	 */
	public boolean isSelectToEdit()
	{
		return selectToEdit;
	}

	private final static MetaDataKey<Boolean> EDITING = new MetaDataKey<Boolean>()
	{
		private static final long serialVersionUID = 1L;
	};

	/**
	 * Sets the edit mode of the row. If selectToEdit is true, this is same as calling
	 * {@link #selectItem(IModel, boolean)}.
	 * 
	 * @see #setSelectToEdit(boolean)
	 * 
	 * @param rowModel
	 *            row model
	 * @param edit
	 *            <code>true</code> if the row should be in editable mode, <code>false</code>
	 *            otherwise.
	 */
	public void setItemEdit(IModel<I> rowModel, boolean edit)
	{
		if (isSelectToEdit())
		{
			selectItem(rowModel, edit);
		}
		else
		{
			WebMarkupContainer rowComponent = findRowComponent(rowModel);
			if (rowComponent != null)
			{
				boolean editing = Boolean.TRUE.equals(rowComponent.getMetaData(EDITING));
				if (editing != edit)
				{
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
	 * @return <code>true</code> if the row is in editable mode, <code>false</code> otherwise.
	 */
	public boolean isItemEdited(IModel<I> rowModel)
	{
		if (isSelectToEdit())
		{
			return isItemSelected(rowModel);
		}
		else
		{
			WebMarkupContainer rowComponent = findRowComponent(rowModel);
			if (rowComponent != null)
			{
				return Boolean.TRUE.equals(rowComponent.getMetaData(EDITING));
			}
			else
			{
				return false;
			}
		}
	}

	public boolean isUseYui() {
		return useYui;
	}

	public void setUseYui(boolean useYui) {
		this.useYui = useYui;
	}
}
