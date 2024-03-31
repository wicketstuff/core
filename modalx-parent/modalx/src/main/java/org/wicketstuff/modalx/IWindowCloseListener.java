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
 * Class Name : IWindowCloseListener Diagram : Wicket Generic Modal Windows Project : Echobase
 * framework Type : interface Interface for listeners of the window close event. A window close
 * event is generated whenever a ModalContentWindow is closed. If you have a parent page, panel or
 * form that you wish to be notified when a child ModalForm is closed make that parent implement
 * IWindowCloseListener and pass it as the IWindowCloseListener parameter in the constructor.
 * 
 * @author Chris Colman
 */
public abstract interface IWindowCloseListener
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-


	/**
	 * Called when the ModalContentWindow is closed.
	 */
	public abstract void windowClosed(Panel panel, AjaxRequestTarget target);

}
