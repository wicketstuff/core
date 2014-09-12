package org.wicketstuff.jeeweb.jsf;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

import org.apache.wicket.Page;
import org.apache.wicket.core.util.lang.WicketObjects;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Same as JspUrlTagSupport but for JSF
 * 
 * @see org.wicketstuff.jeeweb.jsp.JspUrlTagSupport
 * 
 * @author Tobias Soloschenko
 */
public final class JsfUrlTagHandler extends TagHandler {

    private String page;

    private String query;

    /**
     * Creates the tag by reading the config which contains the page and the
     * query as param
     * 
     * @param config
     *            the tag config to read the params
     */
    public JsfUrlTagHandler(TagConfig config) {
	super(config);
	TagAttribute page = getAttribute("page");
	if (page != null) {
	    this.page = page.getValue();
	}
	TagAttribute query = getAttribute("query");
	if (query != null) {
	    this.query = query.getValue();
	}
    }

    /**
     * Applies the url of wicket to the tag
     */
    @Override
    public void apply(FaceletContext context, UIComponent parent)
	    throws IOException {

	PageParameters pageParameters = new PageParameters();
	if (query != null) {
	    RequestUtils.decodeParameters(query, pageParameters);
	}
	Class<Page> resolveClass = WicketObjects.resolveClass(page);
	RequestCycle requestCycle = RequestCycle.get();
	if (requestCycle != null) {
	    final CharSequence urlFor = requestCycle.urlFor(resolveClass,
		    pageParameters);

	    UIComponentBase c = new UIComponentBase() {
		public void encodeEnd(FacesContext ctx) throws IOException {
		    ResponseWriter w = ctx.getResponseWriter();
		    w.write(urlFor.toString());
		}

		// abstract method in base, must override
		public String getFamily() {
		    return "wicket.jsf.taglib";
		}
	    };
	    parent.getChildren().add(c);
	}
    }
}
