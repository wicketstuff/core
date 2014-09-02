package org.wicketstuff.minis.resolver.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tag Support to generate a wicket url within a jsp.
 * 
 * @author Tobias Soloschenko
 */
public class WicketJSPURL extends TagSupport {

    private static final long serialVersionUID = 6146639184284158443L;

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(WicketJSPURL.class);

    private String page = null;

    private String query = null;

    @SuppressWarnings("unchecked")
    @Override
    public int doStartTag() throws JspException {
	try {
	    JspWriter out = pageContext.getOut();
	    PageParameters pageParameters = new PageParameters();
	    if (query != null) {
		RequestUtils.decodeParameters(query, pageParameters);
	    }
	    CharSequence urlFor = RequestCycle.get().urlFor(
		    (Class<? extends WebPage>) Class.forName(page),
		    pageParameters);
	    out.write(urlFor.toString());
	    out.flush();
	} catch (IOException e) {
	    LOGGER.error("Error while generating url for page " + page, e);
	} catch (ClassNotFoundException e) {
	    LOGGER.error(
		    "Error while receiving the class with the name" + page, e);
	}
	return SKIP_BODY;
    }

    public String getPage() {
	return page;
    }

    public void setPage(String page) {
	this.page = page;
    }

    public String getQuery() {
	return query;
    }

    public void setQuery(String query) {
	this.query = query;
    }

}
