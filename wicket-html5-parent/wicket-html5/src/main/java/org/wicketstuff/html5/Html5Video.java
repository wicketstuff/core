/*
 * Created by IntelliJ IDEA.
 * User: kinabalu
 * Date: Jan 31, 2010
 * Time: 9:19:11 AM
 */
package org.wicketstuff.html5;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 *
 * @author Andrew Lombardi
 */
public class Html5Video extends Html5Media {


    // TODO add support for poster
    public Html5Video(String id, IModel<List<MediaSource>> model) {
        super(id, model);
    }

    /**
     * Width of the video (optional)
     *
     * @return
     */
    protected int getWidth() {
        return 0;
    }

    /**
     * Height of the video (optional)
     *
     * @return
     */
    protected int getHeight() {
        return 0;
    }

    /**
     * Processes the component tag.
     *
     * @param tag
     *            Tag to modify
     * @see org.apache.wicket.Component#onComponentTag(org.apache.wicket.markup.ComponentTag)
     */
    @Override
    protected void onComponentTag(final ComponentTag tag) {
        if(getWidth()>0) {
            tag.put("width", getWidth());
        }

        if(getHeight()>0) {
            tag.put("height", getHeight());
        }

        // Default handling for component tag
        super.onComponentTag(tag);
    }


    /**
     * video tag
     *
     * @return the tag name for this html5 media
     */
    protected String getTagName() {
        return "video";
    }


}