package org.wicketstuff.jwicket.tooltip;


import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.util.string.JavascriptUtils;
import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.tooltip.AbstractToolTip;



/**
 * This class is a wrapper around <a href="http://www.walterzorn.com/tooltip/tooltip_e.htm">Walter Zorn's</a>
 * ToolTip library.
 *
 * The implementation covers version 5.31 together with jQuery 1.3.2.
 *
 * For a detailed description of options, CSS styling and other thins have a look at the original documentation.
 */
public class WalterZornTips extends AbstractToolTip {
	private static final long serialVersionUID = 1L;

	public WalterZornTips(final String tooltipText) {
		super(tooltipText);
	}


	public void enable(final AjaxRequestTarget target) {
		update(target);
	}

	
	public void disable(final AjaxRequestTarget target) {
		for (Component component : components) {
			target.appendJavascript(
				"jQuery('#" +
				component.getMarkupId() +
				"').unbind('mouseover');"
			);
			target.appendJavascript(
					"jQuery('#" +
					component.getMarkupId() +
					"').unbind('mouseout');"
				);
		}
	}

	
	@Override
	IHeaderContributor getHeadercontributor() {
		return new HeaderContributor();
	}

	
	
	
	private class HeaderContributor extends JQuery {
		private static final long serialVersionUID = 1L;

		protected HeaderContributor() {
			super(new JavascriptResourceReference(WalterZornTips.class, "walterzorn_tip.js"));
		}

		
		@Override
		public void renderHead(IHeaderResponse response) {
			super.renderHead(response);

			response.renderJavascript("jQuery(function(){tt_Init();});", "initWzToolTips");
			response.renderJavascript(getJavaScript(), null);
		}


		@Override
		protected void respond(AjaxRequestTarget target) { }
	}
	
	
	
	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		super.onComponentTag(component, tag);

		String js = getJavaScript();

		if (js.length() > 0) {
			RequestCycle tequestCycle = RequestCycle.get();
			if (tequestCycle != null) {
				Response response = tequestCycle.getResponse();
				if (response != null)
					JavascriptUtils.writeJavascript(response, js);
			}
		}
	}


	/**
	 * Set the content of the ToolTip. You can provide a simple text or complex HTML code.
	 * 
	 * @param htmlCode the content
	 */
	public WalterZornTips setTooltipText(final String htmlCode) {
		super.setTooltipText(htmlCode);
		return this;
	}


	/**
	 * Set the content of the ToolTip. You can provide a simple text or complex HTML code.
	 * The {@code htmlCode} gets modified:
	 * <table cellspacing="3" cellpadding="0">
	 * 	<thead><tr><th>original</th><th style="padding-left:15px;">replaced by</th></tr>
	 * 	</thead>
	 *	<tbody>
	 * 	<tr><td>{@code "}</td><td style="padding-left:15px;">{@code &quot;}</td></tr>
	 * 	<tr><td>{@code '}</td><td style="padding-left:15px;">{@code \'}</td></tr>
	 *	</tbody>
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


	String getJavaScript() {
		StringBuilder builder = new StringBuilder();
		for (Component component : components) {
			builder.append("jQuery(function(){jQuery('#");
			builder.append(component.getMarkupId());
			builder.append("').bind('mouseover', function(){Tip('");
			builder.append(tooltipText);
			builder.append("'");

			for (String option : options.keySet()) {
				builder.append(",");
				builder.append(option);
				builder.append(",");
				builder.append(options.get(option));
			}

			if (rawOptions != null && rawOptions.trim().length() > 0) {
				builder.append(",");
				builder.append(rawOptions);
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
		if (!value)
			options.remove("ABOVE");
		else
			options.put("ABOVE", "true");
		return this;
	}


	/**
	 * Sets the background color of the tooltip box. 
	 * 
	 * @param color the color in CSS form e.g. {@code #123456} or {@code null} to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setBgColor(final String color) {
		if (color == null)
			options.remove("BGCOLOR");
		else
			options.put("BGCOLOR", "'" + color + "'");
		return this;
	}


	/**
	 * Sets the background image of the tooltip box. 
	 * 
	 * @param image a path to an image file e.g. {@code /images/my-image.png} or {@code null} to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setBgImg(final String image) {
		if (image == null)
			options.remove("BGIMG");
		else
			options.put("BGIMG", "'" + image + "'");
		return this;
	}


	/**
	 * Sets the color of the tooltip box border. 
	 * 
	 * @param color the color in CSS form e.g. {@code #123456} or {@code null} to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setBorderColor(final String color) {
		if (color == null)
			options.remove("BORDERCOLOR");
		else
			options.put("BORDERCOLOR", "'" + color + "'");
		return this;
	}


	/**
	 * Sets the CSS style of the tooltip box border. 
	 * 
	 * @param style the style of the border e.g. {@code solid} or {@code null} to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setBorderStyle(final String style) {
		if (style == null)
			options.remove("BORDERSTYLE");
		else
			options.put("BORDERSTYLE", "'" + style + "'");
		return this;
	}


	/**
	 * Sets the width of the tooltip box border in px. 
	 * 
	 * @param width the width of the border in px or a value less than 0 to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setBorderWidth(final int width) {
		if (width <0)
			options.remove("BORDERWIDTH");
		else
			options.put("BORDERWIDTH", String.valueOf(width));
		return this;
	}


	/**
	 * Center the tooltip horizontally relating to the mouse pointer
	 * 
	 * @param value {@code true} for above or {@code false} for below;
	 * @return this object
	 */
	public WalterZornTips setCentermouse(final boolean value) {
		if (!value)
			options.remove("CENTERMOUSE");
		else
			options.put("CENTERMOUSE", "true");
		return this;
	}


	/**
	 * Should a mouseklick close the tooltip?
	 * 
	 * @param value {@code true} for closing by clicking or {@code false} for below;
	 * @return this object
	 */
	public WalterZornTips setClickclose(final boolean value) {
		if (!value)
			options.remove("CLICKCLOSE");
		else
			options.put("CLICKCLOSE", "true");
		return this;
	}


	/**
	 * Enables the user to fixate the tooltip, by just clicking onto the HTML element (e.g. link) that triggered the tooltip. This might help the user to read the tooltip more conveniently. Value: true, false. 
	 * 
	 * @param value {@code true} for enabling or {@code false} for disabling;
	 * @return this object
	 */
	public WalterZornTips setClicksticky(final boolean value) {
		if (!value)
			options.remove("CLICKSTICKY");
		else
			options.put("CLICKSTICKY", "true");
		return this;
	}


	/**
	 * Should a close button be displayed inside the tooltip box 
	 * 
	 * @param value {@code true} for enabling or {@code false} for disabling;
	 * @return this object
	 */
	public WalterZornTips setCloseButton(final boolean value) {
		if (!value)
			options.remove("CLOSEBTN");
		else
			options.put("CLOSEBTN", "true");
		return this;
	}


	/**
	 * Set the colors used for the closebutton. If one of thecolors is {@code null} then a default value is used.
	 *
	 * @param background the background color in CSS form e.g. {@code #123456} or {@code null} for the default value
	 * @param text the text color in CSS form e.g. {@code #123456} or {@code null} for the default value
	 * @param highlighted the color when the button is hovered in CSS form e.g. {@code #123456} or {@code null} for the default value
	 * @param highlightedText the text color when the button is hovered in CSS form e.g. {@code #123456} or {@code null} for the default value
	 *
	 * @return this object
	 */
	public WalterZornTips setCloseButtonColors(final String background, final String text, final String highlighted, final String highlightedText) {
		if (background == null && text == null && highlighted == null && highlightedText == null)
			options.remove("CLOSEBTNCOLORS");
		else
			options.put("CLOSEBTNCOLORS", "[" +
						((background==null)?"''":"'" + background + "'") +
						((text==null)?"''":"'" + text + "'") +
						((highlighted==null)?"''":"'" + highlighted + "'") +
						((highlightedText==null)?"''":"'" + highlightedText + "'") +
						"]");
		return this;
	}


	/**
	 * Sets the text displayed in a close button
	 * 
	 * @param text the text or {@code null} to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setCloseButtonText(final String text) {
		if (text == null)
			options.remove("CLOSEBTNTEXT");
		else
			options.put("CLOSEBTNTEXT", "'" + text + "'");
		return this;
	}


	/**
	 * Sets the delay in ms after the tolltip shows up 
	 * 
	 * @param ms the delay or a value less than 0 to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setDelay(final int ms) {
		if (ms < 0)
			options.remove("DELAY");
		else
			options.put("DELAY", String.valueOf(ms));
		return this;
	}


	/**
	 * Sets the time in ms until the tolltip is hidden 
	 * 
	 * @param ms the time or a value less than 0 to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setDuration(final int ms) {
		if (ms < 0)
			options.remove("DURATION");
		else
			options.put("DURATION", String.valueOf(ms));
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
		if (!value)
			options.remove("EXCLUSIVE");
		else
			options.put("EXCLUSIVE", "true");
		return this;
	}


	/**
	 * The tooltip is not showed immediately. The appearance is animated and faded in during the given period. 
	 * 
	 * @param ms the time or a value less than 0 to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setFadein(final int ms) {
		if (ms < 0)
			options.remove("FADEIN");
		else
			options.put("FADEIN", String.valueOf(ms));
		return this;
	}


	/**
	 * The tooltip is not hidden immediately. The disappearance is animated and faded out during the given period. 
	 * 
	 * @param ms the time or a value less than 0 to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setFadeout(final int ms) {
		if (ms < 0)
			options.remove("FADEOUT");
		else
			options.put("FADEOUT", String.valueOf(ms));
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
		if (x < 0 || y < 0)
			options.remove("FIX  ");
		else
			options.put("FIX  ", "[" + String.valueOf(x) + "," + String.valueOf(y) + "]");
		return this;
	}


	/**
	 * Displays the tooltip at a position relative to another HTML element 
	 *
	 * @param id the id of the HTML element
	 * @param x the horizontal offset from the given HTML element or a value less than 0 to revert to the default value
	 * @param y the vertical offset from the given HTML element or a value less than 0 to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setFix2(final String id, final int x, final int y) {
		if (id == null || x < 0 || y < 0)
			options.remove("FIX  ");
		else
			options.put("FIX  ", "['" + id + "'," + String.valueOf(x) + "," + String.valueOf(y) + "]");
		return this;
	}


	/**
	 * The tooltip follows the movement of the mouse.
	 * 
	 * @param value {@code false} for disabling or {@code true} for enabling;
	 * @return this object
	 */
	public WalterZornTips setFollowmouse(final boolean value) {
		if (value)
			options.remove("FOLLOWMOUSE");
		else
			options.put("FOLLOWMOUSE", "false");
		return this;
	}


	/**
	 * Sets the font color of the tooltip's content
	 * 
	 * @param fontcolor the color or {@code null} to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setFontcolor(final String fontcolor) {
		if (fontcolor == null)
			options.remove("FONTCOLOR");
		else
			options.put("FONTCOLOR", "'" + fontcolor + "'");
		return this;
	}


	/**
	 * Sets the font face of the tooltip's content
	 * 
	 * @param fontface the font face or {@code null} to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setFontface(final String fontface) {
		if (fontface == null)
			options.remove("FONTFACE");
		else
			options.put("FONTFACE", "'" + fontface + "'");
		return this;
	}


	/**
	 * Sets the font size of the tooltip's content 
	 * 
	 * @param px the time or a value <= 0 to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setFontsize(final int px) {
		if (px <= 0)
			options.remove("FONTSIZE");
		else
			options.put("FONTSIZE", String.valueOf(px));
		return this;
	}


	/**
	 * Sets the font weight of the tooltip's content
	 * 
	 * @param fontweight the font weight or {@code null} to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setFontweight(final String fontweight) {
		if (fontweight == null)
			options.remove("FONTWEIGHT");
		else
			options.put("FONTWEIGHT", "'" + fontweight + "'");
		return this;
	}


	/**
	 * Sets the height of the tooltip in px 
	 * 
	 * @param px the height or 0 to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setHeight(final int px) {
		if (px == 0)
			options.remove("HEIGHT");
		else
			options.put("HEIGHT", String.valueOf(px));
		return this;
	}


	/**
	 * See the original documentation
	 * 
	 * @param value {@code false} for disabling or {@code true} for enabling;
	 * @return this object
	 */
	public WalterZornTips setJumphorz(final boolean value) {
		if (!value)
			options.remove("JUMPHORZ");
		else
			options.put("JUMPHORZ", "true");
		return this;
	}


	/**
	 * See the original documentation
	 * 
	 * @param value {@code false} for disabling or {@code true} for enabling;
	 * @return this object
	 */
	public WalterZornTips setJumpvert(final boolean value) {
		if (!value)
			options.remove("JUMPVERT");
		else
			options.put("JUMPVERT", "true");
		return this;
	}


	/**
	 * Positions the tooltip to the left ot the mouse pointer
	 * 
	 * @param value {@code false} for disabling or {@code true} for enabling;
	 * @return this object
	 */
	public WalterZornTips setLeft(final boolean value) {
		if (!value)
			options.remove("LEFT");
		else
			options.put("LEFT", "true");
		return this;
	}


	/**
	 * Sets the x-offset from the mouse pointer
	 * 
	 * @param px the offset in px
	 * @return this object
	 */
	public WalterZornTips setOffsetX(final int px) {
		if (px == 0)
			options.remove("OFFSETX");
		else
			options.put("OFFSETX", String.valueOf(px));
		return this;
	}


	/**
	 * Sets the y-offset from the mouse pointer
	 * 
	 * @param px the offset in px
	 * @return this object
	 */
	public WalterZornTips setOffsetY(final int px) {
		if (px == 0)
			options.remove("OFFSETY");
		else
			options.put("OFFSETY", String.valueOf(px));
		return this;
	}


	/**
	 * Sets the opacity of the tooltip content
	 * 
	 * @param value the opacity in percent (0-100) or a negative number to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setOpacity(final int value) {
		if (value < 0)
			options.remove("OPACITY");
		else
			options.put("OPACITY", String.valueOf(value));
		return this;
	}


	/**
	 * Sets the padding between the tooltip border and the contents.
	 * 
	 * @param px the padding
	 * @return this object
	 */
	public WalterZornTips setPadding(final int px) {
		if (px < 0)
			options.remove("PADDING");
		else
			options.put("PADDING", String.valueOf(px));
		return this;
	}


	/**
	 * Should the tooltip box drop a shadow
	 * 
	 * @param value {@code false} for disabling or {@code true} for enabling;
	 * @return this object
	 */
	public WalterZornTips setShadow(final boolean value) {
		if (!value)
			options.remove("SHADOW");
		else
			options.put("SHADOW", "true");
		return this;
	}


	/**
	 * Sets the color of the tooltip's shadow 
	 * 
	 * @param color the color in CSS form e.g. {@code #123456} or {@code null} to revert to the default value
	 * @return this object
	 */
	public WalterZornTips setShadowColor(final String color) {
		if (color == null)
			options.remove("SHADOWCOLOR");
		else
			options.put("SHADOWCOLOR", "'" + color + "'");
		return this;
	}


	/**
	 * Sets the width of the tooltip shadow in px
	 * 
	 * @param px the width in px or a negative value to restore the default value
	 * @return this object
	 */
	public WalterZornTips setShadowWidth(final int px) {
		if (px < 0)
			options.remove("SHADOWWIDTH");
		else
			options.put("SHADOWWIDTH", String.valueOf(px));
		return this;
	}


	/**
	 * Should the tooltip stay fixed at it's initial position
	 * 
	 * @param value {@code false} for disabling or {@code true} for enabling;
	 * @return this object
	 */
	public WalterZornTips setSticky(final boolean value) {
		if (!value)
			options.remove("STICKY");
		else
			options.put("STICKY", "true");
		return this;
	}


	/**
	 * Sets the alignment of the tooltip's text
	 * 
	 * @param align the alignment or {@code null} to restore the default behavior
	 * @return this object
	 */
	public WalterZornTips setTextalign(final String align) {
		if (align == null)
			options.remove("TEXTALIGN");
		else
			options.put("TEXTALIGN", "'" + align + "'");
		return this;
	}


	/**
	 * Sets the title of te tooltip
	 * 
	 * @param title the title or {@code null} to restore the default behavior
	 * @return this object
	 */
	public WalterZornTips setTitle(final String title) {
		if (title == null)
			options.remove("TITLE");
		else
			options.put("TITLE", "'" + title + "'");
		return this;
	}


	/**
	 * Sets the alignment of the title
	 * 
	 * @param align the alignment of the title or {@code null} to restore the default behavior
	 * @return this object
	 */
	public WalterZornTips setTitleAlign(final String align) {
		if (align == null)
			options.remove("TITLEALIGN");
		else
			options.put("TITLEALIGN", "'" + align + "'");
		return this;
	}


	/**
	 * Sets the background color of the title
	 * 
	 * @param color the background color of the title or {@code null} to restore the default behavior
	 * @return this object
	 */
	public WalterZornTips setTitleBgColor(final String color) {
		if (color == null)
			options.remove("TITLEBGCOLOR");
		else
			options.put("TITLEBGCOLOR", "'" + color + "'");
		return this;
	}


	/**
	 * Sets the text color of the title
	 * 
	 * @param color the color of the title or {@code null} to restore the default behavior
	 * @return this object
	 */
	public WalterZornTips setTitleFontColor(final String color) {
		if (color == null)
			options.remove("TITLEFONTCOLOR");
		else
			options.put("TITLEFONTCOLOR", "'" + color + "'");
		return this;
	}


	/**
	 * Sets the fontface color of the title
	 * 
	 * @param fontface the color of the title or {@code null} to restore the default behavior
	 * @return this object
	 */
	public WalterZornTips setTitleFontFace(final String fontface) {
		if (fontface == null)
			options.remove("TITLEFONTFACE");
		else
			options.put("TITLEFONTFACE", "'" + fontface + "'");
		return this;
	}


	/**
	 * Sets the fontsize of the title
	 * 
	 * @param px the fontsize or a negative value to restore the default value
	 * @return this object
	 */
	public WalterZornTips setTitleFontSize(final int px) {
		if (px < 0)
			options.remove("TITLEFONTSIZE");
		else
			options.put("TITLEFONTSIZE", String.valueOf(px));
		return this;
	}


	/**
	 * Sets the padding around the title
	 * 
	 * @param px the padding around the title or a negative value to restore the default value
	 * @return this object
	 */
	public WalterZornTips setTitlePadding(final int px) {
		if (px < 0)
			options.remove("TITLEPADDING");
		else
			options.put("TITLEPADDING", String.valueOf(px));
		return this;
	}


	/**
	 * Sets the width of the tooltip's box
	 * 
	 * @param px the width of the tooltip's box or a value <= 0 to restore the default value
	 * @return this object
	 */
	public WalterZornTips setWidth(final int px) {
		if (px <= 0)
			options.remove("WIDTH");
		else
			options.put("WIDTH", String.valueOf(px));
		return this;
	}

}
