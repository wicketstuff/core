package org.wicketstuff.datatables.options;

import de.agilecoders.wicket.jquery.AbstractConfig;
import de.agilecoders.wicket.jquery.IKey;
import de.agilecoders.wicket.jquery.Key;

/**
 * https://datatables.net/extensions/scroller/
 */
public class ScrollerOptions extends AbstractConfig {

	/**
	 * https://datatables.net/reference/option/scroller.loadingIndicator
	 */
	private static final IKey<Boolean> LoadingIndicator = new Key<>("loadingIndicator", false);

	/**
	 * https://datatables.net/reference/option/scroller.boundaryScale
	 */
	private static final IKey<Double> BoundaryScale = new Key<>("boundaryScale", 0.5d);

	/**
	 * https://datatables.net/reference/option/scroller.displayBuffer
	 */
	private static final IKey<Integer> DisplayBuffer = new Key<>("displayBuffer", 20);

	/**
	 * https://datatables.net/reference/option/scroller.serverWait
	 * https://datatables.net/extensions/scroller/examples/initialisation/server-side_processing.html
	 */
	private static final IKey<Integer> ServerWait = new Key<>("serverWait", 200);

	public ScrollerOptions loadingIndicator(boolean loadingIndicator) {
		put(LoadingIndicator, loadingIndicator);
		return this;
	}

	public ScrollerOptions displayBuffer(int displayBuffer) {
		put(DisplayBuffer, displayBuffer);
		return this;
	}

	public ScrollerOptions serverWait(int serverWait) {
		put(ServerWait, serverWait);
		return this;
	}
}
