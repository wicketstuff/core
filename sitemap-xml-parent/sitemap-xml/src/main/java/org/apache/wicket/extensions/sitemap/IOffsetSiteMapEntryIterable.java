package org.apache.wicket.extensions.sitemap;

import java.io.Closeable;
import java.util.Date;
import java.util.Iterator;

public interface IOffsetSiteMapEntryIterable {

    SiteMapIterator getIterator(int startIndex);

    int getUpperLimitNumblocks();

    int getElementsPerSiteMap();

    Date changedDate();

    interface SiteMapIterator extends Iterator<ISiteMapEntry>, Closeable {
    }

    interface SiteMapIterable extends Iterable<ISiteMapEntry> {
        SiteMapIterator iterator();
    }
}
