package org.apache.wicket.extensions.sitemap.example;


import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.sitemap.BasicSiteMapEntry;
import org.apache.wicket.extensions.sitemap.IOffsetSiteMapEntryIterable;
import org.apache.wicket.extensions.sitemap.ISiteMapEntry;
import org.apache.wicket.extensions.sitemap.SiteMapIndex;
import org.apache.wicket.request.target.coding.IRequestTargetUrlCodingStrategy;
import org.apache.wicket.request.target.component.BookmarkablePageRequestTarget;

import java.util.Date;

@SuppressWarnings({"NonSerializableFieldInSerializableClass"})
public class ExampleSiteMap extends SiteMapIndex {

    private static final int ELEMENTS_PER_BLOCK = 1000;

    Session sess;

    public ExampleSiteMap(final PageParameters parameters) {
        super(parameters);
    }


    @Override
    public IOffsetSiteMapEntryIterable[] getDataSources() {
        return new IOffsetSiteMapEntryIterable[]{new IOffsetSiteMapEntryIterable() {
            public SiteMapIterator getIterator(final int startIndex) {

                //todo begin db transaction

                return new SiteMapIterator() {
                    int numcalled;

                    public boolean hasNext() {
                        return numcalled <= ExampleSiteMap.ELEMENTS_PER_BLOCK;
                    }

                    public ISiteMapEntry next() {
                        PageParameters pageParameters = new PageParameters();
                        pageParameters.put("number", numcalled + startIndex);
                        numcalled++;
                        final CharSequence url = WebApplicationWithSiteMap.EXAMPLE_PAGE_MOUNTED_AT.encode(new BookmarkablePageRequestTarget(ExamplePage.class, pageParameters));
                        return new BasicSiteMapEntry(fullUrlFrom(url));
                    }

                    public void remove() {
                        throw new UnsupportedOperationException("not possible here..");
                    }

                    public void close() {
                        //todo end db transaction
                        //todo close iterator if instanceof HibernateIterator
                    }
                };
            }

            public int getUpperLimitNumblocks() {
                //todo count number of elements from db
                return (int) Math.ceil(10000 / ExampleSiteMap.ELEMENTS_PER_BLOCK);
            }

            public int getElementsPerSiteMap() {
                return ExampleSiteMap.ELEMENTS_PER_BLOCK;
            }

            public Date changedDate() {
                return new Date(); //todo query db for last change date
            }
        }};
    }

    @Override
    public IRequestTargetUrlCodingStrategy mountedAt() {
        return WebApplicationWithSiteMap.SITE_MAP_MOUNTED_AT;
    }

    private String fullUrlFrom(CharSequence charSequence) {
        return getDomain() + "/" + charSequence.toString();
    }
}
