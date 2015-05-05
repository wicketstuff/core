package com.iluwatar;

import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.iluwatar.foundation.foundationpanel.FoundationPanelBorder;
import com.iluwatar.foundation.foundationpanel.PanelType;

public class PanelsPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public PanelsPage(PageParameters params) {
		super(params);
		add(new FoundationPanelBorder("regular", Model.of(PanelType.NORMAL)));
		add(new FoundationPanelBorder("callout", Model.of(PanelType.CALLOUT)));
	}

}
