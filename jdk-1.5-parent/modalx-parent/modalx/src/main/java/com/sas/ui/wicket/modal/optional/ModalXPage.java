// -[KeepHeading]-


// -[Copyright]-

/**
 * © 2006, 2009. Step Ahead Software Pty Ltd. All rights reserved.
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software.
 * To maintain code and model synchronization you may directly edit code in method bodies
 * and any sections starting with the 'Keep_*' marker. Make all other changes via Javelin.
 * See http://stepaheadsoftware.com for more details.
 */
package com.sas.ui.wicket.modal.optional;

import java.lang.*;
import com.sas.ui.wicket.modal.ModalMgr;
import org.apache.wicket.markup.html.WebPage;
    
import com.sas.ui.wicket.modal.IWicketModalVisit;
    
import com.sas.ui.wicket.modal.ModalContentWindow;


// -[KeepBeforeClass]-
import org.apache.wicket.PageParameters;
import org.apache.wicket.util.string.StringValueConversionException;


// -[Class]-

/**
 * Class Name : ModalXPage
 * Diagram    : Optional
 * Project    : Echobase framework
 * Type       : concrete
 * Convenience class that can be used by applications as the base class for all application
 * pages to allow fast integration of ModalX. If it is not possible ot introduce this
 * class into the Page class hierarchy then simply make an existing class in the hierarchy
 * implement the ModalMgr interface and copy methods and attributes from this class to
 * that class.
 * 
 * @author Chris Colman
 */
public 
class ModalXPage extends WebPage implements ModalMgr
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * Array of ModalContentWindows - each stacked modal needs a unique ModalContentWindow
 * otherwise after closing the topmost one the one beneath it becomes unusable.
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
public ModalXPage(final PageParameters iParameters)
  throws StringValueConversionException
{
	super(iParameters);

	// Establish the ModalWindows
	modalContentWindows = new ModalContentWindow[MAX_MODALS];
	
	for (int m = 0; m < MAX_MODALS; m++)
	{
		ModalContentWindow modalWindow = new ModalContentWindow(this, "modalWindow_" + m, true);
		modalWindow.setInitialWidth(600);
		modalWindow.setInitialHeight(400);
		add(modalWindow);
		modalContentWindows[m] = modalWindow;
	}
}



/**
 * Records a visit to the modal.
 */
public void trackModalVisit(IWicketModalVisit iWicketModalVisit)

{
	if ( iWicketModalVisit != null )
	{
		// A valid IWicketModalVisit object has been passed so record it
		// Create a tracking object if applicable
	}
}



/**
 * Releases a ModalContentWindow
 */
public void releaseModalWindow(ModalContentWindow modalContentWindow)

{
	nextModalIdx--;
	
	if ( nextModalIdx < 0 )
		System.out.println("release: Too many modal deallocations");
	
}

/**
 * Allocates a ModalContentWindow.
 */
public ModalContentWindow allocateModalWindow()
{
	if ( nextModalIdx < 0 )
		System.out.println("alloc: Too many modal deallocations");
	else if ( nextModalIdx >= MAX_MODALS )
		System.out.println("alloc: Too many modal allocations");
	else
		return modalContentWindows[nextModalIdx++];
		
	return null;
}

}


