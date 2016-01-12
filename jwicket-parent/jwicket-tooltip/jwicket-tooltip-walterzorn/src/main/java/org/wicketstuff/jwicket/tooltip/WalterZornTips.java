package org.wicketstuff.jwicket.tooltip;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.util.string.JavaScriptUtils;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.IComponentAwareHeaderContributor;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.jwicket.JQueryAjaxBehavior;
import org.wicketstuff.jwicket.JQueryResourceReference;
import org.wicketstuff.jwicket.JQueryResourceReferenceType;

/**
 * This class is a wrapper around <a href="http://www.walterzorn.com/tooltip/tooltip_e.htm">Walter Zorn's</a>
 * ToolTip library.
 * <p/>
 * The implementation covers version 5.31 together with jQuery 1.3.2.
 * <p/>
 * For a detailed description of options, CSS styling and other thins have a look at the original documentation.
 */
public class WalterZornTips extends AbstractToolTip {
    private static final long serialVersionUID = 1L;

    public static final JQueryResourceReference walterzorn = new JQueryResourceReference(WalterZornTips.class, "walterzorn_tip.js", JQueryResourceReferenceType.NOT_OVERRIDABLE);


    public WalterZornTips(final String tooltipText) {
        super(tooltipText);
    }

    public void enable(final AjaxRequestTarget target) {
        update(target);
    }


    public void disable(final AjaxRequestTarget target) {
        for (Component component : this.components) {
            target.appendJavaScript(
                    "jQuery('#" +
                            component.getMarkupId() +
                            "').unbind('mouseover');"
            );
            target.appendJavaScript(
                    "jQuery('#" +
                            component.getMarkupId() +
                            "').unbind('mouseout');"
            );
        }
    }

    @Override
    protected IComponentAwareHeaderContributor getHeaderContributor() {
        return new JQueryAjaxBehavior(walterzorn) {
            private static final long serialVersionUID = -4226998278012788512L;

            @Override
            public void renderHead(Component component, IHeaderResponse response) {
                super.renderHead(component, response);

                response.render(OnDomReadyHeaderItem.forScript("jQuery(function(){tt_Init();});"));
                response.render(OnDomReadyHeaderItem.forScript(getJavaScript()));
            }
        };
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        super.onComponentTag(component, tag);

        String js = getJavaScript();

        if (js.length() > 0) {
            RequestCycle tequestCycle = RequestCycle.get();
            if (tequestCycle != null) {
                Response response = tequestCycle.getResponse();
                if (response != null) {
                    JavaScriptUtils.writeJavaScript(response, js);
                }
            }
        }
    }

    /**
     * Set the content of the ToolTip. You can provide a simple text or complex HTML code.
     *
     * @param htmlCode the content
     */
    @Override
    public WalterZornTips setTooltipText(final String htmlCode) {
        super.setTooltipText(htmlCode);
        return this;
    }

    /**
     * Set the content of the ToolTip. You can provide a simple text or complex HTML code.
     * The {@code htmlCode} gets modified:
     * <table cellspacing="3" cellpadding="0">
     * <thead><tr><th>original</th><th style="padding-left:15px;">replaced by</th></tr>
     * </thead>
     * <tbody>
     * <tr><td>{@code "}</td><td style="padding-left:15px;">{@code &quot;}</td></tr>
     * <tr><td>{@code '}</td><td style="padding-left:15px;">{@code \'}</td></tr>
     * </tbody>
     * <table>
     * No html code parsing is done, just a plain replacement. If you have special cases where
     * the replacement leads to the wrong result, use {@link #setTooltipText(String)} instead
     * and be sure to pass correct html code to this method.
     *
     * @param htmlCode the content
     */
    public WalterZornTips setTooltipTextWithCorrections(final String htmlCode) {
        super.setTooltipText(htmlCode.replace("\"", "&quot;").replace("'", "\\'"));
        return this;
    }


    @Override
    public String getJavaScript() {
        StringBuilder builder = new StringBuilder();
        for (Component component : this.components) {
            builder.append("jQuery(function(){jQuery('#");
            builder.append(component.getMarkupId());
            builder.append("').bind('mouseover', function(){Tip('");
            builder.append(getTooltipText());
            builder.append("'");

            for (String option : this.options.keySet()) {
                builder.append(",");
                builder.append(option);
                builder.append(",");
                builder.append(this.options.get(option));
            }

            if (this.rawOptions != null && this.rawOptions.trim().length() > 0) {
                builder.append(",");
                builder.append(this.rawOptions);
            }


            builder.append(");});});");

            builder.append("jQuery(function(){jQuery('#");
            builder.append(component.getMarkupId());
            builder.append("').bind('mouseout', UnTip);});");
        }
        return builder.toString();
    }


    private String rawOptions = null;

    /**
     * You can use this method to set special options not covered by
     * other methods. The options must not start or end with "{" and
     * "}".
     *
     * @param options the special options, e.g. {@code "ABOVE, true, BGCOLOR, ''"}
     * @return this object
     */
    public WalterZornTips setRawOptions(final String options) {
        this.rawOptions = options;
        return this;
    }


    /**
     * Places the tooltip above the mouse symbol.
     *
     * @param value {@code true} for above or {@code false} for below;
     * @return this object
     */
    public WalterZornTips setAbove(final boolean value) {
        if (!value) {
            this.options.remove("ABOVE");
        } else {
            this.options.put("ABOVE", "true");
        }
        return this;
    }


    /**
     * Sets the background color of the tooltip box.
     *
     * @param color the color in CSS form e.g. {@code #123456} or {@code null} to revert to the default value
     * @return this object
     */
    public WalterZornTips setBgColor(final String color) {
        if (color == null) {
            this.options.remove("BGCOLOR");
        } else {
            this.options.put("BGCOLOR", "'" + color + "'");
        }
        return this;
    }


    /**
     * Sets the background image of the tooltip box.
     *
     * @param image a path to an image file e.g. {@code /images/my-image.png} or {@code null} to revert to the default value
     * @return this object
     */
    public WalterZornTips setBgImg(final String image) {
        if (image == null) {
            this.options.remove("BGIMG");
        } else {
            this.options.put("BGIMG", "'" + image + "'");
        }
        return this;
    }


    /**
     * Sets the color of the tooltip box border.
     *
     * @param color the color in CSS form e.g. {@code #123456} or {@code null} to revert to the default value
     * @return this object
     */
    public WalterZornTips setBorderColor(final String color) {
        if (color == null) {
            this.options.remove("BORDERCOLOR");
        } else {
            this.options.put("BORDERCOLOR", "'" + color + "'");
        }
        return this;
    }


    /**
     * Sets the CSS style of the tooltip box border.
     *
     * @param style the style of the border e.g. {@code solid} or {@code null} to revert to the default value
     * @return this object
     */
    public WalterZornTips setBorderStyle(final String style) {
        if (style == null) {
            this.options.remove("BORDERSTYLE");
        } else {
            this.options.put("BORDERSTYLE", "'" + style + "'");
        }
        return this;
    }


    /**
     * Sets the width of the tooltip box border in px.
     *
     * @param width the width of the border in px or a value less than 0 to revert to the default value
     * @return this object
     */
    public WalterZornTips setBorderWidth(final int width) {
        if (width < 0) {
            this.options.remove("BORDERWIDTH");
        } else {
            this.options.put("BORDERWIDTH", String.valueOf(width));
        }
        return this;
    }


    /**
     * Center the tooltip horizontally relating to the mouse pointer
     *
     * @param value {@code true} for above or {@code false} for below;
     * @return this object
     */
    public WalterZornTips setCentermouse(final boolean value) {
        if (!value) {
            this.options.remove("CENTERMOUSE");
        } else {
            this.options.put("CENTERMOUSE", "true");
        }
        return this;
    }


    /**
     * Should a mouseklick close the tooltip?
     *
     * @param value {@code true} for closing by clicking or {@code false} for below;
     * @return this object
     */
    public WalterZornTips setClickclose(final boolean value) {
        if (!value) {
            this.options.remove("CLICKCLOSE");
        } else {
            this.options.put("CLICKCLOSE", "true");
        }
        return this;
    }


    /**
     * Enables the user to fixate the tooltip, by just clicking onto the HTML element (e.g. link) that triggered the tooltip. This might help the user to read the tooltip more conveniently. Value: true, false.
     *
     * @param value {@code true} for enabling or {@code false} for disabling;
     * @return this object
     */
    public WalterZornTips setClicksticky(final boolean value) {
        if (!value) {
            this.options.remove("CLICKSTICKY");
        } else {
            this.options.put("CLICKSTICKY", "true");
        }
        return this;
    }


    /**
     * Should a close button be displayed inside the tooltip box
     *
     * @param value {@code true} for enabling or {@code false} for disabling;
     * @return this object
     */
    public WalterZornTips setCloseButton(final boolean value) {
        if (!value) {
            this.options.remove("CLOSEBTN");
        } else {
            this.options.put("CLOSEBTN", "true");
        }
        return this;
    }


    /**
     * Set the colors used for the closebutton. If one of thecolors is {@code null} then a default value is used.
     *
     * @param background      the background color in CSS form e.g. {@code #123456} or {@code null} for the default value
     * @param text            the text color in CSS form e.g. {@code #123456} or {@code null} for the default value
     * @param highlighted     the color when the button is hovered in CSS form e.g. {@code #123456} or {@code null} for the default value
     * @param highlightedText the text color when the button is hovered in CSS form e.g. {@code #123456} or {@code null} for the default value
     * @return this object
     */
    public WalterZornTips setCloseButtonColors(final String background, final String text, final String highlighted, final String highlightedText) {
        if (background == null && text == null && highlighted == null && highlightedText == null) {
            this.options.remove("CLOSEBTNCOLORS");
        } else {
            this.options.put("CLOSEBTNCOLORS", "[" +
                    (background == null ? "''" : "'" + background + "'") +
                    (text == null ? "''" : "'" + text + "'") +
                    (highlighted == null ? "''" : "'" + highlighted + "'") +
                    (highlightedText == null ? "''" : "'" + highlightedText + "'") +
                    "]");
        }
        return this;
    }


    /**
     * Sets the text displayed in a close button
     *
     * @param text the text or {@code null} to revert to the default value
     * @return this object
     */
    public WalterZornTips setCloseButtonText(final String text) {
        if (text == null) {
            this.options.remove("CLOSEBTNTEXT");
        } else {
            this.options.put("CLOSEBTNTEXT", "'" + text + "'");
        }
        return this;
    }


    /**
     * Sets the delay in ms after the tolltip shows up
     *
     * @param ms the delay or a value less than 0 to revert to the default value
     * @return this object
     */
    public WalterZornTips setDelay(final int ms) {
        if (ms < 0) {
            this.options.remove("DELAY");
        } else {
            this.options.put("DELAY", String.valueOf(ms));
        }
        return this;
    }


    /**
     * Sets the time in ms until the tolltip is hidden
     *
     * @param ms the time or a value less than 0 to revert to the default value
     * @return this object
     */
    public WalterZornTips setDuration(final int ms) {
        if (ms < 0) {
            this.options.remove("DURATION");
        } else {
            this.options.put("DURATION", String.valueOf(ms));
        }
        return this;
    }


    /**
     * The active tooltip stays open until it is closed activly. Not other tooltip is diplayed
     * until then.
     *
     * @param value {@code true} for enabling or {@code false} for disabling;
     * @return this object
     */
    public WalterZornTips setExclusive(final boolean value) {
        if (!value) {
            this.options.remove("EXCLUSIVE");
        } else {
            this.options.put("EXCLUSIVE", "true");
        }
        return this;
    }


    /**
     * The tooltip is not showed immediately. The appearance is animated and faded in during the given period.
     *
     * @param ms the time or a value less than 0 to revert to the default value
     * @return this object
     */
    public WalterZornTips setFadein(final int ms) {
        if (ms < 0) {
            this.options.remove("FADEIN");
        } else {
            this.options.put("FADEIN", String.valueOf(ms));
        }
        return this;
    }


    /**
     * The tooltip is not hidden immediately. The disappearance is animated and faded out during the given period.
     *
     * @param ms the time or a value less than 0 to revert to the default value
     * @return this object
     */
    public WalterZornTips setFadeout(final int ms) {
        if (ms < 0) {
            this.options.remove("FADEOUT");
        } else {
            this.options.put("FADEOUT", String.valueOf(ms));
        }
        return this;
    }


    /**
     * Displays the tooltip at fixed position x and y
     *
     * @param x the horizontal offset from page left border or a value less than 0 to revert to the default value
     * @param y the vertical offset from page top border or a value less than 0 to revert to the default value
     * @return this object
     */
    public WalterZornTips setFix1(final int x, final int y) {
        if (x < 0 || y < 0) {
            this.options.remove("FIX  ");
        } else {
            this.options.put("FIX  ", "[" + String.valueOf(x) + "," + String.valueOf(y) + "]");
        }
        return this;
    }


    /**
     * Displays the tooltip at a position relative to another HTML element
     *
     * @param id the id of the HTML element
     * @param x  the horizontal offset from the given HTML element or a value less than 0 to revert to the default value
     * @param y  the vertical offset from the given HTML element or a value less than 0 to revert to the default value
     * @return this object
     */
    public WalterZornTips setFix2(final String id, final int x, final int y) {
        if (id == null || x < 0 || y < 0) {
            this.options.remove("FIX  ");
        } else {
            this.options.put("FIX  ", "['" + id + "'," + String.valueOf(x) + "," + String.valueOf(y) + "]");
        }
        return this;
    }


    /**
     * The tooltip follows the movement of the mouse.
     *
     * @param value {@code false} for disabling or {@code true} for enabling;
     * @return this object
     */
    public WalterZornTips setFollowmouse(final boolean value) {
        if (value) {
            this.options.remove("FOLLOWMOUSE");
        } else {
            this.options.put("FOLLOWMOUSE", "false");
        }
        return this;
    }


    /**
     * Sets the font color of the tooltip's content
     *
     * @param fontcolor the color or {@code null} to revert to the default value
     * @return this object
     */
    public WalterZornTips setFontcolor(final String fontcolor) {
        if (fontcolor == null) {
            this.options.remove("FONTCOLOR");
        } else {
            this.options.put("FONTCOLOR", "'" + fontcolor + "'");
        }
        return this;
    }


    /**
     * Sets the font face of the tooltip's content
     *
     * @param fontface the font face or {@code null} to revert to the default value
     * @return this object
     */
    public WalterZornTips setFontface(final String fontface) {
        if (fontface == null) {
            this.options.remove("FONTFACE");
        } else {
            this.options.put("FONTFACE", "'" + fontface + "'");
        }
        return this;
    }


    /**
     * Sets the font size of the tooltip's content
     *
     * @param px the time or a value <= 0 to revert to the default value
     * @return this object
     */
    public WalterZornTips setFontsize(final int px) {
        if (px <= 0) {
            this.options.remove("FONTSIZE");
        } else {
            this.options.put("FONTSIZE", String.valueOf(px));
        }
        return this;
    }


    /**
     * Sets the font weight of the tooltip's content
     *
     * @param fontweight the font weight or {@code null} to revert to the default value
     * @return this object
     */
    public WalterZornTips setFontweight(final String fontweight) {
        if (fontweight == null) {
            this.options.remove("FONTWEIGHT");
        } else {
            this.options.put("FONTWEIGHT", "'" + fontweight + "'");
        }
        return this;
    }


    /**
     * Sets the height of the tooltip in px
     *
     * @param px the height or 0 to revert to the default value
     * @return this object
     */
    public WalterZornTips setHeight(final int px) {
        if (px == 0) {
            this.options.remove("HEIGHT");
        } else {
            this.options.put("HEIGHT", String.valueOf(px));
        }
        return this;
    }


    /**
     * See the original documentation
     *
     * @param value {@code false} for disabling or {@code true} for enabling;
     * @return this object
     */
    public WalterZornTips setJumphorz(final boolean value) {
        if (!value) {
            this.options.remove("JUMPHORZ");
        } else {
            this.options.put("JUMPHORZ", "true");
        }
        return this;
    }


    /**
     * See the original documentation
     *
     * @param value {@code false} for disabling or {@code true} for enabling;
     * @return this object
     */
    public WalterZornTips setJumpvert(final boolean value) {
        if (!value) {
            this.options.remove("JUMPVERT");
        } else {
            this.options.put("JUMPVERT", "true");
        }
        return this;
    }


    /**
     * Positions the tooltip to the left ot the mouse pointer
     *
     * @param value {@code false} for disabling or {@code true} for enabling;
     * @return this object
     */
    public WalterZornTips setLeft(final boolean value) {
        if (!value) {
            this.options.remove("LEFT");
        } else {
            this.options.put("LEFT", "true");
        }
        return this;
    }


    /**
     * Sets the x-offset from the mouse pointer
     *
     * @param px the offset in px
     * @return this object
     */
    public WalterZornTips setOffsetX(final int px) {
        if (px == 0) {
            this.options.remove("OFFSETX");
        } else {
            this.options.put("OFFSETX", String.valueOf(px));
        }
        return this;
    }


    /**
     * Sets the y-offset from the mouse pointer
     *
     * @param px the offset in px
     * @return this object
     */
    public WalterZornTips setOffsetY(final int px) {
        if (px == 0) {
            this.options.remove("OFFSETY");
        } else {
            this.options.put("OFFSETY", String.valueOf(px));
        }
        return this;
    }


    /**
     * Sets the opacity of the tooltip content
     *
     * @param value the opacity in percent (0-100) or a negative number to revert to the default value
     * @return this object
     */
    public WalterZornTips setOpacity(final int value) {
        if (value < 0) {
            this.options.remove("OPACITY");
        } else {
            this.options.put("OPACITY", String.valueOf(value));
        }
        return this;
    }


    /**
     * Sets the padding between the tooltip border and the contents.
     *
     * @param px the padding
     * @return this object
     */
    public WalterZornTips setPadding(final int px) {
        if (px < 0) {
            this.options.remove("PADDING");
        } else {
            this.options.put("PADDING", String.valueOf(px));
        }
        return this;
    }


    /**
     * Should the tooltip box drop a shadow
     *
     * @param value {@code false} for disabling or {@code true} for enabling;
     * @return this object
     */
    public WalterZornTips setShadow(final boolean value) {
        if (!value) {
            this.options.remove("SHADOW");
        } else {
            this.options.put("SHADOW", "true");
        }
        return this;
    }


    /**
     * Sets the color of the tooltip's shadow
     *
     * @param color the color in CSS form e.g. {@code #123456} or {@code null} to revert to the default value
     * @return this object
     */
    public WalterZornTips setShadowColor(final String color) {
        if (color == null) {
            this.options.remove("SHADOWCOLOR");
        } else {
            this.options.put("SHADOWCOLOR", "'" + color + "'");
        }
        return this;
    }


    /**
     * Sets the width of the tooltip shadow in px
     *
     * @param px the width in px or a negative value to restore the default value
     * @return this object
     */
    public WalterZornTips setShadowWidth(final int px) {
        if (px < 0) {
            this.options.remove("SHADOWWIDTH");
        } else {
            this.options.put("SHADOWWIDTH", String.valueOf(px));
        }
        return this;
    }


    /**
     * Should the tooltip stay fixed at it's initial position
     *
     * @param value {@code false} for disabling or {@code true} for enabling;
     * @return this object
     */
    public WalterZornTips setSticky(final boolean value) {
        if (!value) {
            this.options.remove("STICKY");
        } else {
            this.options.put("STICKY", "true");
        }
        return this;
    }


    /**
     * Sets the alignment of the tooltip's text
     *
     * @param align the alignment or {@code null} to restore the default behavior
     * @return this object
     */
    public WalterZornTips setTextalign(final String align) {
        if (align == null) {
            this.options.remove("TEXTALIGN");
        } else {
            this.options.put("TEXTALIGN", "'" + align + "'");
        }
        return this;
    }


    /**
     * Sets the title of te tooltip
     *
     * @param title the title or {@code null} to restore the default behavior
     * @return this object
     */
    public WalterZornTips setTitle(final String title) {
        if (title == null) {
            this.options.remove("TITLE");
        } else {
            this.options.put("TITLE", "'" + title + "'");
        }
        return this;
    }


    /**
     * Sets the alignment of the title
     *
     * @param align the alignment of the title or {@code null} to restore the default behavior
     * @return this object
     */
    public WalterZornTips setTitleAlign(final String align) {
        if (align == null) {
            this.options.remove("TITLEALIGN");
        } else {
            this.options.put("TITLEALIGN", "'" + align + "'");
        }
        return this;
    }


    /**
     * Sets the background color of the title
     *
     * @param color the background color of the title or {@code null} to restore the default behavior
     * @return this object
     */
    public WalterZornTips setTitleBgColor(final String color) {
        if (color == null) {
            this.options.remove("TITLEBGCOLOR");
        } else {
            this.options.put("TITLEBGCOLOR", "'" + color + "'");
        }
        return this;
    }


    /**
     * Sets the text color of the title
     *
     * @param color the color of the title or {@code null} to restore the default behavior
     * @return this object
     */
    public WalterZornTips setTitleFontColor(final String color) {
        if (color == null) {
            this.options.remove("TITLEFONTCOLOR");
        } else {
            this.options.put("TITLEFONTCOLOR", "'" + color + "'");
        }
        return this;
    }


    /**
     * Sets the fontface color of the title
     *
     * @param fontface the color of the title or {@code null} to restore the default behavior
     * @return this object
     */
    public WalterZornTips setTitleFontFace(final String fontface) {
        if (fontface == null) {
            this.options.remove("TITLEFONTFACE");
        } else {
            this.options.put("TITLEFONTFACE", "'" + fontface + "'");
        }
        return this;
    }


    /**
     * Sets the fontsize of the title
     *
     * @param px the fontsize or a negative value to restore the default value
     * @return this object
     */
    public WalterZornTips setTitleFontSize(final int px) {
        if (px < 0) {
            this.options.remove("TITLEFONTSIZE");
        } else {
            this.options.put("TITLEFONTSIZE", String.valueOf(px));
        }
        return this;
    }


    /**
     * Sets the padding around the title
     *
     * @param px the padding around the title or a negative value to restore the default value
     * @return this object
     */
    public WalterZornTips setTitlePadding(final int px) {
        if (px < 0) {
            this.options.remove("TITLEPADDING");
        } else {
            this.options.put("TITLEPADDING", String.valueOf(px));
        }
        return this;
    }


    /**
     * Sets the width of the tooltip's box
     *
     * @param px the width of the tooltip's box or a value <= 0 to restore the default value
     * @return this object
     */
    public WalterZornTips setWidth(final int px) {
        if (px <= 0) {
            this.options.remove("WIDTH");
        } else {
            this.options.put("WIDTH", String.valueOf(px));
        }
        return this;
    }
}