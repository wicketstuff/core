package org.wicketstuff.openlayers3.examples.base;

import de.agilecoders.wicket.core.markup.html.bootstrap.behavior.BootstrapBaseBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.html.ChromeFrameMetaTag;
import de.agilecoders.wicket.core.markup.html.bootstrap.html.HtmlTag;
import de.agilecoders.wicket.core.markup.html.bootstrap.html.MetaTag;
import de.agilecoders.wicket.core.markup.html.bootstrap.html.OptimizedMobileViewportMetaTag;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.resource.CssResourceReference;

import java.util.Locale;

/**
 * Provides a basic web page that other application pages may extend.
 */
public class BasePage extends WebPage {

    /**
     * Title of this page.
     */
    protected static final String RESKEY_PAGE_TITLE = "page.title";

    /**
     * Short title for this page.
     */
    protected static final String RESKEY_TITLE_SHORT = "page.title.short";

    /**
     * Description of this page.
     */
    protected static final String RESKEY_DESCRIPTION = "page.description";

    /**
     * Author of this page.
     */
    protected static final String RESKEY_AUTHOR = "page.author";

    /*
     * Component IDs.
     */
    private static final String CID_TITLE = "title";
    private static final String CID_PAGE_TITLE = "titleShort";
    private static final String CID_HEADER = "header";

    /*
     * Components.
     */
    private Component debugBar;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        // add HTML declaration
        add(new HtmlTag("html", Locale.ENGLISH));

        // add Bootstrap
        add(new BootstrapBaseBehavior());
        add(new OptimizedMobileViewportMetaTag("viewport"));
        add(new ChromeFrameMetaTag("chrome-frame"));
        add(new MetaTag("description", Model.of("description"), getPageDescriptionModel()));
        add(new MetaTag("author", Model.of("author"), getPageAuthorModel()));
        add(new Label(CID_TITLE, getPageTitleModel()));

        // add page components
        add(getHeaderNavBar(CID_HEADER).setVisibilityAllowed(isShowHeader()));
        add((new Label(CID_PAGE_TITLE, getPageShortTitleModel())).setVisibilityAllowed(isShowPageTitle()));
    }

    /**
     * Returns the page title model.
     *
     * @return model for the page title
     */
    protected IModel<String> getPageTitleModel() {
        return new ResourceModel(RESKEY_PAGE_TITLE);
    }

    /**
     * Returns the short page title model.
     *
     * @return model for the short page title
     */
    protected IModel<String> getPageShortTitleModel() {
        return new ResourceModel(RESKEY_TITLE_SHORT);
    }

    /**
     * Returns the page description model.
     *
     * @return model for the page description
     */
    protected IModel<String> getPageDescriptionModel() {
        return new StringResourceModel(RESKEY_DESCRIPTION, this, null);
    }

    /**
     * Returns the page author model.
     *
     * @return model for the page author
     */
    protected IModel<String> getPageAuthorModel() {
        return new StringResourceModel(RESKEY_AUTHOR, this, null);
    }

    /**
     * Returns the setTitleModel navigation bar for the page.
     *
     * @param cid Wicket component ID for the setTitleModel navigation bar
     * @return The navigation bar for the setTitleModel
     */
    protected Navbar getHeaderNavBar(final String cid) {
        return new AppNavBar(cid);
    }

    /**
     * Override to disable setTitleModel.
     *
     * @return show (true) or hide (false) setTitleModel
     */
    protected boolean isShowHeader() {
        return true;
    }

    /**
     * Override to hide the page title.
     *
     * @return show (true) or hide (false) the page title
     */
    protected boolean isShowPageTitle() {
        return true;
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        response.render(CssHeaderItem.forReference(new CssResourceReference(BasePage.class, "BasePage.css")));
    }
}
