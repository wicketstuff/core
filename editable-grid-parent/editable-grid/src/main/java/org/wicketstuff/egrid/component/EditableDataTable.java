package org.wicketstuff.egrid.component;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ColGroup;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IStyledColumn;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.navigation.paging.IPageableItems;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.IItemReuseStrategy;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.egrid.provider.IEditableDataProvider;
import org.wicketstuff.egrid.toolbar.AbstractEditableToolbar;

import java.io.Serial;
import java.util.List;

/**
 * Modified Copy of Wicket Extensions' DataTable that supports editing of Rows.
 * The data table must be placed in a Form in order for editing to work.
 *
 * @param <T> the model object type
 * @param <S> the type of the sorting parameter
 * @author Nadeem
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable
 */
public class EditableDataTable<T, S> extends Panel implements IPageableItems {

    public static final MetaDataKey<Boolean> EDITING = new MetaDataKey<>() {
    };

    public static final MetaDataKey<String> TABLE_MARKUP_ID = new MetaDataKey<>() {
    };

    @Serial
    private static final long serialVersionUID = 1L;

    private final EditableDataGridView<T, S> dataGrid;

    private final WebMarkupContainer body;

    private final List<? extends IColumn<T, S>> columns;

    private final ToolbarsContainer topToolbars;

    private final ToolbarsContainer bottomToolbars;

    private final Caption caption;

    private final ColGroup colGroup;

    private long toolbarIdCounter;

    private int currentlyEditingRowsCount = 0;


    /**
     * Constructor
     *
     * @param id           component id
     * @param columns      list of IColumn objects
     * @param dataProvider imodel for data provider
     * @param rowsPerPage  number of rows per page
     */
    public EditableDataTable(final String id, final List<? extends IColumn<T, S>> columns, final IEditableDataProvider<T, S> dataProvider, final long rowsPerPage) {
        super(id);

        Args.notNull(columns, "columns");

        this.columns = columns;
        this.caption = new Caption("caption", getCaptionModel());
        add(caption);
        this.colGroup = new ColGroup("colGroup");
        add(colGroup);
        body = newBodyContainer("body");
        dataGrid = newDataGridView("rows", columns, dataProvider);
        dataGrid.setItemsPerPage(rowsPerPage);
        body.add(dataGrid);
        add(body);
        topToolbars = new ToolbarsContainer("topToolbars");
        bottomToolbars = new ToolbarsContainer("bottomToolbars");
        add(topToolbars);
        add(bottomToolbars);

        setOutputMarkupId(true);
    }

    /**
     * The table checks if it is placed inside a {@link org.apache.wicket.markup.html.form.Form}.
     */
    @Override
    protected void onInitialize() {
        super.onInitialize();
        if (findParent(Form.class) == null) {
            throw new IllegalStateException("form cannot be found in the hierarchy of the editable data table %s".formatted(toString(false)));
        }
    }

    /**
     * Factory method for the EditableDataGrid
     *
     * @param id           The component id
     * @param columns      list of IColumn objects
     * @param dataProvider imodel for data provider
     * @return the data grid view
     */
    protected EditableDataGridView<T, S> newDataGridView(final String id, final List<? extends IColumn<T, S>> columns, final IEditableDataProvider<T, S> dataProvider) {
        return new DefaultEditableDataGridView(id, columns, dataProvider);
    }

    /**
     * Returns the model for table's caption. The caption won't be rendered if the model has empty
     * value.
     *
     * @return the model for table's caption
     */
    protected IModel<String> getCaptionModel() {
        return null;
    }

    /**
     * @return the component used for styling the table column
     */
    public final ColGroup getColGroup() {
        return colGroup;
    }

    /**
     * Create the MarkupContainer for the {@code <tbody>} tag. Users may subclass it to provide their own
     * (modified) implementation.
     *
     * @param id
     * @return A new markup container
     */
    protected WebMarkupContainer newBodyContainer(final String id) {
        return new WebMarkupContainer(id);
    }

    /**
     * Set the 'class' attribute for the tbody tag.
     *
     * @param cssStyle
     */
    public final void setTableBodyCss(final String cssStyle) {
        body.add(new AttributeModifier("class", cssStyle));
    }

    /**
     * Adds a toolbar to the datatable that will be displayed after the data
     *
     * @param toolbar toolbar to be added
     * @see org.wicketstuff.egrid.toolbar.AbstractEditableToolbar
     */
    public void addBottomToolbar(final AbstractEditableToolbar toolbar) {
        addToolbar(toolbar, bottomToolbars);
    }

    /**
     * Adds a toolbar to the datatable that will be displayed before the data
     *
     * @param toolbar toolbar to be added
     * @see org.wicketstuff.egrid.toolbar.AbstractEditableToolbar
     */
    public void addTopToolbar(final AbstractEditableToolbar toolbar) {
        addToolbar(toolbar, topToolbars);
    }

    /**
     * @return the container with the toolbars at the top
     */
    public final ToolbarsContainer getTopToolbars() {
        return topToolbars;
    }

    /**
     * @return the container with the toolbars at the bottom
     */
    public final ToolbarsContainer getBottomToolbars() {
        return bottomToolbars;
    }

    /**
     * @return the container used for the table body
     */
    public final WebMarkupContainer getBody() {
        return body;
    }

    /**
     * @return the component used for the table caption
     */
    public final Caption getCaption() {
        return caption;
    }

    /**
     * @return the editable data provider
     */
    public final IEditableDataProvider<T, S> getDataProvider() {
        return dataGrid.getDataProvider();
    }

    /**
     * @return list of column objects this table displays
     */
    public final List<? extends IColumn<T, S>> getColumns() {
        return columns;
    }

    /**
     * @see org.apache.wicket.markup.html.navigation.paging.IPageable#getCurrentPage()
     */
    @Override
    public final long getCurrentPage() {
        return dataGrid.getCurrentPage();
    }

    /**
     * @see org.apache.wicket.markup.html.navigation.paging.IPageable#setCurrentPage(long)
     */
    @Override
    public final void setCurrentPage(final long page) {
        dataGrid.setCurrentPage(page);
        onPageChanged();
    }

    /**
     * @see org.apache.wicket.markup.html.navigation.paging.IPageable#getPageCount()
     */
    @Override
    public final long getPageCount() {
        return dataGrid.getPageCount();
    }

    /**
     * @return total number of rows in this table
     */
    public final long getRowCount() {
        return dataGrid.getRowCount();
    }

    /**
     * @return number of rows per page
     */
    @Override
    public final long getItemsPerPage() {
        return dataGrid.getItemsPerPage();
    }

    /**
     * Sets the number of items to be displayed per page
     *
     * @param items number of items to display per page
     */
    @Override
    public void setItemsPerPage(final long items) {
        dataGrid.setItemsPerPage(items);
    }

    /**
     * Sets the item reuse strategy. This strategy controls the creation of {@link org.apache.wicket.markup.repeater.Item}s.
     *
     * @param strategy item reuse strategy
     * @return this for chaining
     * @see org.apache.wicket.markup.repeater.RefreshingView#setItemReuseStrategy(IItemReuseStrategy)
     * @see org.apache.wicket.markup.repeater.IItemReuseStrategy
     */
    public final EditableDataTable<T, S> setItemReuseStrategy(final IItemReuseStrategy strategy) {
        dataGrid.setItemReuseStrategy(strategy);
        return this;
    }

    /**
     * @see org.apache.wicket.markup.html.navigation.paging.IPageableItems#getItemCount()
     */
    @Override
    public long getItemCount() {
        return dataGrid.getItemCount();
    }

    private void addToolbar(final AbstractEditableToolbar toolbar, final ToolbarsContainer container) {
        Args.notNull(toolbar, "toolbar");

        container.getRepeatingView().add(toolbar);
    }

    /**
     * Factory method for Item container that represents a cell in the underlying DataGridView
     *
     * @param id    component id for the new data item
     * @param index the index of the new data item
     * @param model the model for the new data item
     * @return DataItem created DataItem
     * @see org.apache.wicket.markup.repeater.Item
     */
    protected Item<IColumn<T, S>> newCellItem(final String id, final int index, final IModel<IColumn<T, S>> model) {
        return new Item<>(id, index, model);
    }

    /**
     * Factory method for Item container that represents a row in the underlying DataGridView.
     *
     * @param id    component id for the new data item
     * @param index the index of the new data item
     * @param model the model for the new data item.
     * @return DataItem created DataItem
     * @see org.apache.wicket.markup.repeater.Item
     */
    protected Item<T> newRowItem(final String id, final int index, final IModel<T> model) {
        return new RowItem<>(id, index, model, getMarkupId());
    }

    /**
     * @see org.apache.wicket.Component#onDetach()
     */
    @Override
    protected void onDetach() {
        super.onDetach();

        for (IColumn<T, S> column : columns) {
            column.detach();
        }
    }

    /**
     * Event listener for page-changed event
     */
    protected void onPageChanged() {
        // noop
    }

    /**
     * @return new id for a toolbar
     * @see org.wicketstuff.egrid.toolbar.AbstractEditableToolbar
     */
    public String newToolbarId() {
        toolbarIdCounter++;
        return String.valueOf(toolbarIdCounter).intern();
    }

    /**
     * @see org.apache.wicket.Component#onComponentTag(ComponentTag)
     */
    @Override
    protected void onComponentTag(final ComponentTag tag) {
        checkComponentTag(tag, "table");
        super.onComponentTag(tag);
    }

    /**
     * Used to react on editing events
     *
     * @see org.apache.wicket.Component#onEvent(IEvent)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onEvent(final IEvent<?> event) {

        if (!(event.getPayload() instanceof Item<?> rowItem)) {
            return;
        }


        if (!rowItem.getMetaData(TABLE_MARKUP_ID).equals(getMarkupId())) {
            return;
        }

        Boolean isEditing = rowItem.getMetaData(EDITING);
        if (isEditing != null) {
            currentlyEditingRowsCount += isEditing ? 1 : -1;
        }

        // RowItem must be of type T because it is from this table
        dataGrid.refresh((RowItem<T>) rowItem);

        event.stop();
    }

    /**
     * Checks if any row of the table is currently being edited
     *
     * @return true if at least one row is currently being edited
     */
    public boolean isCurrentlyAnyEdit() {
        return currentlyEditingRowsCount > 0;
    }

    /**
     * This class acts as a repeater that will contain the toolbar. It makes sure that the table row
     * group (e.g. thead) tags are only visible when they contain rows in accordance with the HTML
     * specification.
     *
     * @author igor.vaynberg
     */
    public static final class ToolbarsContainer extends WebMarkupContainer {
        @Serial
        private static final long serialVersionUID = 1L;

        private final RepeatingView toolbars = new RepeatingView("toolbars");

        /**
         * Constructor
         *
         * @param id
         */
        private ToolbarsContainer(final String id) {
            super(id);

            setOutputMarkupId(true);

            add(toolbars);
        }

        public RepeatingView getRepeatingView() {
            return toolbars;
        }

        @Override
        public void onConfigure() {
            super.onConfigure();

            toolbars.configure();

            Boolean visible = toolbars.visitChildren((object, visit) -> {
                object.configure();
                if (object.isVisible()) {
                    visit.stop(true);
                } else {
                    visit.dontGoDeeper();
                }
            });
            if (visible == null) {
                visible = false;
            }
            setVisible(visible);
        }
    }

    /**
     * A caption for the table. It renders itself only if
     * {@link #getCaptionModel()} has non-empty value.
     */
    public static class Caption extends Label {
        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * Constructor
         *
         * @param id    the component id
         * @param model the caption model
         */
        public Caption(final String id, final IModel<String> model) {
            super(id, model);
        }

        @Override
        protected void onConfigure() {
            setRenderBodyOnly(Strings.isEmpty(getDefaultModelObjectAsString()));

            super.onConfigure();
        }

        @Override
        protected IModel<String> initModel() {
            // don't try to find the model in the parent
            return null;
        }
    }

    public abstract static class CssAttributeBehavior extends Behavior {
        @Serial
        private static final long serialVersionUID = 1L;

        protected abstract String getCssClass();

        /**
         * @see org.apache.wicket.behavior.Behavior#onComponentTag(Component, ComponentTag)
         */
        @Override
        public void onComponentTag(final Component component, final ComponentTag tag) {
            String className = getCssClass();
            if (!Strings.isEmpty(className)) {
                tag.append("class", className, " ");
            }
        }
    }

    /**
     * Extension of Item that is ajax ready and has two MetaDataKeys set.
     * @param <RI> the model object's type
     * @see org.apache.wicket.markup.repeater.Item
     */
    public static class RowItem<RI> extends Item<RI> {
        @Serial
        private static final long serialVersionUID = 1L;

        public RowItem(final String id, final int index, final IModel<RI> model, final String tableMarkupId) {
            super(id, index, model);
            setOutputMarkupId(true);
            setMetaData(EDITING, false);
            setMetaData(TABLE_MARKUP_ID, tableMarkupId);
        }
    }

    private final class DefaultEditableDataGridView extends EditableDataGridView<T, S> {

        private DefaultEditableDataGridView(final String id, final List<? extends ICellPopulator<T>> populators, final IEditableDataProvider<T, S> dataProvider) {
            super(id, populators, dataProvider);
        }

        @SuppressWarnings({"rawtypes", "unchecked"})
        @Override
        protected Item<ICellPopulator<T>> newCellItem(final String id, final int index, final IModel model) {
            Item item = EditableDataTable.this.newCellItem(id, index, model);
            final IColumn<T, S> column = EditableDataTable.this.columns.get(index);
            if (column instanceof IStyledColumn) {
                item.add(new CssAttributeBehavior() {
                    @Serial
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected String getCssClass() {
                        return ((IStyledColumn<T, S>) column).getCssClass();
                    }
                });
            }
            return item;
        }

        @Override
        protected Item<T> newRowItem(final String id, final int index, final IModel<T> model) {
            return EditableDataTable.this.newRowItem(id, index, model);
        }
    }
}
