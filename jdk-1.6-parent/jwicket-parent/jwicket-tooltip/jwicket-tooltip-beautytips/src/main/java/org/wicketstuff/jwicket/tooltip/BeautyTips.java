package org.wicketstuff.jwicket.tooltip;


import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.IComponentAwareHeaderContributor;
import org.wicketstuff.jwicket.BgIframeBehavior;
import org.wicketstuff.jwicket.JQueryAjaxBehavior;
import org.wicketstuff.jwicket.JQueryResourceReference;
import org.wicketstuff.jwicket.JQueryResourceReferenceType;


/**
 * This Class is a wrapper around the jQuery plugin <a href="http://www.lullabot.com/articles/announcing-beautytips-jquery-tooltip-plugin">BeautyTips</a>
 * <p/>
 * The implementation covers version 0.9.5 RC1 of BeautyTips together with jQuery 1.3.2.
 * <p/>
 * Only a few options are controllable directly through Java methods until BeautyTips reaches 1.0 version.
 * But you can control anything via {@link BeautyTips#setRawOptions(String)}.
 * <p/>
 * For a detailed description of options, CSS styling and other thins have a look at the original documentation.
 */
public class BeautyTips extends AbstractToolTip {
    private static final long serialVersionUID = 1L;

    private static final JQueryResourceReference jqueryHoverIntent = new JQueryResourceReference(BeautyTips.class, "jquery.hoverIntent.minified.js", JQueryResourceReferenceType.NOT_OVERRIDABLE);
    private static final JQueryResourceReference excanvas = new JQueryResourceReference(BeautyTips.class, "excanvas.compiled.js", JQueryResourceReferenceType.NOT_OVERRIDABLE);
    private static final JQueryResourceReference bt = new JQueryResourceReference(BeautyTips.class, "jquery.bt.min.js", JQueryResourceReferenceType.NOT_OVERRIDABLE);
    private static final JQueryResourceReference jqueryEasing = new JQueryResourceReference(BeautyTips.class, "jquery.easing.1.3.js", JQueryResourceReferenceType.NOT_OVERRIDABLE);


    public BeautyTips(final String tooltipText) {
        super(tooltipText);
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        response.render(OnDomReadyHeaderItem.forScript(getJavaScript()));
    }

    @Override
    protected IComponentAwareHeaderContributor getHeaderContributor() {
        return new JQueryAjaxBehavior(jqueryHoverIntent, BgIframeBehavior.jQueryBgiframeJs, excanvas, bt, jqueryEasing) {
            private static final long serialVersionUID = 1L;

            @Override
            public void renderHead(Component component, IHeaderResponse response) {
                super.renderHead(component, response);

                response.render(OnDomReadyHeaderItem.forScript(getJavaScript()));
            }
        };
    }

    /*
    return new IHeaderContributor() {
        private static final long serialVersionUID = 1L;

        public void renderHead(final IHeaderResponse response) {
            if (ieVersion < 0) {
                ClientInfo ci = WebSession.get().getClientInfo();
                if (ci instanceof WebClientInfo) {
                    WebClientInfo wci = (WebClientInfo)ci;
                    ClientProperties cp = wci.getProperties();
                    if (cp.isBrowserInternetExplorer()) {
                        ieVersion = cp.getBrowserVersionMajor();
                    }
                    else
                        ieVersion = 0;
                }
            }

            response.renderJavaScriptReference(new JavaScriptResourceReference(JQueryAjaxBehavior.class, "jquery-1.3.2.js"));
            response.renderJavaScript("jQuery.noConflict();", "noConflict");


            response.renderJavaScriptReference(new JavaScriptResourceReference(BeautyTips.class, "jquery.hoverIntent.minified.js"));

            response.renderJavaScriptReference(new JavaScriptResourceReference(BeautyTips.class, "jquery.bgiframe.min.js"));
            if (ieVersion > 0)
                response.renderJavaScriptReference(new JavaScriptResourceReference(BeautyTips.class, "excanvas.compiled.js"));
            response.renderJavaScriptReference(new JavaScriptResourceReference(BeautyTips.class, "jquery.bt.min.js"));
            response.renderJavaScriptReference(new JavaScriptResourceReference(BeautyTips.class, "jquery.easing.1.3.js"));

            response.renderJavaScript(getJavaScript(), null);
        }
    };
    */
//    }


    /**
     * Set the content of the ToolTip. You can provide a simple text or complex HTML code.
     *
     * @param htmlCode the content
     */
    @Override
    public BeautyTips setTooltipText(final String htmlCode) {
        super.setTooltipText(htmlCode);
        return this;
    }


    @Override
    public String getJavaScript() {
        boolean firstOption = true;
        StringBuilder builder = new StringBuilder();
        for (Component component : this.components) {
            builder.append("jQuery(function(){jQuery('#");
            builder.append(component.getMarkupId());
            builder.append("').bt('");
            builder.append(getTooltipText());
            builder.append("',{");


            if (this.widthInPx > 0) {
                if (!firstOption) {
                    builder.append(",");
                } else {
                    firstOption = false;
                }
                builder.append("width:");
                builder.append(this.widthInPx);
                builder.append("px");
            }

            if (this.paddingInPx >= 0) {
                if (!firstOption) {
                    builder.append(",");
                } else {
                    firstOption = false;
                }
                builder.append("padding:");
                builder.append(this.paddingInPx);
                builder.append("px");
            }

            if (this.spikeGirth >= 0) {
                if (!firstOption) {
                    builder.append(",");
                } else {
                    firstOption = false;
                }
                builder.append("spikeGirth:");
                builder.append(this.spikeGirth);
            }

            if (this.spikeLength >= 0) {
                if (!firstOption) {
                    builder.append(",");
                } else {
                    firstOption = false;
                }
                builder.append("spikeLength:");
                builder.append(this.spikeLength);
            }

            if (this.spikeOverlap >= 0) {
                if (!firstOption) {
                    builder.append(",");
                } else {
                    firstOption = false;
                }
                builder.append("overlap:");
                builder.append(this.spikeOverlap);
            }

            if (this.cornerRadius >= 0) {
                if (!firstOption) {
                    builder.append(",");
                } else {
                    firstOption = false;
                }
                builder.append("cornerRadius:");
                builder.append(this.cornerRadius);
            }

            if (this.cssClass != null && this.cssClass.trim().length() > 0) {
                if (!firstOption) {
                    builder.append(",");
                } else {
                    firstOption = false;
                }
                builder.append("cssClass:'");
                builder.append(this.cssClass);
                builder.append("'");
            }

            if (this.cssStyles != null && this.cssStyles.trim().length() > 0) {
                if (!firstOption) {
                    builder.append(",");
                } else {
                    firstOption = false;
                }
                builder.append("cssStyles:'{");
                builder.append(this.cssStyles);
                builder.append("}'");
            }

            if (this.activeClass != null && this.activeClass.trim().length() > 0) {
                if (!firstOption) {
                    builder.append(",");
                } else {
                    firstOption = false;
                }
                builder.append("activeClass:'");
                builder.append(this.activeClass);
                builder.append("'");
            }


            if (this.rawOptions != null && this.rawOptions.trim().length() > 0) {
                if (!firstOption) {
                    builder.append(",");
                } else {
                    firstOption = false;
                }
                builder.append(this.rawOptions);
            }


            builder.append("});});");
        }
        return builder.toString();
    }


    private String rawOptions = null;

    /**
     * You can use this method to set special options not covered by
     * other methods. The options must not start or end with "{" and
     * "}".
     *
     * @param options the special options, e.g. {@code "width:15px,trigger:['focus mouseover', 'blur mouseout']"}
     * @return this object
     */
    public BeautyTips setRawOptions(final String options) {
        this.rawOptions = options;
        return this;
    }


    private int widthInPx = 0;

    /**
     * Sets the width of the ToolTip in px. If the parameter widthInPx is <= 0 the
     * width of the ToolTip is reset to the internal default.
     *
     * @param widthInPx The width of the ToolTip in px
     * @return this object
     */
    public BeautyTips setWidth(final int widthInPx) {
        this.widthInPx = widthInPx;
        return this;
    }


    private int paddingInPx = -1;

    /**
     * Sets the padding of the ToolTip's content in px.
     * If the parameter paddingInPx is < 0 the padding
     * is reset to the internal default.
     *
     * @param paddingInPx The padding of the ToolTip's content of the ToolTip in px
     * @return this object
     */
    public BeautyTips setPadding(final int paddingInPx) {
        this.paddingInPx = paddingInPx;
        return this;
    }


    private int spikeGirth = -1;

    /**
     * Sets the width of the spike. If the width < 0 then the width
     * is reset to the internal default.
     *
     * @param width The width of the spike
     * @return this object
     */
    public BeautyTips setSpikeGirth(final int width) {
        this.spikeGirth = width;
        return this;
    }


    private int spikeLength = -1;

    /**
     * Sets the length of the spike. If the length < 0 then the length
     * is reset to the internal default.
     *
     * @param length The length of the spike
     * @return this object
     */
    public BeautyTips setSpikeLength(final int length) {
        this.spikeLength = length;
        return this;
    }


    private int spikeOverlap = -1;

    /**
     * Sets the overlap of the spike onto target. If the overlap < 0 then the overlap
     * is reset to the internal default.
     *
     * @param overlap The overlap of the spike onto target
     * @return this object
     */
    public BeautyTips setSpikeOverlap(final int overlap) {
        this.spikeOverlap = overlap;
        return this;
    }


    private int cornerRadius = -1;

    /**
     * Sets the radius of the ToolTip box. If the radius < 0 then the radius
     * is reset to the internal default. If the radius = 0 then a square ToolTip
     * box is displayed.
     *
     * @param radius The radius of the ToolTip box.
     * @return this object
     */
    public BeautyTips setCornerRadius(final int radius) {
        this.cornerRadius = radius;
        return this;
    }


    private String cssClass = null;

    /**
     * Sets the CSS class for the ToolTip content.
     *
     * @param cssClassName the name of a CSS class
     * @return this object
     */
    public BeautyTips setCssClass(final String cssClassName) {
        this.cssClass = cssClassName;
        return this;
    }


    private String cssStyles = null;

    /**
     * Sets some CSS styles for the ToolTip content, e.g. {@code "color: red, background-color: black"}
     *
     * @param cssStyles the CSS styles
     * @return this object
     */
    public BeautyTips setCssStyles(final String cssStyles) {
        this.cssStyles = cssStyles;
        return this;
    }


    private String activeClass = null;

    /**
     * Sets the CSS class for the Component that triggers the ToolTip.
     *
     * @param activeClassName the name of a CSS class
     * @return this object
     */
    public BeautyTips setActiveClass(final String activeClassName) {
        this.activeClass = activeClassName;
        return this;
    }

}


/**
 * Defaults for the beauty tips
 *
 * Note this is a variable definition and not a function. So defaults can be
 * written for an entire page by simply redefining attributes like so:
 *
 *   jQuery.bt.options.width = 400;
 *
 * Be sure to use *jQuery.bt.options* and not jQuery.bt.defaults when overriding
 *
 * This would make all Beauty Tips boxes 400px wide.
 *
 * Each of these options may also be overridden during
 *
 * Can be overriden globally or at time of call.
 *
 *
 jQuery.bt.defaults = {
 trigger:         'hover',                // trigger to show/hide tip
 // use [on, off] to define separate on/off triggers
 // also use space character to allow multiple  to trigger
 // examples:
 //   ['focus', 'blur'] // focus displays, blur hides
 //   'dblclick'        // dblclick toggles on/off
 //   ['focus mouseover', 'blur mouseout'] // multiple triggers
 //   'now'             // shows/hides tip without event
 //   'none'            // use $('#selector').btOn(); and ...btOff();
 //   'hoverIntent'     // hover using hoverIntent plugin (settings below)
 // note:
 //   hoverIntent becomes default if available

 clickAnywhereToClose: true,              // clicking anywhere outside of the tip will close it
 closeWhenOthersOpen: false,              // tip will be closed before another opens - stop >= 2 tips being on

 shrinkToFit:      false,                 // should short single-line content get a narrower balloon?
 * width:            '200px',               // width of tooltip box

 * padding:          '10px',                // padding for content (get more fine grained with cssStyles)
 * spikeGirth:       10,                    // width of spike
 * spikeLength:      15,                    // length of spike
 * overlap:          0,                     // spike overlap (px) onto target (can cause problems with 'hover' trigger)
 overlay:          false,                 // display overlay on target (use CSS to style) -- BUGGY!
 killTitle:        true,                  // kill title tags to avoid double tooltips

 textzIndex:       9999,                  // z-index for the text
 boxzIndex:        9998,                  // z-index for the "talk" box (should always be less than textzIndex)
 wrapperzIndex:    9997,
 offsetParent:     null,                  // DOM node to append the tooltip into.
 // Must be positioned relative or absolute. Can be selector or object
 positions:        ['most'],              // preference of positions for tip (will use first with available space)
 // possible values 'top', 'bottom', 'left', 'right' as an array in order of
 // preference. Last value will be used if others don't have enough space.
 // or use 'most' to use the area with the most space
 fill:             "rgb(255, 255, 102)",  // fill color for the tooltip box, you can use any CSS-style color definition method
 // http://www.w3.org/TR/css3-color/#numerical - not all methods have been tested

 windowMargin:     10,                    // space (px) to leave between text box and browser edge

 strokeWidth:      1,                     // width of stroke around box, **set to 0 for no stroke**
 strokeStyle:      "#000",                // color/alpha of stroke

 * cornerRadius:     5,                     // radius of corners (px), set to 0 for square corners

 // following values are on a scale of 0 to 1 with .5 being centered

 centerPointX:     .5,                    // the spike extends from center of the target edge to this point
 centerPointY:     .5,                    // defined by percentage horizontal (x) and vertical (y)

 shadow:           false,                 // use drop shadow? (only displays in Safari and FF 3.1) - experimental
 shadowOffsetX:    2,                     // shadow offset x (px)
 shadowOffsetY:    2,                     // shadow offset y (px)
 shadowBlur:       3,                     // shadow blur (px)
 shadowColor:      "#000",                // shadow color/alpha
 shadowOverlap:   false,                  // when shadows overlap the target element it can cause problem with hovering
 // set this to true to overlap or set to a numeric value to define the amount of overlap
 noShadowOpts:     {strokeStyle: '#999'},  // use this to define 'fall-back' options for browsers which don't support drop shadows

 * cssClass:         '',                    // CSS class to add to the box wrapper div (of the TIP)
 * cssStyles:        {},                    // styles to add the text box
 //   example: {fontFamily: 'Georgia, Times, serif', fontWeight: 'bold'}

 * activeClass:      'bt-active',           // class added to TARGET element when its BeautyTip is active

 contentSelector:  "$(this).attr('title')", // if there is no content argument, use this selector to retrieve the title
 // a function which returns the content may also be passed here

 ajaxPath:         null,                  // if using ajax request for content, this contains url and (opt) selector
 // this will override content and contentSelector
 // examples (see jQuery load() function):
 //   '/demo.html'
 //   '/help/ajax/snip'
 //   '/help/existing/full div#content'

 // ajaxPath can also be defined as an array
 // in which case, the first value will be parsed as a jQuery selector
 // the result of which will be used as the ajaxPath
 // the second (optional) value is the content selector as above
 // examples:
 //    ["$(this).attr('href')", 'div#content']
 //    ["$(this).parents('.wrapper').find('.title').attr('href')"]
 //    ["$('#some-element').val()"]

 ajaxError:        '<strong>ERROR:</strong> <em>%error</em>',
 // error text, use "%error" to insert error from server
 ajaxLoading:     '<blink>Loading...</blink>',  // yes folks, it's the blink tag!
 ajaxData:         {},                    // key/value pairs
 ajaxType:         'GET',                 // 'GET' or 'POST'
 ajaxCache:        true,                  // cache ajax results and do not send request to same url multiple times
 ajaxOpts:         {},                    // any other ajax options - timeout, passwords, processing functions, etc...
 // see http://docs.jquery.com/Ajax/jQuery.ajax#options

 preBuild:         function(){},          // function to run before popup is built
 preShow:          function(box){},       // function to run before popup is displayed
 showTip:          function(box){
 $(box).show();
 },
 postShow:         function(box){},       // function to run after popup is built and displayed

 preHide:          function(box){},       // function to run before popup is removed
 hideTip:          function(box, callback) {
 $(box).hide();
 callback();   // you MUST call "callback" at the end of your animations
 },
 postHide:         function(){},          // function to run after popup is removed

 hoverIntentOpts:  {                          // options for hoverIntent (if installed)
 interval: 300,           // http://cherne.net/brian/resources/jquery.hoverIntent.html
 timeout: 500
 }

 */

