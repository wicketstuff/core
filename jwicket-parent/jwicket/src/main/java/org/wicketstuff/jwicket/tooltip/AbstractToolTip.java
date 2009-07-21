package org.wicketstuff.jwicket.tooltip;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractHeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;


public abstract class AbstractToolTip extends AbstractHeaderContributor {

	private static final long serialVersionUID = 1L;

	IHeaderContributor[] headerContributor = new IHeaderContributor[1];
	List<Component> components = new ArrayList<Component>();
	Map<String, String> options = new HashMap<String, String>();

	String tooltipText;


	AbstractToolTip(final String tooltipText) {
		this.tooltipText = tooltipText.replace("</", "<\\/");
		headerContributor[0] = getHeadercontributor();
	}


	abstract IHeaderContributor getHeadercontributor();


	AbstractToolTip setTooltipText(final String htmlCode) {
		this.tooltipText = htmlCode.replace("</", "<\\/");
		return this;
	}


	@Override
	public IHeaderContributor[] getHeaderContributors() {
		return headerContributor;
	}


	@Override
	public void bind(final Component component) {
		if (component == null)
			throw new IllegalArgumentException("Argument component must be not null");

		components.add(component);
		component.setOutputMarkupId(true);
	}



	abstract String getJavaScript();

	public void update(final AjaxRequestTarget target) {
		target.appendJavascript(getJavaScript());
	}
}
