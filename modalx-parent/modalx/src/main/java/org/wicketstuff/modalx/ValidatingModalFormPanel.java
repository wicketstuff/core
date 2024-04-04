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
import org.apache.wicket.markup.html.form.FormComponent;


// -[Class]-

/**
 * Class Name : ValidatingModalFormPanel Diagram : Wicket Generic Modal Windows Project : Echobase
 * framework Type : concrete Base class for all validating wicket forms.
 * 
 * @author Chris Colman
 */
public class ValidatingModalFormPanel extends ModalFormPanel
{
// -[KeepWithinClass]-


// -[Fields]-


	private static final long serialVersionUID = 1L;
	/**
	 * Stores form components.
	 */
	protected FormComponent<?>[] components;


// -[Methods]-


	/**
	 * Returns components
	 */
	public FormComponent<?>[] getDependentFormComponents()
	{
		return components;
	}

	/**
	 * Constructs the object
	 */
	public ValidatingModalFormPanel(ModalContentWindow iModalContentWindow,
		IWindowCloseListener iWindowCloseListener)
	{
		super(iModalContentWindow, iWindowCloseListener);

		components = new FormComponent<?>[1];
	}

}
