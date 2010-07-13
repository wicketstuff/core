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
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.WicketAjaxReference;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WicketEventReference;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.DynamicImageResource;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.io.IOUtils;
import org.wicketstuff.yui.inc.YUI;

/**
 * @author wickeria at gmail.com
 */
public abstract class CropImagePanel extends Panel implements IHeaderContributor {

	private static final long serialVersionUID = 1L;
	private WebMarkupContainer refresableContainer;
	private YuiImageCropperSettings settings;
	private int prevImgWidth;
	private int prevImgHeight;
	private String fileName;
	private String mimeType;

	public CropImagePanel(String id, int prevImgWidth, int prevImgHeight, YuiImageCropperSettings cropperSettings) {
		super(id);
		this.settings = cropperSettings;
		this.prevImgWidth = prevImgWidth;
		this.prevImgHeight = prevImgHeight;
	}

	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
		removeAll();
		refresableContainer = new WebMarkupContainer("wmc");
		add(refresableContainer.setOutputMarkupId(true));
		refresableContainer.add(new Upload("panel", this));
	}

	protected abstract void onCropImage(InputStream is, String fileName, String mimeType, AjaxRequestTarget target);

	protected abstract void onCancel(AjaxRequestTarget target);

	protected abstract InputStream createThumbnail(InputStream is, int resultImageWidth, int resultImageHeight,
			boolean ratio);

	protected abstract InputStream createCrop(FileInputStream fileInputStream, Rectangle rectangle);

	private class Upload extends Fragment {

		private static final long serialVersionUID = 1L;

		public Upload(final String id, final MarkupContainer markupProvider) {
			super(id, "upload", markupProvider);
			setOutputMarkupId(true);
			UploadForm uploadForm = new UploadForm("form") {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onUploaded(File file, AjaxRequestTarget target) {
					Upload.this.replaceWith(new Crop(id, markupProvider, file));
					target.addComponent(refresableContainer);
				};
			};
			add(uploadForm);
		}
	}

	private class Crop extends Fragment {

		private static final long serialVersionUID = 1L;

		public Crop(String id, MarkupContainer markupProvider, final File file) {
			super(id, "crop", markupProvider);
			setOutputMarkupId(true);
			NonCachingImage image = new NonCachingImage("img", new DynamicImageResource() {

				private static final long serialVersionUID = 1L;

				@Override
				protected byte[] getImageData() {
					try {
						return IOUtils.toByteArray(new FileInputStream(file));
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});
			image.setOutputMarkupId(true);
			add(image);
			final ImageCropper imageCropper = new ImageCropper("cropperImage", new Model<String>(image.getMarkupId()),
					settings) {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onCrop(int top, int left, int height, int width, AjaxRequestTarget target) {
					Rectangle rectangle = new Rectangle(left, top, height, width);
					try {
						InputStream is;
						try {
							is = createCrop(new FileInputStream(file), rectangle);
							InputStream is2 = is;
							if (!settings.isResultImageSizeNoChange()) {
								is2 = createThumbnail(is, settings.getResultImageWidth(), settings
										.getResultImageHeight(), settings.isRatio());
							}
							onCropImage(is2, fileName, mimeType, target);
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					} finally {
						Files.remove(file);
					}
				}

				@Override
				protected void onCancel(AjaxRequestTarget target) {
					CropImagePanel.this.onCancel(target);
				}
			};
			add(imageCropper);
		}
	}

	private class UploadForm extends Form<Void> {

		private static final long serialVersionUID = 1L;

		public UploadForm(String id) {
			super(id);
			setMultiPart(true);
			final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
			feedbackPanel.setOutputMarkupId(true);
			add(feedbackPanel);
			final FileUploadField fileUploadField = new FileUploadField("fileUploadField");
			fileUploadField.setRequired(true);
			add(fileUploadField);
			final IModel<Integer> resultSizeModel = new Model<Integer>(settings.getResultImageWidth());
			final TextField<Integer> resultSize = new TextField<Integer>("resultSize", resultSizeModel, Integer.class) {

				private static final long serialVersionUID = 1L;

				@Override
				public boolean isEnabled() {
					return !settings.isResultImageSizeNoChange() && settings.isResultImageSizeEditable();
				};
			};
			resultSize.setOutputMarkupId(true);
			add(resultSize.setRequired(true));
			add(new AjaxCheckBox("resultImageSizeNoChange", new Model<Boolean>(settings.isResultImageSizeNoChange())) {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					settings.setResultImageSizeNoChange(!settings.isResultImageSizeNoChange());
					target.addComponent(resultSize);
				}

				@Override
				public boolean isEnabled() {
					return settings.isResultImageSizeEditable();
				};

			});
			add(new AjaxSubmitLink("submit") {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					try {
						settings.setResultImageWidth(resultSizeModel.getObject());
						settings.setResultImageHeight(resultSizeModel.getObject());
						FileUpload fileUpload = fileUploadField.getFileUpload();
						fileName = fileUpload.getClientFileName();
						mimeType = fileUpload.getContentType();
						InputStream inputStream = createThumbnail(fileUpload.getInputStream(), prevImgWidth,
								prevImgHeight, false);
						File file = File.createTempFile(UUID.randomUUID().toString(), null);
						Files.writeTo(file, inputStream);
						onUploaded(file, target);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}

				@Override
				protected void onError(AjaxRequestTarget target, Form<?> form) {
					target.addComponent(feedbackPanel);
				}

			});
			add(new AjaxLink<Void>("cancel") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					onCancel(target);
				}
			});
		}

		protected void onUploaded(File file, AjaxRequestTarget target) {
		}
	}

	private static final JavascriptResourceReference JS_YAHOO = new JavascriptResourceReference(YUI.class,
			"2.8.1/yahoo/yahoo-min.js");
	private static final JavascriptResourceReference JS_EVENT = new JavascriptResourceReference(YUI.class,
			"2.8.1/event/event-min.js");
	private static final JavascriptResourceReference JS_DOM = new JavascriptResourceReference(YUI.class,
			"2.8.1/dom/dom-min.js");
	private static final JavascriptResourceReference JS_DRAGDROP = new JavascriptResourceReference(YUI.class,
			"2.8.1/dragdrop/dragdrop-min.js");
	private static final JavascriptResourceReference JS_ELEMENT = new JavascriptResourceReference(YUI.class,
			"2.8.1/element/element-min.js");
	private static final JavascriptResourceReference JS_RESIZE = new JavascriptResourceReference(YUI.class,
			"2.8.1/resize/resize-min.js");
	private static final JavascriptResourceReference JS_IMAGECROPPER = new JavascriptResourceReference(YUI.class,
			"2.8.1/imagecropper/imagecropper-min.js");
	private static final CompressedResourceReference CSS_RESIZE = new CompressedResourceReference(YUI.class,
			"2.8.1/assets/skins/sam/resize.css");
	private static final CompressedResourceReference CSS_IMAGECROPPER = new CompressedResourceReference(YUI.class,
			"2.8.1/assets/skins/sam/imagecropper.css");

	/**
	 * {@inheritDoc}
	 */
	public void renderHead(IHeaderResponse response) {
		response.renderJavascriptReference(WicketEventReference.INSTANCE);
		response.renderJavascriptReference(WicketAjaxReference.INSTANCE);
		if (includeYUILibraries()) {
			response.renderJavascriptReference(JS_YAHOO);
			response.renderJavascriptReference(JS_DOM);
			response.renderJavascriptReference(JS_EVENT);
			response.renderJavascriptReference(JS_DRAGDROP);
			response.renderJavascriptReference(JS_ELEMENT);
			response.renderJavascriptReference(JS_RESIZE);
			response.renderJavascriptReference(JS_IMAGECROPPER);
			response.renderCSSReference(CSS_RESIZE);
			response.renderCSSReference(CSS_IMAGECROPPER);
		}
	}

	/**
	 * Controls whether or not datepicker will contribute YUI libraries to the
	 * page as part of its rendering lifecycle.
	 * 
	 * There may be cases when the user wants to use their own version of YUI
	 * contribution code, in those cases this method should be overridden to
	 * return <code>false</code>.
	 * 
	 * @return
	 */
	protected boolean includeYUILibraries() {
		return true;
	}

}
