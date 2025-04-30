/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.accordion;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.wicketstuff.jquery.core.resource.StyleSheetPackageHeaderItem;
import org.wicketstuff.kendo.ui.KendoUIBehavior;

public class KendoAccordionPage extends AbstractAccordionPage
{
	private static final long serialVersionUID = 1L;

	public KendoAccordionPage()
	{
		this.add(new KendoUIBehavior("#accordion", "kendoPanelBar"));
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(KendoAccordionPage.class));
	}

}
