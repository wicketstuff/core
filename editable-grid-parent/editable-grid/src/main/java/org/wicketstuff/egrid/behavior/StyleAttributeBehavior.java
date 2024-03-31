package org.wicketstuff.egrid.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.egrid.attribute.HTMLAttribute;
import org.wicketstuff.egrid.attribute.StyleAttribute;

import java.io.Serial;

public class StyleAttributeBehavior extends Behavior {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String EMPTY = "";
    private static final String SEPERATOR_SPACE = " ";
    private static final String SEPERATOR_COLON = ":";
    private static final String SEPERATOR_SEMI_COLON = ";";

    private StyleAttribute styleAttribute;

    public StyleAttributeBehavior(final StyleAttribute styleAttribute) {

        if (styleAttribute == null) {
            throw new IllegalArgumentException("Argument [styleAttribute] cannot be null");
        }
        this.styleAttribute = styleAttribute;
    }

    public final void onComponentTag(final Component component, final ComponentTag tag) {
        super.onComponentTag(component, tag);

        String existingStyles = (String) tag.getAttributes().get(HTMLAttribute.STYLE);
        putExistingStyles(existingStyles);

        onStyleAttribute(this.styleAttribute);

        String styles = this.styleAttribute.getStyles();
        if (!Strings.isEmpty(styles)) {
            tag.put(HTMLAttribute.STYLE, styles);
        }
    }

    private void putExistingStyles(final String styleAtt) {

        if (styleAtt != null) {
            String[] items = styleAtt.split(SEPERATOR_SEMI_COLON);
            for (int i = 0; i < items.length; i++) {
                String item = items[i];
                this.styleAttribute.put(
                        item.substring(0, item.indexOf(SEPERATOR_COLON)).replaceAll(SEPERATOR_SPACE, EMPTY),
                        item.substring(item.indexOf(SEPERATOR_COLON) + 1, item.length()).replaceAll(SEPERATOR_SPACE, EMPTY));
            }
        }
    }

    protected void onStyleAttribute(final StyleAttribute styleAttribute) {

    }
}
