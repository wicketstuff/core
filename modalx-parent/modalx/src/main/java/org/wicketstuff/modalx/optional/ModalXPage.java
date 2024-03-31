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
package org.wicketstuff.modalx.optional;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValueConversionException;
import org.wicketstuff.modalx.ModalContentWindow;
import org.wicketstuff.modalx.ModalContentPanel;
import org.wicketstuff.modalx.ModalMgr;

// -[Class]-

/**
 * Class Name : ModalXPage Diagram : Optional Project : Echobase framework Type : concrete
 * Convenience class that can be used by applications as the base class for all application pages to
 * allow fast integration of ModalX. If it is not possible ot introduce this class into the Page
 * class hierarchy then simply make an existing class in the hierarchy implement the ModalMgr
 * interface and copy methods and attributes from this class to that class.
 *
 * @author Chris Colman
 */
public class ModalXPage extends WebPage implements ModalMgr
{
// -[KeepWithinClass]-


// -[Fields]-


	private static final long serialVersionUID = 1L;


	/**
	 * Array of ModalContentWindows - each stacked modal needs a unique ModalContentWindow otherwise
	 * after closing the topmost one the one beneath it becomes unusable.
	 */
	protected ModalContentWindow[] modalContentWindows;


	/**
	 * Index of the next modal window to be allocated.
	 */
	private int nextModalIdx = 0;


	/**
	 * Maximum depth of stacked modal windos supported.
	 */
	public static final int MAX_MODALS = 3;


// -[Methods]-

	/**
 *
 */
	public ModalXPage(final PageParameters iParameters) throws StringValueConversionException
	{
		super(iParameters);

		// Establish the ModalWindows
		modalContentWindows = new ModalContentWindow[MAX_MODALS];

		for (int m = 0; m < MAX_MODALS; m++)
		{
			Form<Void> form = new Form<Void>("wrapperForm_" + m);
			add(form);
			ModalContentWindow modalWindow = new ModalContentWindow(this, "modalWindow_" + m, true);
			form.add(modalWindow);
			modalContentWindows[m] = modalWindow;
		}
	}


	/**
	 * Records a visit to the modal.
	 */
	public void preShow(ModalContentPanel modalContentPanel)
	{
		// Do something prior to showing the modal - perhaps set the variation etc.,

		// To implement previous trackModalVisit behaviour call
		// modalContentPanel.createModalVisit(); and do whatever you previously did with the
		// IWicketModalVisit object that was created
	}


	/**
	 * Releases a ModalContentWindow
	 */
	public void releaseModalWindow(ModalContentWindow modalContentWindow)

	{
		nextModalIdx--;

		if (nextModalIdx < 0)
			System.out.println("release: Too many modal deallocations");

	}

	/**
	 * Allocates a ModalContentWindow.
	 */
	public ModalContentWindow allocateModalWindow()
	{
		if (nextModalIdx < 0)
			System.out.println("alloc: Too many modal deallocations");
		else if (nextModalIdx >= MAX_MODALS)
			System.out.println("alloc: Too many modal allocations");
		else
			return modalContentWindows[nextModalIdx++];

		return null;
	}

}
