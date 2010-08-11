package org.wicketstuff.jquery.demo.lavalamp;

import java.util.List;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.jquery.demo.PageSupport;
import org.wicketstuff.jquery.lavalamp.LavaLampMenuPanel;
import org.wicketstuff.jquery.lavalamp.MenuItem;

public final class Page4LavaLamp extends PageSupport {
    public Page4LavaLamp() {
        super();
        IModel<List<MenuItem>> model = new AbstractReadOnlyModel<List<MenuItem>>() {

            private static final long serialVersionUID = 1L;

            @Override
            public List<MenuItem> getObject() {
                return Utils.newMenuList();
            }
        };

        add(new LavaLampMenuPanel("lavalampMenu", model) {
            private static final long serialVersionUID = 1L;

            @Override
            protected ResourceReference getCssResourceReference() {
                return new CompressedResourceReference(Page4LavaLamp.class, "lavalamp.css");
            }
        });
    }

}
