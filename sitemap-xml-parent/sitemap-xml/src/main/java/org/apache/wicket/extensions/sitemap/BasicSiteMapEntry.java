package org.apache.wicket.extensions.sitemap;

import java.util.Date;

public class BasicSiteMapEntry implements ISiteMapEntry {

    private final String url;
    private final Date modified;
    private final double priority;
    private final CHANGEFREQ frequency;
    private static final double DEFAULT_PRIORITY = 0.5;

    public BasicSiteMapEntry(String url) {
        this.url = url;
        modified = new Date();
        priority = DEFAULT_PRIORITY;
        frequency = CHANGEFREQ.WEEKLY;
    }

    public BasicSiteMapEntry(String url, Date modified, double priority, CHANGEFREQ frequency) {
        this.url = url;
        this.modified = modified;
        this.frequency = frequency;
        if (priority > 1.0) {
            priority = 1.0;
        }
        if (priority < 0.0) {
            priority = 0.0;
        }
        this.priority = priority;
    }

    public String getUrl() {
        return url;
    }

    public Date getModified() {
        return modified;
    }

    public Double getPriority() {
        return priority;
    }

    public CHANGEFREQ getFrequency() {
        return frequency;
    }

}
