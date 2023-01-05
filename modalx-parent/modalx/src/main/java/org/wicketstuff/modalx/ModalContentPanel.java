// -[KeepHeading]-


// -[Copyright]-

/**
 * � 2006, 2009. Step Ahead Software Pty Ltd. All rights reserved.
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software.
 * To maintain code and model synchronization you may directly edit code in method bodies
 * and any sections starting with the 'Keep_*' marker. Make all other changes via Javelin.
 * See http://stepaheadsoftware.com for more details.
 */
package org.wicketstuff.modalx;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalDialog;
import org.apache.wicket.markup.html.panel.Panel;


// -[Class]-

/**
 * Class Name : ModalContentPanel Diagram : Wicket Generic Modal Windows Project : Echobase
 * framework Type : concrete Base class for all panels that are to be displayed in a
 * ModalContentWindow.
 * 
 * @author Chris Colman
 */
public class ModalContentPanel extends Panel
{
// -[KeepWithinClass]-

	// Varation which can be configured during by ModalMgr during call to
	// ModalMgr.preShow(ModalContentPanel)
	private String variation;

// -[Fields]-


	private static final long serialVersionUID = 1L;

	protected IWindowCloseListener windowCloseListener;

	protected ModalContentWindow modalContentWindow;


// -[Methods]-

	public String getVariation()
	{
		if ( variation != null )
			return variation;
		return super.getVariation();
	}
	
	public void setVariation(String variation)
	{
		this.variation = variation;
	}

	/**
	 * Closes the modal windows that contains this panel.
	 */
	public void closeModal(AjaxRequestTarget target)
	{
		modalContentWindow.close(target);
	}

	/**
	 * Get the ModalContentWindow to show this ModalContentPanel.
	 */
	public void show(AjaxRequestTarget target)
	{
		// no reason why we can't auto assign the content panel's content ID from the modal window
		modalContentWindow.setContent(this);

		// Set up the callback mechanism if we have a valid windowCloseListener
		if (windowCloseListener != null)
		{
			modalContentWindow.setWindowClosedCallback(new ModalContentWindow.WindowClosedCallback()
			{
				private static final long serialVersionUID = 1L;

                                @Override
				public void onClose(AjaxRequestTarget target)
				{
					ModalMgr modalMgr = modalContentWindow.getModalMgr();
					if (modalMgr != null)
						modalMgr.releaseModalWindow(modalContentWindow);
					windowCloseListener.windowClosed(ModalContentPanel.this, target);
				}
			});
		}
		else
		{
			// No listener provided so need to set up close callback differently
			modalContentWindow.setWindowClosedCallback(new ModalContentWindow.WindowClosedCallback()
			{
				private static final long serialVersionUID = 1L;

                                @Override
				public void onClose(AjaxRequestTarget target)
				{
					ModalMgr modalMgr = modalContentWindow.getModalMgr();
					if (modalMgr != null)
						modalMgr.releaseModalWindow(modalContentWindow);
				}
			});
		}

		// Notifies the ModalMgr that a ModalContentPanel is about to be opened in a ModalContentWindow
		// ModalMgr can do things like set the variation if required or register the fact that the modal is being
		// opened in some traffic tracking system
		if (modalContentWindow.getModalMgr() != null)
			modalContentWindow.getModalMgr().preShow(this);
		
		modalContentWindow.open(target);
	}

	/**
	 * Constructs a ModalContentPanel: iModalContentWindow must be a reference to the
	 * ModalContentWindow for which this object will be the content. iWindowCloseListener, if not
	 * null, will be notified when the modal window is closed.
	 */
	public ModalContentPanel(ModalContentWindow iModalContentWindow,
		IWindowCloseListener iWindowCloseListener)
	{
		super(ModalDialog.CONTENT_ID);

		modalContentWindow = iModalContentWindow;

		windowCloseListener = iWindowCloseListener;
	}

}
