package org.wicketstuff.openlayers3.examples.base;

import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;
import org.apache.wicket.model.Model;
import org.wicketstuff.openlayers3.examples.HomePage;
import org.wicketstuff.openlayers3.examples.SimpleMap;

/**
 * Provides the navigation bar for the application.
 */
public class AppNavBar extends Navbar {

    public AppNavBar(final String id) {
        super(id);

        setBrandName(Model.of("Wicket Stuff OpenLayers 3"));
        addComponents(NavbarComponents.transform(Navbar.ComponentPosition.LEFT,
                new NavbarButton<Void>(HomePage.class, Model.of("Home"))
                        .setIconType(GlyphIconType.home),
                new NavbarButton<Void>(SimpleMap.class, Model.of("Map"))));
    }
}
