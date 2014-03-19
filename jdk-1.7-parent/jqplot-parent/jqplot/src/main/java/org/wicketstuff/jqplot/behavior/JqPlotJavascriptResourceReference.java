package org.wicketstuff.jqplot.behavior;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import java.util.ArrayList;
import java.util.List;

public class JqPlotJavascriptResourceReference extends JavaScriptResourceReference {

	private static final long serialVersionUID = 2838987626121073426L;

	private static final JqPlotJavascriptResourceReference INSTANCE = new JqPlotJavascriptResourceReference();

	public static JqPlotJavascriptResourceReference get()
	{
		return INSTANCE;
	}

	private JqPlotJavascriptResourceReference()
	{
		super(JqPlotJavascriptResourceReference.class, "jquery.jqplot.js");
	}

	@Override
	public List<HeaderItem> getDependencies()
	{
		List<HeaderItem> dependencies = new ArrayList<>();
        for (HeaderItem headerItem : super.getDependencies()) {
            dependencies.add(headerItem);
        }
		dependencies.add(JavaScriptHeaderItem.forReference(Application.get()
			.getJavaScriptLibrarySettings()
			.getJQueryReference()));
		return dependencies;
	}

}
