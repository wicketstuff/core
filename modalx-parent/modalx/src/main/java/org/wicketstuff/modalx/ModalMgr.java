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


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : ModalMgr Diagram : Wicket Generic Modal Windows Project : Echobase framework Type :
 * interface Manages the allocation and deallocation of modal windows. An application implements
 * this method (typically in a base Page class) and provides for 'n' modalWindows. The markup for
 * that base class should include the appropriate number of modal window <divs>. For example if the
 * maxumum depth of modal windows was n = 2 then the base class markup should have:
 * 
 * <div wicket:id="modalWindow_0"></div> <div wicket:id="modalWindow_1"></div>
 * 
 * 
 * and the constructor of the ModalMgr implementor should contain code like this:
 * 
 * // Establish the ModalWindows
 * 
 * modalContentWindows = new ModalContentWindow[MAX_MODALS]; for (int m = 0; m < MAX_MODALS; m++) {
 * ModalContentWindow modalWindow = new PbModalWindow(this, "modalWindow_" + m, true);
 * modalWindow.setInitialWidth(600);
 * 
 * modalWindow.setInitialHeight(400);
 * 
 * body.add(modalWindow);
 * 
 * modalContentWindows[m] = modalWindow;
 * 
 * }
 * 
 * @author Chris Colman
 */
public abstract interface ModalMgr
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-


	/**
	 * Notifies the ModalMgr of that a ModelContentWindow is about to show.
	 * The method can perform work like setting the markup variation to be used for the ModalContentPanel
	 * via a call to ModalContentPanel.setVariation(String), among other things
	 *
	 * NOTE: This method replaces the old trackModalVisit(IWicketModalVisit) method as that was very specific
           * to tracking and there are more things that are likely to be required than just visit tracking so the name
           * and parameter was changed.
	 * To implement previous trackModalVisit behaviour call modalContentPanel.createModalVisit(); and do 
	 * whatever you previously did with the IWicketModalVisit object that was created
	 */
	public abstract void preShow(ModalContentPanel modalContentPanel);


	/**
	 * Releases a ModalContentWindow
	 */
	public abstract void releaseModalWindow(ModalContentWindow modalContentWindow);


	/**
	 * Allocates a ModalContentWindow.
	 */
	public abstract ModalContentWindow allocateModalWindow();

}
