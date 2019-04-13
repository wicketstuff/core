package ro.nextreports.jofc2.model.elements;

import ro.nextreports.jofc2.model.metadata.Alias;
import ro.nextreports.jofc2.model.metadata.Converter;
import ro.nextreports.jofc2.util.OnShowConverter;

import java.io.Serializable;

/**
 * Adds the ability to disable animation on show for an animated chart, as
 * explained here: http://www.ofc2dz.com/OFC2/downloads/ReleaseNotes-Ichor.html
 * 
 * ------------------------------------------------------------- 22.09.2011
 * Patched with 'delay' and'cascade' fields for OnShow animation Mihai
 * Dinca-Panaitescu
 * -------------------------------------------------------------
 */
public abstract class AnimatedElement extends Element {
	@Alias("on-show")
	private OnShow onShow;

	protected AnimatedElement(String type) {
		super(type);
		// disable the animation by default
		useAnimation(false);
	}

	public OnShow getOnShow() {
		return onShow;
	}

	public void setOnShow(OnShow onShow) {
		this.onShow = onShow;
	}

	public void useAnimation(boolean state) {
		setOnShow(state ? null : new OnShow(""));
	}

	/**
	 * this class has no real content but has the purpose of generating the JSON
	 * string <code>{"type":""}</code> as explained <a
	 * href="http://www.ofc2dz.com/OFC2/downloads/ReleaseNotes-Ichor.html"
	 * >here</a>
	 */
	@Converter(OnShowConverter.class)
	public static class OnShow implements Serializable {
		private String type;
		private int delay = 0;
		private int cascade = 0;

		public OnShow(String type) {
			this.type = type;
		}

		public OnShow(String type, int delay, int cascade) {
			this.type = type;
			this.delay = delay;
			this.cascade = cascade;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getDelay() {
			return delay;
		}

		public void setDelay(int delay) {
			this.delay = delay;
		}

		public int getCascade() {
			return cascade;
		}

		public void setCascade(int cascade) {
			this.cascade = cascade;
		}
	}
}
