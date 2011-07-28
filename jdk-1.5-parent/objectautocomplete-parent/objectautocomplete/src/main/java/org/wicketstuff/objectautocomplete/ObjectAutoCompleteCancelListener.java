package org.wicketstuff.objectautocomplete;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Internal listener interface used for the behaviour to callback in case the user cancelled the
 * input via 'escape'
 * 
 * @author roland
 * @since May 24, 2008
 */
interface ObjectAutoCompleteCancelListener
{

	/**
	 * Autocompletion searched has been cancelled on request of the user (i.e. by pressing 'escape')
	 * 
	 * @param pTarget
	 *            target to add components to add to for updating
	 * @param pForceRestore
	 *            if the old content should be always restored or whether an empty input clears the
	 *            field
	 */
	void searchCanceled(AjaxRequestTarget pTarget, boolean pForceRestore);
}
