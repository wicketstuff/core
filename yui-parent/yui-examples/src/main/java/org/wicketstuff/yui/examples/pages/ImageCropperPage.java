package org.wicketstuff.yui.examples.pages;

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.cropp.CropImageModalWindow;
import org.wicketstuff.yui.markup.html.cropp.YuiImageCropperSettings;

/**
 * Example on using ImageCropper...
 * 
 * @author wickeria at gmail.com
 * 
 */
public class ImageCropperPage extends WicketExamplePage {

	private Rectangle rec;

	public ImageCropperPage() {
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		add(feedbackPanel);
		feedbackPanel.setOutputMarkupId(true);
		final CropImageModalWindow modal = new CropImageModalWindow("modal", YuiImageCropperSettings.getDefault(false,
				108, 108, true)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onCropImage(InputStream is, String fileName, String mimeType, AjaxRequestTarget target) {
				// AlbumFolderNode folderNode = (AlbumFolderNode)
				// AlbumEditTab.this.getDefaultModelObject();
				// try {
				// folderNode.setPreviewImage(new BinaryImpl(is));
				// folderNode.getSession().save();
				// } catch (IOException e) {
				// throw new RuntimeException(e);
				// }
				// target.addComponent(AlbumEditTab.this);
				info(rec.toString());
				target.addComponent(feedbackPanel);
			}

			@Override
			protected InputStream createCrop(FileInputStream fileInputStream, Rectangle rectangle) {
				// return ImageMagicProcessor.createCrop(fileInputStream,
				// rectangle);
				rec = rectangle;
				return fileInputStream;
			}

			@Override
			protected InputStream createThumbnail(InputStream is, int resultImageWidth, int resultImageHeight,
					boolean ratio) {
				// return ImageMagicProcessor.createThumbnail(is,
				// resultImageWidth, resultImageHeight, ratio);
				return is;
			}
		};
		add(modal);
		AjaxLink<Void> changePrevLink = new AjaxLink<Void>("changePrevLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modal.show(target);
			}
		};
		add(changePrevLink);

	}
}
