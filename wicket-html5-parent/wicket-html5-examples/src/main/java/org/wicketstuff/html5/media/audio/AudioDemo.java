/*
 * Created by IntelliJ IDEA.
 * User: kinabalu
 * Date: Jan 31, 2010
 * Time: 8:56:25 PM
 */
package org.wicketstuff.html5.media.audio;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.html5.BasePage;
import org.wicketstuff.html5.media.MediaSource;
import org.wicketstuff.html5.media.audio.Html5Audio;

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
			private static final long serialVersionUID = 1L;

			public List<MediaSource> getObject() {
                return mm;
            }
        };

        add(new Html5Audio("loser", mediaSourceList) {
			private static final long serialVersionUID = 1L;

			@Override
            protected boolean isControls() {
                return true;
            }

        });
    }
}