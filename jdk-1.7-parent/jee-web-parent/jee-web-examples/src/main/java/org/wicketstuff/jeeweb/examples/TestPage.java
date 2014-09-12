package org.wicketstuff.jeeweb.examples;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.jeeweb.ajax.JEEWebGlobalAjaxEvent;

public class TestPage extends WebPage {

    private static final long serialVersionUID = 1457592847439107204L;
    private Label label;

    public TestPage() {
	setStatelessHint(false);
	label = new Label("labelToChange", "This Text will be changed");
	label.setOutputMarkupId(true);
	add(label);

    }

    @Override
    public void onEvent(IEvent<?> event) {
	JEEWebGlobalAjaxEvent castedEvent = JEEWebGlobalAjaxEvent
		.getCastedEvent(event);
	if (castedEvent != null) {
	    AjaxRequestTarget ajaxRequestTarget = castedEvent
		    .getAjaxRequestTarget();
	    label.setDefaultModelObject(castedEvent.getPageParameters()
		    .get("param").toString());
	    ajaxRequestTarget.add(label);
	}
    }
}
