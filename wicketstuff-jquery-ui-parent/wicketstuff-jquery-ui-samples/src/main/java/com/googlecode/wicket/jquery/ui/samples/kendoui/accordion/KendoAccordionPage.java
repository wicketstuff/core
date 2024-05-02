package com.googlecode.wicket.jquery.ui.samples.kendoui.accordion;

import org.apache.wicket.markup.head.IHeaderResponse;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

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
