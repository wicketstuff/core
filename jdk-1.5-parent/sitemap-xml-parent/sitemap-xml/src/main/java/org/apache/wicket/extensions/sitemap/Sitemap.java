package org.apache.wicket.extensions.sitemap;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.MarkupType;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.http.WebRequest;

abstract class Sitemap extends WebPage {

    @Override
    public MarkupType getMarkupType() {
        return MarkupType.HTML_MARKUP_TYPE;
    }

    @Override
    protected void onRender() {
        PrintWriter writer = new PrintWriter(getResponse().getOutputStream());
        try {
            getFeed().writeFeed(writer);
            writer.flush();
        } catch (IOException e) {
            throw new WicketRuntimeException("unable to construct sitemap.xml for request: " + ((WebRequest) getRequest()).getHttpServletRequest().getRemoteAddr(), e);
        } finally {
            writer.close();
        }
    }

    protected abstract SiteMapFeed getFeed();

}


