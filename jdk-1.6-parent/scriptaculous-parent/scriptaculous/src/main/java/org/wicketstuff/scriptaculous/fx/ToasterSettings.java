package org.wicketstuff.scriptaculous.fx;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.wicketstuff.scriptaculous.effect.Effect;
import org.wicketstuff.scriptaculous.effect.Effect.Appear;
import org.wicketstuff.scriptaculous.effect.Effect.Fade;
import org.wicketstuff.scriptaculous.effect.Effect.Pulsate;

/**
 * Use this to control look and feel of toaster
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 * 
 */
public class ToasterSettings implements Serializable {

	public enum ToasterLocation {
		TopLeft("Top", "Left"), TopRight("Top", "Right"), BottomLeft("Bottom",
				"Left"), BottomRight("Bottom", "Right"),MiddleMiddle("Top","Right","50%","50%");
		private String horizontal;
		private String vertical;
		private String verticalPx;
		private String horizontalPx;

		private ToasterLocation(String horizontal, String vertical) {
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.horizontalPx = "0px";
			this.verticalPx = "0px";
		}

		private ToasterLocation(String horizontal, String vertical,
				String horizontalPx, String verticalPx) {
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.horizontalPx = horizontalPx;
			this.verticalPx = verticalPx;
		}

		public String getHorizontal() {
			return horizontal;
		}

		public String getVertical() {
			return vertical;
		}

		public String getVerticalPx() {
			return verticalPx;
		}

		public String getHorizontalPx() {
			return horizontalPx;
		}

	}

	private Effect appear;
	private Effect middle;
	private Effect fade;
	private ToasterLocation location;
	private String toasterBackground = "#F0F0F0";
	private String toasterBorderColor = "#444488";

	public ToasterSettings(Component container) {
		Appear appear = new Effect.Appear(container);
		appear.setDuration(2);
		appear.setQueue("front");
		this.appear = appear;
		Pulsate pulsate = new Effect.Pulsate(container);
		pulsate.setDuration(3);
		pulsate.setQueue("end");
		this.middle = pulsate;
		Fade fade = new Effect.Fade(container);
		fade.setDuration(6);
		fade.setQueue("end");
		this.fade = fade;
		this.location = ToasterLocation.TopLeft;

	}

	public ToasterSettings(Effect appear, Effect middle, Effect fade,
			ToasterLocation location, String toasterBackground,
			String toasterBorderColor) {
		super();
		this.appear = appear;
		this.middle = middle;
		this.fade = fade;
		this.location = location;
		this.toasterBackground = toasterBackground;
		this.toasterBorderColor = toasterBorderColor;
	}

	public ToasterSettings(ToasterLocation location) {
		super();
		this.location = location;
	}

	public Effect getAppear() {
		return appear;
	}

	public Effect getMiddle() {
		return middle;
	}

	public Effect getFade() {
		return fade;
	}

	public ToasterLocation getLocation() {
		return location;
	}

	public String getToasterBackground() {
		return toasterBackground;
	}

	public String getToasterBorderColor() {
		return toasterBorderColor;
	}

	public void setAppear(Effect appear) {
		this.appear = appear;
	}

	public void setMiddle(Effect middle) {
		this.middle = middle;
	}

	public void setFade(Effect fade) {
		this.fade = fade;
	}

	public void setLocation(ToasterLocation location) {
		this.location = location;
	}

	public void setToasterBackground(String toasterBackground) {
		this.toasterBackground = toasterBackground;
	}

	public void setToasterBorderColor(String toasterBorderColor) {
		this.toasterBorderColor = toasterBorderColor;
	}

}
