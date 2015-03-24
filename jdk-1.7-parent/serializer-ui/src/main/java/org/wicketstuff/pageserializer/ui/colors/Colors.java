package org.wicketstuff.pageserializer.ui.colors;

import java.awt.Color;
import java.awt.color.ColorSpace;

public abstract class Colors {

	private Colors() {
		// no instance
	}

	public static Color colorFromHashcode(int hashValue, int scale,HSB hsb) {
		long value = hashValue;
		long divisor = scale;
		if (value > divisor) {
			throw new IllegalArgumentException("hashValue(" + hashValue + ")>scale(" + scale + ")");
		}
		long clusters = 100;
		long clusteredValue = value * clusters / divisor;
		float hue = clusteredValue * 1.0f / clusters;

		Color ret = Color.getHSBColor(hue+hsb.hue(), hsb.saturation(), hsb.brightness());
		return ret;
	}

	public static HSB hsb(float hue, float saturation, float brightness) {
		return new HSB(hue,saturation,brightness);
	}
	
	public static class HSB {

		final float _hue;
		final float _saturation;
		final float _brightness;

		public HSB(float hue, float saturation, float brightness) {
			_hue = hue;
			_saturation = saturation;
			_brightness = brightness;
		}

		public float hue() {
			return _hue;
		}

		public float saturation() {
			return _saturation;
		}

		public float brightness() {
			return _brightness;
		}
	}
}