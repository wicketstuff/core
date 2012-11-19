/*
 * This piece of code is dedicated to the wicket project (http://www.wicketframework.org).
 */
package org.wicketstuff.menu;


import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.jwicket.BgIframeBehavior;

import java.util.List;


/**
 * This is a internal class. It is responsible for rendering a visible {@link Menu}.
 *
 * @author Stefan Lindner (lindner@visionet.de)
 */
class MenuPanel extends Panel {

    private static final long serialVersionUID = 1L;
    private static final AttributeModifier disabledMenuItem = AttributeModifier.replace("class", "disabled");

    private List<IMenuLink> menuItems;

    private final WebMarkupContainer menu;

    protected MenuPanel(final String id, List<IMenuLink> menuItems) {
        super(id);
        if (menuItems == null) {
            throw new IllegalArgumentException("argument [menuItems] cannot be null");
        }
        /*
          if (menuItems.size() < 1) {
              throw new IllegalArgumentException(
                      "argument [menuItems] must contain a list of at least one menuItem");
          }
          */

        this.menuItems = menuItems;


        this.menu = new WebMarkupContainer("menu");
        this.menu.setOutputMarkupId(true);
        this.menu.add(new BgIframeBehavior());

        // add the loop used to generate menu items
        this.menu.add(new Loop("items", menuItems.size()) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(LoopItem item) {
                final int index = item.getIndex();
                final IMenuLink menuItem = MenuPanel.this.menuItems.get(index);
                final AbstractLink link;
                final Component linkLabel = menuItem.getDisplayComponent("menuLinkLabel");

                if (linkLabel instanceof Image) {
                    item.add(new WebMarkupContainer("menuLink1").setVisible(false));
                    link = menuItem.getLink("menuLink2");
                } else {
                    link = menuItem.getLink("menuLink1");
                    item.add(new WebMarkupContainer("menuLink2").setVisible(false));
                }
                link.setOutputMarkupId(true);
                linkLabel.setOutputMarkupId(true);
                link.add(linkLabel);

                item.add(link);


                if (!link.isEnabled()) {
                    item.add(disabledMenuItem);
                }


                if (!link.isVisible()) {
                    item.setVisible(false);
                }


                item.setOutputMarkupId(true);
            }

        });

        add(this.menu);
    }


    public Component getRedrawComponent() {
        return this.menu;
    }


    public void redraw(final AjaxRequestTarget target) {
        target.add(this.menu);
    }

}
