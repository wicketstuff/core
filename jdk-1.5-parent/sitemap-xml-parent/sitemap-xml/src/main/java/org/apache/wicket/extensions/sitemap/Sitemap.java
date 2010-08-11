package org.apache.wicket.extensions.sitemap;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebRequest;

import java.io.IOException;
import java.io.PrintWriter;

abstract class Sitemap extends WebPage {

    @Override
    public String getMarkupType() {
        return "text/xml";
    }

    @Override
    protected void onRender(final MarkupStream markupStream) {
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


