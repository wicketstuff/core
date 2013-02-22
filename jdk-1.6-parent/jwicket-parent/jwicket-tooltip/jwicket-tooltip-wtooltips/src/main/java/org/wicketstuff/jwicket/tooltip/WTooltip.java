package org.wicketstuff.jwicket.tooltip;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.IComponentAwareHeaderContributor;
import org.wicketstuff.jwicket.JQueryAjaxBehavior;
import org.wicketstuff.jwicket.JQueryResourceReference;
import org.wicketstuff.jwicket.JQueryResourceReferenceType;

/**
 * This Class is a wrapper around the jQuery plugin <a href="http://wayfarerweb.com/wtooltip.php">wTooltip</a>
 */
public class WTooltip extends AbstractToolTip {
    private static final long serialVersionUID = 1L;

    private static final JQueryResourceReference wTooltip = new JQueryResourceReference(WTooltip.class, "wTooltip.js", JQueryResourceReferenceType.NOT_OVERRIDABLE);

    private String style;
    private String cssClassName;
    private int offsetX = 1;
    private int offsetY = -10;
    private int fadeinMs = 0;
    private int fadeoutMs = 0;
    private int delayMs = 0;
    private int timeoutMs = 0;


    public WTooltip(final String tooltipText) {
        super(tooltipText);
    }

    @Override
    protected IComponentAwareHeaderContributor getHeaderContributor() {
        return new JQueryAjaxBehavior(wTooltip) {
            private static final long serialVersionUID = 1L;


            @Override
            public void renderHead(Component component, IHeaderResponse response) {
                super.renderHead(component, response);

                response.render(OnDomReadyHeaderItem.forScript(getJavaScript()));
            }
        };
    }

    @Override
    public WTooltip setTooltipText(final String htmlCode) {
        super.setTooltipText(htmlCode);
        return this;
    }


    public WTooltip setStyle(final String styleDefinition) {
        this.style = styleDefinition;
        return this;
    }


    public WTooltip setCssClass(final String classNAme) {
        this.cssClassName = classNAme;
        return this;
    }


    public WTooltip setOffset(final int x, final int y) {
        this.offsetX = x;
        this.offsetY = y;
        return this;
    }


    public WTooltip setFadeIn(final int ms) {
        this.fadeinMs = ms;
        return this;
    }


    public WTooltip setFadeOut(final int ms) {
        this.fadeoutMs = ms;
        return this;
    }


    public WTooltip setDelay(final int ms) {
        this.delayMs = ms;
        return this;
    }


    public WTooltip setTimeout(final int ms) {
        this.timeoutMs = ms;
        return this;
    }


    @Override
    public String getJavaScript() {
        StringBuilder builder = new StringBuilder();
        for (Component component : this.components) {
            builder.append("jQuery(function(){jQuery('#");
            builder.append(component.getMarkupId());
            builder.append("').wTooltip({content:'");
            builder.append(getTooltipText());
            builder.append("'");
            if (this.style != null && this.style.trim().length() > 0) {
                builder.append(",style:{");
                builder.append(this.style);
                builder.append("}");
            }
            if (this.cssClassName != null && this.cssClassName.trim().length() > 0) {
                builder.append(",className:'");
                builder.append(this.cssClassName);
                builder.append("'");
            }
            if (this.offsetX != 1) {
                builder.append(",offsetX:");
                builder.append(this.offsetX);
            }
            if (this.offsetY != -10) {
                builder.append(",offsetY:");
                builder.append(this.offsetY);
            }
            if (this.fadeinMs != 0) {
                builder.append(",fadeIn:");
                builder.append(this.fadeinMs);
            }
            if (this.fadeoutMs != 0) {
                builder.append(",fadeOut:");
                builder.append(this.fadeoutMs);
            }
            if (this.delayMs != 0) {
                builder.append(",delay:");
                builder.append(this.delayMs);
            }
            if (this.timeoutMs != 0) {
                builder.append(",timeout:");
                builder.append(this.timeoutMs);
            }
            builder.append("});});");
        }
        return builder.toString();
    }
}
