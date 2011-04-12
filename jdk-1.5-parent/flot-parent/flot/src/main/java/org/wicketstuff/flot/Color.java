/*
 * Copyright 2009 Michael Würtinger (mwuertinger@users.sourceforge.net)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.flot;

import java.io.Serializable;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Color implements Serializable
{
	/** Required by {@link Serializable} */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(Color.class);

	public static final Color BLACK = new Color(0.0, 0.0, 0.0);
	public static final Color WHITE = new Color(1.0, 1.0, 1.0);
	public static final Color BLUE = new Color(0.0, 0.0, 1.0);
	public static final Color GREEN = new Color(0.0, 1.0, 0.0);
	public static final Color RED = new Color(1.0, 0.0, 0.0);

	private float r, b, g;

	public Color(int r, int g, int b)
	{
		if (r < 0 || g < 0 || b < 0 || r > 255 || g > 255 || b > 255)
			throw new IllegalArgumentException(
				"Arguments have to be between 0 (inclusive) and 256 (exclusive)");
		this.r = (float)(r / 255.0);
		this.g = (float)(g / 255.0);
		this.b = (float)(b / 255.0);
	}

	public Color(double r, double g, double b)
	{
		if (r < 0.0 || g < 0.0 || b < 0.0 || r > 1.0 || g > 1.0 || b > 1.0)
			throw new IllegalArgumentException(
				"Arguments have to be between 0 (inclusive) and 1.0 (inclusive)");
		this.r = (float)r;
		this.g = (float)g;
		this.b = (float)b;
	}

	public float red()
	{
		return r;
	}

	public float green()
	{
		return g;
	}

	public float blue()
	{
		return b;
	}

	public String html()
	{
		return String.format("#%02X%02X%02X", (int)(r * 255.0), (int)(g * 255.0), (int)(b * 255.0));
	}

	@Override
	public String toString()
	{
		return String.format("rgb(%.1f, %.1f, %.1f), html(%s)", r, g, b, html());
	}

	public static Color random()
	{
		Random random = new Random();
		Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
		return color;
	}

	/**
	 * Crops the value to the specified range.
	 * 
	 * @param min
	 *            The minimum value of the range.
	 * @param max
	 *            The maximum value of the range.
	 * @param value
	 *            A value which has to be cropped.
	 * @return cropped value.
	 */
	private static float limit(float min, float max, float value)
	{
		return (float)Math.max(0.0, Math.min(max, value));
	}

	/**
	 * Returns a copy of this color the values of which are multiplied with the according factors
	 * and cropped to 0..1.
	 * 
	 * @param fr
	 *            The factor for red.
	 * @param fg
	 *            The factor for green.
	 * @param fb
	 *            The factor for blue.
	 * @return scaled copy.
	 */
	public Color scale(float fr, float fg, float fb)
	{
		float r = limit(0, 1, this.r * fr);
		float g = limit(0, 1, this.g * fg);
		float b = limit(0, 1, this.b * fb);

		return new Color(r, g, b);
	}
}
