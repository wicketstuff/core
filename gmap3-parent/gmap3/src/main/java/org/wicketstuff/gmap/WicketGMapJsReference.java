package org.wicketstuff.gmap;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * A JavaScript reference for wicket-gmap.js
 */
public class WicketGMapJsReference extends JavaScriptResourceReference {

  private static final long serialVersionUID = 1L;
  public static final WicketGMapJsReference INSTANCE = new WicketGMapJsReference();

	/**
	 * Constructor.
	 */
	private WicketGMapJsReference()
	{
		super(WicketGMapJsReference.class, "wicket-gmap.js");
	}

	@Override
	public List<HeaderItem> getDependencies()
	{
		List<HeaderItem> dependencies = new ArrayList<>();
		Iterable<? extends HeaderItem> superDeps = super.getDependencies();
		for (HeaderItem dep : superDeps) {
			dependencies.add(dep);
		}
		dependencies.add(JavaScriptHeaderItem.forReference(
				Application.get().getJavaScriptLibrarySettings().getWicketAjaxReference()));
		return dependencies;
	}
}
