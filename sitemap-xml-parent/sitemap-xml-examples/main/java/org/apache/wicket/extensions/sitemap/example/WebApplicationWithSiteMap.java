package org.apache.wicket.extensions.sitemap.example;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.target.coding.IRequestTargetUrlCodingStrategy;
import org.apache.wicket.request.target.coding.QueryStringUrlCodingStrategy;

public class WebApplicationWithSiteMap extends WebApplication {
    public Class<? extends Page> getHomePage() {
        return ExamplePage.class;
    }

    @Override
    protected void init() {
        super.init();
        mount(SITE_MAP_MOUNTED_AT);
        mount(EXAMPLE_PAGE_MOUNTED_AT);
    }

    public static final IRequestTargetUrlCodingStrategy SITE_MAP_MOUNTED_AT = new QueryStringUrlCodingStrategy("/sitemap.xml", ExampleSiteMap.class);
    public static final IRequestTargetUrlCodingStrategy EXAMPLE_PAGE_MOUNTED_AT = new QueryStringUrlCodingStrategy("/example", ExamplePage.class);
}
