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

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.lang.Objects;

/**
 * @author wickeria at gmail.com
 */
public abstract class CropImageModalWindow extends ModalWindow {

	private static final long serialVersionUID = 1L;
	private YuiImageCropperSettings yuiImageCropperSettings;

	public CropImageModalWindow(String id, final YuiImageCropperSettings settings) {
		super(id);
		this.yuiImageCropperSettings = settings;
		setWidthUnit("em");
		setInitialWidth(43);
		setUseInitialHeight(false);
		setCookieName("cropp-image-editor");
		setTitle(new ResourceModel("title"));
		setContent(new CropImagePanel(getContentId(), 1024, 768, (YuiImageCropperSettings) Objects
				.cloneObject(yuiImageCropperSettings)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onCropImage(InputStream is, String fileName, String mimeType, AjaxRequestTarget target) {
				close(target);
				CropImageModalWindow.this.onCropImage(is, fileName, mimeType, target);
			}

			@Override
			protected void onCancel(AjaxRequestTarget target) {
				close(target);
			}

			@Override
			protected InputStream createCrop(FileInputStream fileInputStream, Rectangle rectangle) {
				return CropImageModalWindow.this.createCrop(fileInputStream, rectangle);
			}

			@Override
			protected InputStream createThumbnail(InputStream is, int resultImageWidth, int resultImageHeight,
					boolean ratio) {
				return CropImageModalWindow.this.createThumbnail(is, resultImageWidth, resultImageHeight, ratio);
			}
		});
	}

	protected abstract InputStream createThumbnail(InputStream is, int resultImageWidth, int resultImageHeight,
			boolean ratio);

	protected abstract InputStream createCrop(FileInputStream fileInputStream, Rectangle rectangle);

	protected abstract void onCropImage(InputStream is, String fileName, String mimeType, AjaxRequestTarget target);
}
