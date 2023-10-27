package org.wicketstuff.egrid.attribute;

import org.apache.wicket.util.string.Strings;

import java.io.Serial;

public class HTMLAttribute extends BaseHTMLAttribute {

    public static final String CLASS = "class";
    public static final String COL_SPAN = "colspan";
    public static final String COLS = "cols";
    public static final String MAX_LENGTH = "maxlength";
    public static final String READONLY = "readonly";
    public static final String ROWS = "rows";
    public static final String SIZE = "size";
    public static final String TITLE = "title";
    public static final String STYLE = "style";
    @Serial
    private static final long serialVersionUID = 1L;

    public HTMLAttribute() {
        super();
    }

    public final HTMLAttribute setReadOnly() {
        put(READONLY, READONLY);
        return this;
    }

    public final HTMLAttribute setMaxLength(final int maxlength) {
        put(MAX_LENGTH, String.valueOf(maxlength));
        return this;
    }

    public final HTMLAttribute setSize(final int size) {
        put(SIZE, String.valueOf(size));
        return this;
    }

    public final HTMLAttribute setColSpan(final int colspan) {
        put(COL_SPAN, String.valueOf(colspan));
        return this;
    }

    public final HTMLAttribute setTitle(final String title) {
        put(TITLE, title);
        return this;
    }

    public final HTMLAttribute setCols(final int cols) {
        put(COLS, String.valueOf(cols));
        return this;
    }

    public final HTMLAttribute setRows(final int rows) {
        put(ROWS, String.valueOf(rows));
        return this;
    }

    public void addClass(final String className) {

        final StringBuilder appendedClass = new StringBuilder();

        if (getAttributeKeys().contains(CLASS)) {
            final String currentClass = get(CLASS).toString();

            if (!Strings.isEmpty(currentClass)) {
                appendedClass.append(currentClass)
                        .append(" ");
            }
        }
        appendedClass.append(className);

        put(CLASS, appendedClass.toString());
    }
}
