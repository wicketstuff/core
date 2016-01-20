package org.wicketstuff.datatables.options;

import de.agilecoders.wicket.jquery.AbstractConfig;
import de.agilecoders.wicket.jquery.IKey;
import de.agilecoders.wicket.jquery.Key;
import de.agilecoders.wicket.jquery.util.Json;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.util.lang.Args;
import org.wicketstuff.datatables.Sort;
import org.wicketstuff.datatables.res.DataTablesCssReference;

import java.util.List;

/**
 *
 */
public class Options extends AbstractConfig {

    /**
     * https://datatables.net/examples/basic_init/alt_pagination.html
     */
    public enum PagingType {
        numbers,

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

    public enum Style {
        dataTables,
        bootstrap,
        foundation,
        jqueryui,
        none;

        public void renderHead(IHeaderResponse response, String pluginName) {
            if (none != this) {
                if (!(dataTables == this && "dataTables".equals(pluginName))) {
                    String name = "css/" + pluginName + "." + name() + ".css";
                    CssResourceReference cssReference = new CssResourceReference(DataTablesCssReference.class, name);
                    response.render(CssHeaderItem.forReference(cssReference));
                }
            }
        }
    }

    /**
     * https://datatables.net/examples/basic_init/table_sorting.html
     */
    public static final IKey<Sort[]> Order = new Key<>("order", null);

    /**
     * https://datatables.net/examples/basic_init/state_save.html
     */
    public static final IKey<Boolean> StateSave = new Key<>("stateSave", false);

    /**
     * In seconds.
     *
     * https://datatables.net/examples/basic_init/state_save.html
     * https://datatables.net/reference/option/stateDuration
     */
    public static final IKey<Integer> StateDuration = new Key<>("stateDuration", 7200);

    /**
     * https://datatables.net/examples/basic_init/alt_pagination.html
     */
    public static final IKey<PagingType> _PagingType = new Key<>("pagingType", PagingType.simple_numbers);

    /**
     * https://datatables.net/reference/option/paging
     */
    public static final IKey<Boolean> Paging = new Key<>("paging", true);

    /**
     * https://datatables.net/reference/option/scrollY
     * https://datatables.net/examples/basic_init/scroll_y.html
     */
    public static final IKey<String> ScrollY = new Key<>("scrollY", null);

	/**
     * https://datatables.net/reference/option/dom
     */
    public static final IKey<String> Dom = new Key<>("dom", "lfrtip");

    /**
     * https://datatables.net/examples/basic_init/scroll_y.html
     * https://datatables.net/reference/option/scrollCollapse
     */
    public static final IKey<Boolean> ScrollCollapse = new Key<>("scrollCollapse", false);

    /**
     * https://datatables.net/examples/basic_init/scroll_x.html
     * https://datatables.net/reference/option/scrollX
     */
    public static final IKey<Boolean> ScrollX = new Key<>("scrollX", false);

    /**
     * https://datatables.net/manual/server-side
     * https://datatables.net/reference/option/serverSide
     * https://datatables.net/extensions/scroller/examples/initialisation/server-side_processing.html
     */
    public static final IKey<Boolean> ServerSide = new Key<>("serverSide", false);

    public static final IKey<Boolean> Ordering = new Key<>("ordering", true);

    public static final IKey<Boolean> Searching = new Key<>("searching", true);

    public static final IKey<Boolean> DeferRender = new Key<>("deferRender", false);

    public static final IKey<Boolean> Info = new Key<>("info", true);

    public static final IKey<Boolean> Processing = new Key<>("processing", false);

    public static final IKey<String> Ajax = new Key<>("ajax", null);

    /**
     * https://datatables.net/reference/option/rowId
     */
    public static final IKey<Json.RawValue> RowId = new Key<>("rowId", new Json.RawValue("'DT_RowId'"));

    public static final IKey<ScrollerOptions> Scroller = new Key<>("scroller", null);

	/**
	 * https://datatables.net/extensions/select/
     */
    public static final IKey<SelectOptions> Select = new Key<>("select", null);

    /**
     * https://datatables.net/examples/server_side/select_rows.html
     * https://datatables.net/reference/option/rowCallback
     */
    public static final IKey<Json.RawValue> RowCallback = new Key<>("rowCallback", null);

    /**
     *
     * https://datatables.net/reference/option/deferLoading
     */
    public static final IKey<Integer[]> DeferLoading = new Key<>("deferLoading", null);


    /**
     * https://datatables.net/examples/advanced_init/row_callback.html
     * https://datatables.net/reference/option/createdRow
     */
    public static final IKey<Json.RawValue> CreatedRow = new Key<>("createdRow", null);

    /**
     * https://datatables.net/manual/data/renderers
     */
    public static final IKey<Json.RawValue> Render = new Key<>("render", null);

    /**
     * https://datatables.net/examples/advanced_init/length_menu.html
     * https://datatables.net/reference/option/lengthMenu
     */
    public static final IKey<Object[][]> LengthMenu = new Key<>("lengthMenu", null);

    public static final IKey<List<Column>> Columns = new Key<>("columns", null);

    public static final IKey<Integer> PageLength = new Key<>("pageLength", null);

	/**
	 * http://datatables.net/reference/option/retrieve
     */
    public static final IKey<Boolean> Retrieve = new Key<>("retrieve", false);

    private Style style = Style.none;

    public Options style(Style style) {
        if (style == null) {
            style = Style.none;
        }
        this.style = style;
        return this;
    }

    public Style getStyle() {
        return style;
    }

    public Options columns(List<Column> columns) {
        put(Columns, columns);
        return this;
    }

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

    public Options render(Json.RawValue render) {
        put(Render, render);
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

    public Options scrollX(boolean scrollX) {
        put(ScrollX, scrollX);
        return this;
    }

    public Options info(boolean info) {
        put(Info, info);
        return this;
    }

    public Options retrieve(boolean retrieve) {
        put(Retrieve, retrieve);
        return this;
    }

    public Options processing(boolean processing) {
        put(Processing, processing);
        return this;
    }

    public Options serverSide(boolean serverSide) {
        put(ServerSide, serverSide);
        return this;
    }

    public Options ordering(boolean ordering) {
        put(Ordering, ordering);
        return this;
    }

    public Options searching(boolean searching) {
        put(Searching, searching);
        return this;
    }

    public Options deferRender(boolean deferRender) {
        put(DeferRender, deferRender);
        return this;
    }

    public Options scroller(ScrollerOptions scroller) {
        scrollCollapse(false);
        paging(true);

        put(Scroller, scroller);
        return this;
    }

    public Options select(SelectOptions select) {
        put(Select, select);
        return this;
    }

    public Options ajax(CharSequence ajax) {
        put(Ajax, String.valueOf(ajax));
        return this;
    }

    public Options scrollCollapse(boolean scrollCollapse) {
        put(ScrollCollapse, scrollCollapse);
        return this;
    }

    public Options scrollY(String scrollY) {
        put(ScrollY, scrollY);
        return this;
    }

    public Options rowId(Json.RawValue rowId) {
        put(RowId, rowId);
        return this;
    }

    public Options dom(String dom) {
        put(Dom, dom);
        return this;
    }

    public Options paging(boolean paging) {
        put(Paging, paging);
        return this;
    }

    public Options pagingType(PagingType pagingType) {
        put(_PagingType, pagingType);
        return this;
    }

    public Options stateSave(boolean stateSave) {
        put(StateSave, stateSave);
        return this;
    }

    public Options pageLength(int pageLength) {
        put(PageLength, pageLength);
        return this;
    }

    public Options stateDuration(int duration) {
        put(StateDuration, duration);
        return this;
    }

    public Options order(Sort... sortParams) {
        put(Order, sortParams);
        return this;
    }
}
