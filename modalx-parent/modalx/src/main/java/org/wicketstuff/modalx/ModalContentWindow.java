// -[KeepHeading]-

// -[Copyright]-

/**
 * � 2006, 2009. Step Ahead Software Pty Ltd. All rights reserved.
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software. To
 * maintain code and model synchronization you may directly edit code in method
 * bodies and any sections starting with the 'Keep_*' marker. Make all other
 * changes via Javelin. See http://stepaheadsoftware.com for more details.
 */
package org.wicketstuff.modalx;

import java.util.Optional;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalDialog;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.io.IClusterable;

// -[Class]-

/**
 * Class Name : ModalContentWindow Diagram : Wicket Generic Modal Windows Project : Echobase
 * framework Type : concrete Specialized form of ModalWindow that knows how to interact with
 * ModelContentPanels in a way which simplifies the code that opens a modal window.
 * 
 * @author Chris Colman
 */
public class ModalContentWindow extends ModalDialog
{
	private static final long serialVersionUID = 1L;

	// -[KeepWithinClass]-
	public static ResourceReference CSS = new PackageResourceReference(ModalContentWindow.class,
		"css/modalx/ModalX.css");

	// -[Fields]-

	protected ModalMgr modalMgr;

        protected WindowClosedCallback windowClosedCallback;
        
	// -[Methods]-

	/**
	 * Describe here
	 */
	public ModalMgr getModalMgr()
	{
		return modalMgr;
	}

	/**
	 * Override in derived class to set up appropriate styles.
	 */
	public void initStyles()
	{
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.render(CssHeaderItem.forReference(CSS));
	}

	/**
	 * Constructs the object
	 */
	public ModalContentWindow(ModalMgr iModalMgr, String id, boolean maskBehind)
	{
		super(id);

		modalMgr = iModalMgr;

		// Derived classes should call seStyles in their initStyles() override
		initStyles();
	}

        
    public void setWindowClosedCallback(final WindowClosedCallback callback) {
        this.windowClosedCallback = callback;
    }

    @Override
    public ModalDialog close(AjaxRequestTarget target) {
        Optional.ofNullable(windowClosedCallback).ifPresent(w -> w.onClose(target));
        return super.close(target);
    }
    
    /**
     * Callback called after the window has been closed. If no callback instance is specified using
     * {@link BaseModal#setWindowClosedCallback(BaseModal.WindowClosedCallback)}, no ajax
     * request will be fired.
     */
    @FunctionalInterface
    public interface WindowClosedCallback extends IClusterable {

        /**
         * Called after the window has been closed.
         *
         * @param target {@link org.apache.wicket.ajax.AjaxRequestTarget} instance bound with the ajax request.
         */
        void onClose(AjaxRequestTarget target);
    }        
}
