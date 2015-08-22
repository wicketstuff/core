package org.wicketstuff.foundation;

import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.foundation.alert.AlertColor;
import org.wicketstuff.foundation.alert.AlertOptions;
import org.wicketstuff.foundation.alert.FoundationAlert;
import org.wicketstuff.foundation.button.ButtonRadius;

public class AlertsPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public AlertsPage(PageParameters params) {
		super(params);
		add(new FoundationAlert("basic", Model.of("This is standard alert.")));
		
		add(new FoundationAlert("successRadius", Model.of("This is a success alert with a radius. "),
				new AlertOptions(AlertColor.SUCCESS, ButtonRadius.RADIUS)));
		add(new FoundationAlert("warningRound", Model.of("This is a warning alert that is rounded. "),
				new AlertOptions(AlertColor.WARNING, ButtonRadius.ROUND)));
		add(new FoundationAlert("infoRadius", Model.of("This is an info alert with a radius. "),
				new AlertOptions(AlertColor.INFO, ButtonRadius.RADIUS)));
		add(new FoundationAlert("alertRound", Model.of("This is an alert - alert that is rounded. "),
				new AlertOptions(AlertColor.ALERT, ButtonRadius.ROUND)));
		add(new FoundationAlert("secondary", Model.of("This is a secondary alert. "),
				new AlertOptions(AlertColor.SECONDARY)));
	}
}
