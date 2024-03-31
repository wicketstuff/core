package org.wicketstuff.egrid.toolbar;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigatorLabel;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.IModel;
import org.wicketstuff.egrid.component.EditableDataTable;

import java.io.Serial;

/**
 * Adjusted version of Wicket Extensions' HeadersToolbar
 * <p>
 * Toolbar that displays links used to navigate the pages of the datatable as well as a message
 * about which rows are being displayed and their total number in the data table.
 * </p>
 *
 * @author Nadeem Mohammad
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar
 */
public class NavigationToolbar extends AbstractEditableToolbar {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param table data table this toolbar will be attached to
     */
    public NavigationToolbar(final EditableDataTable<?, ?> table) {
        super(table);

        WebMarkupContainer span = new WebMarkupContainer("span");
        add(span);
        span.add(AttributeModifier.replace("colspan", (IModel<String>) () -> String.valueOf(table.getColumns().size()).intern()));

        span.add(newPagingNavigator("navigator", table));
        span.add(newNavigatorLabel("navigatorLabel", table));
    }

    /**
     * Factory method used to create the paging navigator that will be used by the datatable
     *
     * @param navigatorId component id the navigator should be created with
     * @param table       dataview used by datatable
     * @return paging navigator that will be used to navigate the data table
     */
    protected PagingNavigator newPagingNavigator(final String navigatorId,
                                                 final EditableDataTable<?, ?> table) {
        return new AjaxPagingNavigator(navigatorId, table) {
            @Override
            protected void onAjaxEvent(final AjaxRequestTarget target) {
                target.add(getTable());
            }
        };
    }

    /**
     * Factory method used to create the navigator label.
     *
     * @param navigatorId component id navigator label should be created with
     * @param table       dataview used by datatable
     * @return navigator label that will be used to navigate the data table
     */
    protected WebComponent newNavigatorLabel(final String navigatorId, final EditableDataTable<?, ?> table) {
        return new NavigatorLabel(navigatorId, table);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onConfigure() {
        super.onConfigure();
        setVisible(getTable().getPageCount() > 1);
    }

}
