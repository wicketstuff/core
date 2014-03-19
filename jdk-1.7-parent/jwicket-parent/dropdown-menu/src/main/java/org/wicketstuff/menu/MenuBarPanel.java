/*
 * This piece of code is dedicated to the wicket project (http://www.wicketframework.org).
 */
package org.wicketstuff.menu;


import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.ClientInfo;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.protocol.http.ClientProperties;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.wicketstuff.jwicket.IStyleResolver;
import org.wicketstuff.jwicket.JQueryCssResourceReference;


/**
 * A {@code MenuBarPanel} consits of several {@link Menu}s where each {@link Menu} consist of {@link IMenuLink}s.
 * A {@code MenuBarPanel} can be constructed e.g. like this:
 * <pre>
 * List&lt;Menu&gt; menus = new ArrayList&lt;Menu&gt;();
 *
 * // The MenuItems for the first Menu
 * List&lt;MenuItem&gt; menu1Items = new ArrayList&lt;MenuItem&gt;();
 *
 * menu1Items.add(new MenuItem(new Model&lt;String&gt;("Visible menu item 1.1"), new LinkToPage(Page1.class)));
 * menu1Items.add(new MenuItem(new Model&lt;String&gt;("Visible but disabled menu item 1.2"), new LinkToPage(Page2.class)).setEnabled(false));
 * menu1Items.add(new MenuItem(new Model&lt;String&gt;("Invisible menu item 1.3"), new LinkToPage(Page3.class)).setVisible(false));
 * menu1Items.add(new MenuItem(new Model&lt;String&gt;("visible menu item 1.4"), new LinkToPage(Page4.class)));
 *
 * menus.add(new Menu(new Model&lt;String&gt;("First menu"), menu1Items));
 *
 * // The MenuItems for the second Menu
 * List&lt;MenuItem&gt; menu2Items = new ArrayList&lt;MenuItem&gt;();
 *
 * menu2Items.add(new MenuItem(new Model&lt;String&gt;("Visible menu item 2.1"), "http://www.wicketframework.org"));
 * menu2Items.add(new MenuItem(new Model&lt;String&gt;("Visible but disabled menu item 2.2"), new LinkToPage(Page1.class)).setEnabled(false));
 * menu2Items.add(new MenuItem(new Model&lt;String&gt;("Invisible menu item 2.3"), new LinkToPage(null, Page2.class)).setVisible(false));
 * menu2Items.add(new MenuItem(new Model&lt;String&gt;("visible menu item 2.4"), new LinkToPage(Page3.class)));
 *
 * menus.add(new Menu(new Model&lt;String&gt;("Second menu"), menu2Items));
 *
 * add(new MenuBarPanel("menuBar", menus);
 *
 * ...
 *
 * private class LinkToPage implements IPageLink {
 *
 * private Class&lt;? extends Page&gt; pageClass;
 *
 * public LinkToPage(Class&lt;? extends Page&gt; pageClass) {
 * this.pageClass = pageClass;
 * }
 *
 * public Page getPage() {
 * return pageClass.newInstance();
 * }
 * </pre>
 * The HTML markup for this example looks like this:
 * <pre>
 * &lt;div wicket:id="menuBar"&gt;[[The menu bar goes here]]&lt;/div&gt;
 * </pre>
 * You need the following css code to get this example to work with IE:
 * <pre>
 * .menubar h1 {
 * text-align: center;
 * padding: 0 0 0.25em 0;
 * margin: 0;
 * z-index: 999;
 * }
 *
 * .menubar ul {
 * list-style: none;
 * padding: 0;
 * margin: 0;
 * z-index: 999;
 * }
 *
 * #nav a {
 * font-weight: bold;
 * color: rgb(15,15,15);
 * text-align: left;
 * text-decoration: none;
 * text-align: left;
 * }
 *
 * #nav li li a {
 * font-weight: bold;
 * text-align: left;
 * display: block;
 * color: rgb(41,41,41);
 * padding : 0.2em 10px;
 * white-space : nowrap;
 * z-index: 999;
 * }
 *
 * #nav li li.disabled {
 * font-weight: bold;
 * text-align: left;
 * display: block;
 * padding : 0.2em 10px;
 * white-space : nowrap;
 * z-index: 999;
 * }
 *
 * #nav li li a:hover {
 * padding: 0.2em 5px;
 * border: 1px solid rgb(34,69,104);
 * border-width: 0 5px;
 * text-align: left;
 * }
 *
 * .menubar li {
 * float: left;
 * position: relative;
 * text-align: center;
 * cursor: default;
 * background-color: rgb(152,186,225);
 * border: 1px solid rgb(34,69,104);
 * border-width: 1px 0;
 * text-align: left;
 * z-index: 999;
 * }
 *
 * .menubar li#singleMenu {
 * padding-left: 1em;
 * padding-right: 1em;
 * border-left-width: 2px;
 * border-right-width: 2px;
 * margin-right: 2px;
 * white-space : nowrap;
 * }
 *
 * .menubar li#firstMenu {
 * padding-left: 1em;
 * padding-right: 1em;
 * border-left-width: 2px;
 * border-right-width: 2px;
 * margin-right: 2px;
 * white-space : nowrap;
 * }
 *
 * .menubar li#middleMenu {
 * padding-left: 1em;
 * padding-right: 1em;
 * border-left-width: 2px;
 * border-right-width: 2px;
 * margin-right: 2px;
 * white-space : nowrap;
 * }
 *
 * .menubar li#lastMenu {
 * padding-left: 1em;
 * padding-right: 1em;
 * border-left-width: 2px;
 * border-right-width: 2px;
 * white-space : nowrap;
 * }
 *
 * .menubar li ul {
 * display: none;
 * position: absolute;
 * top: 100%;
 * left: 0;
 * font-weight: normal;
 * background-color: rgb(152,186,225);
 * padding: 0.5em 0;
 * border-right: ridge 1px rgb(125,125,125);
 * border-left: ridge 1px rgb(125,125,125);
 * border-bottom: ridge 1px rgb(125,125,125);
 * text-align: left;
 * z-index:99999;
 * }
 *
 * .menubar li>ul {
 * top: auto;
 * left: auto;
 * }
 *
 * .menubar li li {
 * display: block;
 * float: none;
 * background-color: rgb(152,186,225);
 * border: 0;
 * color: rgb(41,41,41);
 * }
 *
 * .menubar li li.disabled {
 * display: block;
 * float: none;
 * background-color: rgb(152,186,225);
 * border: 0;
 * color: rgb(123,123,123);
 * }
 *
 * .menubar li:hover ul, li.over ul {
 * display: block;
 * }
 *
 * .menubar hr {
 * display: none;
 * }
 *
 * .menubar p {
 * clear: left;
 * background: url(images/remora.gif) center left no-repeat;
 * padding: 1em 1em 0 1em;
 * margin: 0;
 * }
 *
 * .menubar p.image {
 * float: right;
 * font-size: 0.8em;
 * text-align: center;
 * color: #7d6340;
 * padding: 1.25em 1.25em 0.25em 0.25em;
 * }
 *
 * .menubar  p.image img {
 * display: block;
 * border: 1px solid #7d6340;
 * }
 * </pre>
 *
 * @author Stefan Lindner (lindner@visionet.de)
 */
public class MenuBarPanel extends Panel implements IStyleResolver {

    private static final long serialVersionUID = 1L;

    public static JQueryCssResourceReference[] CSS = {new JQueryCssResourceReference(MenuBarPanel.class, "menu.css")};

    protected final List<Menu> menus;

    private static final AttributeModifier idSingleMenu = AttributeModifier.replace("class", "singleMenu");
    private static final AttributeModifier idFirstMenu = AttributeModifier.replace("class", "firstMenu");
    private static final AttributeModifier idMiddleMenu = AttributeModifier.replace("class", "middleMenu");
    private static final AttributeModifier idLastMenu = AttributeModifier.replace("class", "lastMenu");

    private final WebMarkupContainer menubar;

    private boolean browserDetected = false;
    private boolean browserIsIe56 = false;

    /**
     * Constructs a {@code MenuBarPanel}.
     *
     * @param id    See {@link org.apache.wicket.Component}
     * @param menus A {@code List} of the {@link Menu}s that belong to this {@code MenuBarPanel}.
     */
    public MenuBarPanel(final String id, final List<Menu> menus) {
        super(id);

        this.menus = menus;

        this.menubar = new WebMarkupContainer("menubar");
        this.menubar.setOutputMarkupId(true);
        add(this.menubar);
    }

    @Override
    protected void onBeforeRender() {
        /* IE5/6 can't do li:hover, so we need some javascript, see below */

        if (!this.browserDetected) {
            ClientInfo ci = WebSession.get().getClientInfo();
            if (ci instanceof WebClientInfo) {
                ClientProperties properties = ((WebClientInfo) ci).getProperties();
                if (properties.isBrowserInternetExplorer()) {
                    if (properties.getBrowserVersionMajor() < 7) {
                        this.browserIsIe56 = true;
                    }
                }
            } else {
                // perhaps it is IE, we don't know.
                this.browserIsIe56 = true;
            }

            this.browserDetected = true;
        }


        // add the loop used to generate each single menu
        this.menubar.addOrReplace(new Loop("menus", MenuBarPanel.this.menus.size()) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final LoopItem item) {
                final int index = item.getIndex();
                final Menu menu = MenuBarPanel.this.menus.get(index);

                if (menu.isVisible()) {
                    item.add(new Label("menuTitle", menu.getModel()).setRenderBodyOnly(true));

                    MenuPanel mp = new MenuPanel("menu", menu.getMenuItems());
                    item.add(mp);
                    mp.setRenderBodyOnly(true);
                    if (index == 0) {
                        if (MenuBarPanel.this.menus.size() == 1) {
                            // Only one menu
                            item.add(idSingleMenu);
                        } else {
                            // More than one menu, this is the first one
                            item.add(idFirstMenu);
                        }
                    } else if (index == MenuBarPanel.this.menus.size() - 1) {
                        // The last menu
                        item.add(idLastMenu);
                    } else {
                        item.add(idMiddleMenu);
                    }
                } else {
                    item.setVisible(false);
                }

//				if (browserIsIe56) {
                item.add(AttributeModifier.replace("onmouseover", "this.className+=' over';"));
                item.add(AttributeModifier.replace("onmouseout", "this.className=this.className.replace(' over','');"));
//				}

                item.setOutputMarkupId(true);
                menu.setAssociatedComponent(item);
            }
        });
        super.onBeforeRender();
    }


    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        JQueryCssResourceReference[] cssResources = getCssResources();
        if (cssResources != null) {
            for (JQueryCssResourceReference cssResource : cssResources) {
                response.render(CssHeaderItem.forReference(cssResource));
            }
        }
    }


    public void refresh(final AjaxRequestTarget target) {
        target.add(this.menubar);
    }

    public JQueryCssResourceReference[] getCssResources() {
        return CSS;
    }
}
