package org.wicketstuff.minis;

import org.apache.wicket.core.util.resource.PackageResourceStream;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContentDisposition;

public class JSPPage extends HomePage {

    private static final long serialVersionUID = -8924679448150238847L;

    public JSPPage(PageParameters parameters) {
	super(parameters);
	add(new Link<String>("download", Model.of("de.test.jspwicket.zip")) {

	    private static final long serialVersionUID = -7012340134908122342L;

	    @Override
	    public void onClick() {
		getRequestCycle().scheduleRequestHandlerAfterCurrent(
			new ResourceStreamRequestHandler(
				new PackageResourceStream(HomePage.class, this
					.getDefaultModelObjectAsString()))
				.setFileName(
					this.getDefaultModelObjectAsString())
				.setContentDisposition(
					ContentDisposition.ATTACHMENT));
	    }
	});
    }
}
