package org.wicketstuff.minis.behavior.tooltipsy;

import java.io.Serializable;

import org.apache.wicket.util.lang.Args;

/**
 * 
 * Options for Tooltipsy
 * @author Ilkka Seppälä https://github.com/iluwatar
 * 
 */
public class TooltipsyOptions implements Serializable {

	private static final long serialVersionUID = 1L;
	private String css = "";
	private Offset offset = new Offset();
	private String content = "";
	private TooltipsyAlignment align = TooltipsyAlignment.ELEMENT;
	private int delay = 200;
	private String tooltipsyClassName = "tooltipsy";
	private String showJavaScript = "function (e, $el) { $el.show(100); }";
	private String hideJavaScript = "function (e, $el) { $el.fadeOut(100); }";

	/**
	 * Create with default options
	 */
	public TooltipsyOptions() {
	}

	/**
	 * Copy constructor
	 * @param options
	 */
	public TooltipsyOptions(TooltipsyOptions options) {
		this();
		if (options != null) {
			this.setCss(options.getCss());
			this.setOffset(options.getOffset());
			this.setContent(options.getContent());
			this.setAlign(options.getAlign());
			this.setDelay(options.getDelay());
			this.setTooltipsyClassName(options.getTooltipsyClassName());
			this.setShowJavaScript(options.getShowJavaScript());
			this.setHideJavaScript(options.getHideJavaScript());
		}
	}
	
	public Offset getOffset() {
		return new Offset(this.offset);
	}

	public void setOffset(Offset offset) {
		Args.notNull(offset, "offset");
		this.offset = new Offset(offset);
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		Args.notNull(css, "css");
		this.css = css;
	}

	/**
	 * Builds parameter string for Tooltipsy
	 * @return
	 */
	public String getParameterString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("offset: [" + String.valueOf(offset.getOffsetX()) + "," + String.valueOf(offset.getOffsetY()) + "]");
		sb.append(",");
		sb.append("content: '" + content + "'");
		sb.append(",");
		sb.append("alignTo: '" + align.getName() + "'");
		sb.append(",");
		sb.append("delay: " + String.valueOf(delay));
		sb.append(",");
		sb.append("className: '" + tooltipsyClassName + "'");
		sb.append(",");
		sb.append("show: " + showJavaScript);
		sb.append(",");
		sb.append("hide: " + hideJavaScript);
		sb.append(",");
		sb.append("css: {");
		sb.append(css);
		sb.append("}");
		sb.append("}");
		return sb.toString();
		
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		Args.notNull(content, "content");
		this.content = content;
	}

	public TooltipsyAlignment getAlign() {
		return align;
	}

	public void setAlign(TooltipsyAlignment align) {
		Args.notNull(align, "align");
		this.align = align;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		Args.notNull(delay, "delay");
		this.delay = delay;
	}

	public String getTooltipsyClassName() {
		return tooltipsyClassName;
	}

	public void setTooltipsyClassName(String tooltipsyClassName) {
		Args.notNull(tooltipsyClassName, "tooltipsyClassName");
		this.tooltipsyClassName = tooltipsyClassName;
	}

	public String getShowJavaScript() {
		return showJavaScript;
	}

	public void setShowJavaScript(String showJavaScript) {
		Args.notNull(showJavaScript, "showJavaScript");
		this.showJavaScript = showJavaScript;
	}

	public String getHideJavaScript() {
		return hideJavaScript;
	}

	public void setHideJavaScript(String hideJavaScript) {
		Args.notNull(hideJavaScript, "hideJavaScript");
		this.hideJavaScript = hideJavaScript;
	}

	/**
	 * 
	 * Simple class to hold offset
	 *
	 */
	public static class Offset {
		private int offsetX = 0;
		private int offsetY = -1;
		public Offset() {
		}
		public Offset(int offsetX, int offsetY) {
			this.offsetX = offsetX;
			this.setOffsetY(offsetY);
		}
		public Offset(Offset other) {
			this.offsetX = other.offsetX;
			this.offsetY = other.offsetY;
		}
		public int getOffsetX() {
			return offsetX;
		}
		public void setOffsetX(int offsetX) {
			this.offsetX = offsetX;
		}
		public int getOffsetY() {
			return offsetY;
		}
		public void setOffsetY(int offsetY) {
			this.offsetY = offsetY;
		}
	}
	
	/**
	 * 
	 * Enum for Tooltipsy alignment
	 *
	 */
	public static enum TooltipsyAlignment {
		ELEMENT("element"), 
		CURSOR("cursor");
		
		private final String name;
		private TooltipsyAlignment(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	};
	
	/**
	 * 
	 * Builder for easy creating of TooltipsyOptions instances
	 *
	 */
	public static class TooltipsyOptionsBuilder {

		private TooltipsyOptions options = new TooltipsyOptions();
		
		public TooltipsyOptionsBuilder() {
		}
		
		public TooltipsyOptionsBuilder setOffset(Offset offset) {
			options.setOffset(offset);
			return this;
		}

		public TooltipsyOptionsBuilder setCss(String css) {
			options.setCss(css);
			return this;
		}
		
		public TooltipsyOptionsBuilder setContent(String content) {
			options.setContent(content);
			return this;
		}
		
		public TooltipsyOptionsBuilder setTooltipsyClassName(String tooltipsyClassName) {
			options.setTooltipsyClassName(tooltipsyClassName);
			return this;
		}
		
		public TooltipsyOptionsBuilder setAlign(TooltipsyAlignment align) {
			options.setAlign(align);
			return this;
		}
		
		public TooltipsyOptionsBuilder setDelay(int delay) {
			options.setDelay(delay);
			return this;
		}
		
		public TooltipsyOptionsBuilder setShowJavaScript(String showJavaScript) {
			options.setShowJavaScript(showJavaScript);
			return this;
		}

		public TooltipsyOptionsBuilder setHideJavaScript(String hideJavaScript) {
			options.setHideJavaScript(hideJavaScript);
			return this;
		}
		
		public TooltipsyOptions getResult() {
			return new TooltipsyOptions(options);
		}
		
	}
	
}
