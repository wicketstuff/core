package org.wicketstuff.datatables;

import de.agilecoders.wicket.jquery.AbstractConfig;
import de.agilecoders.wicket.jquery.IKey;
import de.agilecoders.wicket.jquery.Key;
import de.agilecoders.wicket.jquery.util.Json;
import org.apache.wicket.util.lang.Args;

/**
 *
 */
public class Options extends AbstractConfig {

    /**
     * https://datatables.net/examples/basic_init/alt_pagination.html
     */
    public enum PagingType {
        /**
         *  'Previous' and 'Next' buttons only
         */
        simple,

        /**
         * 'Previous' and 'Next' buttons, plus page numbers
         */
        simple_numbers,

        /**
         * 'First', 'Previous', 'Next' and 'Last' buttons
         */
        full,

        /**
         * 'First', 'Previous', 'Next' and 'Last' buttons, plus page numbers
         */
        full_numbers
    }

    /**
     * https://datatables.net/examples/basic_init/table_sorting.html
     */
    private static final IKey<Sort[]> Order = new Key<Sort[]>("order", null);

    /**
     * https://datatables.net/examples/basic_init/state_save.html
     */
    private static final IKey<Boolean> StateSave = new Key<Boolean>("stateSave", false);

    /**
     * In seconds.
     *
     * https://datatables.net/examples/basic_init/state_save.html
     * https://datatables.net/reference/option/stateDuration
     */
    private static final IKey<Integer> StateDuration = new Key<Integer>("stateDuration", 7200);

    /**
     * https://datatables.net/examples/basic_init/alt_pagination.html
     */
    private static final IKey<PagingType> _PagingType = new Key<PagingType>("pagingType", PagingType.simple_numbers);

    /**
     * https://datatables.net/reference/option/paging
     */
    private static final IKey<Boolean> Paging = new Key<Boolean>("paging", true);

    /**
     * https://datatables.net/examples/basic_init/scroll_y.html
     */
    private static final IKey<String> ScrollY = new Key<String>("scrollY", null);

    /**
     * https://datatables.net/examples/basic_init/scroll_y.html
     * https://datatables.net/reference/option/scrollCollapse
     */
    private static final IKey<Boolean> ScrollCollapse = new Key<Boolean>("scrollCollapse", false);

    /**
     * https://datatables.net/examples/basic_init/scroll_x.html
     * https://datatables.net/reference/option/scrollX
     */
    private static final IKey<Boolean> ScrollX = new Key<Boolean>("scrollX", false);

    /**
     * https://datatables.net/examples/server_side/select_rows.html
     * https://datatables.net/reference/option/rowCallback
     */
    private static final IKey<Json.RawValue> RowCallback = new Key<Json.RawValue>("rowCallback", null);

    /**
     *
     * https://datatables.net/reference/option/deferLoading
     */
    private static final IKey<Integer[]> DeferLoading = new Key<Integer[]>("deferLoading", null);


    /**
     * https://datatables.net/examples/advanced_init/row_callback.html
     * https://datatables.net/reference/option/createdRow
     */
    private static final IKey<Json.RawValue> CreatedRow = new Key<Json.RawValue>("createdRow", null);

    /**
     * https://datatables.net/examples/advanced_init/length_menu.html
     * https://datatables.net/reference/option/lengthMenu
     */
    private static final IKey<Object[][]> LengthMenu = new Key<Object[][]>("lengthMenu", null);

    public Options lengthMenu(Integer[] values, String[] displayValues) {
        Args.notNull(values, "values");
        Args.notNull(displayValues, "displayValues");
        Args.isTrue(values.length == displayValues.length, "The values and display values length are different!");
        put(LengthMenu, new Object[][]{values, displayValues});
        return this;
    }

    public Options createdRow(Json.RawValue createdRow) {
        put(CreatedRow, createdRow);
        return this;
    }

    public Options deferLoading(Integer maxItems) {
        put(DeferLoading, new Integer[] {maxItems});
        return this;
    }

    public Options deferLoadingFiltered(int maxItemsFiltered, int maxItems) {
        put(DeferLoading, new Integer[] {maxItemsFiltered, maxItems});
        return this;
    }

    public Options rowCallback(Json.RawValue rowCallback) {
        put(RowCallback, rowCallback);
        return this;
    }

    public Options scrollX(Boolean scrollX) {
        put(ScrollX, scrollX);
        return this;
    }

    public Options scrollCollapse(Boolean scrollCollapse) {
        put(ScrollCollapse, scrollCollapse);
        return this;
    }

    public Options scrollY(String scrollY) {
        put(ScrollY, scrollY);
        return this;
    }

    public Options paging(Boolean paging) {
        put(Paging, paging);
        return this;
    }

    public Options pagingType(PagingType pagingType) {
        put(_PagingType, pagingType);
        return this;
    }

    public Options stateSave(Boolean stateSave) {
        put(StateSave, stateSave);
        return this;
    }

    public Options stateDuration(Integer duration) {
        put(StateDuration, duration);
        return this;
    }

    public Options order(Sort... sortParams) {
        put(Order, sortParams);
        return this;
    }
}
