package org.wicketstuff.foundation;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.foundation.button.ButtonColor;
import org.wicketstuff.foundation.button.ButtonRadius;
import org.wicketstuff.foundation.progressbar.FoundationProgressBar;
import org.wicketstuff.foundation.progressbar.ProgressBarOptions;

public class ProgressBarPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public ProgressBarPage(PageParameters params) {
		super(params);
		add(new FoundationProgressBar("basic", 100));
		ProgressBarOptions options = new ProgressBarOptions(ButtonColor.SUCCESS).setRadius(ButtonRadius.ROUND);
		add(new FoundationProgressBar("advanced", options, 80));
	}
}
