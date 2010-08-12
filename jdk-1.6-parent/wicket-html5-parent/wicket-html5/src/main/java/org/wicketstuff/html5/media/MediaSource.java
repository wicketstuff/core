/*
 * Created by IntelliJ IDEA.
 * User: kinabalu
 * Date: Jan 31, 2010
 * Time: 1:46:11 PM
 */
package org.wicketstuff.html5.media;

import java.io.Serializable;

/**
 *
 * @author Andrew Lombardi
 */
public class MediaSource implements Serializable {

    private static final long serialVersionUID = 1L;

    private String src;
    private String type;
    private String media;

    private int height;
    private int width;

    public MediaSource(String src) {
        this.src = src;
    }

    public MediaSource(String src, String type) {
        this(src, type, null, 0, 0);
    }

    public MediaSource(String src, String type, String media) {
        this(src, type, media, 0, 0);
    }

    public MediaSource(String src, String type, String media, int width, int height) {
        this.src = src;
        this.type = type;
        this.media = media;
        this.width = width;
        this.height = height;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}