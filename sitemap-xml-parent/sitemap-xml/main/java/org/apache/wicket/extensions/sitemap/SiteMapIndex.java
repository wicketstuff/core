package org.apache.wicket.extensions.sitemap;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.request.target.coding.IRequestTargetUrlCodingStrategy;
import org.apache.wicket.request.target.component.BookmarkablePageRequestTarget;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;

public abstract class SiteMapIndex extends WebPage implements Observer {


    private static final String PARAM_SITEMAP_OFFSET = "offset";
    private static final String PARAM_SITEMAP_SOURCEINDEX = "sourceindex";

    private static final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<sitemapindex xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n";
    private static final String FOOTER = "</sitemapindex>";
    private static final int MAX_BYTES_SITEMAP = 10485760; //10 megabyte
    private static final int MAX_ENTRIES_PER_SITEMAP = 50000;
    private static final SimpleDateFormat STRIPPED_DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private String domain;

    public SiteMapIndex(final PageParameters parameters) {
        super(parameters);
        if (parameters.size() > 0) {
            final Integer index = Integer.valueOf(parameters.getString(PARAM_SITEMAP_OFFSET));
            final Integer sourceIndex = Integer.valueOf(parameters.getString(PARAM_SITEMAP_SOURCEINDEX));

            setResponsePage(new Sitemap() {
                @Override
                protected SiteMapFeed getFeed() {
                    final SiteMapFeed feed = new SiteMapFeed(new IOffsetSiteMapEntryIterable.SiteMapIterable() {
                        public IOffsetSiteMapEntryIterable.SiteMapIterator iterator() {
                            return getDataSources()[sourceIndex].getIterator(index);
                        }
                    });
                    feed.addObserver(SiteMapIndex.this);
                    return feed;
                }
            });
        }
    }

    public String getDomain() {
        if (domain == null) {
            final Request rawRequest = RequestCycle.get().getRequest();
            if (!(rawRequest instanceof WebRequest)) {
                throw new WicketRuntimeException("sitemap.xml generation is only possible for http requests");
            }
            WebRequest wr = (WebRequest) rawRequest;
            domain = "http://" + wr.getHttpServletRequest().getHeader("host");
        }
        return domain;
    }

    public void update(Observable o, Object arg) {
        //todo feedback loop to adjust block sizes
        if (o instanceof SiteMapFeed) {
            final SiteMapFeed siteMapFeed = (SiteMapFeed) o;
            if ((siteMapFeed.getEntriesWritten() > MAX_ENTRIES_PER_SITEMAP) ||
                    (siteMapFeed.getBytesWritten() > MAX_BYTES_SITEMAP)) {
                throw new IllegalStateException("please adjust block sizes for this sitemap.");
            }
        }
    }

    @Override
    public String getMarkupType() {
        return "text/xml";
    }

    public abstract IRequestTargetUrlCodingStrategy mountedAt();

    @Override
    protected void onRender(final MarkupStream markupStream) {
        PrintWriter w = new PrintWriter(getResponse().getOutputStream());
        try {
            w.write(HEADER);
            int sourceNumber = 0;
            for (IOffsetSiteMapEntryIterable dataBlock : getDataSources()) {
                for (int i = 0; i < dataBlock.getUpperLimitNumblocks(); i++) {
                    w.append("<sitemap>\n<loc>");
                    final PageParameters params = new PageParameters();
                    params.put(PARAM_SITEMAP_SOURCEINDEX, String.valueOf(sourceNumber));
                    params.put(PARAM_SITEMAP_OFFSET, String.valueOf(i * dataBlock.getElementsPerSiteMap()));
                    final String url = getDomain() + "/" + String.valueOf(mountedAt().encode(new BookmarkablePageRequestTarget(getClass(), params)));
                    w.append(StringEscapeUtils.escapeXml(url));
                    w.append("</loc>\n<lastmod>");
                    w.append(STRIPPED_DAY_FORMAT.format(dataBlock.changedDate()));
                    w.append("</lastmod>\n</sitemap>\n");
                }
                sourceNumber++;
            }
            w.write(FOOTER);
            w.flush();
        } finally {
            w.close();
        }
    }

    public abstract IOffsetSiteMapEntryIterable[] getDataSources();
}
