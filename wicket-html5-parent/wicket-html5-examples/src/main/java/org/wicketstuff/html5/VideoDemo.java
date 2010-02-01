/*
 * Created by IntelliJ IDEA.
 * User: kinabalu
 * Date: Jan 31, 2010
 * Time: 8:20:47 PM
 */
package org.wicketstuff.html5;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrew Lombardi
 */
public class VideoDemo extends BasePage {

    public VideoDemo() {

        final List<MediaSource> mm = new ArrayList<MediaSource>();
        mm.add(new MediaSource("/dizzy.mp4", "video/mp4"));
        mm.add(new MediaSource("/dizzy.ogv", "video/ogg"));

        IModel<List<MediaSource>> mediaSourceList = new AbstractReadOnlyModel<List<MediaSource>>() {
            public List<MediaSource> getObject() {
                return mm;
            }
        };

        add(new Html5Video("dizzy", mediaSourceList) {

            @Override
            protected boolean isControls() {
                return true;
            }

            @Override
            protected boolean isAutoPlay() {
                return true;
            }
        });
    }
}