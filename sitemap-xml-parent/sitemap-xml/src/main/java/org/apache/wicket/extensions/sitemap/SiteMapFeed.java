package org.apache.wicket.extensions.sitemap;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SiteMapFeed extends Observable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteMapFeed.class);
    private final IOffsetSiteMapEntryIterable.SiteMapIterable entries;
    private int entriesWritten;
    private static final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n";
    private static final SimpleDateFormat SITEMAP_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private int bytesWritten;
    private static final String FOOTER = "</urlset>";
    private static final int URL_MAX_LENGTH = 2048;

    public SiteMapFeed(final IOffsetSiteMapEntryIterable.SiteMapIterable entries) {
        this.entries = entries;
    }

    public void writeFeed(PrintWriter writer) throws IOException {
        IOffsetSiteMapEntryIterable.SiteMapIterator it = entries.iterator();
        try {
            entriesWritten = 0;
            bytesWritten = 0;
            writer.append(HEADER);
            bytesWritten += HEADER.length();
            while (it.hasNext()) {
                ISiteMapEntry entry = it.next();
                final String urlStr = entry.getUrl();
                if (urlStr != null) {
                    writeSingleEntry(writer, entry, urlStr);
                } else {
                    LOGGER.warn("url entry {} for sitemap {} was null, but this is a required attribute..", entry, RequestCycle.get().getRequest());
                }
            }
            writer.append(FOOTER);
            bytesWritten += FOOTER.length();
            setChanged();
            notifyObservers();
        } finally {
            it.close();
        }
    }

    private void writeSingleEntry(PrintWriter writer, ISiteMapEntry entry, String urlStr) throws IOException {
        StringBuilder sb = new StringBuilder();
        entriesWritten++;
        sb.append("<url>\n");
        sb.append("<loc>");
        sb.append(StringEscapeUtils.escapeXml(urlStr));
        if (urlStr.length() > URL_MAX_LENGTH) {
            LOGGER.warn("url {} was too long (>2048 bytes) in sitemap {}", urlStr, RequestCycle.get().getRequest());
        }
        sb.append("</loc>\n");
        final Date modified = entry.getModified();
        if (modified != null) {
            sb.append("<lastmod>");
            sb.append(SITEMAP_DATE_FORMAT.format(modified));
            sb.append("</lastmod>\n");
        }
        final ISiteMapEntry.CHANGEFREQ frequency = entry.getFrequency();
        if (frequency != null) {
            sb.append("<changefreq>");
            sb.append(frequency.toString().toLowerCase());
            sb.append("</changefreq>\n");
        }
        Double prio = entry.getPriority();
        if (prio != null) {
            prio = normalizePriority(entry, prio);
            sb.append("<priority>");
            sb.append(prio);
            sb.append("</priority>\n");
        }
        sb.append("</url>\n");
        bytesWritten += sb.length();
        writer.append(sb);
        if (writer.checkError()) {
            throw new IOException("remote side closed connection? stopping to generate sitemap.");
        }
        setChanged();
        notifyObservers();
    }

    private static Double normalizePriority(ISiteMapEntry entry, Double prio) {
        if (prio > 1.0) {
            LOGGER.warn("priority {} was out of bounds for entry {}, setting to 1.0", prio, entry);
            prio = 1.0;
        }
        if (prio < 0.0) {
            LOGGER.warn("priority {} was out of bounds for entry {}, setting to 0.0", prio, entry);
            prio = 0.0;
        }
        return prio;
    }

    public int getBytesWritten() {
        return bytesWritten;
    }

    public int getEntriesWritten() {
        return entriesWritten;
    }
}
