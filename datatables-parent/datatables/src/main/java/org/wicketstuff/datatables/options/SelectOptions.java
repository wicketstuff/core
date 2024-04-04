package org.wicketstuff.datatables.options;

import de.agilecoders.wicket.jquery.AbstractConfig;
import de.agilecoders.wicket.jquery.IKey;
import de.agilecoders.wicket.jquery.Key;

import java.util.Locale;

/**
 * https://datatables.net/extensions/select
 */
public class SelectOptions extends AbstractConfig {

	public enum Style {
		Api,
		Single,
		Multi,
		OS
	}

	public enum Items {
		Row,
		Column,
		Cell
	}

	/**
	 * https://datatables.net/reference/option/select.style
	 */
	private static final IKey<String> _Style = new Key<>("style", null);

	/**
	 * https://datatables.net/reference/option/select.items
	 */
	private static final IKey<String> _Items = new Key<>("items", Items.Row.name().toLowerCase(Locale.ENGLISH));

	/**
	 * https://datatables.net/reference/option/select.blurable
	 */
	private static final IKey<Boolean> Blurable = new Key<>("blurable", false);

	/**
	 * https://datatables.net/reference/option/select.selector
	 */
	private static final IKey<String> Selector = new Key<>("selector", "th, td");

	/**
	 * https://datatables.net/reference/option/scroller.serverWait
	 * https://datatables.net/extensions/scroller/examples/initialisation/server-side_processing.html
	 */
	private static final IKey<Integer> ServerWait = new Key<>("serverWait", 200);

	public SelectOptions style(Style style) {
		put(_Style, style.name().toLowerCase(Locale.ENGLISH));
		return this;
	}

	public SelectOptions blurable(boolean blurable) {
		put(Blurable, blurable);
		return this;
	}

	public SelectOptions items(Items items) {
		put(_Items, items.name().toLowerCase(Locale.ENGLISH));
		return this;
	}

	public SelectOptions selector(String selector) {
		put(Selector, selector);
		return this;
	}
}
