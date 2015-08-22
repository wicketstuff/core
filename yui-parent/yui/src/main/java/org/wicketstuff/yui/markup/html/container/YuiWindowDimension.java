/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.yui.markup.html.container;

/**
 * Immutable window dimensions with optional position.
 * 
 * @author Erik van Oosten
 */
public class YuiWindowDimension {
	private int[] topLeft;
	private int[] widthHeight;

	/**
	 * Constructor with width, height and position.
	 *
	 * @param top position of the window in pixels from the top
	 * @param left position of the window in pixels from the left
	 * @param width width of the window in pixels
	 * @param height height of the window in pixels
	 */
	public YuiWindowDimension(int top, int left, int width, int height) {
		this.topLeft = new int[] { top, left };
		this.widthHeight = new int[] { width, height };
	}

	/**
	 * Constructor with width and height (and default position).
	 * 
	 * @param width width of the window in pixels
	 * @param height height of the window in pixels
	 */
	public YuiWindowDimension(int width, int height) {
		this.topLeft = null;
		this.widthHeight = new int[] { width, height };
	}

	public int[] getTopLeft() {
		return topLeft;
	}

	public int[] getWidthHeight() {
		return widthHeight;
	}

}
