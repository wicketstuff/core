package org.wicketstuff.foundation;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.foundation.button.ButtonColor;
import org.wicketstuff.foundation.button.ButtonRadius;
import org.wicketstuff.foundation.label.FoundationLabel;
import org.wicketstuff.foundation.label.LabelOptions;

public class LabelPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public LabelPage(PageParameters params) {
		super(params);
		add(new FoundationLabel("basic", "Regular Label"));
		add(new FoundationLabel("radiusSecondary", "Radius Secondary Label", new LabelOptions(ButtonColor.SECONDARY, ButtonRadius.RADIUS)));
		add(new FoundationLabel("roundAlert", "Round Alert Label", new LabelOptions(ButtonColor.ALERT, ButtonRadius.ROUND)));
		add(new FoundationLabel("success", "Success Label", new LabelOptions(ButtonColor.SUCCESS)));
	}
}
