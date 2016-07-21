/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.yui.markup.html.cropp;

import java.io.Serializable;

/**
 * @author wickeria at gmail.com
 */
public class YuiImageCropperSettings implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private int top;
	private int left;
	private int height;
	private int width;
	private int keyTick;
	private int shiftKeyTick;
	private int minHeight;
	private int minWidth;
	private boolean ratio;

	private int resultImageWidth;
	private int resultImageHeight;
	private boolean resultImageSizeEditable;
	private boolean resultImageSizeNoChange;

	public static YuiImageCropperSettings getDefault(boolean ratio, int resultImageWidth, int resultImageHeight,
			boolean resultImageSizeEditable) {
		return new YuiImageCropperSettings(20, 20, 64, 64, 5, 50, 64, 64, ratio, resultImageWidth, resultImageHeight,
				resultImageSizeEditable);
	}

	public YuiImageCropperSettings() {
	}

	public YuiImageCropperSettings(int top, int left, int height, int width, int keyTick, int shiftKeyTick,
			int minHeight, int minWidth, boolean ratio, int resultImageWidth, int resultImageHeight,
			boolean resultImageSizeEditable) {
		super();
		this.top = top;
		this.left = left;
		this.height = height;
		this.width = width;
		this.keyTick = keyTick;
		this.shiftKeyTick = shiftKeyTick;
		this.minHeight = minHeight;
		this.minWidth = minWidth;
		this.ratio = ratio;
		this.resultImageWidth = resultImageWidth;
		this.resultImageHeight = resultImageHeight;
		this.resultImageSizeEditable = resultImageSizeEditable;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getKeyTick() {
		return keyTick;
	}

	public void setKeyTick(int keyTick) {
		this.keyTick = keyTick;
	}

	public int getShiftKeyTick() {
		return shiftKeyTick;
	}

	public void setShiftKeyTick(int shiftKeyTick) {
		this.shiftKeyTick = shiftKeyTick;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	public int getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	public boolean isRatio() {
		return ratio;
	}

	public void setRatio(boolean ratio) {
		this.ratio = ratio;
	}

	public int getResultImageWidth() {
		return resultImageWidth;
	}

	public void setResultImageWidth(int resultImageWidth) {
		this.resultImageWidth = resultImageWidth;
	}

	public int getResultImageHeight() {
		return resultImageHeight;
	}

	public void setResultImageHeight(int resultImageHeight) {
		this.resultImageHeight = resultImageHeight;
	}

	public boolean isResultImageSizeEditable() {
		return resultImageSizeEditable;
	}

	public void setResultImageSizeEditable(boolean resultImageSizeEditable) {
		this.resultImageSizeEditable = resultImageSizeEditable;
	}

	public boolean isResultImageSizeNoChange() {
		return resultImageSizeNoChange;
	}

	public void setResultImageSizeNoChange(boolean resultImageSizeNoChange) {
		this.resultImageSizeNoChange = resultImageSizeNoChange;
	}

}
