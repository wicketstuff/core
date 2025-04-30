/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.multiselect;

import java.util.Arrays;
import java.util.List;

import org.wicketstuff.jquery.ui.samples.KendoSamplePage;

abstract class AbstractMultiSelectPage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultMultiSelectPage.class, "MultiSelect"), // lf
				new DemoLink(LazyMultiSelectPage.class, "MultiSelect: lazy load"), // lf
				new DemoLink(RendererMultiSelectPage.class, "MultiSelect: lazy load & custom renderer"), // lf
				new DemoLink(AjaxMultiSelectPage.class, "AjaxMultiSelect: lazy load") // lf
		);
	}
}
