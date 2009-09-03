package org.wicketstuff.jquery.demo.lavalamp;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.model.Model;
import org.wicketstuff.jquery.lavalamp.MenuItem;

final class Utils {

    private Utils() {
        throw new IllegalStateException();
    }

    public static List<MenuItem> newMenuList() {
        final List<MenuItem> result = new ArrayList<MenuItem>();
        result.add(new MenuItem(new Link<Object>(MenuItem.LINK_ID) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {

            }

        }, new Label(MenuItem.CAPTION_ID, "One")));

        final BookmarkablePageLink<Object> linkToAPage = new BookmarkablePageLink<Object>(
                MenuItem.LINK_ID, APage.class);
        result.add(new MenuItem(linkToAPage, new Label(MenuItem.CAPTION_ID, "APage")));

        final BookmarkablePageLink<Object> linkToAPageWithPopupSettings = new BookmarkablePageLink<Object>(
                MenuItem.LINK_ID, APage.class);
        linkToAPageWithPopupSettings.setPopupSettings(new PopupSettings());
        result.add(new MenuItem(linkToAPageWithPopupSettings, new Label(MenuItem.CAPTION_ID,
                "APage-popup")));

        result.add(new MenuItem(new Link<Object>(MenuItem.LINK_ID) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {

            }

        }, new Label(MenuItem.CAPTION_ID, "Four")));
        return result;
    }

    public static List<MenuItem> newMenuListWithLinkStyles() {
        final List<MenuItem> result = new ArrayList<MenuItem>();
        Link<Object> link1 = new Link<Object>(MenuItem.LINK_ID) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {

            }

        };
        link1.add(new AttributeAppender("class", true, new Model<String>("link1"), " "));
        Label label1 = new Label(MenuItem.CAPTION_ID, "One");
        label1.setVisible(false);

        result.add(new MenuItem(link1, label1));

        AjaxLink<Object> link2 = new AjaxLink<Object>(MenuItem.LINK_ID) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {

            }

            @Override
            protected IAjaxCallDecorator getAjaxCallDecorator() {
                return new AjaxCallDecorator() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public CharSequence decorateOnSuccessScript(CharSequence script) {
                        return "alert('Success');";
                    }

                    @Override
                    public CharSequence decorateOnFailureScript(CharSequence script) {
                        return "alert('Failure');";
                    }

                    @Override
                    public CharSequence decorateScript(CharSequence script) {
                        return "alert('Before ajax call');" + script;
                    }
                };
            }

        };
        link2.add(new AttributeAppender("class", true, new Model<String>("link2"), " "));
        Label label2 = new Label(MenuItem.CAPTION_ID, "One");
        label2.setVisible(false);

        result.add(new MenuItem(link2, label2));

        Link<Object> link3 = new Link<Object>(MenuItem.LINK_ID) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
            }

        };
        link3.add(new AttributeAppender("class", true, new Model<String>("link3"), " "));
        Label label3 = new Label(MenuItem.CAPTION_ID, "One");
        label3.setVisible(false);

        result.add(new MenuItem(link3, label3));

        Link<Object> link4 = new Link<Object>(MenuItem.LINK_ID) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {

            }

        };
        link4.add(new AttributeAppender("class", true, new Model<String>("link4"), " "));
        Label label4 = new Label(MenuItem.CAPTION_ID, "One");
        label4.setVisible(false);

        result.add(new MenuItem(link4, label4));

        return result;
    }

}
