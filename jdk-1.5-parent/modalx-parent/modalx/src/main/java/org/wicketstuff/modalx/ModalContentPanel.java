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
import org.apache.wicket.markup.html.panel.Panel;


// -[Class]-

/**
 * Class Name : ModalContentPanel
 * Diagram    : Wicket Generic Modal Windows
 * Project    : Echobase framework
 * Type       : concrete
 * Base class for all panels that are to be displayed in a ModalContentWindow.
 * 
 * @author Chris Colman
 */
public 
class ModalContentPanel extends Panel
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * The title to be shown in the ModalContentWindow's caption.
 */
private String title;



/**
 * Initial width of the form.
 */
private int initialWidth = 300;



/**
 * Initial height of the form.
 */
private int initialHeight = 300;
    
    protected IWindowCloseListener windowCloseListener;
    
    protected ModalContentWindow modalContentWindow;


// -[Methods]-

/**
 * Creates an IWicketModalVisit.
 * Override this in derived Modal's to create a different class of WicketModalVisit for
 * each type of modal you want to track.
 */
public IWicketModalVisit createModalVisit()
{
	return null;
}




/**
 * Closes the modal windows that contains this panel.
 */
public void closeModal(AjaxRequestTarget target)
{
	modalContentWindow.close(target);
}

/**
 * Sets initialHeight
 */
public void setInitialHeight(int initialHeight)
{
    this.initialHeight = initialHeight;
}

/**
 * Returns initialHeight
 */
public int getInitialHeight()
{
    return initialHeight;
}

/**
 * Sets initialWidth
 */
public void setInitialWidth(int initialWidth)
{
    this.initialWidth = initialWidth;
}

/**
 * Returns initialWidth
 */
public int getInitialWidth()
{
    return initialWidth;
}

/**
 * Sets title
 */
public void setTitle(String title)
{
    this.title = title;
}

/**
 * Returns title
 */
public String getTitle()
{
    return title;
}

/**
 * Get the ModalContentWindow to show this ModalContentPanel.
 */
public void show(AjaxRequestTarget target)
{
	// no reason why we can't auto assign the content panel's content ID from the modal window
	modalContentWindow.setContent(this);
	modalContentWindow.setTitle(title);
	modalContentWindow.setInitialWidth(initialWidth);
	modalContentWindow.setInitialHeight(initialHeight);
	
	// Set up the callback mechanism if we have a valid windowCloseListener 
	if ( windowCloseListener != null )
	{
		modalContentWindow.setWindowClosedCallback
		(
			new ModalContentWindow.WindowClosedCallback()
			{
				public void onClose(AjaxRequestTarget target)
				{
					ModalMgr modalMgr = modalContentWindow.getModalMgr();
					if ( modalMgr != null )
						modalMgr.releaseModalWindow(modalContentWindow);
					windowCloseListener.windowClosed(ModalContentPanel.this, target);
				}
			}
		);
	}
	else
	{
		// No listener provided so need to set up close callback differently
		modalContentWindow.setWindowClosedCallback
		(
			new ModalContentWindow.WindowClosedCallback()
			{
				public void onClose(AjaxRequestTarget target)
				{
					ModalMgr modalMgr = modalContentWindow.getModalMgr();
					if ( modalMgr != null )
						modalMgr.releaseModalWindow(modalContentWindow);
				}
			}
		);
	}
	
	modalContentWindow.show(target);

	// Notifies the ModalMgr that a modal has been visited
	if ( modalContentWindow.getModalMgr() != null )
		modalContentWindow.getModalMgr().trackModalVisit(createModalVisit());
}

/**
 * Constructs a ModalContentPanel:
 * iModalContentWindow must be a reference to the ModalContentWindow for which this object
 * will be the content.
 * iWindowCloseListener, if not null, will be notified when the modal window is closed.
 */
public ModalContentPanel(ModalContentWindow iModalContentWindow, IWindowCloseListener iWindowCloseListener)
{
	super(iModalContentWindow.getContentId());

	modalContentWindow = iModalContentWindow;

	windowCloseListener = iWindowCloseListener;
}

}


