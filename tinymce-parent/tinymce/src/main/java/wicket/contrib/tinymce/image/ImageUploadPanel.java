package wicket.contrib.tinymce.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.wicket.IResourceListener;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Resource;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.markup.parser.XmlTag;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.string.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Image upload panel which is responsible for showing image upload dialog and
 * its called when an image is requested.
 * @author Michal Letynski <mikel@mikel.pl>
 */
public class ImageUploadPanel extends Panel implements IResourceListener {

    private static final long serialVersionUID = -5848356532326545817L;
    private static final Logger log = LoggerFactory.getLogger(ImageUploadPanel.class);
    private static final ResourceReference IMAGE_UPLOAD_JS_RESOURCE = new JavascriptResourceReference(ImageUploadPanel.class, "imageUpload.js");

    private ModalWindow modalWindow;
    private ImageUploadBehavior imageUploadBehavior;

    public ImageUploadPanel(String pId) {
        super(pId);
        setOutputMarkupId(true);
        add(modalWindow = new ModalWindow("imageUploadDialog"));
        modalWindow.setTitle(new ResourceModel("title.label"));
        modalWindow.setInitialHeight(100);
        modalWindow.setInitialWidth(350);
        modalWindow.setWindowClosedCallback(new WindowClosedCallback() {
            public void onClose(AjaxRequestTarget pTarget) {
                resetModalContent();
            }
        });
        add(imageUploadBehavior = new ImageUploadBehavior());
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
            ImageUploadContentPanel content = new ImageUploadContentPanel(modalWindow.getContentId()) {
                @Override
                public void onImageUploaded(ImageFileDescription pImageFileDescription, AjaxRequestTarget pTarget) {
                    modalWindow.close(pTarget);
                    resetModalContent();
                    CharSequence url = ImageUploadPanel.this.urlFor(IResourceListener.INTERFACE);
                    XmlTag xmlImageTag = ImageUploadHelper.createImageTag(pImageFileDescription, url);
                    pTarget.appendJavascript("putImage('"+xmlImageTag.toString()+"');");
                }
            };
            modalWindow.setContent(content);
            // Remember cursor position - it's needed for IE
            pTarget.appendJavascript("saveBookmark();");
            modalWindow.show(pTarget);
        }

        public String getCallbackName() {
            return "showImageUploadDialog";
        }

        @Override
        public void renderHead(IHeaderResponse pResponse) {
            String script = getCallbackName() + " = function () { "
                    + getCallbackScript() + " }";
            pResponse.renderOnDomReadyJavascript(script);
            pResponse.renderJavascriptReference(IMAGE_UPLOAD_JS_RESOURCE);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void onResourceRequested() {
        final String fileName = RequestCycle.get().getRequest().getParameter(ImageUploadHelper.IMAGE_FILE_NAME);
        if (Strings.isEmpty(fileName)) {
            log.warn("There is no file name of image");
            return;
        }
        final String contentType = RequestCycle.get().getRequest().getParameter(ImageUploadHelper.IMAGE_CONTENT_TYPE);
        Resource resource = new Resource() {
            @Override
            public IResourceStream getResourceStream() {
                FileInputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(ImageUploadHelper.getTemporaryDirPath()+File.separatorChar+fileName);
                } catch(FileNotFoundException ex) {
                    log.error("Problem with getting image - " + ex.getMessage(), ex);
                    throw new RuntimeException("Problem with getting image");
                }
                return new FileResourceStream(contentType, inputStream);
            }
        };
        resource.onResourceRequested();
    }

    /**
     * @return the imageUploadBehavior
     */
    public ImageUploadBehavior getImageUploadBehavior() {
        return imageUploadBehavior;
    }
}
