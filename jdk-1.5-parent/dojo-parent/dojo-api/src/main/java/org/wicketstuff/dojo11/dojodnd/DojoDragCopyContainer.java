package org.wicketstuff.dojo11.dojodnd;

import org.wicketstuff.dojo11.dojodnd.DojoDragContainer;

/**
 * <p>
 * A container that creates drag sources, but the sources are copied
 * when they are dropped.
 * </p>
 *
 * @author B. Molenkamp
 * @version SVN: $Id$
 * @deprecated use DojoCopyContainer instead
 */
@SuppressWarnings("serial")
@Deprecated
public abstract class DojoDragCopyContainer extends DojoDragContainer {
	
	/**
	 * Constructor of a drag copy container
	 * 
	 * @param id
	 *            widget id
	 */
	public DojoDragCopyContainer(String id) {
		super(id, true);
	}
}
