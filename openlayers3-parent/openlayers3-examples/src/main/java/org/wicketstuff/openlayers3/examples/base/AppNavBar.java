package org.wicketstuff.openlayers3.examples.base;

import org.apache.wicket.model.Model;
import org.wicketstuff.openlayers3.examples.ClusterPage;
import org.wicketstuff.openlayers3.examples.HomePage;
import org.wicketstuff.openlayers3.examples.MarkerPage;
import org.wicketstuff.openlayers3.examples.ModifyPage;
import org.wicketstuff.openlayers3.examples.OsmPage;
import org.wicketstuff.openlayers3.examples.PopoverPage;
import org.wicketstuff.openlayers3.examples.SimplePage;
import org.wicketstuff.openlayers3.examples.WfsPage;

import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesome5IconType;

/**
 * Provides the navigation bar for the application.
 */
public class AppNavBar extends Navbar {

    /**
     * Creates a new instance.
     *
     * @param id Wicket element ID
     */
    public AppNavBar(final String id) {
        super(id);

        // navigation title
        setBrandName(Model.of("Wicket Stuff: OpenLayers 3"));

        // navigation items
        addComponents(NavbarComponents.transform(Navbar.ComponentPosition.LEFT,
                new NavbarButton<Void>(HomePage.class, Model.of("Home"))
                        .setIconType(FontAwesome5IconType.home_s),
                new NavbarButton<Void>(SimplePage.class, Model.of("Simple")),
                new NavbarButton<Void>(OsmPage.class, Model.of("OpenStreetMap")),
                new NavbarButton<Void>(MarkerPage.class, Model.of("Marker")),
                new NavbarButton<Void>(ModifyPage.class, Model.of("Modify")),
                new NavbarButton<Void>(PopoverPage.class, Model.of("Popover")),
                new NavbarButton<Void>(WfsPage.class, Model.of("WFS")),
                new NavbarButton<Void>(ClusterPage.class, Model.of("Cluster"))));
    }
}
