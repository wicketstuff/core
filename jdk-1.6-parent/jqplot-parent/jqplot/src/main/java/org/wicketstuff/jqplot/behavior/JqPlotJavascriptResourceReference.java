package org.wicketstuff.jqplot.behavior;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class JqPlotJavascriptResourceReference extends JavaScriptResourceReference {

	private static final long serialVersionUID = 2838987626121073426L;

	private static final JqPlotJavascriptResourceReference INSTANCE = new JqPlotJavascriptResourceReference();

	public static JqPlotJavascriptResourceReference get()
	{
		return INSTANCE;
	}

	private JqPlotJavascriptResourceReference()
	{
		super(JqPlotJavascriptResourceReference.class, "jquery.jqplot.min.js");
	}

	@Override
	public Iterable<? extends HeaderItem> getDependencies()
	{
		List<HeaderItem> dependencies = new ArrayList<HeaderItem>();
		for (Iterator<? extends HeaderItem> iterator = super.getDependencies().iterator(); iterator.hasNext();)
		{
			HeaderItem headerItem = (HeaderItem)iterator.next();
			dependencies.add(headerItem);
		}
		dependencies.add(JavaScriptHeaderItem.forReference(Application.get()
			.getJavaScriptLibrarySettings()
			.getJQueryReference()));
		return dependencies;
	}

}
