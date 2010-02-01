/*
 * Created by IntelliJ IDEA.
 * User: kinabalu
 * Date: Jan 31, 2010
 * Time: 8:56:25 PM
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
public class AudioDemo extends BasePage {

    public AudioDemo() {
        final List<MediaSource> mm = new ArrayList<MediaSource>();
        mm.add(new MediaSource("/loser.wav"));
        mm.add(new MediaSource("/loser.ogg"));

        IModel<List<MediaSource>> mediaSourceList = new AbstractReadOnlyModel<List<MediaSource>>() {
            public List<MediaSource> getObject() {
                return mm;
            }
        };

        add(new Html5Audio("loser", mediaSourceList) {

            @Override
            protected boolean isControls() {
                return true;
            }

        });
    }
}