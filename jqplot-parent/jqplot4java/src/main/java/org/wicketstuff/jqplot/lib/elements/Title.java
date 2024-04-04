/*
 *  Copyright 2011 Inaiat H. Moraes.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.wicketstuff.jqplot.lib.elements;

/**
 * Plot Title object.
 *
 * @author inaiat
 */
public class Title implements Element {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -277067293084389272L;
    
    /** The text. */
    private String text;
    
    /** The show. */
    private Boolean show;
    
    /** The font family. */
    private String fontFamily;
    
    /** The font size. */
    private String fontSize;

    /** The font weight. */
    private String fontWeight;

    /** The text align. */
    private String textAlign;
    
    /** The text color. */
    private String textColor;

    /** The renderer. */
    private String renderer;
    
    /** The renderer options. */
    private String rendererOptions;
    
    /** The escape html. */
    private String escapeHtml;

    /**
     * Instantiates a new title.
     */
    public Title() {
    }

    /**
     * Instantiates a new title.
     *
     * @param text the text
     */
    public Title(String text) {
        this.text = text;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the show.
     *
     * @param show the new show
	 * @return Title
     */
    public Title setShow(boolean show) {
        this.setShow((Boolean) show);
        return this;
    }

    /**
     * Gets the font family.
     *
     * @return the font family
     */
    public String getFontFamily() {
        return fontFamily;
    }

    /**
     * Sets the font family.
     *
     * @param fontFamily the new font family
	 * @return Title
     */
    public Title setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
        return this;
    }

    /**
     * Gets the font size.
     *
     * @return the fontSize
     */
    public String getFontSize() {
        return fontSize;
    }

    /**
     * Sets the font size.
     *
     * @param fontSize the fontSize to set
	 * @return Title
     */
    public Title setFontSize(String fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    /**
     * Gets the font weight.
     *
     * @return the fontWeight
     */
    public String getFontWeight() {
        return fontWeight;
    }

    /**
     * Sets the font weight.
     *
     * @param fontWeight the fontWeight to set
	 * @return Title
     */
    public Title setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
        return this;
    }

    /**
     * Gets the text align.
     *
     * @return the textAlign
     */
    public String getTextAlign() {
        return textAlign;
    }

    /**
     * Sets the text align.
     *
     * @param textAlign the textAlign to set
	 * @return Title
     */
    public Title setTextAlign(String textAlign) {
        this.textAlign = textAlign;
        return this;
    }

    /**
     * Gets the text color.
     *
     * @return the textColor
     */
    public String getTextColor() {
        return textColor;
    }

    /**
     * Sets the text color.
     *
     * @param textColor the textColor to set
	 * @return Title
     */
    public Title setTextColor(String textColor) {
        this.textColor = textColor;
        return this;
    }

    /**
     * Gets the renderer.
     *
     * @return the renderer
     */
    public String getRenderer() {
        return renderer;
    }

    /**
     * @param renderer the renderer to set
	 * @return Title
     */
    public Title setRenderer(String renderer) {
        this.renderer = renderer;
        return this;
    }

    /**
     * Gets the renderer options.
     *
     * @return the rendererOptions
     */
    public String getRendererOptions() {
        return rendererOptions;
    }

    /**
     * Sets the renderer options.
     *
     * @param rendererOptions the rendererOptions to set
	 * @return Title
     */
    public Title setRendererOptions(String rendererOptions) {
        this.rendererOptions = rendererOptions;
        return this;
    }

    /**
     * Gets the escape html.
     *
     * @return the escapeHtml
     */
    public String getEscapeHtml() {
        return escapeHtml;
    }

    /**
     * Sets the escape html.
     *
     * @param escapeHtml the escapeHtml to set
	 * @return Title
     */
    public Title setEscapeHtml(String escapeHtml) {
        this.escapeHtml = escapeHtml;
        return this;
    }

    /**
     * Gets the show.
     *
     * @return the show
     */
    public Boolean getShow() {
        return show;
    }

    /**
     * Sets the show.
     *
     * @param show the show to set
	 * @return Title
     */
    public Title setShow(Boolean show) {
        this.show = show;
        return this;
    }

    /**
     * Sets the text.
     *
     * @param text the text to set
	 * @return Title
     */
    public Title setText(String text) {
        this.text = text;
        return this;
    }
}
