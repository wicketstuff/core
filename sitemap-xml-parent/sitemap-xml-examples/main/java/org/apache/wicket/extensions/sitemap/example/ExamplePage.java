package org.apache.wicket.extensions.sitemap.example;

import org.apache.wicket.PageParameters;
import org.apache.wicket.model.Model;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;

public class ExamplePage extends WebPage {

    protected ExamplePage(PageParameters parameters) {
        super(parameters);
        add(new Panel("number",new Model<String>(parameters.getString("number"))));
    }
}
