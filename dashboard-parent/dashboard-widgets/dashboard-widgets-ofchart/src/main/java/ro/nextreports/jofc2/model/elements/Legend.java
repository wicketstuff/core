package ro.nextreports.jofc2.model.elements;

import java.io.Serializable;

public class Legend implements Serializable {

	private static final long serialVersionUID = 2220096987890659431L;
	private String position;
	private boolean visible;
	private String bg_colour;
	private String border_color;
	private boolean shadow;
	private Integer margin;
	private Integer alpha;
	private Integer padding;
	private boolean border;
	private Integer stroke;

	/**
	 * Default constructor for Legend. Automatically sets visibility to true and
	 * position to right cause they are the only correct values at that time
	 */
	public Legend() {
		super();
		setVisible(true);
		setPosition("right");
	}

	public String getPosition() {
		return position;
	}

	/**
	 * Sets the position of the legend. Actually there is only the right side
	 * supported
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	public boolean isVisible() {
		return visible;
	}

	/**
	 * Determines if the legend should be rendered or not (atm you should set it
	 * to true if you pass a legend to chart)
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getBg_colour() {
		return bg_colour;
	}

	/**
	 * Sets the backgroud color of the legend. E.g. "#FFFFFF" for white
	 */
	public void setBg_colour(String bg_colour) {
		this.bg_colour = bg_colour;
	}

	public String getBorder_color() {
		return border_color;
	}

	/**
	 * Sets the border color of the legend. E.g. "#FFFFFF" for white
	 * 
	 */
	public void setBorder_color(String border_color) {
		this.border_color = border_color;
	}

	public boolean isShadow() {
		return shadow;
	}

	/**
	 * Switch if shadows should be used or not
	 * 
	 */
	public void setShadow(boolean shadow) {
		this.shadow = shadow;
	}

	public Integer getMargin() {
		return margin;
	}

	/**
	 * "margin" defines the number of pixel spacing around the outside of the
	 * right side legend box. This can be used to adjust how close the legend
	 * appears to the edge of the window and to the right side Y legend or right
	 * edge of the chart if there is no right side legend.
	 */
	public void setMargin(Integer margin) {
		this.margin = margin;
	}

	public Integer getAlpha() {
		return alpha;
	}

	/**
	 * Sets the alpha (transparency value) of the legend.
	 */
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	public Integer getPadding() {
		return padding;
	}

	/**
	 * "padding" defines a number of pixels to place between the labels and the
	 * border of the right side legend
	 */
	public void setPadding(int padding) {
		this.padding = padding;
	}

	public boolean isBorder() {
		return border;
	}

	/**
	 * "border" defines whether or not to draw a border line around the right
	 * side legend box.
	 */
	public void setBorder(boolean border) {
		this.border = border;
	}

	public Integer getStroke() {
		return stroke;
	}

	/**
	 * "stroke" defines the size of the border line around the right side legend
	 * box.
	 */
	public void setStroke(int stroke) {
		this.stroke = stroke;
	}
}
