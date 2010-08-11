package org.wicketstuff.jquery.ajaxbackbutton;

import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.jquery.JQueryBehavior;

/**
 * @author martin-g
 */
public class HistoryIFramePage extends WebPage implements IHeaderContributor {

	public HistoryIFramePage() {
	}

	public void renderHead(IHeaderResponse response) {
		response.renderJavascriptReference(JQueryBehavior.JQUERY_JS);
	}
	
}
