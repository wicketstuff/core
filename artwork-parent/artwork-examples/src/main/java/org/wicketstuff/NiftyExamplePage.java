package org.wicketstuff;

import org.apache.wicket.PageParameters;
import org.wicketstuff.artwork.niftycorners.NiftyCornersBehavior;
import org.wicketstuff.artwork.niftycorners.NiftyOption;

/**
 * 
 */
public class NiftyExamplePage extends ArtworkParentExamplePage {

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public NiftyExamplePage(final PageParameters parameters) {
		super(parameters);
		add(new NiftyCornersBehavior("ul#nav a,ul#nav span", false, false,  NiftyOption.small, NiftyOption.transparent, NiftyOption.top));
		
	}
}
