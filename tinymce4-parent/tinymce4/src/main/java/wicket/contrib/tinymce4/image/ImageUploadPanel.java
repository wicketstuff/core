package wicket.contrib.tinymce4.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.apache.wicket.Component;
import org.apache.wicket.IResourceListener;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.parser.XmlTag;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Image upload panel which is responsible for showing image upload dialog and
 * its called when an image is requested.
 * 
 * @author Michal Letynski <mikel@mikel.pl>
 */
public class ImageUploadPanel extends Panel implements IResourceListener {

	private static final long serialVersionUID = -5848356532326545817L;
	private static final Logger log = LoggerFactory
			.getLogger(ImageUploadPanel.class);
	private static final ResourceReference IMAGE_UPLOAD_JS_RESOURCE = new JavaScriptResourceReference(
			ImageUploadPanel.class, "imageUpload.js");
	private static final ResourceReference IMAGE_UPLOAD_CSS_RESOURCE = new CssResourceReference(
			ImageUploadPanel.class, "imageUpload.css");

	private ModalWindow modalWindow;
	private ImageUploadBehavior imageUploadBehavior;
	private final String uploadFolderPath;

	public ImageUploadPanel(String pId, String uploadFolderPath) {
		super(pId);
		
		setOutputMarkupId(true);
		add(modalWindow = new ModalWindow("imageUploadDialog"));
		modalWindow.setTitle(new ResourceModel("title.label"));
		modalWindow.setInitialHeight(100);
		modalWindow.setInitialWidth(350);
		modalWindow.setWindowClosedCallback(new WindowClosedCallback() {
			private static final long serialVersionUID = 1L;

            @Override
			public void onClose(AjaxRequestTarget pTarget) {
				resetModalContent();
			}
		});
		add(imageUploadBehavior = new ImageUploadBehavior());
		this.uploadFolderPath = uploadFolderPath;
	}
	
	public ImageUploadPanel(String pId) {
		this(pId,  ImageUploadHelper.getTemporaryDirPath());
	}

	public void resetModalContent() {
		modalWindow.setContent(new EmptyPanel(modalWindow.getContentId()));
	}

	/**
	 * Behavior responsible for showing application dialog.
	 */
	public class ImageUploadBehavior extends AbstractDefaultAjaxBehavior {
		private static final long serialVersionUID = 7786779421116467886L;

		@Override
		protected void respond(AjaxRequestTarget pTarget) {
			ImageUploadContentPanel content = new ImageUploadContentPanel(
					modalWindow.getContentId(), uploadFolderPath) {
				private static final long serialVersionUID = 1L;

				@Override
				public void onImageUploaded(
						ImageFileDescription pImageFileDescription,
						AjaxRequestTarget pTarget) {
					modalWindow.close(pTarget);
					resetModalContent();
					CharSequence url = ImageUploadPanel.this
							.urlFor(IResourceListener.INTERFACE, null);
					XmlTag xmlImageTag = ImageUploadHelper.createImageTag(
							pImageFileDescription, url);
					pTarget.appendJavaScript("putImage('"
							+ xmlImageTag.toString() + "');");
				}
			};
			modalWindow.setContent(content);
			// Remember cursor position - it's needed for IE
			pTarget.appendJavaScript("saveBookmark();");
			modalWindow.show(pTarget);
		}

		public String getCallbackName() {
			return "showImageUploadDialog";
		}

		@Override
		public void renderHead(Component c, IHeaderResponse pResponse) {
			String script = getCallbackName() + " = function () { if(isImgSelected())openImageForm(); else "
					+ getCallbackScript() + "; }";
			pResponse.render(OnDomReadyHeaderItem.forScript(script));
			pResponse.render(JavaScriptHeaderItem
					.forReference(IMAGE_UPLOAD_JS_RESOURCE));
			pResponse.render(CssHeaderItem
					.forReference(IMAGE_UPLOAD_CSS_RESOURCE));
		}
	}

	/**
	 * {@inheritDoc}
	 */
    @Override
	public void onResourceRequested() {
		final String fileName = RequestCycle.get().getRequest()
				.getQueryParameters()
				.getParameterValue(ImageUploadHelper.IMAGE_FILE_NAME)
				.toString();
		if (Strings.isEmpty(fileName)) {
			log.warn("There is no file name of image");
			return;
		}
		final String contentType = RequestCycle.get().getRequest()
				.getQueryParameters()
				.getParameterValue(ImageUploadHelper.IMAGE_CONTENT_TYPE)
				.toString();

		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(uploadFolderPath + File.separatorChar + fileName);
		} catch (FileNotFoundException ex) {
			log.error("Problem with getting image - " + ex.getMessage(), ex);
			throw new RuntimeException("Problem with getting image");
		}
		RequestCycle.get().scheduleRequestHandlerAfterCurrent(
				new ResourceStreamRequestHandler(new FileResourceStream(
						contentType, inputStream)));
	}

	/**
	 * @return the imageUploadBehavior
	 */
	public ImageUploadBehavior getImageUploadBehavior() {
		return imageUploadBehavior;
	}
}
