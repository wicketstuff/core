package org.apache.wicket.extensions.sitemap;

import java.util.Date;

public interface ISiteMapEntry {

    /**
     * required value. may not be null.
     *
     * @return URL of the page.
     *         This URL must begin with the protocol (such as http)
     *         and end with a trailing slash, if your web server requires it.
     *         This value must be less than 2,048 characters
     */
    String getUrl();

    /**
     * @return The date of last modification of the file.
     */
    Date getModified();

    /**
     * @return The priority of this URL relative to other URLs on your site. Valid values range from 0.0 to 1.0.
     *         The default priority of a page is 0.5.
     *         <p/>
     *         Also, please note that assigning a high priority to all of the URLs on your site is not likely to help you.
     *         Since the priority is relative, it is only used to select between URLs on your site.
     */
    Double getPriority();

    /**
     * @return How frequently the page is likely to change.
     */
    CHANGEFREQ getFrequency();

    enum CHANGEFREQ {
        ALWAYS, HOURLY, DAILY, WEEKLY, MONTHLY, YEARLY, NEVER
    }
}
